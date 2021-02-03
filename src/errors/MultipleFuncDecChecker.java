package errors;

import Builder.BuildChecker;
import Builder.SemanticErrorDictionary;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import scope.ThanosScope;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.Token;

public class MultipleFuncDecChecker implements IErrorChecker {

    private ThanosParser.MethodDeclarationContext methodDecCtx;
    private int lineNumber;

    public MultipleFuncDecChecker(ThanosParser.MethodDeclarationContext methodDecCtx) {
        this.methodDecCtx = methodDecCtx;

        Token firstToken = methodDecCtx.getStart();
        this.lineNumber = firstToken.getLine();
    }

    @Override
    public void verify() {
        this.verifyFunctionCall(this.methodDecCtx.Identifier().getText());
    }

    private void verifyFunctionCall(String identifierString) {

        ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
        ThanosFunction ThanosFunction = ThanosScope.getFunction(identifierString);

        if(ThanosFunction != null) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.MULTIPLE_FUNCTION, "", identifierString, this.lineNumber);
        }
    }

}