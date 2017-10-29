package share;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * Project Name: Hangman
 */
public class ErrorAlertBox {

    /**
     * When called, this method will create an error alert box filled with Title and Content received from caller and display to user.
     * The alert box will block out other action until get acknowledged.
     *
     * @param title   A title of the Alert box
     * @param content A content of the Alert box
     */
    public static void showAlertBox(final String title, final String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
