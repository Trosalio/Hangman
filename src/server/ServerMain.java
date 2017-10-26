package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.controllers.ServerController;
import server.models.SQLiteConnector;
import server.models.ServerConnection;
import server.models.ServerManager;

import java.io.IOException;

/**
 * Project Name: Hangman
 */

public class ServerMain extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        final String FXML_URL = "/server/fxml/ServerMainUI.fxml";
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource(FXML_URL));
            Parent root = mainUILoader.load();
            ServerManager serverManager = new ServerManager();
            serverManager.setServerConnection(new ServerConnection(1337));
            serverManager.setSQLiteConnector(new SQLiteConnector());
            ServerController serverController = mainUILoader.getController();
            serverController.setServerManager(serverManager);
            serverController.setUpContent();

            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setOnHidden(e -> {
                serverManager.disconnect();
                Platform.exit();
            });
            primaryStage.setTitle("Hangman: Server");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file " + FXML_URL + "cannot be loaded");
        }
    }
}
