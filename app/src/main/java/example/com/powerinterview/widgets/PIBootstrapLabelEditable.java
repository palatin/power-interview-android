package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 11.05.2017.
 */

public class PIBootstrapLabelEditable extends PIBootstrapLabel implements ICustomizableWidget {


    private BaseCustomizableDialog baseCustomizableDialog;

    public PIBootstrapLabelEditable(List<Attribute> attributes, List<Action> actions, Context context) {
        super(attributes, actions, context);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                customize();
                return true;
            }
        });
    }

    @Override
    public void customize() {

        baseCustomizableDialog = new BaseCustomizableDialog();
        Context context = view.getContext();

        List<BaseCustomizableDialog.BaseCustomizableInfo> attributes = new ArrayList<>();


        List<BaseCustomizableDialog.BaseCustomizableInfo> actions = new ArrayList<>();

        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("text", "Type here button text", view.getText().toString()));

        baseCustomizableDialog.createDialog(this, context, attributes, actions);
    }

    @Override
    public WidgetEntity getWidget() {
        WidgetEntity widgetEntity = new WidgetEntity();
        widgetEntity.setClassName(getClass().getSuperclass().getName());
        widgetEntity.setAttributes(getAttributes());
        widgetEntity.setActions(getActions());
        return widgetEntity;
    }


}
