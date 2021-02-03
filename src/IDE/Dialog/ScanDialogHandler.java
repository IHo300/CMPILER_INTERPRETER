package IDE.Dialog;

import Notifications.*;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import java.util.ConcurrentModificationException;
import java.util.Optional;

import static Notifications.KeyNames.VALUE_ENTERED_KEY;

public class ScanDialogHandler implements NotificationListener {

    TextInputDialog dialog;

    public ScanDialogHandler() {
        dialog = new TextInputDialog();
        dialog.getDialogPane();
        dialog.setTitle("Scan Dialog");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().remove(1);

        NotificationCenter.getInstance().addObserver(Notifications.ON_FOUND_SCAN_STATEMENT, this);
    }

    private void showScanDialog(Parameters params) throws InterruptedException {
        dialog.getEditor().setText("");
        dialog.setContentText(params.getStringExtra(KeyNames.MESSAGE_DISPLAY_KEY, "Input: "));

        // Traditional way to get the response value.
        Platform.runLater(()-> {

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                Parameters parameters = new Parameters();
                parameters.putExtra(VALUE_ENTERED_KEY, result.get());

                try {
                    NotificationCenter.getInstance().postNotification(Notifications.ON_SCAN_DIALOG_DISMISSED, parameters); //report back results to scan command
                }catch (ConcurrentModificationException cmeX){

                }

            } else {
                try {
                    NotificationCenter.getInstance().postNotification(Notifications.ON_SCAN_DIALOG_DISMISSED); //report back results to scan command
                }catch (ConcurrentModificationException cmeX){

                }
            }

        });


    }


    @Override
    public void onNotify(String notificationString, Parameters params) {

        if(notificationString == Notifications.ON_FOUND_SCAN_STATEMENT) {
            try {
                this.showScanDialog(params);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}