package Utlities;

public class KeywordRecognizer {
    public static String PRIMITIVE_TYPE_BOOLEAN = "bool";
    public static String PRIMITIVE_TYPE_CHAR = "char";
    public static String PRIMITIVE_TYPE_INT = "int";
    public static String PRIMITIVE_TYPE_FLOAT = "float";
    public static String PRIMITIVE_TYPE_STRING = "string";

    public static String BOOLEAN_TRUE = "true";
    public static String BOOLEAN_FALSE = "false";

    public static boolean matchesKeyword(String keyword, String textToMatch) {
        if(textToMatch.contains(keyword)) {
            return true;
        }
        else {
            return false;
        }
    }
}