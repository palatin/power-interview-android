package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.05.2017.
 */

public class PISpinnerEditable extends PISpinner implements ICustomizableWidget {


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
        Context context = view.getContext();

        List<BaseCustomizableDialog.BaseCustomizableInfo> attributes = new ArrayList<>();


        List<BaseCustomizableDialog.BaseCustomizableInfo> actions = new ArrayList<>();


        BaseCustomizableDialog baseCustomizableDialog = new BaseCustomizableDialog();

        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("data", "Type here list data with \";\" example(text1;text2)"));
        baseCustomizableDialog.createDialog(this, context, attributes, actions);
    }

    @Override
    public Widget getWidget() {
        Widget widget = new Widget();
        widget.setClassName(getClass().getSuperclass().getName());
        widget.setAttributes(getAttributes());
        widget.setActions(actions);
        return widget;
    }

}
