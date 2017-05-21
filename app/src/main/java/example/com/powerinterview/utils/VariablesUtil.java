package example.com.powerinterview.utils;

import java.util.Scanner;

import example.com.powerinterview.model.Variable;

/**
 * Created by Игорь on 08.05.2017.
 */

public class VariablesUtil {

    //this method return variable type with casted inner variable to expected type
    public static Variable parseVariableToExpectType(Variable variable) {
        Object object = variable.getValue();
        if(isFloat(object)) {
            variable.setValue(Float.parseFloat(object.toString()));
            variable.setType(Variable.Type.Number);
        }
        else if(isBoolean(object)) {
            variable.setValue(Boolean.parseBoolean(object.toString()));
            variable.setType(Variable.Type.Boolean);
        }
        else {
            variable.setType(Variable.Type.String);
        }

        return variable;
    }

    private static boolean isFloat(Object object) {

        String s = object.toString();
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextFloat()) return false;
        // we know it starts with a valid int, now make sure
        // there's nothing left!
        sc.nextFloat();
        return !sc.hasNext();

    }

    private static boolean isBoolean(Object object) {

        return object.toString().toLowerCase().equals("true") || object.toString().toLowerCase().equals("false");

    }

}
