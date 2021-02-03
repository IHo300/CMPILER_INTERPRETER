package Analyzers;

import Commands.EvaluationCommand;
import errors.ParameterMismatchChecker;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import scope.ThanosScope;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

public class FunctionCallVerifier implements ParseTreeListener {

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
            if (EvaluationCommand.isFunctionCall(exprCtx)) {
                if(exprCtx.expression(0) == null)
                    return;

                String functionName = exprCtx.expression(0).getText();

                ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
                ThanosFunction ThanosFunction = ThanosScope.getFunction(functionName);

                if (exprCtx.arguments() != null) {
                    ParameterMismatchChecker paramsMismatchChecker = new ParameterMismatchChecker(ThanosFunction, exprCtx.arguments());
                    paramsMismatchChecker.verify();
                }
            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

}