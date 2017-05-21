package example.com.powerinterview.components;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;
import example.com.powerinterview.factories.AuthModule;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.network.AuthClient;

/**
 * Created by Игорь on 15.04.2017.
 */
@Singleton
@Component(modules = AuthModule.class)
public interface AuthComponent {

    AuthClient authClient();

    AccountManager accountManager();

}
