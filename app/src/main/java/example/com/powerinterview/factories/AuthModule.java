package example.com.powerinterview.factories;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.network.AuthClient;

/**
 * Created by Игорь on 15.04.2017.
 */


@Module
public class AuthModule {


    @Provides
    @Singleton
    AuthClient getAuthClient() {
        return new AuthClient();
    }

    @Provides
    @Singleton
    AccountManager getAccountManager() {
        return new AccountManager();
    }

}
