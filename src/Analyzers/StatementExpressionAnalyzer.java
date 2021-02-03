package Analyzers;

import Commands.*;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import Statements.StatementControlOverseer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class StatementExpressionAnalyzer implements ParseTreeListener {

    private ThanosParser.ExpressionContext readRightHandExprCtx; //used to avoid mistakenly reading right hand expressions as direct function calls as well.

    //TODO: find a way to not rely on tree depth for function calls.
    public final static int FUNCTION_CALL_NO_PARAMS_DEPTH = 13;
    public final static int FUNCTION_CALL_WITH_PARAMS_DEPTH = 14;

    public StatementExpressionAnalyzer() {

    }

    public void analyze(ThanosParser.StatementExpressionContext statementExprCtx) {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, statementExprCtx);
    }

    public void analyze(ThanosParser.ExpressionContext exprCtx) {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, exprCtx);
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

            if(isAssignmentExpression(exprCtx)) {
                System.out.println("Assignment expr detected: " +exprCtx.getText());

                List<ThanosParser.ExpressionContext> exprListCtx = exprCtx.expression();
                AssignmentCommand assignmentCommand = new AssignmentCommand(exprListCtx.get(0), exprListCtx.get(1));

                this.readRightHandExprCtx = exprListCtx.get(1);
                this.handleStatementExecution(assignmentCommand);

            }
            else if(isIncrementExpression(exprCtx)) {
                System.out.println("Increment expr detected: " +exprCtx.getText());

                List<ThanosParser.ExpressionContext> exprListCtx = exprCtx.expression();

                IncDecCommand incDecCommand = new IncDecCommand(exprListCtx.get(0) ,ThanosLexer.INC);
                this.handleStatementExecution(incDecCommand);
            }

            else if(isDecrementExpression(exprCtx)) {
                System.out.println("Decrement expr detected: " +exprCtx.getText());

                List<ThanosParser.ExpressionContext> exprListCtx = exprCtx.expression();

                IncDecCommand incDecCommand = new IncDecCommand(exprListCtx.get(0) ,ThanosLexer.DEC);
                this.handleStatementExecution(incDecCommand);

            }

            else if(this.isFunctionCallWithParams(exprCtx)) {
                this.handleFunctionCallWithParams(exprCtx);
            }

            else if(this.isFunctionCallWithNoParams(exprCtx)) {
                this.handleFunctionCallWithNoParams(exprCtx);
            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    private void handleStatementExecution(ICommand command) {

        StatementControlOverseer statementControl = StatementControlOverseer.getInstance();

        //add to conditional controlled command
        if(statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(command);
            }
            else {
                conditionalCommand.addNegativeCommand(command);
            }
        }

        else if(statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(command);
        }
        else {
            ExecutionManager.getInstance().addCommand(command);
        }

    }

    private void handleFunctionCallWithParams(ThanosParser.ExpressionContext funcExprCtx) {
        ThanosParser.ExpressionContext functionExprCtx = funcExprCtx.expression(0);
        String functionName = functionExprCtx.getText();

        FunctionCallCommand functionCallCommand = new FunctionCallCommand(functionName, funcExprCtx);
        this.handleStatementExecution(functionCallCommand);

        System.out.println("Function call with no params detected: " +functionName);
    }

    private void handleFunctionCallWithNoParams(ThanosParser.ExpressionContext funcExprCtx) {
        String functionName = funcExprCtx.expression(0).getText();

        FunctionCallCommand functionCallCommand = new FunctionCallCommand(functionName, funcExprCtx);
        this.handleStatementExecution(functionCallCommand);

    }
    public static boolean isAssignmentExpression(ThanosParser.ExpressionContext exprCtx) {
        List<TerminalNode> tokenList = exprCtx.getTokens(ThanosLexer.ASSIGN);
        return (tokenList.size() > 0);
    }

    public static boolean isIncrementExpression(ThanosParser.ExpressionContext exprCtx) {
        List<TerminalNode> incrementList = exprCtx.getTokens(ThanosLexer.INC);

        return (incrementList.size() > 0);
    }

    public static boolean isDecrementExpression(ThanosParser.ExpressionContext exprCtx) {
        List<TerminalNode> decrementList = exprCtx.getTokens(ThanosLexer.DEC);

        return (decrementList.size() > 0);
    }

    private boolean isFunctionCallWithParams(ThanosParser.ExpressionContext exprCtx) {
        ThanosParser.ExpressionContext firstExprCtx = exprCtx.expression(0);

        if(firstExprCtx != null) {
            if(exprCtx != this.readRightHandExprCtx) {
                //ThisKeywordChecker thisChecker = new ThisKeywordChecker(firstExprCtx);
                //thisChecker.verify();

                return (exprCtx.expressionList() != null);
            }
        }

        return false;

    }

    private boolean isFunctionCallWithNoParams(ThanosParser.ExpressionContext exprCtx) {
        //ThisKeywordChecker thisChecker = new ThisKeywordChecker(exprCtx);
        //thisChecker.verify();
        //if(exprCtx.Identifier() != null)
        return exprCtx.depth() == FUNCTION_CALL_NO_PARAMS_DEPTH || exprCtx.depth() == 17;
    }
}