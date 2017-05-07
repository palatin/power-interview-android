package example.com.powerinterview.widgets;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Action;
import example.com.powerinterview.model.Attribute;

/**
 * Created by Игорь on 04.05.2017.
 */

abstract class BaseWidget implements Widget {


    protected List<Attribute> attributes;
    protected List<Action> actions;

    public BaseWidget(List<Attribute> attributes, List<Action> actions) {
        this.attributes = attributes != null ? attributes : new ArrayList<Attribute>();
        this.actions = actions != null ? actions : new ArrayList<Action>();
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {
        for (Attribute attribute : attributes)
        {
            int index = this.attributes.indexOf(attribute);
            if(index == -1)
                this.attributes.add(attribute);
            else
                this.attributes.set(index, attribute);
        }
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setActions(List<Action> actions) {

        for (Action action : actions) {
            int index = this.actions.indexOf(action);
            if (index == -1)
                this.actions.add(action);
            else
                this.actions.set(index, action);
        }

    }

    @Override
    public List<Action> getActions() {
        return actions;
    }


}
