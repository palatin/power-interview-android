package example.com.powerinterview.interfaces;

import android.content.Context;
import android.view.View;

import java.lang.reflect.InvocationTargetException;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.04.2017.
 */

public interface IWidgetsProvider {

    IWidget[] getWidgets(Context context) throws FactoryException;


    ICustomizableWidget getEditableSpecificWidget(IWidget widget, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    ICustomizableWidget getEditableSpecificWidget(Widget widget, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
