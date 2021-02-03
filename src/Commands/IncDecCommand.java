package Commands;

import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import Mapping.IValueMapper;
import Mapping.IdentifierMapper;
import representations.ThanosValue;
import representations.PrimitiveType;

public class IncDecCommand implements ICommand {

    private ThanosParser.ExpressionContext exprCtx;
    private int tokenSign;

    public IncDecCommand(ThanosParser.ExpressionContext exprCtx, int tokenSign) {
        this.exprCtx = exprCtx;
        this.tokenSign = tokenSign;
    }

    @Override
    public void execute() {

        IValueMapper leftHandMapper = new IdentifierMapper(
                this.exprCtx.getText());
        leftHandMapper.analyze(this.exprCtx);

        ThanosValue ThanosValue = leftHandMapper.getThanosValue();

        this.performOperation(ThanosValue);
    }

    /*
     * Attempts to perform an increment/decrement operation
     */
    private void performOperation(ThanosValue ThanosValue) {
        if(ThanosValue.getPrimitiveType() == PrimitiveType.INT) {
            int value = Integer.parseInt(ThanosValue.getValue().toString());

            if(this.tokenSign == ThanosLexer.INC) {
                value++;
                ThanosValue.setValue(String.valueOf(value));
            }
            else if(this.tokenSign == ThanosLexer.DEC) {
                value--;
                ThanosValue.setValue(String.valueOf(value));
            }
        }
        else if(ThanosValue.getPrimitiveType() == PrimitiveType.FLOAT) {
            float value = Float.parseFloat(ThanosValue.getValue().toString());

            if(this.tokenSign == ThanosLexer.INC) {
                value++;
                ThanosValue.setValue(String.valueOf(value));
            }
            else if(this.tokenSign == ThanosLexer.DEC) {
                value--;
                ThanosValue.setValue(String.valueOf(value));
            }
        }
    }

}