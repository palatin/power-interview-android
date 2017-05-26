package example.com.powerinterview.core;

import android.content.Context;
import android.test.mock.MockContext;
import android.view.View;

import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.ProcessingInstruction;


import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.exceptions.EmptyQuestionException;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.exceptions.InterviewException;
import example.com.powerinterview.factories.PIWidgetsFactory;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.interfaces.QuestionController;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.WidgetEntity;
import example.com.powerinterview.widgets.PIBootstrapButton;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Игорь on 22.05.2017.
 */
public class BaseInterviewControllerTest {


    private BaseInterviewController controller;
    private InterviewProvider provider;
    private IPIWidgetsFactory factory;
    private QuestionController questionController;

    public BaseInterviewControllerTest() throws FactoryException, EmptyQuestionException {
        provider = mock(InterviewProvider.class);
        when(provider.getContext()).thenReturn(mock(Context.class));
        factory = mock(IPIWidgetsFactory.class);
        questionController = mock(QuestionController.class);
        controller = new BaseInterviewController(provider, questionController);

    }

    /**
     * Test for basic InterviewController initialize
     * @throws FactoryException
     * @throws InterviewElementNotFoundException
     * @throws EmptyQuestionException
     */
    @Test
    public void initInterview() throws FactoryException, InterviewException {

        Interview interview = new Interview();
        List<InterviewObject> objects = new ArrayList<>();
        Question question = new Question();
        question.setId(0);
        objects.add(question);
        interview.setInterviewObjects(objects);
        when(questionController.parseQuestion(Mockito.any(Question.class), Mockito.any(Context.class))).thenReturn(new View[] {});
        controller.initInterview(interview);

        verify(provider).displayViews(Mockito.any(View[].class));
    }

    @Test(expected = InterviewException.class)
    public void initInterviewWithEmptyObjects() throws FactoryException, InterviewException {

        Interview interview = new Interview();
        controller.initInterview(interview);

    }


}