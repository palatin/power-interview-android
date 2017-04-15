package example.com.powerinterview.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 13.04.2017.
 */

public class PISpinner extends MaterialSpinner implements ICustomizableWidget, IWidget {


    private List<Attribute> attributes;
    private List<Action> actions;

    public PISpinner(Context context) {
        super(context);
        defaultInit();
    }

    public PISpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultInit();
    }

    public PISpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultInit();
    }

    private void defaultInit() {
        attributes = new ArrayList<>();
        actions = new ArrayList<>();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 60, 0, 0);

        setLayoutParams(layoutParams);
    }

    @Override
    public void customize() {
        Context context = getContext();

        List<BaseCustomizableDialog.BaseCustomizableInfo> attributes = new ArrayList<>();


        List<BaseCustomizableDialog.BaseCustomizableInfo> actions = new ArrayList<>();


        BaseCustomizableDialog baseCustomizableDialog = new BaseCustomizableDialog();

        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("data", "Type here list data with \";\" example(text1;text2)"));
        baseCustomizableDialog.createDialog(this, context, attributes, actions);
    }

    @Override
    public Widget getWidget() {
        Widget widget = new Widget();
        widget.setClassName(this.getClass().getName());
        widget.setAttributes(getAttributes());
        widget.setActions(actions);
        return widget;
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {

        for (Attribute attribute : attributes)
        {
            int index = this.attributes.indexOf(attribute);
            if(index == -1)
                this.attributes.add(attribute);
            else
                this.attributes.set(index, attribute);
            switch (attribute.getKey()) {
                case "data" :
                    setItems(attribute.getValue().split(";"));
                    break;
            }
        }
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setActions(List<Action> actions) {
        for (Action action : actions) {
            int index = this.actions.indexOf(action);
            if (index == -1)
                this.actions.add(action);
            else
                this.actions.set(index, action);
        }
    }


    @Override
    public List<Action> getActions() {
        return actions;
    }


}
