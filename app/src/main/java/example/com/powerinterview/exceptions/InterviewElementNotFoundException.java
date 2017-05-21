package example.com.powerinterview.exceptions;

/**
 * Created by Игорь on 06.05.2017.
 */

public class InterviewElementNotFoundException extends Exception {


    public InterviewElementNotFoundException() {
        super("Interview element not found");
    }

    public InterviewElementNotFoundException(String msg) {
        super(msg);
    }
}
