package example.com.powerinterview.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import java.util.List;

import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 23.05.2017.
 */

public class PIBootstrapInput extends BaseWidget implements Widget, TextWatcher {




    protected BootstrapEditText view;

    public PIBootstrapInput(List<Attribute> attributes, List<Action> actions, Context context) {
        super(attributes, actions);

        view = new BootstrapEditText(context);
        defaultInit();
    }


    private void defaultInit() {

        view.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        view.setBootstrapSize(DefaultBootstrapSize.LG);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 60, 0, 0);

        view.setLayoutParams(layoutParams);

        view.addTextChangedListener(this);

        setViewAttributes();
    }



    @Override
    Object getValue() {

        return view.getText().toString();
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
                case "hint" :
                    view.setHint(attribute.getValue());
                    break;
            }
        }
    }


    @Override
    public View getView() {
        return view;
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        produceActions();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
