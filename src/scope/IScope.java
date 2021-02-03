package scope;

import representations.ThanosValue;

public interface IScope {
    public abstract ThanosValue searchVariableIncludingLocal(String identifier);
    public abstract boolean isParent();
}
