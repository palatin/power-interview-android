package example.com.powerinterview.interfaces;

import java.util.List;
import java.util.Map;

import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 05.04.2017.
 */

public interface IWidget {

    void setAttributes(List<Attribute> attributes);

    List<Attribute> getAttributes();

    void setActions(List<Action> actions);

    List<Action> getActions();

}
