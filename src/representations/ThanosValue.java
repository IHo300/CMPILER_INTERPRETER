package representations;


public class ThanosValue {
    private Object defaultValue; //this value will no longer change.
    private Object value;
    private PrimitiveType primitiveType = PrimitiveType.NOT_YET_IDENTIFIED;
    private boolean finalFlag = false;


    public ThanosValue(Object value, PrimitiveType primitiveType) {
        if(value == null || checkValueType(value, primitiveType)) {
            this.value = value;
            this.primitiveType = primitiveType;
        }
        else {
            System.err.println("Value is not appropriate for  " +primitiveType+ ".");
        }
    }

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
    }

    public void reset() {
        this.value = this.defaultValue;
    }

    /*
     * Marks this value as final if there is a final keyword
     */
    public void makeFinal() {
        this.finalFlag = true;
    }

    public boolean isFinal() {
        return this.finalFlag;
    }

    public void setValue(String value) {

        if(this.primitiveType == PrimitiveType.NOT_YET_IDENTIFIED) {
            System.err.println("Primitive type not yet identified!");
        }

        else if(this.primitiveType == PrimitiveType.STRING) {
            this.value = StringUtilities.removeQuotes(value);
        }
        else if(this.primitiveType == PrimitiveType.ARRAY) {
            System.err.println(this.primitiveType + " is an array. Cannot directly change value.");
        }
        else {
            //attempts to type cast the value
            this.value = this.attemptTypeCast(value);
        }
    }


    private Object attemptTypeCast(String value) {
        switch(this.primitiveType) {
            case BOOLEAN: return Boolean.valueOf(value);
            case CHAR: return Character.valueOf(value.charAt(0)); //only get first char at value
            case INT: return Integer.valueOf(value);
            case FLOAT: return Float.valueOf(value);
            case STRING: return value;
            default: return null;
        }
    }

    public Object getValue() {
        return this.value;
    }

    public PrimitiveType getPrimitiveType() {
        return this.primitiveType;
    }


    public static boolean checkValueType(Object value, PrimitiveType primitiveType) {
        switch(primitiveType) {
            case BOOLEAN:
                return value instanceof Boolean;
            case CHAR:
                return value instanceof Character;
            case INT:
                return value instanceof Integer;
            case FLOAT:
                return value instanceof Float;
            case STRING:
                return value instanceof String;
            case ARRAY:
                return value instanceof Object;
            default:
                return false;
        }
    }

    /*
     * Utility function.
     * Attempts to add an empty variable based from keywords
     */
    public static ThanosValue createEmptyVariable(String primitiveTypeString) {

        //identify primitive type
        PrimitiveType primitiveType = PrimitiveType.NOT_YET_IDENTIFIED;

        if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_BOOLEAN, primitiveTypeString)) {
            primitiveType = PrimitiveType.BOOLEAN;
        }
        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_CHAR, primitiveTypeString)) {
            primitiveType = PrimitiveType.CHAR;
        }
        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_FLOAT, primitiveTypeString)) {
            primitiveType = PrimitiveType.FLOAT;
        }
        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_INT, primitiveTypeString)) {
            primitiveType = PrimitiveType.INT;
        }
        else if(KeywordRecognizer.matchesKeyword(KeywordRecognizer.PRIMITIVE_TYPE_STRING, primitiveTypeString)) {
            primitiveType = PrimitiveType.STRING;
        }

        //create empty mobi value
        ThanosValue mobiValue = new ThanosValue(null, primitiveType);

        return mobiValue;
    }
}
