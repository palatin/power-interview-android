package example.com.powerinterview.factories;

import android.content.Context;

import java.util.List;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIWidgetsFactory  implements IPIWidgetsFactory {


    public Widget create(WidgetEntity widgetEntity, Context context) throws FactoryException {

        Widget widget = null;
        try {
            widget = (Widget) Class.forName(widgetEntity.getClassName()).getDeclaredConstructor(List.class, List.class, Context.class)
                    .newInstance(new Object[] {widgetEntity.getAttributes(), widgetEntity.getActions(), context});
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FactoryException(ex.getMessage());
        }

        return widget;
    }

}
