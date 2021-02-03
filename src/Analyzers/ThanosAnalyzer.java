package Analyzers;

import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import scope.ThanosScope;
import Utlities.IdentifiedTokenHolder;
import Utlities.KeywordRecognizer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class ThanosAnalyzer implements ParseTreeListener {

    private ThanosScope ThanosScope;
    private IdentifiedTokenHolder identifiedTokenHolder;

    public final static String ACCESS_CONTROL_KEY = "ACCESS_CONTROL_KEY";
    public final static String CONST_CONTROL_KEY = "CONST_CONSTROL_KEY";
    public final static String STATIC_CONTROL_KEY = "STATIC_CONTROL_KEY";
    public final static String PRIMITIVE_TYPE_KEY = "PRIMITIVE_TYPE_KEY";
    public final static String IDENTIFIER_KEY = "IDENTIFIER_KEY";
    public final static String IDENTIFIER_VALUE_KEY = "IDENTIFIER_VALUE_KEY";

    public ThanosAnalyzer() {

    }

    public void analyze(ThanosParser.StartContext ctx) {
//        String className = ctx.Identifier().getText();

        //Console.log(LogType.DEBUG, "Class name is " +className);
//        ClassNameChecker classNameChecker = new ClassNameChecker(className);
//        classNameChecker.verify();

        this.ThanosScope = new ThanosScope();
        this.identifiedTokenHolder = new IdentifiedTokenHolder();

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
//        if(ctx instanceof ClassDeclarationContext) {
//            SymbolTableManager.getInstance().addClassScope(this.mainScope.getClassName(), this.mainScope);
//        }

        this.analyzeClassMembers(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }

    private void analyzeClassMembers(ParserRuleContext ctx) {
//        if(ctx instanceof ClassOrInterfaceModifierContext) {
//            ClassOrInterfaceModifierContext classModifierCtx = (ClassOrInterfaceModifierContext) ctx;
//
//            this.analyzeModifier(classModifierCtx);
//        }

        //else
        if(ctx instanceof ThanosParser.FieldDeclarationContext) {
            ThanosParser.FieldDeclarationContext fieldCtx = (ThanosParser.FieldDeclarationContext) ctx;

            if(fieldCtx.typeType() != null) {
                ThanosParser.TypeTypeContext typeCtx = fieldCtx.typeType();

                //check if its a primitive type
                if(ThanosAnalyzer.isPrimitiveDeclaration(typeCtx)) {
                    ThanosParser.PrimitiveTypeContext primitiveTypeCtx = typeCtx.primitiveType();
                    this.identifiedTokenHolder.addToken(PRIMITIVE_TYPE_KEY, primitiveTypeCtx.getText());

                    //create a field analyzer to walk through declarations
                    FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(this.identifiedTokenHolder, this.ThanosScope);
                    fieldAnalyzer.analyze(fieldCtx.variableDeclarators());

                    //clear tokens for reause
                    this.identifiedTokenHolder.clearTokens();
                }

                //check if its array declaration
                else if(ThanosAnalyzer.isPrimitiveArrayDeclaration(typeCtx)) {
                    //Console.log(LogType.DEBUG, "Primitive array declaration: " +fieldCtx.getText());
                    ArrayAnalyzer arrayAnalyzer = new ArrayAnalyzer(this.identifiedTokenHolder, this.ThanosScope);
                    arrayAnalyzer.analyze(fieldCtx);
                }

                //this is for class type ctx
                else {

                    //a string identified
                    if(typeCtx.classOrInterfaceType().getText().contains(KeywordRecognizer.PRIMITIVE_TYPE_STRING)) {
                        ThanosParser.ClassOrInterfaceTypeContext classInterfaceCtx = typeCtx.classOrInterfaceType();
                        this.identifiedTokenHolder.addToken(PRIMITIVE_TYPE_KEY, classInterfaceCtx.getText());
                    }

                    //create a field analyzer to walk through declarations
                    FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(this.identifiedTokenHolder, this.ThanosScope);
                    fieldAnalyzer.analyze(fieldCtx.variableDeclarators());

                    //clear tokens for reause
                    this.identifiedTokenHolder.clearTokens();
                }
            }
        }

        else if(ctx instanceof ThanosParser.MethodDeclarationContext) {
            ThanosParser.MethodDeclarationContext methodDecCtx = (ThanosParser.MethodDeclarationContext) ctx;
            FunctionAnalyzer functionAnalyzer = new FunctionAnalyzer(this.identifiedTokenHolder, this.ThanosScope);
            functionAnalyzer.analyze(methodDecCtx);

            //reuse tokens
            this.identifiedTokenHolder.clearTokens();
        }
    }

    public static boolean isPrimitiveDeclaration(ThanosParser.TypeTypeContext typeCtx) {
        if(typeCtx.primitiveType() != null) {
            List<TerminalNode> lBrackToken = typeCtx.getTokens(ThanosLexer.LBRACK);
            List<TerminalNode> rBrackToken = typeCtx.getTokens(ThanosLexer.RBRACK);

            return (lBrackToken.size() == 0 && rBrackToken.size() == 0);
        }

        return false;
    }

    public static boolean isPrimitiveArrayDeclaration(ThanosParser.TypeTypeContext typeCtx) {
        if(typeCtx.primitiveType() != null) {
            List<TerminalNode> lBrackToken = typeCtx.getTokens(ThanosLexer.LBRACK);
            List<TerminalNode> rBrackToken = typeCtx.getTokens(ThanosLexer.RBRACK);

            return (lBrackToken.size() > 0 && rBrackToken.size() > 0);
        }

        return false;
    }

//    private void analyzeModifier(ClassOrInterfaceModifierContext ctx) {
//        if(ctx.getTokens(JavaLexer.PUBLIC).size() > 0 || ctx.getTokens(JavaLexer.PRIVATE).size() > 0
//                || ctx.getTokens(JavaLexer.PROTECTED).size() > 0) {
//            Console.log(LogType.DEBUG, "Detected accessor: " +ctx.getText());
//            this.identifiedTokenHolder.addToken(ACCESS_CONTROL_KEY, ctx.getText());
//        }
//        else if(ctx.getTokens(JavaLexer.FINAL).size() > 0) {
//            Console.log(LogType.DEBUG, "Detected const: " +ctx.getText());
//            this.identifiedTokenHolder.addToken(CONST_CONTROL_KEY, ctx.getText());
//        }
//        else if(ctx.getTokens(JavaLexer.STATIC).size() > 0) {
//            Console.log(LogType.DEBUG, "Detected static: " +ctx.getText());
//            this.identifiedTokenHolder.addToken(STATIC_CONTROL_KEY, ctx.getText());
//        }
//    }

}