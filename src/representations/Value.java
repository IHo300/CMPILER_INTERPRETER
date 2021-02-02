package representations;

import java.util.Stack;

public class Value {
    public enum PrimitiveType {
        NOT_YET_IDENTIFIED,
        BOOL,
        INT,
        DECIMAL,
        STRING,
        CHAR,
        ARRAY
    }


    private Stack<Object> defaultValue; //this value will no longer change.
    private Stack<Object> value;
    private PrimitiveType primitiveType = PrimitiveType.NOT_YET_IDENTIFIED;
    private boolean finalFlag = false;

    public Value(Object value, PrimitiveType primitiveType) {
        if(value == null || checkValueType(value, primitiveType)) {
            this.value = new Stack<Object>();

            this.value.push(value);
            this.primitiveType = primitiveType;
        }
        else {
            System.out.println("Value is not appropriate for  " +primitiveType+ "!");
        }
    }

    public static boolean checkValueType(Object value, PrimitiveType primitiveType) {
        switch(primitiveType) {
            case CHAR:
                return value instanceof Character;
            case BOOL:
                return value instanceof Boolean;
            case INT:
                return value instanceof Integer;
            case DECIMAL:
                return value instanceof Double;
            case STRING:
                return value instanceof String;
            case ARRAY:
                return value instanceof Object;
            default:
                return false;
        }
    }
}
