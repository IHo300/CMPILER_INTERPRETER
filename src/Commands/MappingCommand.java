package Commands;

import Analyzers.FunctionCallVerifier;
import errors.UndeclaredChecker;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosValue;
import representations.PrimitiveType;
import Searcher.VariableSearcher;
import Utlities.AssignmentUtilities;
import Utlities.StringUtilities;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class MappingCommand implements ICommand{

    private String identifierString;
    private ThanosParser.ExpressionContext parentExprCtx;

    private String modifiedExp;

    public MappingCommand(String identifierString, ThanosParser.ExpressionContext exprCtx) {
        this.identifierString = identifierString;
        this.parentExprCtx = exprCtx;

        UndeclaredChecker undeclaredChecker = new UndeclaredChecker(this.parentExprCtx);
        undeclaredChecker.verify();

        ParseTreeWalker functionWalker = new ParseTreeWalker();
        functionWalker.walk(new FunctionCallVerifier(), this.parentExprCtx);

    }


    @Override
    public void execute() {
        this.modifiedExp = this.parentExprCtx.getText();
        System.out.println("Executing Mapping Command");
        String value = "";
        EvaluationCommand evaluationCommand;
        ThanosValue ThanosValue = VariableSearcher.searchVariable(this.identifierString);
        if(this.modifiedExp.contains("\"")) {
            value = StringUtilities.removeQuotes(this.modifiedExp);
            ThanosValue.setPrimitiveType(PrimitiveType.STRING);
            ThanosValue.setValue(value);
        }else if(this.modifiedExp.contains("'")){
            value = StringUtilities.removeQuotes(this.modifiedExp);
            ThanosValue.setPrimitiveType(PrimitiveType.CHAR);
            ThanosValue.setValue(value);
        }
        else {
            evaluationCommand = new EvaluationCommand(this.parentExprCtx);
            evaluationCommand.execute();
            AssignmentUtilities.assignAppropriateValue(ThanosValue, evaluationCommand.getResult());
        }

    }

    /*
     * Returns the modified exp, with mapped values.
     */
    public String getModifiedExp() {
        return this.modifiedExp;
    }
}