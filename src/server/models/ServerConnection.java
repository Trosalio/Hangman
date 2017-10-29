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
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ServerConnection {

    private ServerSocket serverSocket;
    private boolean connectionStatus;
    private TextArea logTextArea;
    private int port;
    private PrintWriter serverOutput;
    private Random random = new Random();
    private ArrayList<String> words;

    /**
     * Construct and assign port number to the object.
     * @param port A number in which server and client will use to communicate with each other
     */
    public ServerConnection(int port) {
        this.port = port;
    }

    public void establishConnection() {
        createServer();
        new Thread(new ServerRunnable()).start();
    }

    /**
     * This method will get called by establishConnection().
     * When called, this method will create a server socket on a given port
     * The error will be shown if the port is not available to use.
     */
    private void createServer() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will forcefully close the server socket when server needs to go offline.
     */
    public void disconnect() {
        try {
            connectionStatus = false;
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will generate a random index within the list's size and will return a String from that index.
     * @return A random String from words::ArrayList<String>
     */
    private String sendRandomWord() {
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    /**
     * This method will truncate the word and return an array of String which contains truncated word and its hint alphabet.
     * @param word A word to be truncated
     * @return An array of String consists of (1)Truncated word and (2) Hint alphabet
     */
    private String[] truncateWord(String word) {
        String[] strArr = new String[2];
        int hintAlphabetPosition = random.nextInt(word.length());
        char hintAlphabet = word.charAt(hintAlphabetPosition);
        String s = String.join("", Collections.nCopies(word.length(), "_"));
        StringBuilder truncatedWord = new StringBuilder(s);
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == hintAlphabet) {
                truncatedWord.replace(i, i + 1, String.valueOf(hintAlphabet));
            }
        }
        strArr[0] = truncatedWord.toString();
        strArr[1] = String.valueOf(hintAlphabet);
        return strArr;
    }

    /**
     * This is a setter method that receives a Log::TextArea from the caller (In this case is ServerManager).
     * @param logTextArea A text area received from the caller
     */
    public void receiveLogControl(TextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    /**
     * This is a setter method that receives a words::ArrayList<String> from the caller (In this case is ServerManager).
     * @param words An array list received from the caller
     */
    public void receiveWordArray(ArrayList<String> words) {
        this.words = words;
    }

    /**
     * A private class that act as a thread runner for this class
     */
    private class ServerRunnable implements Runnable {

        /**'
         * This method is a thread that will get called and run every time the server is established.
         * Update a log in the process.
         */
        @Override
        public void run() {
            try {
                connectionStatus = true;
                while (connectionStatus) {
                    listenForServerRequest();
                }
                logTextArea.appendText("Close Connection\n");
            } catch (IOException e) {
                System.err.println("Could not create listening socket / No connection from client");
            }
        }

        /**
         * This method will listen a message sent from Client and handle the message accordingly.
         * Update a log in the process.
         * @throws IOException If error occurs, throw it to the caller(which is run() in this case)
         */
        private void listenForServerRequest() throws IOException {
            logTextArea.appendText("Waiting for Connection on port " + serverSocket.getLocalPort() + "...\n");
            Socket socket = serverSocket.accept();
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOutput = new PrintWriter(socket.getOutputStream());
            logTextArea.appendText("Connection received from: " + socket.getRemoteSocketAddress() + "\n");

            String request = clientInput.readLine();
            if (request != null) {
                considerRequest(request);
            }
            logTextArea.appendText("Close Connection\n\n");
        }


        /**
         * If the sent message is "Request new word", server will generate and sent out a word which is random from Words.
         * Update a log in the process.
         * @param request A requested string, a message, that is received from Client
         */
        private void considerRequest(String request) {
            if (request.equals("Request new word")) {
                logTextArea.appendText("Client has requested a new word\n");
                logTextArea.appendText("Generating a word...\n");
                String word = sendRandomWord();
                String[] strArr = truncateWord(word);
                String truncatedWord = strArr[0];
                String hintAlphabet = strArr[1];
                logTextArea.appendText(String.format("Sending Word '%s' as '%s' to client\n", word, truncatedWord));
                serverOutput.println(word);
                serverOutput.println(truncatedWord);
                serverOutput.println(hintAlphabet);
                serverOutput.flush();
            }
        }
    }
}
