package example.com.powerinterview.core;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.factories.PIWidgetsFactory;
import example.com.powerinterview.interfaces.ActionListener;
import example.com.powerinterview.interfaces.Command;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 06.05.2017.
 */

public class QuestionController implements ActionListener {

    private Question question;
    private InterviewController interviewController;
    private IPIWidgetsFactory factory;

    public QuestionController(InterviewController controller, IPIWidgetsFactory factory) {
        this.interviewController = controller;
        this.factory = factory;
    }

    public View[] parseQuestion(Question question, Context context) throws FactoryException {
        this.question = question;
        List<WidgetEntity> widgetEntities = question.getWidgetEntities();
        List<Widget> widgets = new ArrayList<>(widgetEntities.size());
        View[] views = new View[widgetEntities.size()];
        int i = 0;
        for (WidgetEntity widgetEntity : widgetEntities) {
            Widget widget = factory.create(widgetEntity, context);
            widget.setActionListener(this);
            widgets.add(widget);
            views[i] = widget.getView();
            i++;
        }

        return views;
    }


    @Override
    public void notify(List<Command> commands, Command.QueueStatus queueStatus) {
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
