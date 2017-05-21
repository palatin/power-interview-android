package example.com.powerinterview.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import java.util.List;

import example.com.powerinterview.interfaces.ActionListener;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 02.04.2017.
 */

public class PIBootstrapLabel extends BaseWidget implements Widget {


    protected AwesomeTextView view;

    public PIBootstrapLabel(List<Attribute> attributes, List<Action> actions, Context context) {
        super(attributes, actions);

        view = new AwesomeTextView(context);
        defaultInit();
    }

    private void defaultInit() {
        view.setText("Text");
        view.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 60, 0, 0);

        view.setLayoutParams(layoutParams);
        setViewAttributes();
    }



    @Override
    Object getValue() {
        return null;
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
