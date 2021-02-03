package Analyzers;

import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosArray;
import representations.ThanosFunction;
import representations.ThanosValue;
import representations.PrimitiveType;
import Utlities.IdentifiedTokenHolder;
import Utlities.KeywordRecognizer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ParameterAnalyzer implements ParseTreeListener {

    private final static String PARAMETER_TYPE_KEY = "PARAMETER_TYPE_KEY";
    private final static String PARAMETER_IDENTIFIER_KEY = "PARAMETER_IDENTIFIER_KEY";
    private final static String IS_ARRAY_KEY = "IS_ARRAY_KEY";


    private IdentifiedTokenHolder identifiedTokenHolder;
    private ThanosFunction declaredThanosFunction;

    public ParameterAnalyzer(ThanosFunction declaredThanosFunction) {
        this.declaredThanosFunction = declaredThanosFunction;
    }

    public void analyze(ThanosParser.FormalParameterListContext ctx) {
        this.identifiedTokenHolder = new IdentifiedTokenHolder();

        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, ctx);
    }

    /* (non-Javadoc)
     * @see org.antlr.v4.runtime.tree.ParseTreeListener#visitTerminal(org.antlr.v4.runtime.tree.TerminalNode)
     */
    @Override
    public void visitTerminal(TerminalNode node) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.antlr.v4.runtime.tree.ParseTreeListener#visitErrorNode(org.antlr.v4.runtime.tree.ErrorNode)
     */
    @Override
    public void visitErrorNode(ErrorNode node) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.antlr.v4.runtime.tree.ParseTreeListener#enterEveryRule(org.antlr.v4.runtime.ParserRuleContext)
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.FormalParameterContext) {
            ThanosParser.FormalParameterContext formalParamCtx = (ThanosParser.FormalParameterContext) ctx;
            this.analyzeParameter(formalParamCtx);
        }
    }

    /* (non-Javadoc)
     * @see org.antlr.v4.runtime.tree.ParseTreeListener#exitEveryRule(org.antlr.v4.runtime.ParserRuleContext)
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub

    }

    private void analyzeParameter(ThanosParser.FormalParameterContext formalParamCtx) {
        if(formalParamCtx.typeType() != null) {
            ThanosParser.TypeTypeContext typeCtx = formalParamCtx.typeType();

            //return type is a primitive type
            if(ThanosAnalyzer.isPrimitiveDeclaration(typeCtx)) {
                ThanosParser.PrimitiveTypeContext primitiveTypeCtx = typeCtx.primitiveType();
                this.identifiedTokenHolder.addToken(PARAMETER_TYPE_KEY, primitiveTypeCtx.getText());
            }
            //check if its array declaration
            else if(ThanosAnalyzer.isPrimitiveArrayDeclaration(typeCtx)) {
                ThanosParser.PrimitiveTypeContext primitiveTypeCtx = typeCtx.primitiveType();
                this.identifiedTokenHolder.addToken(PARAMETER_TYPE_KEY, primitiveTypeCtx.getText());
                this.identifiedTokenHolder.addToken(IS_ARRAY_KEY, IS_ARRAY_KEY);
            }

            //return type is a string or a class type
            else {
                //a string type
                if(typeCtx.classOrInterfaceType().getText().contains(KeywordRecognizer.PRIMITIVE_TYPE_STRING)) {
                    this.identifiedTokenHolder.addToken(PARAMETER_TYPE_KEY, typeCtx.classOrInterfaceType().getText());
                }
            }
        }

        if(formalParamCtx.variableDeclaratorId() != null) {
            TerminalNode identifier = formalParamCtx.variableDeclaratorId().Identifier();
            this.identifiedTokenHolder.addToken(PARAMETER_IDENTIFIER_KEY, identifier.getText());

            this.createThanosValue();
        }

    }

    private void createThanosValue() {
        if(this.identifiedTokenHolder.containsTokens(IS_ARRAY_KEY, PARAMETER_TYPE_KEY, PARAMETER_IDENTIFIER_KEY)) {
            String typeString = this.identifiedTokenHolder.getToken(PARAMETER_TYPE_KEY);
            String identifierString = this.identifiedTokenHolder.getToken(PARAMETER_IDENTIFIER_KEY);

            ThanosArray declaredArray = ThanosArray.createArray(typeString, identifierString);
            ThanosValue ThanosValue = new ThanosValue(declaredArray, PrimitiveType.ARRAY);
            this.declaredThanosFunction.addParameter(identifierString, ThanosValue);

            //Console.log(LogType.DEBUG, "Created array parameter for " +this.declaredBaracoMethod.getFunctionName());
        }
        else if(this.identifiedTokenHolder.containsTokens(PARAMETER_TYPE_KEY, PARAMETER_IDENTIFIER_KEY)) {
            String typeString = this.identifiedTokenHolder.getToken(PARAMETER_TYPE_KEY);
            String identifierString = this.identifiedTokenHolder.getToken(PARAMETER_IDENTIFIER_KEY);

            ThanosValue ThanosValue = ThanosValue.createEmptyVariable(typeString);
            this.declaredThanosFunction.addParameter(identifierString, ThanosValue);
        }

        this.identifiedTokenHolder.clearTokens();
    }

}