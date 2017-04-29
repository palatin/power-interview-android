package example.com.powerinterview.components;

import javax.inject.Singleton;

import dagger.Component;
import example.com.powerinterview.factories.InterviewModule;
import example.com.powerinterview.activities.EditQuestionActivity;
import example.com.powerinterview.factories.PIWidgetsFactory;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.network.InterviewClient;

/**
 * Created by Игорь on 04.04.2017.
 */

@Singleton
@Component(modules = InterviewModule.class)
public interface InterviewComponent {

    IWidgetsProvider getConstructorWidgetsProvider();

    IPIWidgetsFactory getWidgetsFactory();

    InterviewClient getInterviewClient();


}
