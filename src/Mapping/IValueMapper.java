package Mapping;

import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosValue;

public interface IValueMapper {
    public abstract void analyze(ThanosParser.ParExpressionContext exprCtx);
    public abstract void analyze(ThanosParser.ExpressionContext exprCtx);
    public abstract String getOriginalExp();
    public abstract String getModifiedExp();
    public abstract ThanosValue getThanosValue();
}
