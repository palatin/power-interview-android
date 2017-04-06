package example.com.powerinterview.components;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.factories.InterviewModule;
import example.com.powerinterview.fragments.EditQuestionFragment;

/**
 * Created by Игорь on 04.04.2017.
 */

@Singleton
@Component(modules = InterviewModule.class)
public interface InterviewComponent {

    void inject(EditQuestionFragment fragment);


}
