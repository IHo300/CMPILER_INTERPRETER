package Utlities;

import representations.ThanosValue;
import representations.PrimitiveType;

import java.math.BigDecimal;

public class AssignmentUtilities {

    /*
     * Assigns an appropriate value depending on the primitive type. Since expression class returns a double value, we attempt
     * to properly cast it. All expression commands accept INT, LONG, BYTE, SHORT, FLOAT and DOUBLE.
     */
    public static void assignAppropriateValue(ThanosValue ThanosValue, BigDecimal evaluationValue) {
        if(ThanosValue.getPrimitiveType() == PrimitiveType.INT) {
            ThanosValue.setValue(Integer.toString(evaluationValue.intValue()));
        }
        else if(ThanosValue.getPrimitiveType() == PrimitiveType.FLOAT) {
            ThanosValue.setValue(Float.toString(evaluationValue.floatValue()));
        }
        else if(ThanosValue.getPrimitiveType() == PrimitiveType.BOOLEAN) {
            int result = evaluationValue.intValue();

            if(result == 1) {
                ThanosValue.setValue(KeywordRecognizer.BOOLEAN_TRUE);
            }
            else {
                ThanosValue.setValue(KeywordRecognizer.BOOLEAN_FALSE);
            }
        }
        else {
            System.err.println("Thanos Value: Did not find appropriate type");
            //Console.log(LogType.DEBUG, "MobiValue: DID NOT FIND APPROPRIATE TYPE!!");
        }
    }

}