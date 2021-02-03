package Commands;

import Execution.ExecutionManager;
import Notifications.*;
import representations.ThanosValue;
import representations.ThanosValueSearcher;
import Utlities.StringUtilities;

public class ScanCommand implements ICommand, NotificationListener{

    public final static String MESSAGE_DISPLAY_KEY = "MESSAGE_DISPLAY_KEY";

    private String messageToDisplay;
    private String identifier;

    public ScanCommand(String messageToDisplay, String identifier) {
        this.messageToDisplay = StringUtilities.removeQuotes(messageToDisplay);
        this.identifier = identifier;

    }
    @Override
    public void execute() {
        NotificationCenter.getInstance().addObserver(Notifications.ON_SCAN_DIALOG_DISMISSED, this); //add an observer to listen to when the dialog has been dismissed

        Parameters params = new Parameters();
        params.putExtra(MESSAGE_DISPLAY_KEY, this.messageToDisplay);

        ExecutionManager.getInstance().blockExecution();
        NotificationCenter.getInstance().postNotification(Notifications.ON_FOUND_SCAN_STATEMENT, params);
    }

    private void acquireInputFromUser(Parameters params) {
        String valueEntered = "";

        valueEntered = params.getStringExtra(KeyNames.VALUE_ENTERED_KEY, "");
            //TODO Change to IDE Front-End


        ThanosValue ThanosValue = ThanosValueSearcher.searchVariable(identifier);
        ThanosValue.setValue(valueEntered);

        NotificationCenter.getInstance().removeObserver(Notifications.ON_SCAN_DIALOG_DISMISSED, this); //remove observer after using
        ExecutionManager.getInstance().resumeExecution(); //resume execution of thread.
    }

    @Override
    public void onNotify(String notificationString, Parameters params) {
        if(notificationString.equals(Notifications.ON_SCAN_DIALOG_DISMISSED)) {
            this.acquireInputFromUser(params);
        }
    }

}