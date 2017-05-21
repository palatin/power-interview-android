package example.com.powerinterview.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;




/**
 * Created by Игорь on 19.02.2017.
 */

public class IOSStyledButton extends Button {


    public IOSStyledButton(Context context) {
        super(context);
        setUp();
    }

    public IOSStyledButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public IOSStyledButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        int color = getCurrentTextColor();
        int[][] states = new int[][] {
                new int[] {  -android.R.attr.state_pressed},
                new int[] { android.R.attr.state_pressed}
        };


        int[] colors = new int[] {
                color,
                isColorDark(color) ? lighterColor(color) : darkerColor(color)
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);
        setTextColor(colorStateList);
    }

    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.5;
    }

    private int darkerColor(int color)
    {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *=  0.65f; // value component;
        return Color.HSVToColor(hsv);
    }

    private int lighterColor(int color)
    {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = 1.0f - 0.5f * (1.0f - hsv[2]);
        return Color.HSVToColor(hsv);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
