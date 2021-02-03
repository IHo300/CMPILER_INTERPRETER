package Templates;

public class ThanosFunctionTemplateParameter {

    String parameterName;
    String dataType;

    public ThanosFunctionTemplateParameter() {

    }

    public ThanosFunctionTemplateParameter(String parameterName, String dataType) {
        this.parameterName = parameterName;
        this.dataType = dataType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public ThanosFunctionTemplateParameter setParameterName(String parameterName) {
        this.parameterName = parameterName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public ThanosFunctionTemplateParameter setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }


    @Override
    public String toString() {
        return dataType + " " + parameterName;
    }
}
