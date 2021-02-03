package errors;

import Builder.BuildChecker;
import Builder.SemanticErrorDictionary;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import representations.ThanosValue;
import Searcher.VariableSearcher;
import scope.ThanosScope;
import scope.LocalScopeHandler;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class MultipleVarDecChecker implements IErrorChecker, ParseTreeListener {

    private ThanosParser.VariableDeclaratorIdContext varDecIdCtx;
    private int lineNumber;

    public MultipleVarDecChecker(ThanosParser.VariableDeclaratorIdContext varDecIdCtx) {
        this.varDecIdCtx = varDecIdCtx;

        Token firstToken = this.varDecIdCtx.getStart();
        this.lineNumber = firstToken.getLine();
    }

    /* (non-Javadoc)
     * @see com.neildg.mobiprog.builder.errorcheckers.IErrorChecker#verify()
     */
    @Override
    public void verify() {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, this.varDecIdCtx);
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
        if(ctx instanceof ThanosParser.VariableDeclaratorIdContext) {
            ThanosParser.VariableDeclaratorIdContext varDecCtx = (ThanosParser.VariableDeclaratorIdContext) ctx;
            this.verifyVariableOrConst(varDecCtx.getText());
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    private void verifyVariableOrConst(String identifierString) {
        ThanosValue ThanosValue = null;

        if(ExecutionManager.getInstance().isInFunctionExecution()) {
            ThanosFunction ThanosFunction = ExecutionManager.getInstance().getCurrentFunction();
            ThanosValue = VariableSearcher.searchVariableInFunction(ThanosFunction, identifierString);
        }

        //if after function finding, mobi value is still null, search local scope
        if(ThanosValue == null) {
            ThanosValue = LocalScopeHandler.searchVariableInLocalIterative(identifierString, LocalScopeHandler.getInstance().getActiveLocalScope());
        }

        //if mobi value is still null, search class
        if(ThanosValue == null) {
            ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
            ThanosValue = VariableSearcher.searchVariableInClass(ThanosScope, identifierString);
        }


        if(ThanosValue != null) {
            BuildChecker.reportCustomError(SemanticErrorDictionary.MULTIPLE_VARIABLE, "", identifierString, this.lineNumber);
        }
    }


}