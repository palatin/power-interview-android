package example.com.powerinterview.core;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import example.com.powerinterview.components.AuthComponent;
import example.com.powerinterview.components.DaggerAuthComponent;
import example.com.powerinterview.factories.AuthModule;

/**
 * Created by Игорь on 30.03.2017.
 */

public class PowerInterviewApp extends Application {

    private AuthComponent authComponent;

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install(this);

        authComponent = DaggerAuthComponent.builder().authModule(new AuthModule()).build();

    }

    public AuthComponent getAuthComponent() {
        return authComponent;
    }


}
