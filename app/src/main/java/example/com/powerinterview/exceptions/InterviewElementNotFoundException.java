package example.com.powerinterview.exceptions;

/**
 * Created by Игорь on 06.05.2017.
 */

public class InterviewElementNotFoundException extends InterviewException {


    public InterviewElementNotFoundException() {
        super("Interview element not found");
    }

    public InterviewElementNotFoundException(String msg) {
        super("Interview element not found " +msg);
    }
}
