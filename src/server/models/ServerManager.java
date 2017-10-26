package server.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Random;

/**
 * Project Name: Hangman
 */
public class ServerManager {

    private SQLiteConnector sqLiteConnector;
    private ServerConnection serverConnection;
    private Random random = new Random();
    private ArrayList<Word> words = new ArrayList<>();
    private final ObservableList<Word> obsList = FXCollections.observableList(words);
    private final ObjectProperty<Word> currentWord = new SimpleObjectProperty<>(null);

    public String sendRandomWord() {
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex).getWord();
    }

    public void insertWord(String word) {
        Word.setPrimaryKeyID(Word.getPrimaryKeyID() + 1);
        Word newWord = new Word(word, Word.getPrimaryKeyID());
        obsList.add(newWord);
        if (sqLiteConnector != null) sqLiteConnector.insertWordToDB(newWord);
    }

    public Word removeWord(int removedIndex) {
        Word word = obsList.remove(removedIndex);
        if (sqLiteConnector != null) sqLiteConnector.removeWordFromDB(word.getID());
        return word;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void setSQLiteConnector(SQLiteConnector sqLiteConnector) {
        this.sqLiteConnector = sqLiteConnector;
        this.sqLiteConnector.loadWordsFromDB(words);
    }

    public void establishConnection() {
        serverConnection.establishConnection();
    }

    public void disconnect() {
        serverConnection.disconnect();
    }

    public ObservableList<Word> getObsList() {
        return obsList;
    }

    public void setCurrentWord(Word currentWord) {
        this.currentWord.set(currentWord);
    }

    public void receiveLogControl(TextArea logTextArea) {
        serverConnection.receiveLogControl(logTextArea);
    }
}
