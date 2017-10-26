package server.models;

import javafx.beans.property.SimpleStringProperty;
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
    private ArrayList<String> words = new ArrayList<>();
    private final ObservableList<String> obsList = FXCollections.observableList(words);
    private final SimpleStringProperty currentWord = new SimpleStringProperty(null);

    public String sendRandomWord() {
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    public boolean isNotExisted(String newWord){
        for(String word : words){
            if(newWord.equals(word)){
                return false;
            }
        }
        return true;
    }

    public void insertWord(String word) {
        obsList.add(word);
        if (sqLiteConnector != null) sqLiteConnector.insertWordToDB(word);
    }

    public String removeWord(int removedIndex) {
        String word = obsList.remove(removedIndex);
        if (sqLiteConnector != null) sqLiteConnector.removeWordFromDB(word);
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

    public ObservableList<String> getObsList() {
        return obsList;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord.set(currentWord);
    }

    public void receiveLogControl(TextArea logTextArea) {
        serverConnection.receiveLogControl(logTextArea);
    }
}
