package ANTLR;

public class ThanosError {

    public static final String NO_VIABLE_ALT_KEY = "no viable alternative at input";
    public static final String MISSING_KEY = "missing";
    public static final String MISMATCHED_INPUT_KEY = "mismatched input";
    public static final String EXTRANEOUS_INPUT_KEY = "extraneous input";
    public static final String TOKEN_RECOGNITION_KEY = "token recognition error";

    private int lineNumber;
    private int charNumber = -1;
    private String errorPrefix;
    private String lineLayout;
    private String errorSuffix;
    private ErrorType type;

    public enum ErrorType {
        NO_VIABLE_ALTERNATIVE,
        MISSING,
        MISMATCHED_INPUT,
        EXTRANEOUS_INPUT,
        TOKEN_RECOGNITION
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getCharNumber() {
        return charNumber;
    }

    public void setCharNumber(int charNumber) {
        this.charNumber = charNumber;
    }

    public String getErrorPrefix() {
        return errorPrefix;
    }

    public void setErrorPrefix(String errorPrefix) {
        this.errorPrefix = errorPrefix;
    }

    public String getLineLayout() {
        return lineLayout;
    }

    public void setLineLayout(String lineLayout) {
        this.lineLayout = lineLayout;
    }

    public String getErrorSuffix() {
        return errorSuffix;
    }

    public void setErrorSuffix(String errorSuffix) {
        this.errorSuffix = errorSuffix;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
