package IDE.Dialog;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class RefactorDialogHandler {

    TextInputDialog dialog;

    public RefactorDialogHandler() {
        dialog = new TextInputDialog();
        dialog.setTitle("Refactor");
    }

    public String showRefactorDialog(String originalName) {
        dialog.setContentText("Set new name for method \"" + originalName + "\":");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().isEmpty()) {
            return result.get();
        }

        return null;
    }
}
