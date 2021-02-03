package representations;

import Utlities.KeywordRecognizer;

public class ThanosArray {

    private representations.ThanosValue[] ThanosValueArray;
    private PrimitiveType arrayPrimitiveType;
    private String arrayIdentifier;
    private boolean finalFlag = false;

    public ThanosArray(PrimitiveType primitiveType, String identifier) {
        this.arrayPrimitiveType = primitiveType;
        this.arrayIdentifier = identifier;
    }

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.arrayPrimitiveType = primitiveType;
    }

    public PrimitiveType getPrimitiveType() {
        return this.arrayPrimitiveType;
    }

    public void markFinal() {
        this.finalFlag = true;
    }

    public boolean isFinal() {
        return this.finalFlag;
    }

    public void initializeSize(int size) {
        this.ThanosValueArray = new representations.ThanosValue[size];
        System.err.println("Mobi array initialized to size " +this.ThanosValueArray.length);
    }

    public int getSize() {
        return this.ThanosValueArray.length;
    }

    public void updateValueAt(representations.ThanosValue mobiValue, int index) {
        if(index >= this.ThanosValueArray.length) {
           // Console.log(LogType.ERROR, String.format(ErrorRepository.getErrorMessage(ErrorRepository.RUNTIME_ARRAY_OUT_OF_BOUNDS), this.arrayIdentifier));
            return;
        }
        this.ThanosValueArray[index] = mobiValue;
    }

    public representations.ThanosValue getValueAt(int index) {
        if(index >= this.ThanosValueArray.length) {
           // Console.log(LogType.ERROR, String.format(ErrorRepository.getErrorMessage(ErrorRepository.RUNTIME_ARRAY_OUT_OF_BOUNDS), this.arrayIdentifier));
            return this.ThanosValueArray[this.ThanosValueArray.length - 1];
        }
        else {
            return this.ThanosValueArray[index];
        }
    }

    /*
     * Utility function that returns an arary of specified primitive type.
     */
    public static ThanosArray createArray(String primitiveTypeString, String arrayIdentifier) {
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

        ThanosArray ThanosArray = new ThanosArray(primitiveType, arrayIdentifier);

        return ThanosArray;
    }
}
