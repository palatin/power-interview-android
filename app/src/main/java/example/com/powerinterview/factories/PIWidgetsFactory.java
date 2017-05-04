package example.com.powerinterview.factories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.IWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIWidgetsFactory  implements IPIWidgetsFactory {


    public IWidget create(Widget widget, Context context) throws FactoryException {

        IWidget iWidget = null;
        try {
            iWidget = (IWidget) Class.forName(widget.getClassName()).getDeclaredConstructor(List.class, List.class, Context.class)
                    .newInstance(new Object[] {widget.getAttributes(), widget.getActions(), context});
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FactoryException(ex.getMessage());
        }

        return iWidget;
    }

}
