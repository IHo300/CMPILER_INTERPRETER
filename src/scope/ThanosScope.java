package scope;


import representations.ThanosValue;

public class ThanosScope implements IScope{
    private final static String TAG = "MobiProg_ClassScope";


    private HashMap<String, ThanosValue> variables;

    private HashMap<String, ThanosFunction> functions;

    private LocalScope parentLocalScope; //represents the parent local scope which is the local scope covered by the main() function. Other classes may not contain this.

    public ThanosScope() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    /*
     * Sets the parent local scope which is instantiated if this class contains a main function.
     */
    public void setParentLocalScope(LocalScope localScope) {
        this.parentLocalScope = localScope;
    }

    @Override
    /*
     * A class scope is automatically the parent of local scopes.
     * (non-Javadoc)
     * @see com.neildg.mobiprog.semantics.symboltable.scopes.IScope#isParent()
     */
    public boolean isParent(){
        return true;
    }

    /*
     * Attempts to add an empty variable based from keywords
     */
    public void addEmptyVariable(String primitiveTypeString, String identifierString) {

        //create empty mobi value
        ThanosValue ThanosValue = ThanosValue.createEmptyVariable(primitiveTypeString);

        this.variables.put(identifierString, ThanosValue);
    }

    /*
     * Attempts to add an initialized variable mobi value
     */
    public void addInitializedVariable(String primitiveTypeString, String identifierString, String valueString) {
        this.addEmptyVariable( primitiveTypeString, identifierString);

        ThanosValue ThanosValue = this.variables.get(identifierString);

        ThanosValue.setValue(valueString);

    }

    public ThanosValue getVariable(String identifier){
        if(this.containsVariable(identifier)) {
            return this.variables.get(identifier);
        }
        else {
            System.err.println(identifier + " is not found"); //TODO Change to IDE
            return null;
        }
    }

    public void addFunction(String identifier, ThanosFunction ThanosFunction){
        this.functions.put(identifier, ThanosFunction);
        //Console.log(LogType.DEBUG, "Created private function " +identifier+ " with return type " +mobiFunction.getReturnType());
    }


    public void addThanosValue(String identifier, ThanosValue ThanosValue) {
        this.variables.put(identifier, ThanosValue);
    }

    public ThanosFunction getFunction(String identifier) {
        if(this.containsFunction(identifier)) {
            return this.functions.get(identifier);
        }
        else {
            System.err.println(identifier + " is not found"); //TODO Change to IDE
            return null;
        }
    }


    public ThanosFunction searchFunction(String identifier) {
        if(this.containsFunction(identifier)) {
            return this.functions.get(identifier);
        }
        else {
            //Log.e(TAG, identifier + " is not found in " +this.className);
            System.err.println(identifier + " is not found"); //TODO Change to IDE
            return null;
        }
    }

    public boolean containsFunction(String identifier) {
        return this.functions.containsKey(identifier);
    }

    @Override
    /* Attempts to find a variable first in the private and public variable storage, then on the local scopes.
     * (non-Javadoc)
     * @see com.neildg.mobiprog.semantics.symboltable.scopes.IScope#getVariable(java.lang.String)
     */
    public ThanosValue searchVariableIncludingLocal(String identifier) {
        ThanosValue value;
        if(this.containsVariable(identifier)) {
            value = this.getVariable(identifier);
        }
        else {
            value = LocalScopeHandler.searchVariableInLocalIterative(identifier, this.parentLocalScope);
        }

        return value;
    }

    public ThanosValue searchVariable(String identifier) {
        ThanosValue value = null;
        if(this.containsVariable(identifier)) {
            value = this.getVariable(identifier);
        }

        return value;
    }

    public boolean containsVariable(String identifier) {
        return this.variables.containsKey(identifier);
    }

    /*
     * Resets all stored variables. This is called after the execution manager finishes
     */
    public void resetValues() {
        ThanosValue[] ThanosValues = this.variables.values().toArray(new ThanosValue[this.variables.size()]);

        for(int i = 0; i < ThanosValues.length; i++) {
            ThanosValues[i].reset();
        }
    }
}
