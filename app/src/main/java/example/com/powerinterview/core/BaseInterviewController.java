package example.com.powerinterview.core;

import android.content.Context;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.factories.PIWidgetsFactory;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.Variable;

/**
 * Created by Игорь on 06.05.2017.
 */

public class BaseInterviewController implements InterviewController {

    private InterviewProvider provider;
    private Interview interview;
    private int currentId;
    private Context context;
    private QuestionController questionController;
    private ConditionController conditionController;
    private IPIWidgetsFactory factory;


    public BaseInterviewController(InterviewProvider provider, IPIWidgetsFactory factory) {
        this.provider = provider;
        this.context = provider.getContext();
        this.factory = factory;
        questionController = new QuestionController(this, factory);
        conditionController = new ConditionController(this);
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
        else if(object instanceof ConditionBlock) {
            conditionController.produceConditionBlock((ConditionBlock) object);
        }
    }

    @Override
    public void moveTo(int id) {

        if(id == -1) {
            endInterview();
            return;
        }
        currentId = id;
        try {
            parseInterviewObjects();
        } catch (InterviewElementNotFoundException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }

    private void endInterview() {
        provider.endInterview();
    }

    @Override
    public Variable getVariable(String key) {
        return interview.getVariables().get(key);
    }

    @Override
    public void setVariable(String key, Object value) {
        Variable variable = interview.getVariables().get(key);
        variable.castObjectToSameType(value);
        variable.setValue(value);
        interview.getVariables().put(key, variable);
    }
}
