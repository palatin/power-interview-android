package example.com.powerinterview.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.model.Condition;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.Variable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Игорь on 22.05.2017.
 */
public class ConditionControllerTest {


    InterviewController controller = mock(InterviewController.class);

    ConditionController conditionController = new ConditionController(controller);


    /**
     * test basic condition parser
     * @throws Exception
     */
    @Test
    public void produceConditionBlock() throws Exception {

        //create condition block
        ConditionBlock conditionBlock = new ConditionBlock();
        List<Condition> conditions = new ArrayList<>();
        Condition condition = new Condition();
        condition.setCondition("test_variable::>::5");
        condition.setGoTo("1");
        //this condition is true
        Condition anotherCondition = new Condition();
        anotherCondition.setCondition("test_variable::<::5");
        anotherCondition.setGoTo("2");
        conditions.add(condition);
        conditions.add(anotherCondition);
        conditionBlock.setConditions(conditions);
        Variable variable = new Variable(4);
        when(controller.getVariable("test_variable")).thenReturn(variable);

        conditionController.produceConditionBlock(conditionBlock);
        verify(controller, times(2)).getVariable("test_variable");
        verify(controller).moveTo(2);
    }

}