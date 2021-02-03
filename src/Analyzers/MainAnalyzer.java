package Analyzers;

import Execution.ExecutionManager;
import GeneratedAntlrClasses.ThanosParser;
import scope.ThanosScope;
import scope.LocalScope;
import scope.LocalScopeHandler;
import scope.SymbolTableManager;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class MainAnalyzer implements ParseTreeListener {

    public MainAnalyzer() {

    }

    public void analyze(ThanosParser.MainDeclarationContext ctx) {
        if(!ExecutionManager.getInstance().hasFoundEntryPoint()) {
//            ExecutionManager.getInstance().reportFoundEntryPoint(ParserHandler.getInstance().getCurrentClassName());

            //automatically create a local scope for main() whose parent is the class scope
            ThanosScope ThanosScope = SymbolTableManager.getInstance().getMainScope();
            LocalScope localScope = LocalScopeHandler.getInstance().openLocalScope();
            localScope.setParent(ThanosScope);
            ThanosScope.setParentLocalScope(localScope);
            ParseTreeWalker treeWalker = new ParseTreeWalker();
            treeWalker.walk(this, ctx);


        }
        else {
            System.out.println("Already found main in " + ExecutionManager.getInstance().getEntryClassName());
        }
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        if(parserRuleContext instanceof ThanosParser.MethodBodyContext) {
            ThanosParser.BlockContext blockCtx = ((ThanosParser.MethodBodyContext) parserRuleContext).block();

            BlockAnalyzer blockAnalyzer = new BlockAnalyzer();
            blockAnalyzer.analyze(blockCtx);
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
