package Mapping;

import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import representations.ThanosValue;
import scope.ThanosScope;
import scope.LocalScope;
import scope.LocalScopeHandler;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class FunctionIdentifierMapper implements ParseTreeListener, IValueMapper {

    private String originalExp = null;
    private String modifiedExp = null;

    private ThanosFunction assignedFunction;
    private ThanosValue ThanosValue;
    private LocalScope functionLocalScope;

    public FunctionIdentifierMapper(String originalExp, ThanosFunction assignedFunction) {
        this.originalExp = originalExp;
        this.modifiedExp = originalExp;
        this.assignedFunction = assignedFunction;
        this.functionLocalScope = assignedFunction.getParentLocalScope();
    }


    public void analyze(ThanosParser.ExpressionContext exprCtx) {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, exprCtx);
    }

    public void analyze(ThanosParser.ParExpressionContext exprCtx) {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, exprCtx);
    }




    @Override
    public void visitErrorNode(ErrorNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.PrimaryContext) {
            ThanosParser.PrimaryContext primaryCtx = (ThanosParser.PrimaryContext) ctx;

            if(primaryCtx.Identifier() != null) {
                String variableKey = primaryCtx.getText();
                this.searchVariable(variableKey);
            }
        }
    }

    private void searchVariable(String identifierString) {
        if(this.assignedFunction.hasParameter(identifierString)) {
            this.modifiedExp = this.modifiedExp.replace(identifierString, this.assignedFunction.getParameter(identifierString).getValue().toString());
        }
        else {
            this.ThanosValue = LocalScopeHandler.searchVariableInLocalIterative(identifierString, this.functionLocalScope);

            if (this.ThanosValue == null) {
                ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
                this.ThanosValue = ThanosScope.searchVariableIncludingLocal(identifierString);

                //Console.log("Variable in global scope: " +this.mobiValue.getValue());
            }
            this.modifiedExp = this.modifiedExp.replace(identifierString, this.ThanosValue.getValue().toString());
        }
    }


    @Override
    public String getOriginalExp() {
        return this.originalExp;
    }

    @Override
    public String getModifiedExp() {
        return this.modifiedExp;
    }

    @Override
    public ThanosValue getThanosValue() {
        return null;
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }
}