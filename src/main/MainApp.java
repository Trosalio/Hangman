package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Project Name: Hangman
 */

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final String FXML_URL = "/main/zresources/fxml/main/MainAppUI.fxml";
        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(FXML_URL))));
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.setTitle("Hangman: Activator");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML file "+ FXML_URL +"cannot be loaded");
        }

    }
}