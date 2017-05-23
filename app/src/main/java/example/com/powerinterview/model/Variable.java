package example.com.powerinterview.model;

import java.io.Serializable;

import example.com.powerinterview.utils.VariablesUtil;

/**
 * Created by Игорь on 07.05.2017.
 */

public class Variable implements Serializable {



    public enum Type { String, Number, Boolean };

    private static final long serialVersionUID = 7504125690550250633L;
    private Object value;
    private Type type;

    public Variable(Object value) {
        this.value = value;
    }

    public Variable(Type type, Object value) {
        this.type = type;
        this.value = value;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * method determine expected variable type
     * @param object type that we want to cast
     * @return casted object
     */
    public Object castObjectToSameType(Object object) {
        //if in some reason type not set
        if(type == null)
            type = VariablesUtil.parseVariableToExpectType(this).getType();
        Type type = getType();
        if (type.equals(Type.Number)) {
            return Float.parseFloat(object.toString());
        }

        return object;
    }
}
