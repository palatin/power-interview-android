package example.com.powerinterview.interfaces;

import example.com.powerinterview.model.Variable;

/**
 * Created by Игорь on 06.05.2017.
 */

public interface InterviewController {

    void moveTo(int id);

    Variable getVariable(String key);

    void setVariable(String key, Variable variable);

}
