package Commands;

import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosArray;

public class ArrayInitializeCommand implements ICommand {

    private ThanosArray assignedThanosArray;
    private ThanosParser.ArrayCreatorRestContext arrayCreatorCtx;

    public ArrayInitializeCommand(ThanosArray ThanosArray, ThanosParser.ArrayCreatorRestContext arrayCreatorCtx) {
        this.assignedThanosArray = ThanosArray;
        this.arrayCreatorCtx = arrayCreatorCtx;
    }

    @Override
    public void execute() {
        ThanosParser.ExpressionContext exprCtx = this.arrayCreatorCtx.expression(0);

        if(exprCtx != null) {
            EvaluationCommand evaluationCommand = new EvaluationCommand(exprCtx);
            evaluationCommand.execute();

            this.assignedThanosArray.initializeSize(evaluationCommand.getResult().intValue());
        }

    }

}