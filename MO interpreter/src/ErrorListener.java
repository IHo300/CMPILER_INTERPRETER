import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.Objects;

public class ErrorListener extends BaseErrorListener {


    public static boolean curlyBraceError = false;
    public static boolean curlyBraceMissing = false;

    public static String later="";

    public static ArrayList<String> errorsArray = new ArrayList<String>();

    public static ErrorListener INSTANCE = new ErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        String message = new String();

        if (e instanceof LexerNoViableAltException) {
            message = "LNVAE";
        } else if (e instanceof InputMismatchException) {
            message = "mismatched"+((CommonToken) offendingSymbol).getText();
        } else if (e instanceof NoViableAltException) {
            message ="noviable"+((CommonToken) offendingSymbol).getText();
        } else if (Objects.isNull(e)) {
// Handle Errors specified in my grammar
            message ="?? "+((CommonToken) offendingSymbol).getText();
        } else {
            message = msg;
        }

        //System.out.println(message);

        String errorLog = msg + " at line " + line;

        if(errorLog.contains("missing '}'")){
            errorLog = "missing closing curly brace";
            curlyBraceError=false;
            curlyBraceMissing = true;
        }
        if(errorLog.contains("mismatched input '{'")&&!curlyBraceMissing){
            errorLog = "missing curly brace";//closing?
            curlyBraceError=true;
        }
        else if(errorLog.contains("mismatched input '{'")&&curlyBraceMissing)
            later = errorLog;
        if(curlyBraceError==true&&errorLog.contains("extraneous input '{'")){
            errorLog ="";
        }
        if(curlyBraceError==true&&errorLog.contains("missing ';'")){//notsureyet
            errorLog ="";
        }


        errorLog = errorLog.replace("rule primaryExpression ", "");
        errorLog = errorLog.replace("rule multiplicativeExpression ", "");
        errorLog = errorLog.replace("rule forCondition ", "");
        errorLog = errorLog.replace("rule expressionStatement ", "");
        errorLog = errorLog.replace("rule postfixExpression ", "");
        errorLog = errorLog.replace("rule additiveExpression ", "");
        errorLog = errorLog.replace("rule comparisonStatement ", "");
        errorLog = errorLog.replace("rule jumpStatement ", "");
        errorLog = errorLog.replace("rule printStatement ", "");
        errorLog = errorLog.replace("rule argumentExpressionList ", "");;
        errorLog = errorLog.replace("rule typeQualifier ", "");
        errorLog = errorLog.replace("rule equalityExpression ", "");
        errorLog = errorLog.replace("rule declaration ", "");

        if(errorLog!="")
            errorsArray.add(errorLog);

        //System.out.println(errorLog);
        //SyntaxErrorCollector.getInstance().recordError(errorLog);
    }



}

