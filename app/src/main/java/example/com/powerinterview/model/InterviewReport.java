package example.com.powerinterview.model;

/**
 * Created by Игорь on 20.05.2017.
 */

public class InterviewReport {

    private long id;

    private String templateName;

    private String respondentEmail;

    private String date;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getRespondentEmail() {
        return respondentEmail;
    }

    public void setRespondentEmail(String respondentEmail) {
        this.respondentEmail = respondentEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
