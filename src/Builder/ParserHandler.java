package Builder;

import GeneratedAntlrClasses.ThanosBaseListener;
import GeneratedAntlrClasses.ThanosLexer;
import GeneratedAntlrClasses.ThanosParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ParserHandler {

    private final static String TAG = "MobiProg_ParserHandler";
    private static ParserHandler sharedInstance = null;

    public static ParserHandler getInstance() {
        if(sharedInstance == null) {
            sharedInstance = new ParserHandler();
        }

        return sharedInstance;
    }

    private ThanosLexer ThanosLexer;
    private ThanosParser ThanosParser;

    private String currentClassName; //the current class being parsed

    private ParserHandler() {

    }

    public void parseText(String textToParse) throws IOException {
        InputStream stream = new ByteArrayInputStream(textToParse.getBytes(StandardCharsets.UTF_8));
        CharStream charStream = CharStreams.fromStream(stream);
        this.ThanosLexer = new ThanosLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(this.ThanosLexer);
        this.ThanosParser = new ThanosParser(tokens);
        this.ThanosParser.removeErrorListeners();
        this.ThanosParser.addErrorListener(BuildChecker.getInstance());

        ParserRuleContext parserRuleContext = this.ThanosParser.compilationUnit();

        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(new ThanosBaseListener(), parserRuleContext);

    }

}