package main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.server.controllers.MainController;
import main.server.models.ServerConnection;
import main.server.models.persistents.DBManager;
import main.server.models.persistents.SQLiteConnector;

import java.io.IOException;
import java.io.Serializable;

/**
 * Project Name: Hangman
 */

public class ServerMain extends TemplateMain {

    private ServerConnection serverConnection;

    public static void main(String[] args) {
        launch(args);
    }

    public ServerMain(){
        serverConnection = createServer();
        connection = serverConnection;
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        primaryStage.setTitle("Hangman: Server");
    }

    @Override
    protected Parent createContent() throws IOException {
        Parent root = null;
        final String FXML_URL = "/main/resources/server/MainUILoader.fxml";
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource(FXML_URL));
            root = mainUILoader.load();
            MainController mainController = mainUILoader.getController();
            SQLiteConnector sqliteConnector = new SQLiteConnector();
            DBManager dbManager = new DBManager(sqliteConnector);
            serverConnection.setDBManager(dbManager);
            mainController.setConnection(serverConnection);
            mainController.setUpContent();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file " + FXML_URL + "cannot be loaded");
        }
        return root;
    }

    private ServerConnection createServer() {
        return new ServerConnection(80085, (Serializable data) -> {
            Platform.runLater(() -> {
                System.out.println("Server said: Hi");
            });
        });
    }
}
