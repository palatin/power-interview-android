package example.com.powerinterview.core;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import example.com.powerinterview.components.DaggerInterviewComponent;
import example.com.powerinterview.components.InterviewComponent;
import example.com.powerinterview.factories.InterviewModule;

/**
 * Created by Игорь on 30.03.2017.
 */

public class PowerInterviewApp extends Application {

    private InterviewComponent interviewComponent;

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install(this);

        interviewComponent = DaggerInterviewComponent.builder().interviewModule(new InterviewModule()).build();
    }


    public InterviewComponent getInterviewComponent() {
        return interviewComponent;
    }
}
