package example.com.powerinterview.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIBootstrapButton extends BootstrapButton implements ICustomizableWidget, IWidget {


    private List<Attribute> attributes;
    private List<Action> actions;

    public PIBootstrapButton(Context context) {
        super(context);
        defaultInit();

    }

    public PIBootstrapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultInit();
    }

    private void defaultInit() {
        attributes = new ArrayList<>();
        actions = new ArrayList<>();
        setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        setText("Text");
        setBootstrapSize(DefaultBootstrapSize.LG);

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

        attributes.add(baseCustomizableDialog.createBaseCustomizableInfo("text", "Type here button text"));
        actions.add(baseCustomizableDialog.createBaseCustomizableInfo("go_to", "Enter id on which the transition will be made (example: 2)"));
        baseCustomizableDialog.createDialog(this, context, attributes, actions);
    }

    @Override
    public Widget getWidget() {
        Widget widget = new Widget();
        widget.setClassName(this.getClass().getName());
        widget.setAttributes(getAttributes());
        widget.setActions(getActions());
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
                case "text" :
                    setText(attribute.getValue());
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
