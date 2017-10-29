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
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ClientMain extends Application {

    /**
     * This method will call launch(args) which then call start(primaryStage)
     *
     * @param args an argument received when calls a program
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method will import .fxml, initialize ClientConnection, ClientController, ClientManager and display primary stage
     * IP is "127.0.0.1"(localhost) & Port number is 1337
     * @param primaryStage a main stage/window for this program.
     */
    @Override
    public void start(Stage primaryStage) {
        final String FXML_URL = "/client/ClientUI.fxml";
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
