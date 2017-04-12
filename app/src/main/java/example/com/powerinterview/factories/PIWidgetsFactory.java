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

public class PIWidgetsFactory  implements IPIWidgetsFactory{


    public View create(Widget widget, Context context) throws FactoryException {

        View view = null;
        try {
            view = (View) Class.forName(widget.getClassName()).getDeclaredConstructor(Context.class).newInstance(context);
            List<Attribute> attributes = widget.getAttributes();
            List<Action> actions = widget.getActions();
            if(attributes != null && !attributes.isEmpty())
                ((IWidget) view).setAttributes(attributes);
            if(actions != null && !actions.isEmpty())
                ((IWidget) view).setActions(actions);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FactoryException(ex.getMessage());
        }

        return view;
    }

}
