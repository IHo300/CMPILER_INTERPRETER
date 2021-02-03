package Adders;

import Commands.ICommand;

public interface IExecutionAdder {
    public abstract void addCommand(ICommand command);
}
