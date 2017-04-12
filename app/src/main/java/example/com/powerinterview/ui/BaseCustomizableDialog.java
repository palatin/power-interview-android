package example.com.powerinterview.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IWidget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 10.04.2017.
 */

public class BaseCustomizableDialog {


    public void createDialog(final IWidget widget, Context context, List<BaseCustomizableInfo> attributes, List<BaseCustomizableInfo> actions) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        final List<EditText> attributesEditTexts = new ArrayList<>(attributes.size());
        final List<EditText> actionsEditTexts = new ArrayList<>(actions.size());

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (BaseCustomizableInfo info: attributes) {
            EditText editText = new EditText(context);
            editText.setTag(info.getAttributeKey());
            editText.setHint(info.getUserHint().isEmpty() ? info.getAttributeKey() : info.getUserHint());
            attributesEditTexts.add(editText);
        }

        for (BaseCustomizableInfo info: actions) {
            EditText editText = new EditText(context);
            editText.setTag(info.getAttributeKey());
            editText.setHint(info.getUserHint().isEmpty() ? info.getAttributeKey() : info.getUserHint());
            actionsEditTexts.add(editText);
        }

        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(linearLayout);
        TextView attributesTextView = new TextView(context);
        attributesTextView.setText("Attributes");
        linearLayout.addView(attributesTextView);
        for (View view: attributesEditTexts) {
            linearLayout.addView(view);
        }

        TextView actionsTextView = new TextView(context);
        actionsTextView.setText("Actions");
        linearLayout.addView(actionsTextView);

        for (View view: actionsEditTexts) {
            linearLayout.addView(view);
        }

        alertDialog.setView(scrollView);
        alertDialog.setTitle("Customize this widget!");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Attribute> attributes = new ArrayList<>();
                for (EditText editText: attributesEditTexts) {
                    Attribute attribute = new Attribute();
                    attribute.setKey(editText.getTag().toString());
                    attribute.setValue(editText.getText().toString());
                    attributes.add(attribute);
                }

                List<Action> actions = new ArrayList<>();
                for (EditText editText: actionsEditTexts) {
                    Action action = new Action();
                    action.setKey(editText.getTag().toString());
                    action.setValue(editText.getText().toString());
                    actions.add(action);
                }
                widget.setAttributes(attributes);
                widget.setActions(actions);
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    public BaseCustomizableInfo createBaseCustomizableInfo(String attributeKey, String userHint) {

        BaseCustomizableInfo baseCustomizableInfo = new BaseCustomizableInfo(attributeKey);

        baseCustomizableInfo.setUserHint(userHint);

        return baseCustomizableInfo;
    }

    class BaseCustomizableInfo {

        private String attributeKey;
        private String userHint;

        BaseCustomizableInfo(String attributeKey) {
            this.attributeKey = attributeKey;
        }

        String getAttributeKey() {
            return attributeKey;
        }


        String getUserHint() {
            return userHint;
        }

        void setUserHint(String userHint) {
            this.userHint = userHint;
        }
    }
}


