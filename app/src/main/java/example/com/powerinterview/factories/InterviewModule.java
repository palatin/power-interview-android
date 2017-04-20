package example.com.powerinterview.factories;

import android.view.View;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.powerinterview.core.ConstructorWidgetsProvider;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.IWidgetsProvider;

/**
 * Created by Игорь on 04.04.2017.
 */

@Module
public class InterviewModule {

    @Provides
    @Singleton
    IWidgetsProvider getConstructorWidgetsProvider(IPIWidgetsFactory widgetsFactory) {
        return new ConstructorWidgetsProvider(widgetsFactory);
    }

    @Provides
    @Singleton
    IPIWidgetsFactory getWidgetsFactory() {
        return new PIWidgetsFactory();
    }



}
