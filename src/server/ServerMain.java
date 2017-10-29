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
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ServerMain extends Application {

    /**
     * This method will call launch(args) which then call start(primaryStage)
     *
     * @param args
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * This method will import .fxml, initialize ServerManager, ServerConnection, SQLiteConnector, and display primary stage
     *
     * @param primaryStage a main stage/window for this program.
     */
    @Override
    public void start(Stage primaryStage) {

        final String FXML_URL = "/server/fxml/ServerUI.fxml";
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
