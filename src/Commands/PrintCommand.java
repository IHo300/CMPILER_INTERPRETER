package Commands;

import errors.UndeclaredChecker;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosArray;
import representations.ThanosValue;
import representations.ThanosValueSearcher;
import representations.PrimitiveType;
import Utlities.StringUtilities;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class PrintCommand implements ICommand, ParseTreeListener {

    private ThanosParser.ExpressionContext expressionCtx;

    private String statementToPrint = "";
    private boolean complexExpr = false;
    private boolean arrayAccess = false;

    public PrintCommand(ThanosParser.ExpressionContext expressionCtx) {
        this.expressionCtx = expressionCtx;

        UndeclaredChecker undeclaredChecker = new UndeclaredChecker(this.expressionCtx);
        undeclaredChecker.verify();
    }

    @Override
    public void execute() {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, this.expressionCtx);

        System.out.println(this.statementToPrint);//TODO PRINT STATEMENT ON FRONT END
        this.statementToPrint = ""; //reset statement to print afterwards
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitErrorNode(ErrorNode node) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.LiteralContext) {
            ThanosParser.LiteralContext literalCtx = (ThanosParser.LiteralContext) ctx;

            if(literalCtx.StringLiteral() != null) {
                String quotedString = literalCtx.StringLiteral().getText();

                this.statementToPrint += StringUtilities.removeQuotes(quotedString);
            }
			else if(literalCtx.IntegerLiteral() != null) {
				int value = Integer.parseInt(literalCtx.IntegerLiteral().getText());
				this.statementToPrint += value;
			}

			else if(literalCtx.FloatingPointLiteral() != null) {
				float value = Float.parseFloat(literalCtx.FloatingPointLiteral().getText());
				this.statementToPrint += value;
			}

			else if(literalCtx.BooleanLiteral() != null) {
				this.statementToPrint += literalCtx.BooleanLiteral().getText();
			}

			else if(literalCtx.CharacterLiteral() != null) {
                String quotedString = literalCtx.CharacterLiteral().getText();
                this.statementToPrint +=  StringUtilities.removeQuotes(quotedString);
			}
        }

        else if(ctx instanceof ThanosParser.PrimaryContext) {
            ThanosParser.PrimaryContext primaryCtx = (ThanosParser.PrimaryContext) ctx;

            if(primaryCtx.expression() != null) {
                ThanosParser.ExpressionContext exprCtx = primaryCtx.expression();
                this.complexExpr = true;
//                Console.log(LogType.DEBUG, "Complex expression detected: " +exprCtx.getText());

                EvaluationCommand evaluationCommand = new EvaluationCommand(exprCtx);
                evaluationCommand.execute();

                this.statementToPrint += evaluationCommand.getResult().toEngineeringString();
            }

            else if(primaryCtx.Identifier() != null && this.complexExpr == false) {
                String identifier = primaryCtx.getText();

                ThanosValue value = ThanosValueSearcher.searchVariable(identifier);
                if(value.getPrimitiveType() == PrimitiveType.ARRAY) {
                    this.arrayAccess = true;
                    this.evaluateArrayPrint(value, primaryCtx);
                }
                else if(this.arrayAccess == false) {
                    this.statementToPrint += value.getValue();
                }


            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }

    public String getStatementToPrint() {
        return this.statementToPrint;
    }

    private void evaluateArrayPrint(ThanosValue ThanosValue, ThanosParser.PrimaryContext primaryCtx) {

        //move up and determine expression contexts
        ThanosParser.ExpressionContext parentExprCtx = (ThanosParser.ExpressionContext) primaryCtx.getParent().getParent();
        ThanosParser.ExpressionContext arrayIndexExprCtx = parentExprCtx.expression(1);

        EvaluationCommand evaluationCommand = new EvaluationCommand(arrayIndexExprCtx);
        evaluationCommand.execute();

        ThanosArray ThanosArray = (ThanosArray) ThanosValue.getValue();
        ThanosValue arrayThanosValue = ThanosArray.getValueAt(evaluationCommand.getResult().intValue());

        this.statementToPrint += arrayThanosValue.getValue().toString();
    }



}