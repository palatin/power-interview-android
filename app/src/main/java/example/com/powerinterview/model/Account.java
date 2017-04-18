package example.com.powerinterview.model;

/**
 * Created by Игорь on 16.04.2017.
 */

public class Account {

    private User user;
    private String token;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
