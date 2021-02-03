package IDE.Dialog;

import Commands.FunctionList;
import Templates.ThanosFunctionTemplate;
import Templates.ThanosFunctionTemplateParameter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GenerateFunctionGrid extends GridPane {

    private static final String TYPE_VOID = "void";
    private static final String TYPE_STRING = "string";
    private static final String TYPE_INT = "int";
    private static final String TYPE_DECIMAL = "decimal";
    private static final String TYPE_BOOL = "bool";
    private static final String TYPE_STRING_ARRAY = "string[]";
    private static final String TYPE_INT_ARRAY = "int[]";
    private static final String TYPE_DECIMAL_ARRAY = "decimal[]";
    private static final String TYPE_BOOL_ARRAY = "bool[]";

    private TabPane tabPane;
    private VBox parametersHolder;
    private TextField methodName;
    private TextArea sampleArea;
    private ComboBox<String> returnTypeComboBox;
    private RadioButton publicRadio;
    private RadioButton privateRadio;
    private Node addThisButton;
    private Node addAllButton;
    private Label parameterErrorMessageLabel;
    private Label errorMessageLabel;

    public GenerateFunctionGrid(TabPane tabPane, Node addThisButton, Node addAllButton) {
        this.tabPane = tabPane;
        this.addThisButton = addThisButton;
        this.addThisButton.setDisable(true);
        this.addAllButton = addAllButton;
        this.addAllButton.setDisable(true);
        this.setup();
    }

    private void setup() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20, 150, 10, 10));

        ToggleGroup radioButtons = new ToggleGroup();
        publicRadio = new RadioButton("public");
        publicRadio.setToggleGroup(radioButtons);
        publicRadio.setSelected(true);
        publicRadio.setOnAction(event -> {
            updateSampleArea();
        });
        privateRadio = new RadioButton("private");
        privateRadio.setToggleGroup(radioButtons);
        privateRadio.setOnAction(event -> {
            updateSampleArea();
        });

        HBox radioButtonHolder = new HBox(10);
        radioButtonHolder.getChildren().addAll(publicRadio, privateRadio);

        this.add(radioButtonHolder, 0, 0);

        methodName = new TextField();
        methodName.setPromptText("Method Name");
        methodName.setId("method-name");

        this.add(new Label("Method Name:"), 0, 1);
        this.add(methodName, 1, 1);

        errorMessageLabel = new Label("Method name exists!");
        errorMessageLabel.setTextFill(Color.RED);
        errorMessageLabel.setVisible(false);
        this.add(errorMessageLabel, 2, 1);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        TYPE_VOID,
                        TYPE_STRING,
                        TYPE_INT,
                        TYPE_DECIMAL,
                        TYPE_BOOL,
                        TYPE_STRING_ARRAY,
                        TYPE_INT_ARRAY,
                        TYPE_DECIMAL_ARRAY,
                        TYPE_BOOL_ARRAY
                );
        returnTypeComboBox = new ComboBox(options);
        returnTypeComboBox.getSelectionModel().selectFirst();
        returnTypeComboBox.setOnAction(event -> {
            updateSampleArea();
        });

        this.add(new Label("Return Type:"), 0, 2);
        this.add(returnTypeComboBox, 1, 2);

        this.add(new Label("Parameters:"), 0, 3);

        parametersHolder = new VBox();
        parametersHolder.setPrefHeight(200);
        parametersHolder.setPrefWidth(350);
        ScrollPane parametersScroll = new ScrollPane(parametersHolder);
        parametersScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.add(parametersScroll, 0, 4, GridPane.REMAINING, 1);

        parameterErrorMessageLabel = new Label("Method contains invalid parameter names!");
        parameterErrorMessageLabel.setTextFill(Color.RED);
        parameterErrorMessageLabel.setVisible(false);
        this.add(parameterErrorMessageLabel, 0, 5);

        ObservableList<String> dataTypeOptions =
                FXCollections.observableArrayList(
                        TYPE_STRING,
                        TYPE_INT,
                        TYPE_DECIMAL,
                        TYPE_BOOL,
                        TYPE_STRING_ARRAY,
                        TYPE_INT_ARRAY,
                        TYPE_DECIMAL_ARRAY,
                        TYPE_BOOL_ARRAY
                );

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            addThisButton.setDisable(true);
            addAllButton.setDisable(true);
            parameterErrorMessageLabel.setVisible(true);
            ComboBox dataTypes = new ComboBox(dataTypeOptions);
            dataTypes.setOnAction(chooseEvent -> {
                updateSampleArea();
            });
            dataTypes.getSelectionModel().selectFirst();

            TextField parameterName = new TextField();
            parameterName.setPromptText("Parameter Name");
            parameterName.textProperty().addListener((observable, oldValue, newValue) -> {
                /*System.out.println("Parameter Name: " + newValue);
                if (hasInvalidParameters()) {
                    addAllButton.setDisable(true);
                    parameterErrorMessageLabel.setVisible(true);
                    System.out.println("Found duplicate parameter name");
                }
                else {
                    addAllButton.setDisable(false);
                    parameterErrorMessageLabel.setVisible(false);
                }*/
                /*boolean valid = inputIsValid();

                addThisButton.setDisable(!valid);
                addAllButton.setDisable(!valid);
                parameterErrorMessageLabel.setVisible(hasInvalidParameters());*/

                validateInput();
                updateSampleArea();
            });

            Button removeButton = new Button("X");

            HBox parameters = new HBox(dataTypes, parameterName, removeButton);

            removeButton.setOnAction(value -> {
                parametersHolder.getChildren().remove(parameters);
                /*boolean invalid = !inputIsValid();
                addThisButton.setDisable(invalid);
                addAllButton.setDisable(invalid);
                parameterErrorMessageLabel.setVisible(hasInvalidParameters());*/
                validateInput();
                updateSampleArea();
            });

            parametersHolder.getChildren().add(parameters);
        });
        this.add(addButton, 1, 3);

        sampleArea = new TextArea();
        sampleArea.setEditable(false);
        sampleArea.setPrefWidth(200);
        updateSampleArea();

        this.add(sampleArea, 0, 6, GridPane.REMAINING, 1);


        // Do some validation (using the Java 8 lambda syntax).
        methodName.textProperty().addListener((observable, oldValue, newValue) -> {
            /*boolean methodNameExists = methodNameExists();
            boolean invalid = !inputIsValid();
            addThisButton.setDisable(invalid);
            addAllButton.setDisable(invalid);
            errorMessageLabel.setVisible(methodNameExists);*/
            validateInput();
            updateSampleArea();
        });

        //dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> methodName.requestFocus());

        /*// Convert the result to a string
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return generateSampleString();

            }
            return null;
        });*/
    }

    public boolean hasInvalidParameters() {
        ThanosFunctionTemplate methodTemplate = new ThanosFunctionTemplate();
        for (Node node : parametersHolder.getChildren()) {
            HBox child = (HBox) node;

            ObservableList<Node> parameterInfo = child.getChildren();

            String parameterName = ((TextField) parameterInfo.get(1)).getText().trim();

            if (parameterName.isEmpty()) {
                return true;
            }

            String dataType = ((ComboBox) parameterInfo.get(0)).getValue().toString();
            ThanosFunctionTemplateParameter parameter = new ThanosFunctionTemplateParameter(parameterName, dataType);

            if (methodTemplate.hasParameter(parameter)) {
                return true;
            }

            methodTemplate.addParameter(parameter);
        }
        return false;
    }

    public boolean inputIsValid() {
//        boolean methodNameExists = MethodList.getInstance().methodNameExists(methodName.getText());
        boolean methodNameExists = methodNameExists();
        boolean invalidMethodName = methodName.getText().trim().isEmpty() || methodNameExists;

        return !methodNameExists && !invalidMethodName && !hasInvalidParameters();
    }

    public boolean methodNameExists() {
        if (FunctionList.getInstance().methodNameExists(methodName.getText())) {
            return true;
        }
        else {
            for (Tab tab : tabPane.getTabs()) {
                GridPane tabGrid = (GridPane) tab.getContent();
                if (tabGrid == this) {
                    continue;
                }
                TextField methodNameTextField = (TextField) tabGrid.lookup("#method-name");
                String text = methodNameTextField.getText();

                if (tabGrid != this && text.equals(methodName.getText())) {
                    return true;
                }
            }
            return false;
        }
    }

    private void updateSampleArea() {
        this.sampleArea.setText(generateSampleString());
    }

    public String generateSampleString() {
        ThanosFunctionTemplate methodTemplate = new ThanosFunctionTemplate();
        methodTemplate.setMethodName(methodName.getText().trim());
        methodTemplate.setReturnType(returnTypeComboBox.getValue().toString());
        methodTemplate.setIsPublic(publicRadio.isSelected());

        for (Node node : parametersHolder.getChildren()) {
            HBox child = (HBox) node;

            ObservableList<Node> parameterInfo = child.getChildren();

            String parameterName = ((TextField) parameterInfo.get(1)).getText().trim();

            if (parameterName.isEmpty()) {
                continue;
            }

            String dataType = ((ComboBox) parameterInfo.get(0)).getValue().toString();
            ThanosFunctionTemplateParameter parameter = new ThanosFunctionTemplateParameter(parameterName, dataType);

            /*if (methodTemplate.hasParameter(parameter)) {
                ErrorDialogHandler errorDialogHandler = new ErrorDialogHandler();
                errorDialogHandler.showErrorDialog("Duplicate parameter! Try again!");
                return null;
            }*/

            methodTemplate.addParameter(parameter);
        }

        return methodTemplate.toString();
    }

    public boolean validateGlobally() {
        for (Tab tab : tabPane.getTabs()) {
            GenerateFunctionGrid tabGrid = (GenerateFunctionGrid) tab.getContent();

            if (!tabGrid.inputIsValid()) {
                return false;
            }
        }

        return true;
    }

    public void validateInput() {
        boolean invalid = !inputIsValid();
        addThisButton.setDisable(invalid);
        addAllButton.setDisable(!validateGlobally());
        parameterErrorMessageLabel.setVisible(hasInvalidParameters());
        errorMessageLabel.setVisible(methodNameExists());
    }
}
