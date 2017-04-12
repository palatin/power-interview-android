package example.com.powerinterview.components;

import javax.inject.Singleton;

import dagger.Component;
import example.com.powerinterview.factories.InterviewModule;
import example.com.powerinterview.activities.EditQuestionActivity;

/**
 * Created by Игорь on 04.04.2017.
 */

@Singleton
@Component(modules = InterviewModule.class)
public interface InterviewComponent {

    void inject(EditQuestionActivity activity);


}
