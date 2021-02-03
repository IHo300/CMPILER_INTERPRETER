package errors;

import Builder.BuildChecker;
import Builder.SemanticErrorDictionary;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosValue;
import representations.PrimitiveType;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TypeChecker implements IErrorChecker, ParseTreeListener {

    private ThanosValue ThanosValue;
    private ThanosParser.ExpressionContext exprCtx;
    private int lineNumber;

    public TypeChecker(ThanosValue assignmentThanosValue, ThanosParser.ExpressionContext exprCtx) {
        this.ThanosValue = assignmentThanosValue;
        this.exprCtx = exprCtx;

        Token firstToken = exprCtx.getStart();
        this.lineNumber = firstToken.getLine();
    }

    @Override
    public void verify() {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, this.exprCtx);
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    @Override
    public void visitTerminal(TerminalNode node) {

    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.LiteralContext) {
            if(this.ThanosValue == null) {
                return;
            }
            ThanosParser.LiteralContext literalCtx = (ThanosParser.LiteralContext) ctx;
            String expressionString = literalCtx.getText();

            if(this.ThanosValue.getPrimitiveType() == PrimitiveType.ARRAY) {

            }
            else if(this.ThanosValue.getPrimitiveType() == PrimitiveType.BOOLEAN) {
                if(literalCtx.BooleanLiteral() == null) {
                    String additionalMessage = "Expected boolean.";
                    BuildChecker.reportCustomError(SemanticErrorDictionary.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
            else if(this.ThanosValue.getPrimitiveType() == PrimitiveType.INT) {
                if(literalCtx.IntegerLiteral() == null) {
                    String additionalMessage = "Expected int.";
                    BuildChecker.reportCustomError(SemanticErrorDictionary.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
            else if(this.ThanosValue.getPrimitiveType() == PrimitiveType.FLOAT) {
                if(literalCtx.FloatingPointLiteral() == null) {
                    String additionalMessage = "Expected floating point or double.";
                    BuildChecker.reportCustomError(SemanticErrorDictionary.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }

            else if(this.ThanosValue.getPrimitiveType() == PrimitiveType.STRING) {
                if(expressionString.charAt(0) != '\"' && expressionString.charAt(expressionString.length() - 1) != '\"') {
                    String additionalMessage = "Expected string.";
                    BuildChecker.reportCustomError(SemanticErrorDictionary.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }

                else if(literalCtx.StringLiteral() == null) {
                    String additionalMessage = "Expected string.";
                    BuildChecker.reportCustomError(SemanticErrorDictionary.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

}