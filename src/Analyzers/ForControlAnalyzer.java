package Analyzers;

import Commands.AssignmentCommand;
import Commands.ICommand;
import Commands.IncDecCommand;
import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ForControlAnalyzer implements ParseTreeListener{

    private ThanosParser.LocalVariableDeclarationContext localVarDecCtx;
    private ThanosParser.ExpressionContext exprCtx;
    private ICommand updateCommand;

    public ForControlAnalyzer() {

    }

    public void analyze(ThanosParser.ForControlContext forControlCtx) {

        //we don't need to walk the expression anymore, therefore, immediately assign it.
        if(forControlCtx.expression() != null) {
            this.exprCtx = forControlCtx.expression();
        }

        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, forControlCtx);
    }

    public void analyzeForLoop(ParserRuleContext ctx) {

        if(ctx instanceof ThanosParser.ForInitContext) {
            ThanosParser.ForInitContext forInitCtx = (ThanosParser.ForInitContext) ctx;

            this.localVarDecCtx = forInitCtx.localVariableDeclaration();

            LocalVariableAnalyzer localVariableAnalyzer = new LocalVariableAnalyzer();
            localVariableAnalyzer.analyze(this.localVarDecCtx);
        }

        else if(ctx instanceof ThanosParser.ForUpdateContext) {
            ThanosParser.ForUpdateContext forUpdateCtx = (ThanosParser.ForUpdateContext) ctx;
            ThanosParser.ExpressionContext exprCtx = forUpdateCtx.expressionList().expression(0);

            if(StatementExpressionAnalyzer.isAssignmentExpression(exprCtx)) {
                //this.updateCommand = new AssignmentCommand(exprCtx.expression(0), exprCtx.expression(1));
                this.updateCommand = new AssignmentCommand(exprCtx.expression(0), exprCtx.expression(1));
            }
            else if(StatementExpressionAnalyzer.isIncrementExpression(exprCtx)) {
                this.updateCommand = new IncDecCommand(exprCtx.expression(0), ThanosLexer.INC);
            }
            else if(StatementExpressionAnalyzer.isDecrementExpression(exprCtx)) {
                this.updateCommand = new IncDecCommand(exprCtx.expression(0), ThanosLexer.DEC);
            }
        }
    }

    public ThanosParser.ExpressionContext getExprContext() {
        return this.exprCtx;
    }

    public ThanosParser.LocalVariableDeclarationContext getLocalVarDecContext() {
        return this.localVarDecCtx;
    }

    public ICommand getUpdateCommand() {
        return this.updateCommand;
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
        this.analyzeForLoop(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }
}