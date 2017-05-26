package example.com.powerinterview.core;


import android.app.Application;
import android.content.Context;
import android.content.Intent;


import com.orm.SugarContext;
import com.squareup.leakcanary.LeakCanary;

import example.com.powerinterview.activities.ExceptionActivity;
import example.com.powerinterview.components.DaggerInterviewComponent;
import example.com.powerinterview.components.InterviewComponent;
import example.com.powerinterview.factories.InterviewModule;

import example.com.powerinterview.components.AuthComponent;
import example.com.powerinterview.components.DaggerAuthComponent;
import example.com.powerinterview.factories.AuthModule;

/**
 * Created by Игорь on 30.03.2017.
 */

public class PowerInterviewApp extends Application implements Thread.UncaughtExceptionHandler {

    private InterviewComponent interviewComponent;
    private int permission = 1000;


    private AuthComponent authComponent;

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install(this);

        authComponent = DaggerAuthComponent.builder().authModule(new AuthModule()).build();

        interviewComponent = DaggerInterviewComponent.builder().interviewModule(new InterviewModule()).build();

        SugarContext.init(getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler(this);


    }


    public InterviewComponent getInterviewComponent() {
        return interviewComponent;
    }

    public AuthComponent getAuthComponent() {
        return authComponent;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handleException(e);
    }


    /**
     * Method start the new activity in which user get info about error
     * @param ex - current exception
     */
    public void handleException(Throwable ex) {
        Intent intent = new Intent(this, ExceptionActivity.class);
        intent.putExtra("exception", ex);
        startActivity(intent);
    }
}
