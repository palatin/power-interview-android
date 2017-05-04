package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.IWidget;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.04.2017.
 */

public class ConstructorWidgetsProvider implements IWidgetsProvider {


    private IPIWidgetsFactory widgetsFactory;
    private Class[] widgetsClasses;

    @Inject
    public ConstructorWidgetsProvider(IPIWidgetsFactory widgetsFactory) {
        this.widgetsFactory = widgetsFactory;
        widgetsClasses = new Class[] {PIBootstrapButton.class, PISpinner.class};
    }

    @Override
    public IWidget[] getWidgets(Context context) throws FactoryException {

        IWidget[] widgets = new IWidget[widgetsClasses.length];


        int i = 0;
        for (Class widgetClass: widgetsClasses) {
            Widget widget = new Widget();
            widget.setClassName(widgetClass.getName());
            widgets[i] = widgetsFactory.create(widget, context);
            i++;
        }

        return widgets;
    }

    @Override
    public ICustomizableWidget getEditableSpecificWidget(IWidget widget, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return (ICustomizableWidget) Class.forName(widget.getClass().getName() + "Editable").getDeclaredConstructor(List.class, List.class, Context.class)
                .newInstance(widget.getAttributes(), widget.getActions(), context);
    }

    @Override
    public ICustomizableWidget getEditableSpecificWidget(Widget widget, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        widget.setClassName(widget.getClassName() + "Editable");
        return (ICustomizableWidget) Class.forName(widget.getClassName()).getDeclaredConstructor(List.class, List.class, Context.class)
                .newInstance(widget.getAttributes(), widget.getActions(), context);
    }

}
