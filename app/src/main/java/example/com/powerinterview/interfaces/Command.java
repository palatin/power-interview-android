package example.com.powerinterview.interfaces;

/**
 * Created by Игорь on 07.05.2017.
 */

public interface Command {

    void execute(InterviewController controller);

    QueueStatus getQueue();

    enum QueueStatus {
        BaseQueue,
        LastQueue
    }
}
