package Analyzers;

import Commands.*;
import errors.UndeclaredChecker;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import scope.LocalScopeHandler;
import Statements.StatementControlOverseer;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class StatementAnalyzer {

    public StatementAnalyzer() {

    }

    public void analyze(ThanosParser.StatementContext ctx) {
        //print statement
        if(ctx.printStatement() != null) {
            this.handlePrintStatement(ctx.printStatement());
        }
        else if(ctx.scanStatement() != null) {
            this.handleScanStatement(ctx.scanStatement());
        }
        //an expression
        else if(ctx.statementExpression() != null) {
            StatementExpressionAnalyzer expressionAnalyzer = new StatementExpressionAnalyzer();
            expressionAnalyzer.analyze(ctx.statementExpression());
        }

        //a block statement
        else if(ctx.block() != null) {
            ThanosParser.BlockContext blockCtx = ctx.block();

            BlockAnalyzer blockAnalyzer = new BlockAnalyzer();
            blockAnalyzer.analyze(blockCtx);
        }

        //an IF statement
        else if(isIFStatement(ctx)) {
            //Console.log("Par expression: " +ctx.parExpression().getText());

            ThanosParser.StatementContext statementCtx = ctx.statement(0);

            //Console.log(LogType.DEBUG, "IF statement: " +statementCtx.getText());

            IfCommand ifCommand = new IfCommand(ctx.parExpression());
            StatementControlOverseer.getInstance().openConditionalCommand(ifCommand);

            StatementAnalyzer statementAnalyzer = new StatementAnalyzer();
            statementAnalyzer.analyze(statementCtx);

            StatementControlOverseer.getInstance().reportExitPositiveRule();

            //check if there is an ELSE statement
            if(isELSEStatement(ctx)) {
                statementCtx = ctx.statement(1);

                //Console.log(LogType.DEBUG, "ELSE statement: " +statementCtx.getText());
                statementAnalyzer.analyze(statementCtx);
            }

            StatementControlOverseer.getInstance().compileControlledCommand();
        }

        else if(isWHILEStatement(ctx)) {
            //Console.log(LogType.DEBUG, "While par expression: " +ctx.parExpression().getText());

            ThanosParser.StatementContext statementCtx = ctx.statement(0);

            WhileCommand whileCommand = new WhileCommand(ctx.parExpression());
            StatementControlOverseer.getInstance().openControlledCommand(whileCommand);

            StatementAnalyzer statementAnalyzer = new StatementAnalyzer();
            statementAnalyzer.analyze(statementCtx);

            StatementControlOverseer.getInstance().compileControlledCommand();
            //Console.log(LogType.DEBUG, "End of WHILE expression: " +ctx.parExpression().getText());
        }

        else if(isDOWHILEStatement(ctx)) {
            //Console.log(LogType.DEBUG, "Do while expression: " +ctx.parExpression().getText());

            ThanosParser.StatementContext statementCtx = ctx.statement(0);

            DoWhileCommand doWhileCommand = new DoWhileCommand(ctx.parExpression());
            StatementControlOverseer.getInstance().openControlledCommand(doWhileCommand);

            StatementAnalyzer statementAnalyzer = new StatementAnalyzer();
            statementAnalyzer.analyze(statementCtx);

            StatementControlOverseer.getInstance().compileControlledCommand();
            //Console.log(LogType.DEBUG, "End of DO-WHILE expression: " +ctx.parExpression().getText());
        }

        else if(isFORStatement(ctx)) {
//            Console.log(LogType.DEBUG, "FOR expression: " +ctx.forControl().getText());

            LocalScopeHandler.getInstance().openLocalScope();

            ForControlAnalyzer forControlAnalyzer = new ForControlAnalyzer();
            forControlAnalyzer.analyze(ctx.forControl());

            ForCommand forCommand = new ForCommand(forControlAnalyzer.getLocalVarDecContext(), forControlAnalyzer.getExprContext(), forControlAnalyzer.getUpdateCommand());
            StatementControlOverseer.getInstance().openControlledCommand(forCommand);

            ThanosParser.StatementContext statementCtx = ctx.statement(0);
            StatementAnalyzer statementAnalyzer = new StatementAnalyzer();
            statementAnalyzer.analyze(statementCtx);

            StatementControlOverseer.getInstance().compileControlledCommand();

            LocalScopeHandler.getInstance().closeLocalScope();
//            Console.log(LogType.DEBUG, "End of FOR loop");
        }

        else if(isRETURNStatement(ctx) && ExecutionManager.getInstance().isInFunctionExecution()) {
//            Console.log(LogType.DEBUG, "Detected return expression: " +ctx.expression(0).getText());
            this.handleReturnStatement(ctx.expression());
        }
    }

    private void handlePrintStatement(ThanosParser.PrintStatementContext ctx) {
//        System.out.println("HANDLE PRINT: " + ctx.expression().size());

        PrintCommand printCommand = new PrintCommand(ctx.expression());

        StatementControlOverseer statementControl = StatementControlOverseer.getInstance();
        //add to conditional controlled command
        if(statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(printCommand);
            }
            else {
                conditionalCommand.addNegativeCommand(printCommand);
            }
        }

        else if(statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(printCommand);
        }
        else {
            ExecutionManager.getInstance().addCommand(printCommand);
        }

    }

    private void handleScanStatement(ThanosParser.ScanStatementContext ctx) {
        ScanCommand scanCommand = new ScanCommand(ctx.expression().getText(), ctx.Identifier().getText());
        UndeclaredChecker.verifyVarOrConstForScan(ctx.Identifier().getText(), ctx);

        StatementControlOverseer statementControl = StatementControlOverseer.getInstance();
        //add to conditional controlled command
        if(statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(scanCommand);
            }
            else {
                conditionalCommand.addNegativeCommand(scanCommand);
            }
        }

        else if(statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(scanCommand);
        }
        else {
            ExecutionManager.getInstance().addCommand(scanCommand);
        }

    }

    private void handleReturnStatement(ThanosParser.ExpressionContext exprCtx) {
        ReturnCommand returnCommand = new ReturnCommand(exprCtx, ExecutionManager.getInstance().getCurrentFunction());
        /*
         * TODO: Return commands supposedly stops a controlled or conditional command and returns back the control to the caller.
         * Find a way to halt such commands if they are inside a controlled command.
         */
        StatementControlOverseer statementControl = StatementControlOverseer.getInstance();

        if(statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(returnCommand);
            }
            else {
                String functionName = ExecutionManager.getInstance().getCurrentFunction().getFunctionName();
                conditionalCommand.addNegativeCommand(returnCommand);
            }
        }

        else if(statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(returnCommand);
        }
        else {
            ExecutionManager.getInstance().addCommand(returnCommand);
        }

    }

    public static boolean isIFStatement(ThanosParser.StatementContext ctx) {
        List<TerminalNode> tokenList = ctx.getTokens(ThanosLexer.IF);

        return (tokenList.size() != 0);
    }

    public static boolean isELSEStatement(ThanosParser.StatementContext ctx) {
        List<TerminalNode> tokenList = ctx.getTokens(ThanosLexer.ELSE);

        return (tokenList.size() != 0);
    }

    public static boolean isWHILEStatement(ThanosParser.StatementContext ctx) {
        List<TerminalNode> whileTokenList = ctx.getTokens(ThanosLexer.WHILE);
        List<TerminalNode> doTokenList = ctx.getTokens(ThanosLexer.DO);

        return (whileTokenList.size() != 0 && doTokenList.size() == 0);
    }

    public static boolean isDOWHILEStatement(ThanosParser.StatementContext ctx) {
        List<TerminalNode> whileTokenList = ctx.getTokens(ThanosLexer.WHILE);
        List<TerminalNode> doTokenList = ctx.getTokens(ThanosLexer.DO);

        return (whileTokenList.size() != 0 && doTokenList.size() != 0);
    }

    public static boolean isFORStatement(ThanosParser.StatementContext ctx) {
        List<TerminalNode> forTokenList = ctx.getTokens(ThanosLexer.FOR);

        return (forTokenList.size() != 0);
    }

    public static boolean isRETURNStatement(ThanosParser.StatementContext ctx) {
        List<TerminalNode> returnTokenList = ctx.getTokens(ThanosLexer.RETURN);

        return (returnTokenList.size() != 0);
    }
}
