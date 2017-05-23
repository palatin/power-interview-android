package example.com.powerinterview.model;

/**
 * Created by Игорь on 18.05.2017.
 */

public class Profile {

    private User user;

    public Profile() {
        user = new User();
    }

    private String job;

    private String imagePath;

    private String name;

    private String organization;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
