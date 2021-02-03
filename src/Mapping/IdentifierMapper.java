package Mapping;

import Execution.FunctionTracker;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosValue;

public class IdentifierMapper implements IValueMapper{

    private IValueMapper valueMapper;

    public IdentifierMapper(String originalExp) {
        if(FunctionTracker.getInstance().isInsideFunction()) {
            this.valueMapper = new FunctionIdentifierMapper(originalExp, FunctionTracker.getInstance().getLatestFunction());
        } else{
            this.valueMapper = new ThanosIdentifierMapper(originalExp);
        }
    }

    @Override
    public void analyze(ThanosParser.ExpressionContext exprCtx) {
        this.valueMapper.analyze(exprCtx);
    }

    @Override
    public void analyze(ThanosParser.ParExpressionContext exprCtx) {
        this.valueMapper.analyze(exprCtx);
    }

    @Override
    public String getOriginalExp() {
        return this.valueMapper.getOriginalExp();
    }

    @Override
    public String getModifiedExp() {
        return this.valueMapper.getModifiedExp();
    }

    @Override
    public ThanosValue getThanosValue() {
        return this.valueMapper.getThanosValue();
    }
}
