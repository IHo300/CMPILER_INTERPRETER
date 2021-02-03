package Analyzers;

import Commands.MappingCommand;
import errors.MultipleVarDecChecker;
import errors.TypeChecker;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosValue;
import scope.ThanosScope;
import Utlities.IdentifiedTokenHolder;
import Utlities.KeywordRecognizer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class FieldAnalyzer implements ParseTreeListener {

    private ThanosScope ThanosScope;
    private IdentifiedTokenHolder identifiedTokenHolder;

    public FieldAnalyzer(IdentifiedTokenHolder identifiedTokenHolder, ThanosScope ThanosScope) {
        this.identifiedTokenHolder = identifiedTokenHolder;
        this.ThanosScope = ThanosScope;
    }

    public void analyze(ThanosParser.VariableDeclaratorsContext varDecCtxList) {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, varDecCtxList);
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
        if(ctx instanceof ThanosParser.VariableDeclaratorContext) {
            ThanosParser.VariableDeclaratorContext varCtx = (ThanosParser.VariableDeclaratorContext) ctx;

            //check for duplicate declarations
            MultipleVarDecChecker multipleDeclaredChecker = new MultipleVarDecChecker(varCtx.variableDeclaratorId());
            multipleDeclaredChecker.verify();

            this.identifiedTokenHolder.addToken(ThanosAnalyzer.IDENTIFIER_KEY, varCtx.variableDeclaratorId().getText());
            this.createThanosValue();

            if(varCtx.variableInitializer() != null) {

                //we do not evaluate strings.
                if(this.identifiedTokenHolder.containsTokens(ThanosAnalyzer.PRIMITIVE_TYPE_KEY)) {
                    String primitiveTypeString = this.identifiedTokenHolder.getToken(ThanosAnalyzer.PRIMITIVE_TYPE_KEY);
                    if(primitiveTypeString.contains(KeywordRecognizer.PRIMITIVE_TYPE_STRING)) {
                        this.identifiedTokenHolder.addToken(ThanosAnalyzer.IDENTIFIER_VALUE_KEY, varCtx.variableInitializer().getText());
                        return;
                    }
                }

                MappingCommand mappingCommand = new MappingCommand(varCtx.variableDeclaratorId().getText(), varCtx.variableInitializer().expression());
                ExecutionManager.getInstance().addCommand(mappingCommand);

                ThanosValue declaredThanosValue = this.ThanosScope.searchVariableIncludingLocal(varCtx.variableDeclaratorId().getText());

                TypeChecker typeChecker = new TypeChecker(declaredThanosValue, varCtx.variableInitializer().expression());
                typeChecker.verify();
            }

        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    /*
     * Attempts to create an intermediate representation of the variable once a sufficient amount of info has been retrieved.
     */
    private void createThanosValue() {

        if(this.identifiedTokenHolder.containsTokens(ThanosAnalyzer.PRIMITIVE_TYPE_KEY, ThanosAnalyzer.IDENTIFIER_KEY)) {

            String primitiveTypeString = this.identifiedTokenHolder.getToken(ThanosAnalyzer.PRIMITIVE_TYPE_KEY);
            String identifierString = this.identifiedTokenHolder.getToken(ThanosAnalyzer.IDENTIFIER_KEY);
            String identifierValueString = null;

            //Console.log(LogType.DEBUG, "Class modifier: " +classModifierString);

            if(this.identifiedTokenHolder.containsTokens(ThanosAnalyzer.IDENTIFIER_VALUE_KEY)) {
                identifierValueString = this.identifiedTokenHolder.getToken(ThanosAnalyzer.IDENTIFIER_VALUE_KEY);
                this.ThanosScope.addInitializedVariable(primitiveTypeString, identifierString, identifierValueString);
            }
            else {
                this.ThanosScope.addEmptyVariable(primitiveTypeString, identifierString);
            }

            ThanosValue declaredValue = this.ThanosScope.searchVariableIncludingLocal(identifierString);
            //verify if the declared variable is a constant
            if(this.identifiedTokenHolder.containsTokens(ThanosAnalyzer.CONST_CONTROL_KEY)) {
                declaredValue.makeFinal();
            }



            //remove the following tokens
            this.identifiedTokenHolder.removeToken(ThanosAnalyzer.IDENTIFIER_KEY);
            this.identifiedTokenHolder.removeToken(ThanosAnalyzer.IDENTIFIER_VALUE_KEY);
        }
    }
}