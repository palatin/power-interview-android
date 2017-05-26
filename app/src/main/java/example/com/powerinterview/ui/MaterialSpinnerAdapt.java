package example.com.powerinterview.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;

import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * Created by Игорь on 07.02.2017.
 */

public class MaterialSpinnerAdapt extends MaterialSpinner {

    public MaterialSpinnerAdapt(Context context) {
        super(context);
    }

    public MaterialSpinnerAdapt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialSpinnerAdapt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        getPopupWindow().setWidth(this.getMeasuredWidth());
    }
}
