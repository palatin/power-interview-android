package example.com.powerinterview.core;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Игорь on 30.03.2017.
 */

public class PowerInterviewApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        LeakCanary.install(this);

    }

}
