package example.com.powerinterview.core;

import android.content.Context;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.factories.PIWidgetsFactory;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;

/**
 * Created by Игорь on 06.05.2017.
 */

public class BaseInterviewController implements InterviewController {

    private InterviewProvider provider;
    private Interview interview;
    private int currentId;
    private Context context;
    private QuestionController questionController;
    private IPIWidgetsFactory factory;


    public BaseInterviewController(InterviewProvider provider, IPIWidgetsFactory factory) {
        this.provider = provider;
        this.context = provider.getContext();
        this.factory = factory;
        questionController = new QuestionController(this, factory);
    }

    public void initInterview(Interview interview) throws InterviewElementNotFoundException, FactoryException {
        this.interview = interview;
        currentId = 0;
        parseInterviewObjects();
    }

    private void parseInterviewObjects() throws InterviewElementNotFoundException, FactoryException {
        InterviewObject object = getObjectById(currentId);
        produceInterviewObject(object);
    }


    private InterviewObject getObjectById(int id) throws InterviewElementNotFoundException {

        for(InterviewObject obj : interview.getInterviewObjects()) {
            if(id == obj.getId())
                return obj;
        }

        throw new InterviewElementNotFoundException();
    }

    private void produceInterviewObject(InterviewObject object) throws FactoryException {
        if(object instanceof Question) {
            provider.displayViews(questionController.parseQuestion((Question) object, context));
        }
    }

    @Override
    public void moveTo(int id) {
        currentId = id;
        try {
            parseInterviewObjects();
        } catch (InterviewElementNotFoundException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }
}
