package example.com.powerinterview.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import example.com.powerinterview.ui.CustomToast;

/**
 * Created by Игорь on 15.04.2017.
 */

public class BaseWorkerActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);


    }

    protected void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    protected void showToast(String message, CustomToast.ToastType toastType) {

        new CustomToast(this, message, Toast.LENGTH_LONG, toastType).show();
    }

}
