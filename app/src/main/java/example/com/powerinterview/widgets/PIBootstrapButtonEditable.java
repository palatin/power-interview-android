package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 04.05.2017.
 */

public class PIBootstrapButtonEditable extends PIBootstrapButton implements ICustomizableWidget, Widget {


    private BaseCustomizableDialog baseCustomizableDialog = new BaseCustomizableDialog();

    public PIBootstrapButtonEditable(List<Attribute> attributes, List<Action> actions, Context context) {
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


        Context context = view.getContext();

        List<BaseCustomizableDialog.BaseCustomizableInfo> attributes = new ArrayList<>();


        List<BaseCustomizableDialog.BaseCustomizableInfo> actions = new ArrayList<>();


        List<Action> currentActions = getActions();
        int index = -1;

        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("text", "Type here button text", view.getText().toString()));
        Action gotoAction = new Action();
        gotoAction.setKey("go_to");
        index = currentActions.indexOf(gotoAction);
        actions.add(baseCustomizableDialog.createBaseCustomizableInfo("go_to", "Enter id on which the transition will be made (example: 2)",
                index == -1 ? "" : currentActions.get(index).getValue()));


        Action bindAction = new Action();
        bindAction.setKey("bind");
        index = currentActions.indexOf(bindAction);
        actions.add(baseCustomizableDialog.createBaseCustomizableInfo("bind", "Bind variable to value of this widget. Set variable's name to bind.",
                index == -1 ? "" : currentActions.get(index).getValue()));
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
