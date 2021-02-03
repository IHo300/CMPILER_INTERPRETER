package Analyzers;

import Commands.MappingCommand;
import errors.MultipleVarDecChecker;
import errors.TypeChecker;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosValue;
import scope.LocalScope;
import scope.LocalScopeHandler;
import Utlities.IdentifiedTokenHolder;
import Utlities.KeywordRecognizer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class LocalVariableAnalyzer implements ParseTreeListener {

    private final static String PRIMITIVE_TYPE_KEY = "PRIMITIVE_TYPE_KEY";
    private final static String IDENTIFIER_KEY = "IDENTIFIER_KEY";
    private final static String IDENTIFIER_VALUE_KEY = "IDENTIFIER_VALUE_KEY";

    private IdentifiedTokenHolder identifiedTokenHolder;
    private boolean executeMappingImmediate = false;
    private boolean hasPassedArrayDeclaration = false;

    public LocalVariableAnalyzer() {

    }

    public void analyze(ThanosParser.LocalVariableDeclarationContext localVarDecCtx) {
        this.identifiedTokenHolder = new IdentifiedTokenHolder();

        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, localVarDecCtx);

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
        this.analyzeVariables(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    private void analyzeVariables(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.TypeTypeContext) {
            ThanosParser.TypeTypeContext typeCtx = (ThanosParser.TypeTypeContext) ctx;
            //clear tokens for reuse
            this.identifiedTokenHolder.clearTokens();

            if(ThanosAnalyzer.isPrimitiveDeclaration(typeCtx)) {
                ThanosParser.PrimitiveTypeContext primitiveTypeCtx = typeCtx.primitiveType();
                this.identifiedTokenHolder.addToken(PRIMITIVE_TYPE_KEY, primitiveTypeCtx.getText());

            }

            //check if its array declaration
            else if(ThanosAnalyzer.isPrimitiveArrayDeclaration(typeCtx)) {
                //Console.log(LogType.DEBUG, "Primitive array declaration: " +typeCtx.getText());
                ArrayAnalyzer arrayAnalyzer = new ArrayAnalyzer(this.identifiedTokenHolder, LocalScopeHandler.getInstance().getActiveLocalScope());
                arrayAnalyzer.analyze(typeCtx.getParent());
                this.hasPassedArrayDeclaration = true;
            }

            //this is for class type ctx
            else {
                //a string identified
                if(typeCtx.classOrInterfaceType().getText().contains(KeywordRecognizer.PRIMITIVE_TYPE_STRING)) {
                    ThanosParser.ClassOrInterfaceTypeContext classInterfaceCtx = typeCtx.classOrInterfaceType();
                    this.identifiedTokenHolder.addToken(PRIMITIVE_TYPE_KEY, classInterfaceCtx.getText());
                }
            }


        }

        else if(ctx instanceof ThanosParser.VariableDeclaratorContext) {

            ThanosParser.VariableDeclaratorContext varCtx = (ThanosParser.VariableDeclaratorContext) ctx;

            if(this.hasPassedArrayDeclaration) {
                return;
            }

            //check for duplicate declarations
            if(this.executeMappingImmediate == false) {
                MultipleVarDecChecker multipleDeclaredChecker = new MultipleVarDecChecker(varCtx.variableDeclaratorId());
                multipleDeclaredChecker.verify();
            }

            this.identifiedTokenHolder.addToken(IDENTIFIER_KEY, varCtx.variableDeclaratorId().getText());
            this.createThanosValue();

            if(varCtx.variableInitializer() != null) {

                //we do not evaluate strings.
                if(this.identifiedTokenHolder.containsTokens(PRIMITIVE_TYPE_KEY)) {
                    String primitiveTypeString = this.identifiedTokenHolder.getToken(PRIMITIVE_TYPE_KEY);
                    if(primitiveTypeString.contains(KeywordRecognizer.PRIMITIVE_TYPE_STRING)) {
                        this.identifiedTokenHolder.addToken(IDENTIFIER_VALUE_KEY, varCtx.variableInitializer().getText());
                    }
                }

                this.processMapping(varCtx);

                LocalScope localScope = LocalScopeHandler.getInstance().getActiveLocalScope();
                ThanosValue declaredThanosValue = localScope.searchVariableIncludingLocal(varCtx.variableDeclaratorId().getText());

                TypeChecker typeChecker = new TypeChecker(declaredThanosValue, varCtx.variableInitializer().expression());
                typeChecker.verify();
            }

        }

    }

    /*
     * Local variable analyzer is also used for loops. Whenever there is a loop,
     * mapping command should be executed immediately to update the value in the symbol table.
     * Otherwise, it proceeds normally.
     */
    private void processMapping(ThanosParser.VariableDeclaratorContext varCtx) {
        if(this.executeMappingImmediate) {
            MappingCommand mappingCommand = new MappingCommand(varCtx.variableDeclaratorId().getText(), varCtx.variableInitializer().expression());
            mappingCommand.execute();
        }
        else {
            MappingCommand mappingCommand = new MappingCommand(varCtx.variableDeclaratorId().getText(), varCtx.variableInitializer().expression());
            ExecutionManager.getInstance().addCommand(mappingCommand);
        }
    }

    public void markImmediateExecution() {
        this.executeMappingImmediate = true;
    }

    /*
     * Attempts to create an intermediate representation of the variable once a sufficient amount of info has been retrieved.
     */
    private void createThanosValue() {

        if(this.identifiedTokenHolder.containsTokens(PRIMITIVE_TYPE_KEY, IDENTIFIER_KEY)) {

            String primitiveTypeString = this.identifiedTokenHolder.getToken(PRIMITIVE_TYPE_KEY);
            String identifierString = this.identifiedTokenHolder.getToken(IDENTIFIER_KEY);
            String identifierValueString = null;

            LocalScope localScope = LocalScopeHandler.getInstance().getActiveLocalScope();

            if(this.identifiedTokenHolder.containsTokens(IDENTIFIER_VALUE_KEY)) {
                identifierValueString = this.identifiedTokenHolder.getToken(IDENTIFIER_VALUE_KEY);
                localScope.addInitializedVariable(primitiveTypeString, identifierString, identifierValueString);
            }
            else {
                localScope.addEmptyVariable(primitiveTypeString, identifierString);
            }

            //remove the following tokens
            this.identifiedTokenHolder.removeToken(IDENTIFIER_KEY);
            this.identifiedTokenHolder.removeToken(IDENTIFIER_VALUE_KEY);

        }
    }
}