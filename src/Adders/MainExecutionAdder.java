package Adders;

import Commands.ICommand;

import java.util.ArrayList;

public class MainExecutionAdder implements IExecutionAdder {

    private ArrayList<ICommand> mainExecutionList;

    public MainExecutionAdder(ArrayList<ICommand> mainExecutionList) {
        this.mainExecutionList = mainExecutionList;
    }

    @Override
    public void addCommand(ICommand command) {
        this.mainExecutionList.add(command);
    }

}