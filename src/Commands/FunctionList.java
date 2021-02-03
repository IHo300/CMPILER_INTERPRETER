package Commands;

import java.util.ArrayList;

public class FunctionList {

    private static FunctionList instance = null;
    private ArrayList<String> methodNames;

    private FunctionList() {
        this.methodNames = new ArrayList<String>();
    }

    public static FunctionList getInstance() {
        if (instance == null) {
            instance = new FunctionList();

        }

        return instance;
    }

    public void addMethodName(String methodName) {
        this.methodNames.add(methodName);
    }

    public boolean methodNameExists(String methodName) {
        return this.methodNames.contains(methodName);
    }

    public ArrayList<String> getMethodNames() {
        return this.methodNames;
    }
}