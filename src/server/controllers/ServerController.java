package server.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import server.models.ServerManager;

/**
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class ServerController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab serverLogTab;

    @FXML
    private Button addWordBtn, clearLogBtn, deleteWordBtn, establishConnBtn;

    @FXML
    private TextArea logTextArea;

    @FXML
    private TableView<String> wordListTable;

    @FXML
    private TableColumn<String, String> wordColumn;

    @FXML
    private TextField addWordTextF;

    private boolean isEstablished;
    private ServerManager serverManager;

    /**
     * When addWordBtn pressed, add a word input from addWordTextF::TextField into words:ArrayList<String> In server manager,
     * and in database if available, in certain conditions:
     * (1) Word must contains only character from A-Z, regardless whether it is a uppercase or lowercase.
     * ->(2) Word's length must be less than or equal to 8 characters, for the sake of difficulty being solvable.
     * ->(3) Word must not appear/exist in a words::ArrayList.
     * If all condition are not met, display an error according to its error.
     */
    @FXML
    private void onAddWord() {
        String word = addWordTextF.getText().toUpperCase();
        if (word.matches("[A-Z]+")) {
            if (serverManager.isNotExisted(word)) {
                if (word.length() <= 8) {
                    if (deleteWordBtn.isDisable()) deleteWordBtn.setDisable(false);
                    logTextArea.appendText("Word: " + word + " added!\n");
                    serverManager.insertWord(word);
                } else {
                    String content = "This word is too long, which will make it difficult to guess\n" +
                            "Suggestion: word should not contain more than 8 letters";
                    showAlertBox("Too long", content);
                }
            } else {
                showAlertBox("Already Exist Word", "This word is already in a word list");
            }
        } else {
            showAlertBox("Invalid Input", "Input must be alphabet characters! (a-z, A-Z)");
        }
        addWordTextF.clear();
    }

    /**
     * When clearLogBtn is pressed, clear a log and disale a clearLogBtn button
     */
    @FXML
    private void onClearLog() {
        logTextArea.clear();
        clearLogBtn.setDisable(true);
    }

    /**
     * When deleteWordBtn is pressed, Delete a selected word from words::ArrayList<String> in server manager.
     * Update a log in the process.
     */
    @FXML
    void onDeleteWord() {
        if (wordListTable.getSelectionModel().getSelectedItem() != null) {
            int removeIndex = wordListTable.getSelectionModel().getSelectedIndex();
            String word = serverManager.removeWord(removeIndex);
            logTextArea.appendText("Word: " + word + " removed!\n");
            wordListTable.getSelectionModel().clearSelection();
            if (serverManager.getObsList().isEmpty()) {
                deleteWordBtn.setDisable(true);
                logTextArea.appendText("Word list is empty!\n");
            }
        }

    }

    /**
     * When establishConnBtn is pressed. Either (1) establish a connection if not already, or (2) disconnect a connection.
     * This method will also change the text and state in establishConnBtn
     */
    @FXML
    private void onEstablishConnection() {
        if (!isEstablished) {
            serverManager.establishConnection();
            establishConnBtn.setText("Disable Connection");
            isEstablished = true;
        } else {
            serverManager.disconnect();
            establishConnBtn.setText("Establish Connection");
            isEstablished = false;
        }
    }

    /**
     * Setting up contents in ServerUI.fxml
     */
    public void setUpContent() {
        // Setup table view
        wordListTable.setItems(serverManager.getObsList());
        wordColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        wordListTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> serverManager.setCurrentWord(newSelection));
        // Setup tab pane
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab.equals(serverLogTab)) wordListTable.getSelectionModel().clearSelection();
        });
        // Setup buttons
        if (serverManager.getObsList().isEmpty()) deleteWordBtn.setDisable(true);
        logTextArea.textProperty().addListener(e -> clearLogBtn.setDisable(logTextArea.getText().trim().equals("")));
    }

    /**
     * This method is a setter to set a server manager into it. And then pass a log::TextArea to the it as well.
     *
     * @param serverManager A manager which is injected to this class.
     */
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        this.serverManager.passLogControl(logTextArea);
    }

    /**
     * When called, this method will create an error alert box filled with Title and Content received from caller and display to user.
     * The alert box will block out other action until get acknowledged.
     *
     * @param title   A title of the Alert box
     * @param content A content of the Alert box
     */
    private void showAlertBox(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
