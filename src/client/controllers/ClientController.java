package client.controllers;

import client.models.ClientManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
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
    private Button tryAgainBtn, quitBtn;
    @FXML
    private Circle head;
    @FXML
    private Line body, leftarm, rightarm, leftleg, rightleg;
    @FXML
    private Label gameOverLebel, winLabel;

    private ClientManager clientManager;
    private Button[] alphabetsButtons;
    private Shape[] hangee;
    private int guessingChance;

    @FXML
    public void initialize() {
        alphabetsButtons = new Button[alphabetGrid.getRowConstraints().size() * alphabetGrid.getColumnConstraints().size()];
        hangee = new Shape[]{rightleg, leftleg, rightarm, leftarm, body, head};
        guessingChance = hangee.length;
    }

    @FXML
    private void onNewWordRequest() {
        if(clientManager.requestNewWord()){
            restartGame();
        }
    }

    @FXML
    private void onQuit() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onTryAgain() {
        clientManager.retryWord();
        restartGame();
    }

    private void onAlphabetButtonPressed(Button button) {
        System.out.println(button.getText());
        String guessingAlphabet = button.getText();
        button.setDisable(true);
        if (clientManager.guessResult(guessingAlphabet)) {
            if (clientManager.isAllMatch()){
                alphabetGrid.setDisable(true);
                winLabel.setVisible(true);
                tryAgainBtn.setDisable(true);
            }
        } else {
            if (guessingChance > 0) {
                guessingChance--;
                hangee[guessingChance].setVisible(true);
            }
            if (guessingChance <= 0) {
                alphabetGrid.setDisable(true);
                gameOverLebel.setVisible(true);
            }
        }
    }

    public void setUpContent() {
        for (int i = 0; i < alphabetsButtons.length; i++) {
            String alphabet = String.valueOf((char) ('A' + i));
            Button alphabetButton = new Button(alphabet);
            alphabetButton.setOnAction(e -> onAlphabetButtonPressed(alphabetButton));
            alphabetButton.setFocusTraversable(false);
            alphabetsButtons[i] = alphabetButton;
            alphabetGrid.add(alphabetsButtons[i], i % alphabetGrid.getColumnConstraints().size(), i / alphabetGrid.getColumnConstraints().size());
        }
        alphabetGrid.setDisable(true);
    }

    public void setManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        clientManager.attachWordHBox(wordHBox);
    }

    private void enableAllAlphabetButtons() {
        for (Button button : alphabetsButtons) {
            button.setDisable(false);
        }
        alphabetGrid.setDisable(false);
    }

    private void hideHangee() {
        for (Shape bodyPart : hangee) {
            bodyPart.setVisible(false);
        }
        guessingChance = hangee.length;
    }

    private void restartGame() {
        enableAllAlphabetButtons();
        hideHangee();
        winLabel.setVisible(false);
        gameOverLebel.setVisible(false);
        tryAgainBtn.setDisable(false);
    }
}
