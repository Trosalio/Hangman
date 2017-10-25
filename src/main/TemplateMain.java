package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Project Name: Hangman
 */

public abstract class TemplateMain extends Application {

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
}
