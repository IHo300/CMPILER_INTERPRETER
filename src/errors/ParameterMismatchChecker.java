package errors;

import Builder.BuildChecker;
import Builder.SemanticErrorDictionary;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;

import java.util.List;

public class ParameterMismatchChecker implements IErrorChecker {

    private ThanosFunction ThanosFunction;
    private List<ThanosParser.ExpressionContext> exprCtxList;
    private int lineNumber;

    public ParameterMismatchChecker(ThanosFunction ThanosFunction, ThanosParser.ArgumentsContext argumentsCtx) {
        this.ThanosFunction = ThanosFunction;

        if(argumentsCtx.expressionList() != null) {
            this.exprCtxList = argumentsCtx.expressionList().expression();
        }

        this.lineNumber = argumentsCtx.getStart().getLine();
    }

    @Override
    public void verify() {
        if(this.ThanosFunction == null) {
            return;
        }

        if(this.exprCtxList == null && this.ThanosFunction.getParameterValueSize() != 0) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.PARAMETER_COUNT_MISMATCH, "", this.ThanosFunction.getFunctionName(), this.lineNumber);
        }
        else if(this.exprCtxList != null && this.exprCtxList.size() != this.ThanosFunction.getParameterValueSize()) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.PARAMETER_COUNT_MISMATCH, "", this.ThanosFunction.getFunctionName(), this.lineNumber);
        }
    }

}