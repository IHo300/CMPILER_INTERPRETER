package Analyzers;

import GeneratedAntlrClasses.ThanosParser;
import scope.LocalScopeHandler;

import java.util.List;

public class BlockAnalyzer {

    public BlockAnalyzer() {
        LocalScopeHandler.getInstance().openLocalScope();
    }

    public void analyze(ThanosParser.BlockContext ctx) {

        List<ThanosParser.BlockStatementContext> blockListCtx = ctx.blockStatement();

        for(ThanosParser.BlockStatementContext blockStatementCtx : blockListCtx) {
            if(blockStatementCtx.statement() != null) {
                ThanosParser.StatementContext statementCtx = blockStatementCtx.statement();
                StatementAnalyzer statementAnalyzer = new StatementAnalyzer();
                statementAnalyzer.analyze(statementCtx);
            }
            else if(blockStatementCtx.localVariableDeclarationStatement() != null) {
                ThanosParser.LocalVariableDeclarationStatementContext localVarDecStatementCtx = blockStatementCtx.localVariableDeclarationStatement();

                LocalVariableAnalyzer localVarAnalyzer = new LocalVariableAnalyzer();
                localVarAnalyzer.analyze(localVarDecStatementCtx.localVariableDeclaration());
            }
        }

        LocalScopeHandler.getInstance().closeLocalScope();
    }
}