package client.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import share.ErrorAlertBox;
import share.STATUS_CODE;

/**
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ClientConnection {

    private String ip;
    private int port;
    private boolean connected = false;

    /**
     * Construct and assign an IP and Port number to the class.
     * @param ip String of IP
     * @param port A Port number
     */
    public ClientConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * This method will try to create a socket to connect to the server.
     * If connected, send a status code "REQUEST_NEW_WORD" to server, when receive a status code, do the following:
     *  (1) if status code is "RESPOND_NEW_WORD", receive an array of answers from server and set 'connected' to True.
     *  (2) if status code is "RESPOND_NO_WORD", displayNoWord() notifies user that word cannot be found from server.
     * If not, however, display a "Connect Find Server" to user and set 'connected' to False
     * @return An array of answers contain answer word, guessing word and hint alphabet
     */
    public String[] requestNewWordFromServer() {
        String[] words = new String[3];
        try (Socket clientSocket = new Socket(ip, port);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream())) {
            clientOutput.println(STATUS_CODE.REQUEST_NEW_WORD.toString());
            clientOutput.flush();
            String respondCode = serverInput.readLine();
            if(respondCode.equals(STATUS_CODE.RESPOND_NEW_WORD.toString())){
                words[0] = serverInput.readLine(); // full word
                words[1] = serverInput.readLine(); // truncated word
                words[2] = serverInput.readLine(); // hint alphabet
                connected = true;
            } else if (respondCode.equals(STATUS_CODE.RESPOND_NO_WORD.toString())){
                displayNoWord();
            }
        } catch (IOException e) {
            //e.printStackTrace();
            displayCannotFindServer();
            connected = false;
        }
        return words;
    }

    /**
     * Getting method.
     * @return True if a connection is connected, False otherwise
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * State that there's no word in server to user, call displayAlertBox to show an alert box.
     */
    private void displayNoWord() {
        String content = "There's no word in the server\n";
        content += "Suggestion: Contact to the server user";
        ErrorAlertBox.showAlertBox("No word found", content);
    }

    /**
     * State that it cannot find the server to user, call displayAlertBox to show an alert box.
     */
    private void displayCannotFindServer() {
        String content = String.format("Cannot find port: %d On this IP: %s%n", port, ip);
        content += "Suggestion: Please try to open Server.jar and establish a connection first";
        ErrorAlertBox.showAlertBox("Port not found", content);
    }
}
