package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.server.controllers.MainController;
import main.server.models.ServerManager;
import main.server.models.persistents.DBManager;
import main.server.models.persistents.SQLiteConnector;

import java.io.IOException;

/**
 * Project Name: Hangman
 */

public class ServerMain extends TemplateMain{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        primaryStage.setTitle("Hangman: Server");
    }

    @Override
    protected Parent createContent() throws IOException {
        Parent root = null;
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource("MainUILoader.fxml"));
            root = mainUILoader.load();
            MainController mainController = mainUILoader.getController();
            SQLiteConnector sqliteConnector = new SQLiteConnector();
            DBManager dbManager = new DBManager(sqliteConnector);
            mainController.setManager(new ServerManager(dbManager));
            mainController.setUpContent();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file cannot be loaded");
        }
        return root;
    }
}
