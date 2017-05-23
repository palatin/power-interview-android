package example.com.powerinterview.core;

import android.content.Context;

import example.com.powerinterview.exceptions.EmptyQuestionException;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.exceptions.InterviewException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.interfaces.QuestionController;
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
    private QuestionController baseQuestionController;
    private ConditionController baseConditionController;


    public BaseInterviewController(InterviewProvider provider, QuestionController controller) {
        this.provider = provider;
        this.context = provider.getContext();
        baseQuestionController = controller;
        baseConditionController = new ConditionController(this);
        baseQuestionController.subscribe(this);
    }

    public void initInterview(Interview interview) throws InterviewException, FactoryException {
        this.interview = interview;

        if(interview.getInterviewObjects() == null || interview.getInterviewObjects().isEmpty())
            throw new InterviewException("Seems like interview is empty");

        currentId = 0;
        parseInterviewObjects();
    }

    private void parseInterviewObjects() throws InterviewElementNotFoundException, FactoryException, EmptyQuestionException {
        InterviewObject object = getObjectById(currentId);
        produceInterviewObject(object);
    }


    private InterviewObject getObjectById(int id) throws InterviewElementNotFoundException {



        for(InterviewObject obj : interview.getInterviewObjects()) {
            if(id == obj.getId())
                return obj;
        }

        throw new InterviewElementNotFoundException("id=2" + String.valueOf(id));
    }

    private void produceInterviewObject(InterviewObject object) throws FactoryException, EmptyQuestionException {
        if(object instanceof Question) {
            provider.displayViews(baseQuestionController.parseQuestion((Question) object, context));
        }
        else if(object instanceof ConditionBlock) {
            baseConditionController.produceConditionBlock((ConditionBlock) object);
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
        } catch (Exception e) {
            e.printStackTrace();
            provider.handleException(e);
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
        variable.setValue(variable.castObjectToSameType(value));
        interview.getVariables().put(key, variable);
    }
}
