package example.com.powerinterview.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.components.InterviewComponent;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 01.04.2017.
 */

public class EditQuestionActivity extends Activity {



    private IWidgetsProvider widgetsProvider;



    private Unbinder unbinder;
    private Question question;


    @BindView(R.id.widgetsLayout)
    LinearLayout widgetsLayout;

    @BindView(R.id.questionView)
    LinearLayout questionView;




    private List<ICustomizableWidget> customizableWidgets;
    private Widget[] widgets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);


        unbinder = ButterKnife.bind(this);

        InterviewComponent interviewComponent = ((PowerInterviewApp) getApplication()).getInterviewComponent();
        widgetsProvider = interviewComponent.getConstructorWidgetsProvider();

        try {
            question = getIntent().getExtras().getParcelable("question");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        customizableWidgets = new ArrayList<>();


        initWidgets();

    }

    private void initWidgets() {

        if (question != null) {
            if(question.getWidgetEntities() != null) {
                for (WidgetEntity widgetEntity : question.getWidgetEntities()) {
                    try {
                        ICustomizableWidget customizableWidget = widgetsProvider.getEditableSpecificWidget(widgetEntity, EditQuestionActivity.this);
                        customizableWidgets.add(customizableWidget);
                        questionView.addView(((Widget) customizableWidget).getView());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        View.OnClickListener createWidget = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ICustomizableWidget customizableWidget = widgetsProvider.getEditableSpecificWidget(widgets[(int) v.getTag()], EditQuestionActivity.this);
                    customizableWidgets.add(customizableWidget);
                    questionView.addView(((Widget) customizableWidget).getView());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        try {
            widgets = widgetsProvider.getWidgets(EditQuestionActivity.this);
            int i = 0;
            for (Widget widget: widgets) {
                View view = widget.getView();
                view.setTag(i);
                view.setOnClickListener(createWidget);
                widgetsLayout.addView(view);
                i++;
            }
        } catch (FactoryException e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.cancelButton)
    public void onCancel(){
        finish();
    }

    @OnClick(R.id.doneButton)
    public void onDone(){
        List<WidgetEntity> widgetEntities = new ArrayList<>();

        for (ICustomizableWidget widget: customizableWidgets) {
            widgetEntities.add(widget.getWidget());
        }

        question.setWidgetEntities(widgetEntities);
        Intent intent = new Intent();
        intent.putExtra("question", (Parcelable) question);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
