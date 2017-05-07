package example.com.powerinterview.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.core.BaseInterviewController;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.managers.InterviewsTemplatesManager;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.ui.CustomToast;

public class InterviewActivity extends BaseWorkerActivity implements InterviewProvider {


    private AccountManager accountManager;
    private Unbinder unbinder;
    private InterviewsTemplatesManager interviewsTemplatesManager;
    private BaseInterviewController controller;

    private Interview interview;

    @BindView(R.id.interviewArea)
    LinearLayout interviewArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        File file = (File) getIntent().getSerializableExtra("template");

        interviewsTemplatesManager = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewManager();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();

        unbinder = ButterKnife.bind(this);

        try {
            interview = interviewsTemplatesManager.loadInterviewByFile(file);
            controller = new BaseInterviewController(this, ((PowerInterviewApp) getApplication()).getInterviewComponent().getWidgetsFactory());
            controller.initInterview(interview);

        } catch (IOException | ClassNotFoundException  e) {
            e.printStackTrace();
            showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
        } catch (FactoryException e) {
            e.printStackTrace();
            showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
        } catch (InterviewElementNotFoundException e) {
            e.printStackTrace();
            showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void displayViews(View[] views) {
        interviewArea.removeAllViews();
        for (View view: views) {
            interviewArea.addView(view);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
