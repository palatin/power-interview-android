package example.com.powerinterview.widgets;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 13.04.2017.
 */

public class PISpinner extends BaseWidget implements Widget {


    protected MaterialSpinner view;

    public PISpinner(List<Attribute> attributes, List<Action> actions, Context context) {
        super(attributes, actions);

        view = new MaterialSpinner(context);
        defaultInit();
    }


    private void defaultInit() {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 60, 0, 0);

        view.setLayoutParams(layoutParams);

        setViewAttributes();
    }


    @Override
    public void setAttributes(List<Attribute> attributes) {

        super.setAttributes(attributes);
        setViewAttributes();

    }

    @Override
    Object getValue() {
        return view.getItems().get(view.getSelectedIndex());
    }

    private void setViewAttributes() {
        for (Attribute attribute : attributes)
        {
            switch (attribute.getKey()) {
                case "data" :
                    view.setItems(attribute.getValue().split(";"));
                    break;
            }
        }
    }


    @Override
    public View getView() {
        return view;
    }
}
