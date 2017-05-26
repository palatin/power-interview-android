package example.com.powerinterview.core;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.exceptions.EmptyQuestionException;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.factories.PIWidgetsFactory;
import example.com.powerinterview.interfaces.ActionListener;
import example.com.powerinterview.interfaces.Command;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.QuestionController;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 06.05.2017.
 */

public class BaseQuestionController implements QuestionController {

    private Question question;
    private InterviewController interviewController;
    private IPIWidgetsFactory factory;

    public BaseQuestionController(IPIWidgetsFactory factory) {
        this.factory = factory;
    }

    public View[] parseQuestion(Question question, Context context) throws FactoryException, EmptyQuestionException {
        this.question = question;
        List<WidgetEntity> widgetEntities = question.getWidgetEntities();
        if(widgetEntities == null)
            throw new EmptyQuestionException("Seems like question is empty");

        View[] views = new View[widgetEntities.size()];
        int i = 0;
        for (WidgetEntity widgetEntity : widgetEntities) {
            Widget widget = factory.create(widgetEntity, context);
            widget.setActionListener(this);
            views[i] = widget.getView();
            i++;
        }

        return views;
    }

    @Override
    public void subscribe(InterviewController controller) {
        this.interviewController = controller;
    }


    @Override
    public void notify(List<Command> commands, Command.QueueStatus queueStatus) {

        if(interviewController == null)
            return;

        for (Command command: commands) {

            if(command.getQueue().equals(queueStatus)) {

                command.execute(interviewController);
                commands.remove(command);
            }
        }

        if(!commands.isEmpty()) {
            this.notify(commands, Command.QueueStatus.LastQueue);
        }

    }
}
