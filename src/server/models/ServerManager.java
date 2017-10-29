package server.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

/**
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ServerManager {

    private SQLiteConnector sqLiteConnector;
    private ServerConnection serverConnection;
    private ArrayList<String> words = new ArrayList<>();
    private final ObservableList<String> obsList = FXCollections.observableList(words);
    private final SimpleStringProperty currentWord = new SimpleStringProperty(null);

    /**
     * This method will check whether newWord::String is already existed in words::ArrayList<String>
     *
     * @param newWord an issued word
     * @return returns True if it does not exist, returns False otherwise
     */
    public boolean isNotExisted(String newWord) {
        for (String word : words) {
            if (newWord.equals(word)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will add word into words::ArrayList<String>.
     * If sqLiteConnector is connected, pass the word to sqLiteConnector's method.
     *
     * @param word a word to be inserted into words::ArrayList<String>
     */
    public void insertWord(String word) {
        obsList.add(word);
        if (sqLiteConnector != null) sqLiteConnector.insertWordToDB(word);
    }

    /**
     * This method will remove word from words::ArrayList<String>.
     * If sqLiteConnector is connected, pass the word to sqLiteConnector's method.
     *
     * @param removedIndex an index to be removed from words::ArrayList<String>
     * @return removed word
     */
    public String removeWord(int removedIndex) {
        String word = obsList.remove(removedIndex);
        if (sqLiteConnector != null) sqLiteConnector.removeWordFromDB(word);
        return word;
    }

    /**
     * This method will set a serverConnection to ServerManager and will also sent words::ArrayList<String> to it.
     *
     * @param serverConnection a server connection
     */
    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
        this.serverConnection.receiveWordArray(words);
    }

    /**
     * This method will set sqLiteConnector to ServerManager and will also start to pull a data from database.
     *
     * @param sqLiteConnector a database connector
     */
    public void setSQLiteConnector(SQLiteConnector sqLiteConnector) {
        this.sqLiteConnector = sqLiteConnector;
        this.sqLiteConnector.loadWordsFromDB(words);
    }

    /**
     * This method will call serverConnection to establish a connection.
     */
    public void establishConnection() {
        serverConnection.establishConnection();
    }

    /**
     * This method will call serverConnection to disconnect the server socket.
     */
    public void disconnect() {
        serverConnection.disconnect();
    }

    /**
     * This method will return an ObsList.
     *
     * @return an ObserverbleList to be get
     */
    public ObservableList<String> getObsList() {
        return obsList;
    }

    /**
     * This method will set the currentWord to the given one.
     *
     * @param currentWord a word to be set
     */
    public void setCurrentWord(String currentWord) {
        this.currentWord.set(currentWord);
    }

    /**
     * Passing log::TextArea to serverConnection.
     *
     * @param logTextArea Log that will be sent to serverConnection
     */
    public void passLogControl(TextArea logTextArea) {
        serverConnection.receiveLogControl(logTextArea);
    }
}
