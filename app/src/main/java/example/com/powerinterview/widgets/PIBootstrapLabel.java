package example.com.powerinterview.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

/**
 * Created by Игорь on 02.04.2017.
 */

public class PIBootstrapLabel extends AwesomeTextView {


    public PIBootstrapLabel(Context context) {
        super(context);
        defaultInit();
    }

    public PIBootstrapLabel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        defaultInit();
    }

    public PIBootstrapLabel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultInit();
    }

    private void defaultInit() {
        setText("Text");
        setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 60, 0, 0);

        setLayoutParams(layoutParams);
    }

}
