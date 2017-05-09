package example.com.powerinterview.utils;

import java.util.Scanner;

import example.com.powerinterview.model.Variable;

/**
 * Created by Игорь on 08.05.2017.
 */

public class VariablesUtil {

    public static Variable.Type identifyVariableType(Object object) {
        return null;
    }

    public static Variable parseVariableToExpectType(Variable variable) {
        Object object = variable.getValue();
        if(isFloat(object)) {
            variable.setValue(Float.parseFloat(object.toString()));
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
}
