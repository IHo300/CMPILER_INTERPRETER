package Adders;

import Commands.ICommand;
import representations.ThanosFunction;

public class FunctionExecutionAdder implements IExecutionAdder {

    private ThanosFunction assignedThanosFunction;

    public FunctionExecutionAdder(ThanosFunction ThanosFunction) {
        this.assignedThanosFunction = ThanosFunction;
    }

    @Override
    public void addCommand(ICommand command) {
        this.assignedThanosFunction.addCommand(command);
    }

    public ThanosFunction getAssignedFunction() {
        return this.assignedThanosFunction;
    }

}