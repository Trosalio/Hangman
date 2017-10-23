package main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.client.controllers.MainController;
import main.client.models.ClientConnection;

import java.io.IOException;
import java.io.Serializable;

/**
 * Project Name: Hangman
 */

public class ClientMain extends TemplateMain {

    private ClientConnection clientConnection;
    public static void main(String[] args) {
        launch(args);
    }

    public ClientMain(){
        clientConnection = createClient();
        connection = clientConnection;
    }
    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        primaryStage.setTitle("Hangman: Client");
    }

    @Override
    protected Parent createContent() throws IOException {
        Parent root = null;
        final String FXML_URL = "/main/resources/client/MainUILoader.fxml";
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource(FXML_URL));
            root = mainUILoader.load();
            MainController mainController = mainUILoader.getController();
            mainController.setConnection(clientConnection);
            mainController.setUpContent();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file "+ FXML_URL +"cannot be loaded");
        }
        return root;
    }

    private ClientConnection createClient(){
        return new ClientConnection("localhost", 80085, (Serializable data) -> {
            Platform.runLater(() -> {
                System.out.println("Client said Hi!");
            });
        });
    }
}
