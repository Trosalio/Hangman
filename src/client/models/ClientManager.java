package client.models;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ClientManager {

    private ClientConnection clientConnection;
    private String answerWord, originalGuessingWord, hintAlphabet;
    private StringBuilder currentGuessingWord;
    private HBox wordHBox = new HBox();
    private Label[] charactersLabel;

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public boolean guessResult(String guessingAlphabet) {
        boolean result = false;
        char guessingChr = guessingAlphabet.charAt(0);
        for (int i = 0; i < answerWord.length(); i++) {
            if (answerWord.charAt(i) == guessingChr) {
                currentGuessingWord.replace(i, i + 1, guessingAlphabet);
                result = true;
            }
        }
        updateWordInHBox(currentGuessingWord);
        return result;
    }

    public void retryWord() {
        currentGuessingWord = new StringBuilder(originalGuessingWord);
        updateWordInHBox(currentGuessingWord);
        wordHBox.getChildren().addAll(charactersLabel);
    }

    public boolean requestNewWord() {
        String[] receivedWords = clientConnection.requestNewWordFromServer();
        if (clientConnection.isConnected()) {
            answerWord = receivedWords[0];
            originalGuessingWord = receivedWords[1];
            hintAlphabet = receivedWords[2];
            currentGuessingWord = new StringBuilder(originalGuessingWord);
            createWordInHBox(currentGuessingWord);
        }
        return clientConnection.isConnected();
    }

    private void createWordInHBox(StringBuilder word) {
        charactersLabel = new Label[word.length()];
        for (int i = 0; i < word.length(); i++) {
            charactersLabel[i] = new Label(String.valueOf(word.charAt(i)));
            charactersLabel[i].setFont(new Font(36));
        }
        wordHBox.getChildren().addAll(charactersLabel);
    }

    private void updateWordInHBox(StringBuilder word) {
        for (int i = 0; i < word.length(); i++) {
            charactersLabel[i].setText(String.valueOf(word.charAt(i)));
        }
    }

    public boolean isAllMatch() {
        return currentGuessingWord.toString().equals(answerWord);
    }

    public void attachWordHBox(HBox hbox) {
        wordHBox = hbox;
    }

    public String getHintAlphabet() {
        return hintAlphabet;
    }
}
