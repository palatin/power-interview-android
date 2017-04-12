package example.com.powerinterview.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 01.04.2017.
 */

public class EditQuestionActivity extends Activity {


    @Inject
    IWidgetsProvider widgetsProvider;

    @Inject
    IPIWidgetsFactory widgetsFactory;

    private Unbinder unbinder;
    private Question question;


    @BindView(R.id.widgetsLayout)
    LinearLayout widgetsLayout;

    @BindView(R.id.questionView)
    LinearLayout questionView;

    @BindView(R.id.doneButton)
    BootstrapButton doneButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);


        unbinder = ButterKnife.bind(this);

        ((PowerInterviewApp) getApplication()).getInterviewComponent().inject(this);

        try {
            question = getIntent().getExtras().getParcelable("question");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }


        initWidgets();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Widget> widgets = new ArrayList<>();

                for (int i = 0; i < questionView.getChildCount(); i++) {
                    widgets.add(((ICustomizableWidget)questionView.getChildAt(i)).getWidget());
                }

                question.setWidgets(widgets);
                Intent intent = new Intent();
                intent.putExtra("question", question);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void initWidgets() {

        final View.OnClickListener customizeWidget = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ICustomizableWidget) v).customize();
            }
        };


        if (question != null) {
            if(question.getWidgets() != null) {
                for (Widget widget : question.getWidgets()) {
                    try {
                        View view = widgetsFactory.create(widget, this);
                        questionView.addView(view);
                        view.setOnClickListener(customizeWidget);
                    } catch (FactoryException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        View.OnClickListener createWidget = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    View view = widgetsProvider.getEditableSpecificWidget(v, EditQuestionActivity.this);
                    if(view instanceof ICustomizableWidget) {
                        view.setOnClickListener(customizeWidget);
                    }
                    questionView.addView(view);
                } catch (FactoryException e) {
                    e.printStackTrace();
                }
            }
        };


        try {
            View[] views = widgetsProvider.getWidgets(EditQuestionActivity.this);
            for (View view: views) {
                //TODO add visitor pattern
                view.setOnClickListener(createWidget);
                widgetsLayout.addView(view);
            }
        } catch (FactoryException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
