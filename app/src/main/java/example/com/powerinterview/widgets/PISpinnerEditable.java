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
 * Created by Игорь on 04.05.2017.
 */

public class PISpinnerEditable extends PISpinner implements ICustomizableWidget {


    BaseCustomizableDialog baseCustomizableDialog;

    public PISpinnerEditable(List<Attribute> attributes, List<Action> actions, Context context) {

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


        BaseCustomizableDialog baseCustomizableDialog = new BaseCustomizableDialog();

        List<Attribute> currentAttributes = getAttributes();
        List<Action> currentActions = getActions();

        int index = -1;

        Attribute attribute = new Attribute();
        attribute.setKey("data");
        index = currentAttributes.indexOf(attribute);

        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("data", "Type here list data with \";\" example(text1;text2)",
                index == -1 ? "" : currentAttributes.get(index).getValue()));

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
        widgetEntity.setActions(actions);
        return widgetEntity;
    }

}
