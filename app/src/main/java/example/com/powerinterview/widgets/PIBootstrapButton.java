package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import java.util.List;

import example.com.powerinterview.interfaces.ActionListener;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIBootstrapButton extends BaseWidget implements Widget, View.OnClickListener {




    protected BootstrapButton view;

    public PIBootstrapButton(List<Attribute> attributes, List<Action> actions, Context context) {
        super(attributes, actions);

        view = new BootstrapButton(context);
        defaultInit();
    }


    private void defaultInit() {

        view.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        view.setText("Text");
        view.setBootstrapSize(DefaultBootstrapSize.LG);
        view.setOnClickListener(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 60, 0, 0);

        view.setLayoutParams(layoutParams);

        setViewAttributes();
    }



    @Override
    Object getValue() {
        Attribute attribute = new Attribute();
        attribute.setKey("value");
        List<Attribute> attributes = getAttributes();
        int index = attributes.indexOf(attribute);
        return index == - 1 ? view.getText().toString() : attributes.get(index).getValue();
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {
        super.setAttributes(attributes);
        setViewAttributes();
    }

    private void setViewAttributes() {
        for (Attribute attribute : attributes)
        {
            switch (attribute.getKey()) {
                case "text" :
                    view.setText(attribute.getValue());
                    break;
            }
        }
    }


    @Override
    public View getView() {
        return view;
    }




    @Override
    public void onClick(View v) {
        produceActions();
    }
}
