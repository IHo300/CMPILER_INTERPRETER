package Controller;

import ANTLR.ThanosError;
import Builder.BuildChecker;
import Builder.ParserHandler;
import Execution.ExecutionManager;
import IDE.View;
import scope.LocalScopeHandler;
import Statements.StatementControlOverseer;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private View view;

    public Controller(View view) {
        this.view = view;
    }

    public void printErrorsInConsole (ArrayList<ThanosError> errors) {

        for (int i = 0; i < errors.size(); i++) {
            printErrorInConsole(errors.get(i));
        }

    }

    public void printErrorInConsole (ThanosError error) {

        view.appendErrorInConsole(error);

    }

    public void run(String input, String fileName) throws IOException {
        // Perform interpretation

        ExecutionManager.reset();
        LocalScopeHandler.reset();
      //  SymbolTableManager.reset();
        BuildChecker.reset();
        StatementControlOverseer.reset();
       // FunctionTracker.reset();
//        LocalVariableTracker.reset();

        view.resetConsole();


        ParserHandler.getInstance().parseText(input);

        if(BuildChecker.getInstance().canExecute()) {
            ExecutionManager.getInstance().executeAllActions();
            System.out.println("BuildChecker executed");
            //this.mViewPager.setCurrentItem(1);
        }
        else {
            System.out.println("Fix identified errors before executing!");
        }

        /*String output = "";
        for(int i = 0; i < tokens.size(); i++) {
            //output += tokens.get(i).getText() + "\n";
            if (i == tokens.size() - 1) {
                output += MokaLexer.VOCABULARY.getSymbolicName(tokens.get(i).getType());
            }
            else {
                output += MokaLexer.VOCABULARY.getSymbolicName(tokens.get(i).getType()) + ", ";
            }
        }

        System.out.print(output);

        console.append("\n" + output);*/

        //TESTED PRINT FUNCTION

        /*console.setText("");
        console.append("Console:");
        console.append("\n");

        MokaLibrary.parsePrint(input);

        if(MokaLibrary.printStatement != null)
            console.append(MokaLibrary.printStatement);

        MokaLibrary.printStatement = "";*/

    }

    public void buildOnly(String input, String fileName) throws IOException {
        ExecutionManager.reset();
        LocalScopeHandler.reset();
     //   SymbolTableManager.reset();
        BuildChecker.reset();
        StatementControlOverseer.reset();
       // FunctionTracker.reset();
//        LocalVariableTracker.reset();


        ParserHandler.getInstance().parseText(input);
    }

}