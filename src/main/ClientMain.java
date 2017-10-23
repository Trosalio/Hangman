package main;/**
 * Project Name: Hangman
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.client.controllers.MainController;
import main.client.models.ClientManger;

import java.io.IOException;

public class ClientMain extends TemplateMain {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        primaryStage.setTitle("Hangman: Client");
    }

    @Override
    protected Parent createContent() throws IOException {
        Parent root = null;
        try {
            FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource("MainUILoader.fxml"));
            root = mainUILoader.load();
            MainController mainController = mainUILoader.getController();
            mainController.setManager(new ClientManger());
            mainController.setUpContent();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file cannot be loaded");
        }
        return root;
    }
}
