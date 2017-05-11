package example.com.powerinterview.widgets;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 04.04.2017.
 */

public class ConstructorWidgetsProvider implements IWidgetsProvider {


    private IPIWidgetsFactory widgetsFactory;
    private Class[] widgetsClasses;

    @Inject
    public ConstructorWidgetsProvider(IPIWidgetsFactory widgetsFactory) {
        this.widgetsFactory = widgetsFactory;
        widgetsClasses = new Class[] {PIBootstrapLabel.class, PIBootstrapButton.class, PISpinner.class};
    }

    @Override
    public Widget[] getWidgets(Context context) throws FactoryException {

        Widget[] widgets = new Widget[widgetsClasses.length];


        int i = 0;
        for (Class widgetClass: widgetsClasses) {
            WidgetEntity widgetEntity = new WidgetEntity();
            widgetEntity.setClassName(widgetClass.getName());
            Widget widget = widgetsFactory.create(widgetEntity, context);
            widgets[i] = widget;
            i++;
        }

        return widgets;
    }

    @Override
    public ICustomizableWidget getEditableSpecificWidget(Widget widget, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return (ICustomizableWidget) Class.forName(widget.getClass().getName() + "Editable").getDeclaredConstructor(List.class, List.class, Context.class)
                .newInstance(widget.getAttributes(), widget.getActions(), context);
    }

    @Override
    public ICustomizableWidget getEditableSpecificWidget(WidgetEntity widgetEntity, Context context) throws FactoryException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        widgetEntity.setClassName(widgetEntity.getClassName() + "Editable");
        return (ICustomizableWidget) Class.forName(widgetEntity.getClassName()).getDeclaredConstructor(List.class, List.class, Context.class)
                .newInstance(widgetEntity.getAttributes(), widgetEntity.getActions(), context);
    }

}
