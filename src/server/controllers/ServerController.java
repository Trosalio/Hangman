package server.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import server.models.ServerManager;
import server.models.Word;

/**
 * Project Name: HangmanClient
 */

public class ServerController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab serverLogTab;

    @FXML
    private Button clearLogBtn;

    @FXML
    private Button establishConnBtn;

    @FXML
    private TextArea logTextArea;

    @FXML
    private TableView<Word> wordListTable;

    @FXML
    private TableColumn<Word, String> wordColumn;

    @FXML
    private Button deleteWordBtn;

    @FXML
    private TextField addWordTextF;

    private boolean isEstablished;
    private ServerManager serverManager;

    @FXML
    private void onAddWord() {
        String word = addWordTextF.getText().toUpperCase();
        if (word.matches("[A-Z]+")) {
            if (deleteWordBtn.isDisable()) deleteWordBtn.setDisable(false);
            logTextArea.appendText("Word: " + word + " added!\n");
            serverManager.insertWord(word);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Input must be alphabet characters! (a-z, A-Z)");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
        addWordTextF.clear();
    }

    @FXML
    private void onClearLog() {
        logTextArea.clear();
        clearLogBtn.setDisable(true);
    }

    @FXML
    void onDeleteWord() {
        if (wordListTable.getSelectionModel().getSelectedItem() != null) {
            int removeIndex = wordListTable.getSelectionModel().getSelectedIndex();
            Word word = serverManager.removeWord(removeIndex);
            logTextArea.appendText("Word: " + word.getWord() + " removed!\n");
            wordListTable.getSelectionModel().clearSelection();
            if (serverManager.getObsList().isEmpty()) {
                deleteWordBtn.setDisable(true);
                logTextArea.appendText("Word list is empty!\n");
            }
        }

    }

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

    public void setUpContent() {
        // Setup table view
        wordListTable.setItems(serverManager.getObsList());
        wordColumn.setCellValueFactory(cell -> cell.getValue().wordProperty());
        wordListTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> serverManager.setCurrentWord(newSelection));
        // Setup tab pane
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab.equals(serverLogTab)) wordListTable.getSelectionModel().clearSelection();
        });
        // Setup buttons
        if (serverManager.getObsList().isEmpty()) deleteWordBtn.setDisable(true);
        logTextArea.textProperty().addListener(e -> clearLogBtn.setDisable(logTextArea.getText().trim().equals("")));
    }

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        this.serverManager.receiveLogControl(logTextArea);
    }
}
