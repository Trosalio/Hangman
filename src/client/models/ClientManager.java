package client.models;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Project Name: Hangman
 */
public class ClientManager {

    private ClientConnection clientConnection;
    private StringBuilder answerWord, originalGuessingWord, currentGuessingWord;
    private HBox wordHBox;
    Label[] charactersLabel;

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
        currentGuessingWord = originalGuessingWord;
        updateWordInHBox(currentGuessingWord);
    }

    public boolean requestNewWord() {
        String[] receivedWords = clientConnection.requestNewWordFromServer();
        if (clientConnection.isConnected()) {
            answerWord = new StringBuilder(receivedWords[0]);
            originalGuessingWord = new StringBuilder(receivedWords[1]);
            currentGuessingWord = originalGuessingWord;
            createWordInHBox(currentGuessingWord);
        }
        return clientConnection.isConnected();
    }

    private void createWordInHBox(StringBuilder word) {
        System.out.println(word);
        for (int i = 0; i < word.length(); i++) {
            charactersLabel = new Label[word.length()];
            charactersLabel[i] = new Label(String.valueOf(word.charAt(i)));
            charactersLabel[i].setFont(new Font(36));
        }
    }

    private void updateWordInHBox(StringBuilder word) {
        for (int i = 0; i < word.length(); i++) {
            charactersLabel[i].setText(String.valueOf(word.charAt(i)));
        }
    }

    public boolean isAllMatch() {
        return currentGuessingWord.equals(answerWord);
    }

    public void attachWordHBox(HBox hbox) {
        wordHBox = hbox;
    }
}
