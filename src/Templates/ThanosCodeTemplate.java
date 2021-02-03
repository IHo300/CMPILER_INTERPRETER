package Templates;

public class ThanosCodeTemplate {

    public static String createNewClassTemplate(String fileName) {
        String codeTemplate = "public class " +fileName+ " {"
                + "\n \n"
                + "}";

        return codeTemplate;
    }
}
