package Analyzers;

import errors.MultipleFuncDecChecker;
import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import representations.ThanosFunction;
import representations.FunctionType;
import scope.ThanosScope;
import scope.LocalScopeHandler;
import Utlities.IdentifiedTokenHolder;
import Utlities.KeywordRecognizer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class FunctionAnalyzer implements ParseTreeListener {

    private ThanosScope ThanosScope;
    private IdentifiedTokenHolder identifiedTokenHolder;
    private ThanosFunction declaredThanosFunction;

    public FunctionAnalyzer(IdentifiedTokenHolder identifiedTokens, ThanosScope ThanosScope) {
        this.identifiedTokenHolder = identifiedTokens;
        this.ThanosScope = ThanosScope;
        this.declaredThanosFunction = new ThanosFunction();
    }

    public void analyze(ThanosParser.MethodDeclarationContext ctx) {
        ExecutionManager.getInstance().openFunctionExecution(this.declaredThanosFunction);

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
        if(ctx instanceof ThanosParser.MethodDeclarationContext) {
            ThanosParser.MethodDeclarationContext methodDecCtx = (ThanosParser.MethodDeclarationContext) ctx;
            MultipleFuncDecChecker funcDecChecker = new MultipleFuncDecChecker(methodDecCtx);
            funcDecChecker.verify();

            this.analyzeIdentifier(methodDecCtx.Identifier()); //get the function identifier
        }
        else {
            this.analyzeMethod(ctx);
        }

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if(ctx instanceof ThanosParser.MethodDeclarationContext) {
            ExecutionManager.getInstance().closeFunctionExecution();
        }
    }

    private void analyzeMethod(ParserRuleContext ctx) {

        if(ctx instanceof ThanosParser.TypeTypeContext) {
            ThanosParser.TypeTypeContext typeCtx = (ThanosParser.TypeTypeContext) ctx;

            //return type is a primitive type
            if(typeCtx.primitiveType() != null) {
                ThanosParser.PrimitiveTypeContext primitiveTypeCtx = typeCtx.primitiveType();
                this.declaredThanosFunction.setReturnType(ThanosFunction.identifyFunctionType(primitiveTypeCtx.getText()));
            }
            //return type is a string or a class type
            else {
                this.analyzeClassOrInterfaceType(typeCtx.classOrInterfaceType());
            }
        }

        else if(ctx instanceof ThanosParser.FormalParametersContext) {
            ThanosParser.FormalParametersContext formalParamsCtx = (ThanosParser.FormalParametersContext) ctx;
            this.analyzeParameters(formalParamsCtx);
            this.storeThanosFunction();
        }

        else if(ctx instanceof ThanosParser.MethodBodyContext) {

            ThanosParser.BlockContext blockCtx = ((ThanosParser.MethodBodyContext) ctx).block();

            BlockAnalyzer blockAnalyzer = new BlockAnalyzer();
            this.declaredThanosFunction.setParentLocalScope(LocalScopeHandler.getInstance().getActiveLocalScope());
            blockAnalyzer.analyze(blockCtx);

        }

    }

    private void analyzeClassOrInterfaceType(ThanosParser.ClassOrInterfaceTypeContext classOrInterfaceCtx) {
        //a string identified
        if(classOrInterfaceCtx.getText().contains(KeywordRecognizer.PRIMITIVE_TYPE_STRING)) {
            this.declaredThanosFunction.setReturnType(FunctionType.STRING_TYPE);
        }
        //a class identified
        else {
            //Console.log(LogType.DEBUG, "Class identified: " + classOrInterfaceCtx.getText());
        }
    }

    private void analyzeIdentifier(TerminalNode identifier) {
        this.declaredThanosFunction.setFunctionName(identifier.getText());
    }

    private void analyzeParameters(ThanosParser.FormalParametersContext formalParamsCtx) {
        if(formalParamsCtx.formalParameterList() != null) {
            ParameterAnalyzer parameterAnalyzer = new ParameterAnalyzer(this.declaredThanosFunction);
            parameterAnalyzer.analyze(formalParamsCtx.formalParameterList());
        }
    }

    /*
     * Stores the created function in its corresponding class scope
     */
    private void storeThanosFunction() {
        if(this.identifiedTokenHolder.containsTokens(ThanosAnalyzer.ACCESS_CONTROL_KEY)) {
            String accessToken = this.identifiedTokenHolder.getToken(ThanosAnalyzer.ACCESS_CONTROL_KEY);

            this.ThanosScope.addFunction(this.declaredThanosFunction.getFunctionName(), this.declaredThanosFunction);

            this.identifiedTokenHolder.clearTokens(); //clear tokens for reuse
        }
    }

}
