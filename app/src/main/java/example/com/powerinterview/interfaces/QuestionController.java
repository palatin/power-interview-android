package example.com.powerinterview.interfaces;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.exceptions.EmptyQuestionException;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 22.05.2017.
 */

public interface QuestionController extends ActionListener {

    View[] parseQuestion(Question question, Context context) throws FactoryException, EmptyQuestionException;

    void subscribe(InterviewController controller);

    void notify(List<Command> commands, Command.QueueStatus queueStatus);

}
