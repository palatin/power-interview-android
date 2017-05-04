package example.com.powerinterview.managers;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.orm.SugarRecord;

import example.com.powerinterview.model.Account;
import example.com.powerinterview.model.User;
import example.com.powerinterview.utils.Encrypt;

/**
 * Created by Игорь on 15.04.2017.
 */

public class AccountManager {

    private Account account;

    public AccountManager() {
        account = new Account();
    }

    private void storeUser(User user) {
        account.setUser(user);
    }

    public void setToken(String token) {
        account.setToken(token);
    }

    public String getToken() {
        return account.getToken();
    }


    public void storeAccount(User user, Context context) throws Exception {
        removeAccount();
        user.setPassword(Encrypt.encryptByAES(user.getPassword(), getEncryptKey(context)));
        SugarRecord.save(user);
        storeUser(user);
    }

    public void removeAccount() {
        try {
            SugarRecord.deleteAll(User.class);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User getAccount(Context context) throws Exception {
        User user = SugarRecord.first(User.class);;
        if(user != null) {
            user.setPassword(Encrypt.decryptByAES(user.getPassword(), getEncryptKey(context)));
            storeUser(user);
        }

        return user;
    }

    private String getEncryptKey(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String key = telephonyManager.getDeviceId();
        if(key == null)
            throw new NullPointerException();
        return key;
    }
}
