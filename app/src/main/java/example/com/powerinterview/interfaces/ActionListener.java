package example.com.powerinterview.interfaces;

import java.util.List;

import example.com.powerinterview.model.Action;

/**
 * Created by Игорь on 07.05.2017.
 */

public interface ActionListener {


    void notify(List<Command> commands, Command.QueueStatus queueStatus);
}
