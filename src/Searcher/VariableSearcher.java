package Searcher;

import Execution.FunctionTracker;
import representations.ThanosFunction;
import representations.ThanosValue;
import scope.ThanosScope;
import scope.LocalScopeHandler;
import scope.SymbolTableManager;

public class VariableSearcher {

    public static ThanosValue searchVariable(String identifierString) {
        ThanosValue ThanosValue = null;

        if(FunctionTracker.getInstance().isInsideFunction()) {
            ThanosValue = searchVariableInFunction(FunctionTracker.getInstance().getLatestFunction(), identifierString);
        }

        if(ThanosValue == null) {
            ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
            ThanosValue = searchVariableInClassIncludingLocal(ThanosScope, identifierString);
        }

        return ThanosValue;
    }

    public static ThanosValue searchVariableInFunction(ThanosFunction ThanosFunction, String identifierString) {
        ThanosValue ThanosValue = null;

        if(ThanosFunction.hasParameter(identifierString)) {
            ThanosValue = ThanosFunction.getParameter(identifierString);
        }
        else {
            ThanosValue = LocalScopeHandler.searchVariableInLocalIterative(identifierString, ThanosFunction.getParentLocalScope());
        }

        return ThanosValue;
    }

    public static ThanosValue searchVariableInClassIncludingLocal(ThanosScope ThanosScope, String identifierString) {
        return ThanosScope.searchVariableIncludingLocal(identifierString);
    }

    public static ThanosValue searchVariableInClass(ThanosScope ThanosScope, String identifierString) {
        return ThanosScope.getVariable(identifierString);
    }

}