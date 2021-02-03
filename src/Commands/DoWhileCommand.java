package Commands;

import Execution.ExecutionManager;
import Execution.ExecutionMonitor;
import GeneratedAntlrClasses.ThanosParser;

public class DoWhileCommand extends WhileCommand {

    public DoWhileCommand(ThanosParser.ParExpressionContext parExprCtr) {
        super(parExprCtr);
    }

    @Override
    public void execute() {
        this.executeFirstCommandSequence();
        super.execute();
    }

    /*
     * Executes the first command sequence before actually executing the behavior for the while command
     */
    private void executeFirstCommandSequence() {
        this.identifyVariables();

        ExecutionMonitor executionMonitor = ExecutionManager.getInstance().getExecutionMonitor();

        try {
            for(ICommand command : this.commandSequences) {
                executionMonitor.tryExecution();
                command.execute();
            }

        } catch(InterruptedException e) {
            //Log.e(TAG, "Monitor block interrupted! " +e.getMessage());
            System.err.println("DoWhileCommand: Monitor block interrupted! " + e.getMessage()); //TODO Change to IDE
        }
    }

    @Override
    public ControlTypeEnum getControlType() {
        return ControlTypeEnum.DO_WHILE_CONTROL;
    }
}