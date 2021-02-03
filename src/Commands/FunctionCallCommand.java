package Commands;

import Analyzers.FunctionCallVerifier;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import representations.ThanosValue;
import representations.PrimitiveType;
import Searcher.VariableSearcher;
import scope.ThanosScope;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public class FunctionCallCommand implements ICommand {

    private ThanosFunction ThanosFunction;
    private ThanosParser.ExpressionContext exprCtx;
    private String functionName;

    public FunctionCallCommand(String functionName, ThanosParser.ExpressionContext exprCtx) {
        this.functionName = functionName;
        this.exprCtx = exprCtx;

        this.searchFunction();

        ParseTreeWalker functionWalker = new ParseTreeWalker();
        functionWalker.walk(new FunctionCallVerifier(), this.exprCtx);

        this.verifyParameters();
    }

    @Override
    public void execute() {
        this.mapParameters();
        this.ThanosFunction.execute();
    }

    private void searchFunction() {
        ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
        this.ThanosFunction = ThanosScope.getFunction(this.functionName);
    }

    private void verifyParameters() {
        if(this.exprCtx.arguments() == null || this.exprCtx.arguments().expressionList() == null
                || this.exprCtx.arguments().expressionList().expression() == null) {
            return;
        }

        List<ThanosParser.ExpressionContext> exprCtxList = this.exprCtx.arguments().expressionList().expression();
        //map values in parameters
        for(int i = 0; i < exprCtxList.size(); i++) {
            ThanosParser.ExpressionContext parameterExprCtx = exprCtxList.get(i);
            this.ThanosFunction.verifyParameterByValueAt(parameterExprCtx, i);
        }
    }

    /*
     * Maps parameters when needed
     */
    private void mapParameters() {
        if(this.exprCtx.arguments() == null || this.exprCtx.arguments().expressionList() == null
                || this.exprCtx.arguments().expressionList().expression() == null) {
            return;
        }

        List<ThanosParser.ExpressionContext> exprCtxList = this.exprCtx.arguments().expressionList().expression();

        //map values in parameters
        for(int i = 0; i < exprCtxList.size(); i++) {
            ThanosParser.ExpressionContext parameterExprCtx = exprCtxList.get(i);

            if(this.ThanosFunction.getParameterAt(i).getPrimitiveType() == PrimitiveType.ARRAY) {
                ThanosValue ThanosValue = VariableSearcher.searchVariable(parameterExprCtx.getText());
                this.ThanosFunction.mapArrayAt(ThanosValue, i, parameterExprCtx.getText());
            }
            else {
                EvaluationCommand evaluationCommand = new EvaluationCommand(parameterExprCtx);
                evaluationCommand.execute();

                this.ThanosFunction.mapParameterByValueAt(evaluationCommand.getResult().toEngineeringString(), i);
            }
        }
    }

    public ThanosValue getReturnValue() {
        return this.ThanosFunction.getReturnValue();
    }
}
