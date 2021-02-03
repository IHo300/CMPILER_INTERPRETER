package representations;

import Execution.FunctionTracker;
import scope.ThanosScope;
import scope.LocalScopeHandler;
import scope.SymbolTableManager;

public class ThanosValueSearcher {

    public static representations.ThanosValue searchVariable(String identifierString) {
        representations.ThanosValue ThanosValue = null;

        if(FunctionTracker.getInstance().isInsideFunction()) {
            ThanosValue = searchVariableInFunction(FunctionTracker.getInstance().getLatestFunction(), identifierString);
        }

        if(ThanosValue == null) {
            ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
            ThanosValue = searchVariableInClassIncludingLocal(ThanosScope, identifierString);
        }

        return ThanosValue;
    }

    public static representations.ThanosValue searchVariableInFunction(ThanosFunction mobiFunction, String identifierString) {
        representations.ThanosValue ThanosValue = null;

        if(mobiFunction.hasParameter(identifierString)) {
            ThanosValue = mobiFunction.getParameter(identifierString);
        }
        else {
            ThanosValue = LocalScopeHandler.searchVariableInLocalIterative(identifierString, mobiFunction.getParentLocalScope());
        }

        return ThanosValue;
    }

    public static representations.ThanosValue searchVariableInClassIncludingLocal(ThanosScope ThanosScope, String identifierString) {
        return ThanosScope.searchVariableIncludingLocal(identifierString);
    }

    public static representations.ThanosValue searchVariableInClass(ThanosScope ThanosScope, String identifierString) {
        return ThanosScope.getVariable(identifierString);
    }

}
