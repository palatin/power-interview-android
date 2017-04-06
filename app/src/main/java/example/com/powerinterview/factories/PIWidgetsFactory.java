package example.com.powerinterview.factories;

import android.content.Context;
import android.view.View;

import java.lang.reflect.InvocationTargetException;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIWidgetsFactory  implements IPIWidgetsFactory{


    public View create(String className, Context context) throws FactoryException {

        View view = null;
        try {
            view = (View) Class.forName(className).getDeclaredConstructor(Context.class).newInstance(context);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FactoryException(ex.getMessage());
        }

        return view;
    }

}
