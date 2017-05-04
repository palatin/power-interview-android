package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.05.2017.
 */

public class PIBootstrapButtonEditable extends PIBootstrapButton implements ICustomizableWidget, IWidget {

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

        BaseCustomizableDialog baseCustomizableDialog = new BaseCustomizableDialog();

        Context context = view.getContext();

        List<BaseCustomizableDialog.BaseCustomizableInfo> attributes = new ArrayList<>();


        List<BaseCustomizableDialog.BaseCustomizableInfo> actions = new ArrayList<>();




        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("text", "Type here button text"));
        actions.add(baseCustomizableDialog.createBaseCustomizableInfo("go_to", "Enter id on which the transition will be made (example: 2)"));
        baseCustomizableDialog.createDialog(this, context, attributes, actions);
    }

    @Override
    public Widget getWidget() {
        Widget widget = new Widget();
        widget.setClassName(getClass().getSuperclass().getName());
        widget.setAttributes(getAttributes());
        widget.setActions(getActions());
        return widget;
    }


}
