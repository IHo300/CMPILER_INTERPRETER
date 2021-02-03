package representations;

import Commands.ControlTypeEnum;
import Commands.ICommand;
import Commands.IControlledCommand;
import errors.TypeChecker;
import Execution.ExecutionManager;
import Execution.ExecutionMonitor;
import Execution.FunctionTracker;
import GeneratedAntlrClasses.ThanosParser;
import scope.ThanosScope;
import scope.LocalScope;
import Utlities.KeywordRecognizer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class ThanosFunction implements IControlledCommand{

    private String functionName;
    private List<ICommand> commandSequences; //the list of commands execution by the function

    private LocalScope parentLocalScope; //refers to the parent local scope of this function.

    private LinkedHashMap<String, ThanosScope> parameterReferences; //the list of parameters accepted that follows the 'call-by-reference' standard.
    private LinkedHashMap<String, representations.ThanosValue> parameterValues;	//the list of parameters accepted that follows the 'call-by-value' standard.
    private representations.ThanosValue returnValue; //the return value of the function. null if it's a void type
    private FunctionType returnType = FunctionType.VOID_TYPE; //the return type of the function

    public ThanosFunction() {
        this.commandSequences = new ArrayList<ICommand>();
        this.parameterValues = new LinkedHashMap<String, representations.ThanosValue>();
        this.parameterReferences = new LinkedHashMap<String, ThanosScope>();
    }

    public void setParentLocalScope(LocalScope localScope) {
        this.parentLocalScope = localScope;
    }

    public LocalScope getParentLocalScope() {
        return this.parentLocalScope;
    }

    public void setReturnType(FunctionType functionType) {
        this.returnType = functionType;

        //create an empty mobi value as a return value
        switch(this.returnType) {
            case BOOLEAN_TYPE: this.returnValue = new representations.ThanosValue(true, representations.PrimitiveType.BOOLEAN); break;
            case CHAR_TYPE: this.returnValue = new representations.ThanosValue(' ', representations.PrimitiveType.CHAR); break;
            case INT_TYPE: this.returnValue = new representations.ThanosValue(0, representations.PrimitiveType.INT); break;
            case FLOAT_TYPE: this.returnValue = new representations.ThanosValue(0, representations.PrimitiveType.FLOAT); break;
            case STRING_TYPE: this.returnValue = new representations.ThanosValue("", representations.PrimitiveType.STRING); break;
            default:break;
        }
    }

    public FunctionType getReturnType() {
        return this.returnType;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    /*
     * Maps parameters by values, which means that the value is copied to its parameter listing
     */
    public void mapParameterByValue(String... values) {
        for(int i = 0; i < values.length; i++) {
            representations.ThanosValue ThanosValue = this.getParameterAt(i);
            ThanosValue.setValue(values[i]);
        }
    }

    public void mapParameterByValueAt(String value, int index) {
        if(index >= this.parameterValues.size()) {
            return;
        }

        representations.ThanosValue mobiValue = this.getParameterAt(index);
        mobiValue.setValue(value);
    }

    public void mapArrayAt(representations.ThanosValue mobiValue, int index, String identifier) {
        if(index >= this.parameterValues.size()) {
            return;
        }

        representations.ThanosArray mobiArray = (representations.ThanosArray) mobiValue.getValue();

        representations.ThanosArray newArray = new representations.ThanosArray(mobiArray.getPrimitiveType(), identifier);
        representations.ThanosValue newValue = new representations.ThanosValue(newArray, representations.PrimitiveType.ARRAY);

        newArray.initializeSize(mobiArray.getSize());

        for(int i = 0; i < newArray.getSize(); i++) {
            newArray.updateValueAt(mobiArray.getValueAt(i), i);
        }

        this.parameterValues.put(this.getParameterKeyAt(index), newValue);

    }

    public int getParameterValueSize() {
        return this.parameterValues.size();
    }

    public void verifyParameterByValueAt(ThanosParser.ExpressionContext exprCtx, int index) {
        if(index >= this.parameterValues.size()) {
            return;
        }

        representations.ThanosValue ThanosValue = this.getParameterAt(index);
        TypeChecker typeChecker = new TypeChecker(ThanosValue, exprCtx);
        typeChecker.verify();
    }

    /*
     * Maps parameters by reference, in this case, accept a class scope.
     */
    public void mapParameterByReference(ThanosScope... classScopes) {
        System.err.println("Mapping of parameter by reference not yet supported.");
    }

    public void addParameter(String identifierString, representations.ThanosValue ThanosValue) {
        this.parameterValues.put(identifierString, ThanosValue);
      //  Console.log(LogType.DEBUG, this.functionName + " added an empty parameter " +identifierString+ " type " +mobiValue.getPrimitiveType());
    }

    public boolean hasParameter(String identifierString) {
        return this.parameterValues.containsKey(identifierString);
    }
    public representations.ThanosValue getParameter(String identifierString) {
        if(this.hasParameter(identifierString)) {
            return this.parameterValues.get(identifierString);
        }
        else {
            System.err.println(identifierString + " not found in parameter list");
            return null;
        }
    }

    public representations.ThanosValue getParameterAt(int index) {
        int i = 0;

        for(representations.ThanosValue ThanosValue : this.parameterValues.values()) {
            if(i == index) {
                return ThanosValue;
            }

            i++;
        }

        System.err.println(index + " has exceeded parameter list.");
        return null;
    }

    private String getParameterKeyAt(int index) {
        int i = 0;

        for(String key : this.parameterValues.keySet()) {
            if(i == index) {
                return key;
            }

            i++;
        }

        System.err.println(index + " has exceeded parameter list.");
        return null;
    }

    public representations.ThanosValue getReturnValue() {
        if(this.returnType == FunctionType.VOID_TYPE) {
//            Console.log(LogType.DEBUG, this.functionName + " is a void function. Null mobi value is returned");
            return null;
        }
        else {
            return this.returnValue;
        }
    }

    @Override
    public void addCommand(ICommand command) {
        this.commandSequences.add(command);
        //Console.log("Command added to " +this.functionName);
    }

    @Override
    public void execute() {
        ExecutionMonitor executionMonitor = ExecutionManager.getInstance().getExecutionMonitor();
        FunctionTracker.getInstance().reportEnterFunction(this);
        try {
            for(ICommand command : this.commandSequences) {
                executionMonitor.tryExecution();
                command.execute();
            }

        } catch(InterruptedException e) {
            System.err.println("Monitor block interrupted! " +e.getMessage());
        }

        FunctionTracker.getInstance().reportExitFunction();
    }

    @Override
    public ControlTypeEnum getControlType() {
        return ControlTypeEnum.FUNCTION_TYPE;
    }

    public static FunctionType identifyFunctionType(String primitiveTypeString) {

        if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_BOOLEAN, primitiveTypeString)) {
            return FunctionType.BOOLEAN_TYPE;
        }

        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_CHAR, primitiveTypeString)) {
            return FunctionType.CHAR_TYPE;
        }
        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_FLOAT, primitiveTypeString)) {
            return FunctionType.FLOAT_TYPE;
        }
        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_INT, primitiveTypeString)) {
            return FunctionType.INT_TYPE;
        }

        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_STRING, primitiveTypeString)) {
            return FunctionType.STRING_TYPE;
        }

        return FunctionType.VOID_TYPE;
    }
}
