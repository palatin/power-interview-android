package example.com.powerinterview.managers;

import android.content.Context;

import example.com.powerinterview.model.Account;
import example.com.powerinterview.model.User;

/**
 * Created by Игорь on 15.04.2017.
 */

public class AccountManager {

    private Account account;

    public AccountManager() {
        account = new Account();
    }

    public void storeUser(User user) {
        account.setUser(user);
    }

    public void setToken(String token) {
        account.setToken(token);
    }

    public String getToken(String token) {
        return account.getToken();
    }
}
