package example.com.powerinterview.interfaces;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 04.04.2017.
 */

public interface IWidgetsProvider {

    Widget[] getWidgets(Context context) throws FactoryException;


    ICustomizableWidget getEditableSpecificWidget(Widget widget, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    ICustomizableWidget getEditableSpecificWidget(WidgetEntity widgetEntity, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
