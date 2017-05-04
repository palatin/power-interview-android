package example.com.powerinterview.model;


import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.io.Serializable;

/**
 * Created by Игорь on 15.04.2017.
 */


@Table
public class User implements Serializable {

    @Unique
    private String email;
    private String password;

    public User() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
