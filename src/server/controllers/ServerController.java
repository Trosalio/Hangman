package server.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import server.models.ServerManager;

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
    private TableView<String> wordListTable;

    @FXML
    private TableColumn<String, String> wordColumn;

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
            if (serverManager.isNotExisted(word)) {
                if (deleteWordBtn.isDisable()) deleteWordBtn.setDisable(false);
                logTextArea.appendText("Word: " + word + " added!\n");
                serverManager.insertWord(word);
            } else {
                showAlertBox("Already Exist Word", "This word is already in a word list");
            }
        } else {
            showAlertBox("Invalid Input", "Input must be alphabet characters! (a-z, A-Z)");
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
            String word = serverManager.removeWord(removeIndex);
            logTextArea.appendText("Word: " + word + " removed!\n");
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

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        this.serverManager.passLogControl(logTextArea);
    }

    private void showAlertBox(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
