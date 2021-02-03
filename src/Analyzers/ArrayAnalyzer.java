package Analyzers;

import Commands.ArrayInitializeCommand;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosArray;
import representations.ThanosValue;
import representations.PrimitiveType;
import scope.ThanosScope;
import scope.LocalScope;
import Utlities.IdentifiedTokenHolder;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ArrayAnalyzer implements ParseTreeListener{
    private final static String TAG = "MobiProg_ArrayAnalyzer";

    private final static String ARRAY_PRIMITIVE_KEY = "ARRAY_PRIMITIVE_KEY";
    private final static String ARRAY_IDENTIFIER_KEY = "ARRAY_IDENTIFIER_KEY";

    private IdentifiedTokenHolder identifiedTokenHolder;
    private ThanosScope ThanosScope;
    private LocalScope localScope;
    private ThanosArray declaredArray;

    public ArrayAnalyzer(IdentifiedTokenHolder identifiedTokenHolder, ThanosScope ThanosScope) {
        this.identifiedTokenHolder = identifiedTokenHolder;
        this.ThanosScope = ThanosScope;
    }

    public ArrayAnalyzer(IdentifiedTokenHolder identifiedTokenHolder, LocalScope localScope) {
        this.identifiedTokenHolder = identifiedTokenHolder;
        this.localScope = localScope;
    }

    public void analyze(ParserRuleContext ctx) {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.PrimitiveTypeContext) {
            ThanosParser.PrimitiveTypeContext primitiveCtx = (ThanosParser.PrimitiveTypeContext) ctx;
            this.identifiedTokenHolder.addToken(ARRAY_PRIMITIVE_KEY, primitiveCtx.getText());
        }
        else if(ctx instanceof ThanosParser.VariableDeclaratorIdContext) {
            ThanosParser.VariableDeclaratorIdContext varDecIdCtx = (ThanosParser.VariableDeclaratorIdContext) ctx;
            this.identifiedTokenHolder.addToken(ARRAY_IDENTIFIER_KEY, varDecIdCtx.getText());

            this.analyzeArray();
        }
        else if(ctx instanceof ThanosParser.CreatedNameContext) {
            ThanosParser.CreatedNameContext createdNameCtx = (ThanosParser.CreatedNameContext) ctx;
            //Console.log(LogType.DEBUG, "Array created name: " +createdNameCtx.getText());
        }

        else if(ctx instanceof ThanosParser.ArrayCreatorRestContext) {
            ThanosParser.ArrayCreatorRestContext arrayCreatorCtx = (ThanosParser.ArrayCreatorRestContext) ctx;
            this.createInitializeCommand(arrayCreatorCtx);
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }

    private void analyzeArray() {

        if(this.ThanosScope != null) {
            if(this.identifiedTokenHolder.containsTokens(ThanosAnalyzer.ACCESS_CONTROL_KEY, ARRAY_PRIMITIVE_KEY, ARRAY_IDENTIFIER_KEY)) {
                String accessControlString = this.identifiedTokenHolder.getToken(ThanosAnalyzer.ACCESS_CONTROL_KEY);
                String arrayTypeString = this.identifiedTokenHolder.getToken(ARRAY_PRIMITIVE_KEY);
                String arrayIdentifierString = this.identifiedTokenHolder.getToken(ARRAY_IDENTIFIER_KEY);

                //initialize an array mobivalue
                this.declaredArray = ThanosArray.createArray(arrayTypeString, arrayIdentifierString);
                ThanosValue ThanosValue = new ThanosValue(this.declaredArray, PrimitiveType.ARRAY);

                this.ThanosScope.addThanosValue(arrayIdentifierString, ThanosValue);
                //Console.log(LogType.DEBUG, "Creating array with type " +arrayTypeString+ " variable " +arrayIdentifierString);

                this.identifiedTokenHolder.clearTokens();
            }
        }
        else if(this.localScope != null) {
            if(this.identifiedTokenHolder.containsTokens(ARRAY_PRIMITIVE_KEY, ARRAY_IDENTIFIER_KEY)) {
                String arrayTypeString = this.identifiedTokenHolder.getToken(ARRAY_PRIMITIVE_KEY);
                String arrayIdentifierString = this.identifiedTokenHolder.getToken(ARRAY_IDENTIFIER_KEY);

                //initialize an array mobivalue
                this.declaredArray = ThanosArray.createArray(arrayTypeString, arrayIdentifierString);
                ThanosValue ThanosValue = new ThanosValue(this.declaredArray, PrimitiveType.ARRAY);

                this.localScope.addThanosValue(arrayIdentifierString, ThanosValue);
                //Console.log(LogType.DEBUG, "Creating array with type " +arrayTypeString+ " variable " +arrayIdentifierString);

                this.identifiedTokenHolder.clearTokens();
            }
        }

    }

    private void createInitializeCommand(ThanosParser.ArrayCreatorRestContext arrayCreatorCtx) {
        ArrayInitializeCommand arrayInitializeCommand = new ArrayInitializeCommand(this.declaredArray, arrayCreatorCtx);
        ExecutionManager.getInstance().addCommand(arrayInitializeCommand);
    }
}