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

    /**
     * This is a setter method that will inject ClientConnection to this class
     * @param clientConnection a setter
     */
    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    /**
     * This method will receive a guessing alphabet that will be checked if it matches any character in answer
     * @param guessingAlphabet A String which will test if it is matched or not
     * @return True if guessing alphabet matches with any index in answer::String, False otherwise
     */
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

    /**
     * When called, get the original word and have it passed and updated to HBox
     */
    public void retryWord() {
        currentGuessingWord = new StringBuilder(originalGuessingWord);
        updateWordInHBox(currentGuessingWord);
        wordHBox.getChildren().addAll(charactersLabel);
    }

    /**
     * Ask clientConnection to request a new word from Server.
     * If Client can make a connection to Server, get the answer, the guessing word and the hint from the server,
     * and then create/draw a word in HBox.
     * @return True if connected, False otherwise
     */
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

    /**
     * Create and add labels in HBox according to the given word
     * @param word A set of character which will be created in each label
     */
    private void createWordInHBox(StringBuilder word) {
        charactersLabel = new Label[word.length()];
        for (int i = 0; i < word.length(); i++) {
            charactersLabel[i] = new Label(String.valueOf(word.charAt(i)));
            charactersLabel[i].setFont(new Font(36));
        }
        wordHBox.getChildren().addAll(charactersLabel);
    }

    /**
     * Update labels in HBox according to the given word
     * @param word word A set of character which will be updated in label
     */
    private void updateWordInHBox(StringBuilder word) {
        for (int i = 0; i < word.length(); i++) {
            charactersLabel[i].setText(String.valueOf(word.charAt(i)));
        }
    }

    /**
     * This method will check if all the character in currentGuessingWord::String is equal to answerWord::String
     * @return True if all is matched, False otherwise
     */
    public boolean isAllMatched() {
        return currentGuessingWord.toString().equals(answerWord);
    }

    /**
     * Attach a HBox to this class to use
     * @param hbox A HBox attached from Client Controller
     */
    public void attachWordHBox(HBox hbox) {
        wordHBox = hbox;
    }

    /**
     *
     * @return A string which is the current hint alphabet
     */
    public String getHintAlphabet() {
        return hintAlphabet;
    }
}
