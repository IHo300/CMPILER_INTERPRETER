package scope;

import java.util.HashMap;

public class SymbolTableManager {
    private static SymbolTableManager instance = null;
    private ThanosScope ThanosScope;
    private HashMap<String, LocalScope> classTable;


    public static SymbolTableManager getInstance(){
        if(instance == null){
            instance = new SymbolTableManager();
        }
        return instance;
    }

    public static void initialize() {
        instance = new SymbolTableManager();
    }
    private SymbolTableManager(){
        ThanosScope = new ThanosScope();
    }

    public ThanosScope getMainScope(){
        return ThanosScope;
    }



    public void resetClassTables() {
        ThanosScope[] classScopes = this.classTable.values().toArray(new ThanosScope[this.classTable.size()]);

        for(int i = 0; i < classScopes.length; i++) {
            classScopes[i].resetValues();
        }
    }
}
