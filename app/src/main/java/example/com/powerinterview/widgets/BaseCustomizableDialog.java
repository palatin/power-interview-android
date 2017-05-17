package example.com.powerinterview.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 10.04.2017.
 */

public class BaseCustomizableDialog {


    public void createDialog(final Widget widget, Context context, List<BaseCustomizableInfo> attributes, List<BaseCustomizableInfo> actions) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        final List<EditText> attributesEditTexts = createFields(attributes, context);
        final List<EditText> actionsEditTexts = createFields(actions, context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.dialog_cistomize_widget, null);

        LinearLayout attributesLayout = (LinearLayout) dialogView.findViewById(R.id.attributesLayout);
        LinearLayout actionsLayout = (LinearLayout) dialogView.findViewById(R.id.actionsLayout);
        for (View view: attributesEditTexts) {
            attributesLayout.addView(view);
        }


        for (View view: actionsEditTexts) {
            actionsLayout.addView(view);
        }



        dialogView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Attribute> attributes = new ArrayList<>();
                for (EditText editText: attributesEditTexts) {
                    Attribute attribute = new Attribute();
                    attribute.setKey(editText.getTag().toString());
                    attribute.setValue(editText.getText().toString());
                    attributes.add(attribute);
                }

                List<Action> actions = new ArrayList<>();
                for (EditText editText: actionsEditTexts) {
                    String value = editText.getText().toString();
                    if(!value.isEmpty()) {
                        Action action = new Action();
                        action.setKey(editText.getTag().toString());
                        action.setValue(editText.getText().toString());
                        actions.add(action);
                    }
                }
                widget.setAttributes(attributes);
                widget.setActions(actions);
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(dialogView);
        alertDialog.setTitle("Customize this widget!");




        alertDialog.show();

    }

    private List<EditText> createFields(List<BaseCustomizableInfo> data, Context context) {

        List<EditText> editTexts = new ArrayList<>();
        for (BaseCustomizableInfo info: data) {
            EditText editText = new EditText(context);
            editText.setTag(info.getAttributeKey());
            editText.setHint(info.getUserHint().isEmpty() ? info.getAttributeKey() : info.getUserHint());
            editText.setText(info.currentValue);
            editTexts.add(editText);
        }

        return editTexts;
    }

    public BaseCustomizableInfo createBaseCustomizableInfo(String attributeKey, String userHint, String value) {

        BaseCustomizableInfo baseCustomizableInfo = new BaseCustomizableInfo(attributeKey);

        baseCustomizableInfo.setUserHint(userHint);

        baseCustomizableInfo.setCurrentValue(value);

        return baseCustomizableInfo;
    }


    class BaseCustomizableInfo {

        private String attributeKey;
        private String userHint;
        private String currentValue = "";

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

        public String getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(String currentValue) {
            this.currentValue = currentValue;
        }
    }
}


