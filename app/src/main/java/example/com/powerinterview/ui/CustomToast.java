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



    private Context context;
    private String text;
    private int duration;
    private Toast toast;
    private Integer color = null;

    public enum ToastType {TOAST_WITHOUT_IMAGE, TOAST_ALERT, TOAST_SUCCESS}

    private ToastType type;

    public CustomToast(Context context, String text, int duration, ToastType type){
        this.context = context;
        this.text = text;
        this.duration = duration;
        this.type = type;
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
        if(type == ToastType.TOAST_ALERT){
            ImageView imageView = (ImageView) view.findViewById(R.id.toastImage);
            imageView.setImageResource(R.drawable.ic_error_white_24dp);
            imageView.setVisibility(View.VISIBLE);
        }
        else if(type == ToastType.TOAST_SUCCESS){
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

