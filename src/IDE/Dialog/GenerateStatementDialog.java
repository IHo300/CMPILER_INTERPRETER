package IDE.Dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class GenerateStatementDialog {

    private static final String TYPE_IF = "if";
    private static final String TYPE_IF_ELSE = "if else";
    private static final String TYPE_FOR = "for";
    private static final String TYPE_WHILE = "while";
    private static final String TYPE_DO_WHILE = "do while";

    private Dialog<String> dialog;
    private ComboBox statementTypeComboBox;
    private TextField conditionTextField;
    private TextArea sampleArea;

    public GenerateStatementDialog() {
        this.dialog = new Dialog<>();
        this.setupDialog();
    }

    private void setupDialog() {
        dialog.setTitle("Generate Statement");
        dialog.setHeaderText(null);


        // Set the button types.
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.FINISH);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, confirmButtonType);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        TYPE_IF,
                        TYPE_IF_ELSE,
                        TYPE_FOR,
                        TYPE_WHILE,
                        TYPE_DO_WHILE
                );
        statementTypeComboBox = new ComboBox(options);
        statementTypeComboBox.getSelectionModel().selectFirst();
        statementTypeComboBox.setOnAction(event -> {
            updateSampleArea();
        });

        grid.add(new Label("Type:"), 0, 0);
        grid.add(statementTypeComboBox, 1, 0);

        conditionTextField = new TextField();
        conditionTextField.setPromptText("Condition");
        this.conditionTextField.textProperty().addListener(event -> {
            updateSampleArea();
        });

        grid.add(new Label("Condition:"), 0, 1);
        grid.add(conditionTextField, 1, 1);

        sampleArea = new TextArea();
        sampleArea.setEditable(false);
        sampleArea.setPrefWidth(200);
        updateSampleArea();

        grid.add(sampleArea, 0, 2, GridPane.REMAINING, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        //Platform.runLater(() -> methodName.requestFocus());

        // Convert the result to a string
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return sampleArea.getText();
            }
            return null;
        });
    }

    public String showGenerateStatementDialog() {
        Optional<String> result = dialog.showAndWait();

        return result.get();
    }

    private void updateSampleArea() {
        this.sampleArea.setText(generateSampleString());
    }

    private String generateSampleString() {
        String sample = "";

        String selected = this.statementTypeComboBox.getValue().toString();

        if (selected.equals(TYPE_IF) || selected.equals(TYPE_IF_ELSE)) {
            sample += "\tif(" + this.conditionTextField.getText() + "):\n\t\t" +
                    "// Code goes here" +
                    "\n\tend";

            if (selected.equals(TYPE_IF_ELSE)) {
                sample += "\n\telse:\n\t\t" +
                        "// More code goes here" +
                        "\n\tend";
            }
        }
        else if (selected.equals(TYPE_FOR)) {
            sample += "\tfor(" + this.conditionTextField.getText() + "):\n\t\t" +
                    "// Code goes here" +
                    "\n\tend";
        }
        else if (selected.equals(TYPE_WHILE)) {
            sample += "\twhile(" + this.conditionTextField.getText() + "):\n\t\t" +
                    "// Code goes here" +
                    "\n\tend";
        }
        else if (selected.equals(TYPE_DO_WHILE)) {
            sample += "\tdo:\n\t\t" +
                    "// Code goes here" +
                    "\n\tend while(" + this.conditionTextField.getText() + ");";
        }

        return sample;
    }
}
