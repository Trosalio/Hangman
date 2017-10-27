package server.models;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Project Name: HangmanServer
 */

public class ServerConnection {

    private ServerSocket serverSocket;
    private Socket socket;
    private boolean connectionStatus;
    private TextArea logTextArea;
    private int port;
    private BufferedReader clientInput;
    private PrintWriter serverOutput;
    private Random random = new Random();
    private ArrayList<String> words;

    public ServerConnection(int port) {
        this.port = port;
    }

    public void establishConnection() {
        createServer(port);
        new Thread(new ServerRunnable()).start();
    }

    private void createServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No free port available");
        }
    }

    public void disconnect() {
        try {
            connectionStatus = false;
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveLogControl(TextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    public String sendRandomWord() {
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    public void receiveWordArray(ArrayList<String> words) {
        this.words = words;
    }

    private class ServerRunnable implements Runnable {

        @Override
        public void run() {
            try {
                connectionStatus = true;
                while (connectionStatus) {
                    listenForServerRequest();
                }
                logTextArea.appendText("Close Connection\n");
            } catch (IOException e)
            {
                System.err.println("Could not create listening socket / No connection from client");
            }
        }

        private void listenForServerRequest() throws IOException {
            logTextArea.appendText("Waiting for Connection on port " + serverSocket.getLocalPort() + "...\n");
            socket = serverSocket.accept();
            clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOutput = new PrintWriter(socket.getOutputStream());
            logTextArea.appendText("Connection received from: " + socket.getRemoteSocketAddress() + "\n");

            String request = clientInput.readLine();
            if (request != null) {
                considerRequest(request);
            }
            logTextArea.appendText("Close Connection\n");
        }

        private void considerRequest(String request) {
            if (request.equals("Request new word")) {
                logTextArea.appendText("Client has requested a new word");
                logTextArea.appendText("Generating a word...\n");
                String word = sendRandomWord();
                String truncatedWord = truncateWord(word);
                logTextArea.appendText(String.format("Sending Word: %s as '%s' to client\n", word, truncatedWord));
                serverOutput.println(word);
                serverOutput.println(truncatedWord);
                serverOutput.flush();
            } else if (request.equals("Disconnect")) {
                logTextArea.appendText("Client has disconnected from server\n");
                logTextArea.appendText("Closing the current socket...\n");
                disconnect();
            }
        }

        private String truncateWord(String word) {
            int initAlphabetPosition = random.nextInt(word.length());
            char initAlphabet = word.charAt(initAlphabetPosition);
            String s = String.join("", Collections.nCopies(word.length(), "_"));
            StringBuilder truncatedWord = new StringBuilder(s);
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == initAlphabet) {
                    truncatedWord.replace(i, i + 1, String.valueOf(initAlphabet));
                }
            }
            return truncatedWord.toString();
        }
    }
}
