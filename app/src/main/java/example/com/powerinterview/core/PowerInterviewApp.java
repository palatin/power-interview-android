package example.com.powerinterview.core;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import example.com.powerinterview.components.DaggerInterviewComponent;
import example.com.powerinterview.components.InterviewComponent;
import example.com.powerinterview.factories.InterviewModule;

import example.com.powerinterview.components.AuthComponent;
import example.com.powerinterview.components.DaggerAuthComponent;
import example.com.powerinterview.factories.AuthModule;

/**
 * Created by Игорь on 30.03.2017.
 */

public class PowerInterviewApp extends Application {

    private InterviewComponent interviewComponent;

    private AuthComponent authComponent;

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install(this);

        authComponent = DaggerAuthComponent.builder().authModule(new AuthModule()).build();

        interviewComponent = DaggerInterviewComponent.builder().interviewModule(new InterviewModule()).build();
    }


    public InterviewComponent getInterviewComponent() {
        return interviewComponent;
    }
    public AuthComponent getAuthComponent() {
        return authComponent;
    }


}
