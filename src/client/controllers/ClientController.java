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
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ClientController {

    @FXML
    private GridPane alphabetGrid;
    @FXML
    private HBox wordHBox;
    @FXML
    private Button newWordBtn, quitBtn, tryAgainBtn;
    @FXML
    private Circle head;
    @FXML
    private Line body, leftarm, rightarm, leftleg, rightleg;
    @FXML
    private Label gameOverLebel, winLabel;

    private ClientManager clientManager;
    private Button[] alphabetButtons;
    private Shape[] hangee;
    private int guessingChance;

    /**
     * Initialize buttons and list.
     */
    @FXML
    public void initialize() {
        alphabetButtons = new Button[alphabetGrid.getRowConstraints().size() * alphabetGrid.getColumnConstraints().size()];
        hangee = new Shape[]{rightleg, leftleg, rightarm, leftarm, body, head};
        guessingChance = hangee.length;
    }

    /**
     * When newWordBtn is pressed,  wipes labels in HBox, calls clientManager to request a new word from server, and enables buttons.
     */
    @FXML
    private void onNewWordRequest() {
        wordHBox.getChildren().clear();
        if (clientManager.requestNewWord()) {
            enableAnyAlphabetButtonButOne(clientManager.getHintAlphabet());
            restartGame();
        }
    }

    /**
     * When quitBtn is pressed, close and exit the program.
     */
    @FXML
    private void onQuit() {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * When tryAgainBtn is pressed, wipes labels in HBox, calls clientManager to retry a game, and enables buttons.
     */
    @FXML
    private void onTryAgain() {
        wordHBox.getChildren().clear();
        clientManager.retryWord();
        enableAnyAlphabetButtonButOne(clientManager.getHintAlphabet());
        restartGame();
    }

    /**
     * When a button in alphabetButtons array is pressed, asks clientManager to guess the result according to the alphabet user has pressed,
     * and disable that button.
     * @param button A button that is pressed
     */
    private void onAlphabetButtonPressed(Button button) {
        System.out.println(button.getText());
        String guessingAlphabet = button.getText();
        button.setDisable(true);
        if (clientManager.guessResult(guessingAlphabet)) {
            if (clientManager.isAllMatched()) {
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

    /**
     * Setup a content in .fxml
     */
    public void setUpContent() {
        for (int i = 0; i < alphabetButtons.length; i++) {
            String alphabet = String.valueOf((char) ('A' + i));
            Button alphabetButton = new Button(alphabet);
            alphabetButton.setOnAction(e -> onAlphabetButtonPressed(alphabetButton));
            alphabetButton.setFocusTraversable(false);
            alphabetButtons[i] = alphabetButton;
            alphabetGrid.add(alphabetButtons[i], i % alphabetGrid.getColumnConstraints().size(), i / alphabetGrid.getColumnConstraints().size());
        }
        alphabetGrid.setDisable(true);
    }

    /**
     * A setter method.
     * Set a client manager and attach a HBox to it.
     * @param clientManager A setter
     */
    public void setManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        clientManager.attachWordHBox(wordHBox);
    }

    /**
     * This method will set button which contains the hint alphabet will be disabled
     * @param hintAlphabet A String that button which contains the hint alphabet will be disabled
     */
    private void enableAnyAlphabetButtonButOne(String hintAlphabet) {
        int hintIndex = -1;
        for (int i = 0; i < alphabetButtons.length; i++) {
            if (alphabetButtons[i].getText().equals(hintAlphabet)) hintIndex = i;
            alphabetButtons[i].setDisable(false);
        }
        alphabetButtons[hintIndex].setDisable(true);
        alphabetGrid.setDisable(false);
    }

    /**
     * When called, hides shapes which is hangee
     */
    private void hideHangee() {
        for (Shape bodyPart : hangee) {
            bodyPart.setVisible(false);
        }
        guessingChance = hangee.length;
    }

    /**
     * When called, calls hide hangee and enable tryAgainBtn
     * and hide winLabel and gameOverLabel
     */
    private void restartGame() {
        hideHangee();
        tryAgainBtn.setDisable(false);
        winLabel.setVisible(false);
        gameOverLebel.setVisible(false);
    }
}
