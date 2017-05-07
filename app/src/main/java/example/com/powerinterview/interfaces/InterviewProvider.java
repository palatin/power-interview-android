package example.com.powerinterview.interfaces;

import android.content.Context;
import android.view.View;

/**
 * Created by Игорь on 06.05.2017.
 */

public interface InterviewProvider {

    void displayViews(View[] views);

    Context getContext();

}
