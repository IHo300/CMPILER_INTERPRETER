package Builder;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

public class BuildChecker implements ANTLRErrorListener {

    private static BuildChecker sharedInstance = null;

    private boolean successful = true;

    public static BuildChecker getInstance() {
        return sharedInstance;
    }

    private BuildChecker() {

    }

    public static void initialize() {
        sharedInstance = new BuildChecker();
        SemanticErrorDictionary.initialize();
    }

    public static void reset() {
        sharedInstance.successful  = true;
        SemanticErrorDictionary.reset();
    }

    public boolean canExecute() {
        return this.successful;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        System.err.println("Syntax error at line " +line+ ". " +msg); //TODO: change to IDE

        this.successful = false;
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex,
                                int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa,
                                            int startIndex, int stopIndex, BitSet conflictingAlts,
                                            ATNConfigSet configs) {
        // TODO Auto-generated method stub

    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa,
                                         int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        // TODO Auto-generated method stub

    }

    public static void reportCustomError(int errorCode, String additionalMessage) {
        String errorMessage = SemanticErrorDictionary.getErrorMessage(errorCode) + " " + additionalMessage;
        System.err.println(errorMessage); //TODO Change to IDE

        sharedInstance.successful = false;
    }

    public static void reportCustomError(int errorCode, String additionalMessage, Object... parameters) {
        String errorMessage = String.format(SemanticErrorDictionary.getErrorMessage(errorCode) + " " + additionalMessage, parameters);
        System.err.println(errorMessage); //TODO Change to IDE

        sharedInstance.successful = false;
    }
}