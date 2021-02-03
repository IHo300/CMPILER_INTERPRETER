package Utlities;

public class StringUtilities {
    public static String removeQuotes(String stringWithQuotes) {
        String newString = stringWithQuotes.replace("\"", "");
        newString = newString.replace("'", "");

        return newString;
    }
}