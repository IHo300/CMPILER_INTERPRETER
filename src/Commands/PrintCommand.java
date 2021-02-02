package commands;

public class PrintCommand implements ICommand{

    private String expr;
    public PrintCommand(String expr){

        this.expr = expr;
    }

    private void evaluate(){
        //Mobiprog
        //MobiArray mobiArray = (MobiArray) mobiValue.getValue();
        //		MobiValue arrayMobiValue = mobiArray.getValueAt(evaluationCommand.getResult().intValue());
        //
        //		this.statementToPrint += arrayMobiValue.getValue().toString();

        //
    }

    public void execute(){
        this.evaluate();
    }
}
