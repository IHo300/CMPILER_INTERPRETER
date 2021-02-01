import org.antlr.v4.runtime.ParserRuleContext;

public class Mylistener extends CBaseListener{

    @Override
    public void enterDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {

       // System.out.println(ctx.declarationSpecifier().size()+"EDS");

        //for(int i=0;i<ctx.declarationSpecifier().size();i++){
            //ctx.declarationSpecifier().get(0).enterRule(this);
        //}
    }

    @Override
    public void enterDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) {
        // System.out.println("do ds");
        //ctx.typeSpecifier().enterRule(this);

    }

    @Override
    public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) {

       // System.out.println("asdf:"+ctx.Int());

    }

    @Override
    public void enterFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
        ctx.declarationSpecifiers().enterRule(this);
        //System.out.println(ctx.declarationSpecifiers());

        ctx.declarationSpecifiers().declarationSpecifier(0).enterRule(this);
        ctx.declarationSpecifiers().declarationSpecifier(0).typeSpecifier().enterRule(this);

        //System.out.println("type: "+ctx.declarationSpecifiers().declarationSpecifier(0).typeSpecifier().getText());

        //System.out.println("entered function: " + ctx.declarationSpecifiers());
//        ctx.declarationSpecifiers().enterRule(this);

    }

}
