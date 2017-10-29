package client.models;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
     * If connected, request a new word and receive an array of answers from server and set 'connected' to True.
     * If not, however, display a "Connect Find Server" to user and set 'connected' to False
     * @return An array of answers contain answer word, guessing word and hint alphabet
     */
    public String[] requestNewWordFromServer() {
        String[] words = new String[3];
        try (Socket clientSocket = new Socket(ip, port);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream())) {
            clientOutput.println("Request new word");
            clientOutput.flush();
            words[0] = serverInput.readLine(); // full word
            words[1] = serverInput.readLine(); // truncated word
            words[2] = serverInput.readLine(); // hint alphabet
            connected = true;
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
     * Create an error alert box and display it the user, block any action until get acknowledged.
     */
    private void displayCannotFindServer() {
        String content = String.format("Cannot find port: %d On this IP: %s%n", port, ip);
        content += "Suggestion: Please try to open Server.jar and establish a connection first";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Port not found");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
