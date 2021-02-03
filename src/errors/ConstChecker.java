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

public class ConstChecker implements IErrorChecker, ParseTreeListener {

    private ThanosParser.ExpressionContext exprCtx;
    private int lineNumber;

    public ConstChecker(ThanosParser.ExpressionContext exprCtx) {
        this.exprCtx = exprCtx;

        Token firstToken = this.exprCtx.getStart();
        this.lineNumber = firstToken.getLine();
    }

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
            if(EvaluationCommand.isVariableOrConst(exprCtx)) {
                this.verifyVariableOrConst(exprCtx);
            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

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

        if(ThanosValue != null && ThanosValue.isFinal()) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.CONST_REASSIGNMENT, "", varExprCtx.getText(), this.lineNumber);
        }
    }

}