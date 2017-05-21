package example.com.powerinterview.interfaces;

import android.content.Context;
import android.view.View;

/**
 * Created by palatin on 3/22/17.
 */

public interface IInternetConnectionActivity {

    View getSnackBarLayout();

    Context getContext();

    void notifyAboutConnectionLack();

}
