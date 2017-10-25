package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Project Name: Hangman
 */

public class MainController {

    @FXML
    private Button actvServerBtn;

    @FXML
    private Button actvClientBtn;

    @FXML
    private Label clientStatusLbl;

    @FXML
    private Label serverStatusLbl;

    public void activateServer() {
        actvServerBtn.setDisable(true);
        serverStatusLbl.setText("Actived");
        serverStatusLbl.setTextFill(Color.GREEN);

    }

    public void activateClient() {
        actvClientBtn.setDisable(true);
        clientStatusLbl.setText("Actived");
        clientStatusLbl.setTextFill(Color.GREEN);
    }
}
