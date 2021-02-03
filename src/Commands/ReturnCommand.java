package Commands;

import errors.TypeChecker;
import errors.UndeclaredChecker;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import representations.ThanosValue;
import Utlities.AssignmentUtilities;

public class ReturnCommand implements ICommand {

    private ThanosParser.ExpressionContext expressionCtx;
    private ThanosFunction assignedThanosFunction;

    public ReturnCommand(ThanosParser.ExpressionContext expressionCtx, ThanosFunction ThanosFunction) {
        this.expressionCtx = expressionCtx;
        this.assignedThanosFunction = ThanosFunction;

        UndeclaredChecker undeclaredChecker = new UndeclaredChecker(this.expressionCtx);
        undeclaredChecker.verify();

        ThanosValue ThanosValue = this.assignedThanosFunction.getReturnValue();
        TypeChecker typeChecker = new TypeChecker(ThanosValue, this.expressionCtx);
        typeChecker.verify();
    }

    @Override
    public void execute() {
        EvaluationCommand evaluationCommand = new EvaluationCommand(this.expressionCtx);
        evaluationCommand.execute();

        ThanosValue ThanosValue = this.assignedThanosFunction.getReturnValue();

        AssignmentUtilities.assignAppropriateValue(ThanosValue, evaluationCommand.getResult());
        //Console.log(LogType.DEBUG,"Return value is: " +evaluationCommand.getResult().toEngineeringString());
    }

}