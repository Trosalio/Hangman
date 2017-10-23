package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.models.NetworkConnection;

import java.io.IOException;

/**
 * Project Name: Hangman
 */

public abstract class TemplateMain extends Application {

    protected NetworkConnection connection;
    protected abstract Parent createContent() throws IOException;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(new Scene(createContent()));
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.setTitle("Hangman: <Template>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {
        if (connection != null) connection.openConnection();
    }

    @Override
    public void stop() throws Exception {
        if (connection != null) connection.closeConnection();
    }
}
