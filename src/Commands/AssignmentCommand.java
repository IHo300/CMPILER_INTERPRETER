package Commands;

import Analyzers.FunctionCallVerifier;
import errors.ConstChecker;
import errors.TypeChecker;
import errors.UndeclaredChecker;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosArray;
import representations.ThanosValue;
import Searcher.VariableSearcher;
import Utlities.AssignmentUtilities;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class AssignmentCommand implements ICommand{


    private ThanosParser.ExpressionContext leftHandExprCtx;
    private ThanosParser.ExpressionContext rightHandExprCtx;

    public AssignmentCommand(ThanosParser.ExpressionContext leftHandExprCtx,
                             ThanosParser.ExpressionContext rightHandExprCtx) {
        this.leftHandExprCtx = leftHandExprCtx;
        this.rightHandExprCtx = rightHandExprCtx;

        UndeclaredChecker undeclaredChecker = new UndeclaredChecker(this.leftHandExprCtx);
        undeclaredChecker.verify();

        ConstChecker constChecker = new ConstChecker(this.leftHandExprCtx);
        constChecker.verify();

        undeclaredChecker = new UndeclaredChecker(this.rightHandExprCtx);
        undeclaredChecker.verify();

        ParseTreeWalker functionWalker = new ParseTreeWalker();
        functionWalker.walk(new FunctionCallVerifier(), this.rightHandExprCtx);

        ThanosValue ThanosValue;
        if(ExecutionManager.getInstance().isInFunctionExecution()) {
            ThanosValue = VariableSearcher.searchVariableInFunction(ExecutionManager.getInstance().getCurrentFunction(), this.leftHandExprCtx.getText());
        }
        else {
            ThanosValue = VariableSearcher.searchVariable(this.leftHandExprCtx.getText());
        }

        TypeChecker typeChecker = new TypeChecker(ThanosValue, this.rightHandExprCtx);
        typeChecker.verify();
    }

    @Override
    public void execute() {
        EvaluationCommand evaluationCommand = new EvaluationCommand(this.rightHandExprCtx);
        evaluationCommand.execute();

        if(this.isLeftHandArrayAccessor()) {
            this.handleArrayAssignment(evaluationCommand.getResult().toEngineeringString());
        }
        else {
            ThanosValue ThanosValue = VariableSearcher.searchVariable(this.leftHandExprCtx.getText());
            AssignmentUtilities.assignAppropriateValue(ThanosValue, evaluationCommand.getResult());
        }
    }

    private boolean isLeftHandArrayAccessor() {
        List<TerminalNode> lBrackTokens = this.leftHandExprCtx.getTokens(ThanosLexer.LBRACK);
        List<TerminalNode> rBrackTokens = this.leftHandExprCtx.getTokens(ThanosLexer.RBRACK);

        return(lBrackTokens.size() > 0 && rBrackTokens.size() > 0);
    }

    private void handleArrayAssignment(String resultString) {
        TerminalNode identifierNode = this.leftHandExprCtx.expression(0).primary().Identifier();
        ThanosParser.ExpressionContext arrayIndexExprCtx = this.leftHandExprCtx.expression(1);

        ThanosValue ThanosValue = VariableSearcher.searchVariable(identifierNode.getText());
        ThanosArray ThanosArray = (ThanosArray) ThanosValue.getValue();

        EvaluationCommand evaluationCommand = new EvaluationCommand(arrayIndexExprCtx);
        evaluationCommand.execute();

        //create a new array value to replace value at specified index
        ThanosValue newArrayValue = new ThanosValue(null, ThanosArray.getPrimitiveType());
        newArrayValue.setValue(resultString);
        ThanosArray.updateValueAt(newArrayValue, evaluationCommand.getResult().intValue());

        //Console.log("Index to access: " +evaluationCommand.getResult().intValue()+ " Updated with: " +resultString);
    }
}