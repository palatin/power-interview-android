package example.com.powerinterview.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.com.powerinterview.R;


/**
 * Created by Игорь on 30.01.2017.
 */

public class CustomToast {

    public static int TOAST_WITHOUT_IMAGE = 0;
    public static int TOAST_ALERT = 1;
    public static int TOAST_SUCCESS = 2;

    private Context context;
    private String text;
    private int duration;
    private Toast toast;
    private int drawable;
    private Integer color = null;

    public CustomToast(Context context, String text, int duration, int drawableType){
        this.context = context;
        this.text = text;
        this.duration = duration;
        this.drawable = drawableType;
        createToast();
    }

    public CustomToast(Context context, String text, int duration, int drawableType, int color){
        this.context = context;
        this.text = text;
        this.duration = duration;
        this.drawable = drawableType;
        this.color = color;
        createToast();
    }

    private void createToast(){
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.custom_toast, null);
        ((TextView) view.findViewById(R.id.customToastText)).setText(text);
        if(color != null)
        {
            view.findViewById(R.id.toast_background_layout).setBackgroundColor(color);
        }
        if(drawable == 1){
            ImageView imageView = (ImageView) view.findViewById(R.id.toastImage);
            imageView.setImageResource(R.drawable.ic_error_white_24dp);
            imageView.setVisibility(View.VISIBLE);
        }
        else if(drawable == 2){
            ImageView imageView = (ImageView) view.findViewById(R.id.toastImage);
            imageView.setImageResource(R.drawable.ic_check_circle_white_24dp);
            imageView.setVisibility(View.VISIBLE);
        }
        toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);
    }

    public void show(){
        toast.show();
    }
}

