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

import java.util.HashMap;
import java.util.Map;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IWidget;

/**
 * Created by Игорь on 04.04.2017.
 */

public class PIBootstrapButton extends BootstrapButton implements ICustomizableWidget, IWidget {

    public PIBootstrapButton(Context context) {
        super(context);
        defaultInit();

    }

    public PIBootstrapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultInit();
    }

    private void defaultInit() {
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
        final EditText textEditText = new EditText(context);
        textEditText.setHint("Button text");
        textEditText.setTag("text");
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(textEditText).create();
        alertDialog.setTitle("Customize your button");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, String> attributes = new HashMap<>();

                attributes.put("text", textEditText.getText().toString());
                setAttributes(attributes);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {

        for (Map.Entry<String, String> entry : attributes.entrySet())
        {
            switch (entry.getKey()) {
                case "text" :
                    setText(entry.getValue());
                    break;
            }
        }
    }
}
