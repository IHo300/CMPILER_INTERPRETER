package IDE.Dialog;

import javafx.scene.control.Alert;

public class ErrorDialogHandler {

    Alert alert;

    public ErrorDialogHandler() {
        this. alert = new Alert(Alert.AlertType.ERROR);
    }

    public void showErrorDialog(String message) {
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}