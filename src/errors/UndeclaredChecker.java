package errors;

import Builder.BuildChecker;
import Builder.SemanticErrorDictionary;
import Commands.EvaluationCommand;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import representations.ThanosValue;
import Searcher.VariableSearcher;
import scope.ThanosScope;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class UndeclaredChecker implements IErrorChecker, ParseTreeListener {

    private ThanosParser.ExpressionContext exprCtx;
    private int lineNumber;

    public UndeclaredChecker(ThanosParser.ExpressionContext exprCtx) {
        this.exprCtx = exprCtx;

        Token firstToken = this.exprCtx.getStart();
        this.lineNumber = firstToken.getLine();
    }

    /* (non-Javadoc)
     * @see com.neildg.mobiprog.builder.errorcheckers.IErrorChecker#verify()
     */
    @Override
    public void verify() {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, this.exprCtx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.ExpressionContext) {
            ThanosParser.ExpressionContext exprCtx = (ThanosParser.ExpressionContext) ctx;
            if(EvaluationCommand.isFunctionCall(exprCtx)) {
                this.verifyFunctionCall(exprCtx);
            }
            else if(EvaluationCommand.isVariableOrConst(exprCtx)) {
                this.verifyVariableOrConst(exprCtx);
            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    private void verifyFunctionCall(ThanosParser.ExpressionContext funcExprCtx) {

        if(funcExprCtx.expression() == null)
            return;

        String functionName = funcExprCtx.expression(0).getText();

        ThanosScope classScope = SymbolTableManager.getInstance().getMainScope();
        ThanosFunction ThanosFunction = classScope.getFunction(functionName);

        if(ThanosFunction == null) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.UNDECLARED_FUNCTION, "", functionName, this.lineNumber);
        }
        else {
//            Console.log(LogType.DEBUG, "Function found: " +functionName);
        }
    }

    private void verifyVariableOrConst(ThanosParser.ExpressionContext varExprCtx) {
        ThanosValue ThanosValue = null;

        if(ExecutionManager.getInstance().isInFunctionExecution()) {
            ThanosFunction ThanosFunction = ExecutionManager.getInstance().getCurrentFunction();
            ThanosValue = VariableSearcher.searchVariableInFunction(ThanosFunction, varExprCtx.primary().Identifier().getText());
        }

        //if after function finding, mobi value is still null, search class
        if(ThanosValue == null) {
            ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
            ThanosValue = VariableSearcher.searchVariableInClassIncludingLocal(ThanosScope, varExprCtx.primary().Identifier().getText());
        }

        //after second pass, we conclude if it cannot be found already
        if(ThanosValue == null) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.UNDECLARED_VARIABLE, "", varExprCtx.getText(), this.lineNumber);
        }
    }

    /*
     * Verifies a var or const identifier from a scan statement since scan grammar is different.
     */
    public static void verifyVarOrConstForScan(String identifier, ThanosParser.ScanStatementContext statementCtx) {
        ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
        ThanosValue ThanosValue = VariableSearcher.searchVariableInClassIncludingLocal(ThanosScope, identifier);

        Token firstToken = statementCtx.getStart();

        if(ThanosValue == null) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.UNDECLARED_VARIABLE, "", identifier, firstToken.getLine());
        }
    }

}