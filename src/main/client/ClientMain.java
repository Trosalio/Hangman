package main.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.client.controllers.ClientController;
import main.client.models.ClientConnection;

import java.io.IOException;

/**
 * Project Name: Hangman
 */

public class ClientMain extends Application {

    private ClientConnection clientConnection;

    public ClientMain(){
        clientConnection = new ClientConnection("127.0.0.1", 1337);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(new Scene(createContent()));
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.setTitle("Hangman: Client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Parent createContent() throws IOException {
        Parent root = null;
        final String FXML_URL = "/main/zresources/fxml/client/ClientMainUI.fxml";
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource(FXML_URL));
            root = mainUILoader.load();
            ClientController clientController = mainUILoader.getController();
            clientController.setConnection(clientConnection);
            clientController.setUpContent();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file "+ FXML_URL +"cannot be loaded");
        }
        return root;
    }
}
