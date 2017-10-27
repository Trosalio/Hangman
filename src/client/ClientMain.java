package client;

import client.controllers.ClientController;
import client.models.ClientConnection;
import client.models.ClientManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Project Name: Hangman
 */

public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final String FXML_URL = "/client/ClientMainUI.fxml";
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource(FXML_URL));
            Parent root = mainUILoader.load();
            ClientController clientController = mainUILoader.getController();
            ClientManager clientManager = new ClientManager();
            clientManager.setClientConnection(new ClientConnection("127.0.0.1", 1337));
            clientController.setManager(clientManager);
            clientController.setUpContent();

            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setOnHidden(e -> Platform.exit());
            primaryStage.setTitle("Hangman: Client");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file " + FXML_URL + "cannot be loaded");
        }
    }
}
