package Commands;

import Execution.ExecutionManager;
import Execution.ExecutionMonitor;
import GeneratedAntlrClasses.ThanosParser;
import Mapping.IValueMapper;
import Mapping.IdentifierMapper;
import Utlities.ConditionalEvaluator;

import java.util.ArrayList;
import java.util.List;

public class WhileCommand implements IControlledCommand {

    protected List<ICommand> commandSequences; //the list of commands inside the WHILE statement

    protected ThanosParser.ParExpressionContext conditionalExpr;
    protected String modifiedConditionExpr;

    public WhileCommand(ThanosParser.ParExpressionContext conditionalExpr) {
        this.commandSequences = new ArrayList<>();
        this.conditionalExpr = conditionalExpr;
    }

    @Override
    public void execute() {
        this.identifyVariables();

        ExecutionMonitor executionMonitor = ExecutionManager.getInstance().getExecutionMonitor();

        try {
            //evaluate the given condition
            while(ConditionalEvaluator.evaluateCondition(this.conditionalExpr)) {
                for(ICommand command : this.commandSequences) {
                    executionMonitor.tryExecution();
                    command.execute();
                }

                this.identifyVariables(); //identify variables again to detect changes to such variables used.
            }

        } catch(InterruptedException e) {
            //Log.e(TAG, "Monitor block interrupted! " +e.getMessage());
            System.err.println("WhileCommand: Monitor block interrupted " + e.getMessage()); //TODO Change to IDE
        }
    }

    protected void identifyVariables() {
        IValueMapper identifierMapper = new IdentifierMapper(this.conditionalExpr.getText());
        identifierMapper.analyze(this.conditionalExpr);

        this.modifiedConditionExpr = identifierMapper.getModifiedExp();
    }

    @Override
    public ControlTypeEnum getControlType() {
        return ControlTypeEnum.WHILE_CONTROL;
    }

    @Override
    public void addCommand(ICommand command) {

        //Console.log(LogType.DEBUG, "		Added command to WHILE");
        this.commandSequences.add(command);
    }

    public int getCommandCount() {
        return this.commandSequences.size();
    }

}