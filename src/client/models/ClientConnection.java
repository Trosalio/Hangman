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

    public ClientConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

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

    public boolean isConnected() {
        return connected;
    }

    private void displayCannotFindServer() {
        String s = String.format("Cannot find port: %d On this IP: %s%n", port, ip);
        s += "Suggestion: Please try to open Server.jar and establish a connection first";
        showAlertBox(s);
    }


    private void showAlertBox(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Port not found");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
