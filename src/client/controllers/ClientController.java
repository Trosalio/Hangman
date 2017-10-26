package client.controllers;

import client.models.ClientConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * Project Name: HangmanClient
 */

public class ClientController {

    @FXML
    private GridPane alphabetGrid;
    @FXML
    private HBox wordHBox;
    @FXML
    private Button newWordBtn, tryAgainBtn, quitBtn;
    @FXML
    private Circle head;
    @FXML
    private Line body, leftarm, rightarm, leftleg, rightleg;
    @FXML
    private Label gameOverLebel, winLabel;

    private ClientConnection clientConnection;

    @FXML
    private void onNewWordRequest() {

    }

    @FXML
    private void onQuit() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onTryAgain() {

    }

    public void setUpContent() {
    }

    public void setConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

}
