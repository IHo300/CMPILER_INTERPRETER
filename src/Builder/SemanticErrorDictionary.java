package Builder;

import java.util.HashMap;

public class SemanticErrorDictionary {
    private final static String TAG = "MobiProg_ErrorRepository";

    public final static int INCONSISTENT_CLASS_NAME = 1000;
    public final static int TYPE_MISMATCH = 2000;
    public final static int UNDECLARED_VARIABLE = 3000;
    public final static int UNDECLARED_FUNCTION = 3500;
    public final static int CONST_REASSIGNMENT = 3100;
    public final static int MULTIPLE_VARIABLE = 5000;
    public final static int MULTIPLE_FUNCTION = 5500;
    public final static int PARAMETER_COUNT_MISMATCH = 6000;
    public final static int RUNTIME_ARRAY_OUT_OF_BOUNDS = 7000;
    public final static int MISSING_THIS_KEYWORD = 8000;

    private static SemanticErrorDictionary instance = null;

    private HashMap<Integer, String> errorMessageDictionary;

    private SemanticErrorDictionary() {
        this.errorMessageDictionary = new HashMap<>();
        this.populateErrorMessages();
    }

    private void populateErrorMessages() {
        this.errorMessageDictionary.put(SemanticErrorDictionary.INCONSISTENT_CLASS_NAME, "Inconsistent class name. ");
        this.errorMessageDictionary.put(TYPE_MISMATCH, "Type mismatch at line %d. ");
        this.errorMessageDictionary.put(UNDECLARED_VARIABLE, "Undeclared variable %s at line %d. ");
        this.errorMessageDictionary.put(UNDECLARED_FUNCTION, "Undeclared function %s at line %d. ");
        this.errorMessageDictionary.put(CONST_REASSIGNMENT, "Constant %s can no longer reassign a new value at line %d.");
        this.errorMessageDictionary.put(MULTIPLE_VARIABLE, "Duplicate declaration of variable %s at line %d. ");
        this.errorMessageDictionary.put(MULTIPLE_FUNCTION, "Duplicate method declaration %s at line %d. ");
        this.errorMessageDictionary.put(PARAMETER_COUNT_MISMATCH, "Argument size for method call %s at line %d does not match with its declaration. ");
        this.errorMessageDictionary.put(RUNTIME_ARRAY_OUT_OF_BOUNDS, "Array %s out of bounds. Aborting operation. ");
        this.errorMessageDictionary.put(MISSING_THIS_KEYWORD, "Missing 'this' keyword for method call %s line %d.");
    }

    public static void initialize() {
        instance = new SemanticErrorDictionary();
    }

    public static void reset() {
        instance.errorMessageDictionary.clear();
        instance.populateErrorMessages();
    }

    public static String getErrorMessage(int errorCode) {
        return instance.errorMessageDictionary.getOrDefault(errorCode, "Error code "+errorCode+ " not found.");
    }
}