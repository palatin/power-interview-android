package example.com.powerinterview.widgets;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import java.util.List;

import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIBootstrapButton extends BaseWidget implements Widget {




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


}
