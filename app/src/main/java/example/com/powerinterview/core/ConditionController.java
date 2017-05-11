package example.com.powerinterview.core;

import java.util.List;

import example.com.powerinterview.interfaces.InterviewController;
import example.com.powerinterview.model.Condition;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.Variable;

/**
 * Created by Игорь on 10.05.2017.
 */

public class ConditionController {

    private InterviewController controller;

    public ConditionController(InterviewController controller) {
        this.controller = controller;
    }


    public void produceConditionBlock(ConditionBlock conditionBlock) {

        List<Condition> conditions = conditionBlock.getConditions();
        for (Condition condition: conditions) {
            if(checkCondition(condition)) {
                controller.moveTo(Integer.parseInt(condition.getGoTo()));
            }
        }
    }

    private boolean checkCondition(Condition condition) {
        Variable letSideVariable = controller.getVariable(condition.getLeftSide());
        Variable rightSideVariable = controller.getVariable(condition.getRightSide());
        String operand = condition.getOperand();
        Object rightSide;
        if(rightSideVariable == null)
            rightSide = letSideVariable.castObjectToSameType(condition.getRightSide());
        else
            rightSide = rightSideVariable.getValue();
        switch (operand) {
            case ">":
                return (float) letSideVariable.getValue() > (float) rightSide;
            case "<":
                return (float) letSideVariable.getValue() < (float) rightSide;
            case "≥":
                return (float) letSideVariable.getValue() >= (float) rightSide;
            case "≤":
                return (float) letSideVariable.getValue() <= (float) rightSide;
            case "=":
                return  letSideVariable.getValue().equals(rightSide);
            case "≠":
                return  !letSideVariable.getValue().equals(rightSide);
        }

        return false;
    }


}
