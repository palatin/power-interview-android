package example.com.powerinterview.core;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

import javax.inject.Inject;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.model.Widget;
import example.com.powerinterview.ui.PIBootstrapButton;
import example.com.powerinterview.ui.PIBootstrapLabel;

/**
 * Created by Игорь on 04.04.2017.
 */

public class ConstructorWidgetsProvider implements IWidgetsProvider {


    private IPIWidgetsFactory widgetsFactory;
    private Class[] widgetsClasses;

    @Inject
    public ConstructorWidgetsProvider(IPIWidgetsFactory widgetsFactory) {
        this.widgetsFactory = widgetsFactory;
        widgetsClasses = new Class[] {PIBootstrapLabel.class, PIBootstrapButton.class, MaterialSpinner.class};
    }

    @Override
    public View[] getWidgets(Context context) throws FactoryException {

        View[] views = new View[widgetsClasses.length];


        int i = 0;
        for (Class widgetClass: widgetsClasses) {
            Widget widget = new Widget();
            widget.setClassName(widgetClass.getName());
            View view = widgetsFactory.create(widget, context);
            views[i] = view;
            i++;
        }

        return views;
    }

    @Override
    public View getEditableSpecificWidget(View specificView, Context context) throws FactoryException {
        Widget widget = new Widget();
        widget.setClassName(specificView.getClass().getName());
        View view = widgetsFactory.create(widget, context);
        return view;
    }
}
