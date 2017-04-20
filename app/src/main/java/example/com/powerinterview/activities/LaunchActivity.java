package example.com.powerinterview.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.com.powerinterview.R;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.model.User;

public class LaunchActivity extends AppCompatActivity {


    private int permission = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            AlertDialog dialog =  new AlertDialog.Builder(this).setTitle("Permission")
                    .setMessage("To implement the autologin, app need permission to receive information about the phone.")
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LaunchActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    permission);
                        }
                    }).create();

            dialog.show();
        }

        else
            loadAccount();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    loadAccount();

                } else {

                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
            }

        }
    }


    private void loadAccount() {
        User user = null;
        try {
            user = ((PowerInterviewApp) getApplication()).getAuthComponent().accoutnManager().getAccount(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user == null){
            startActivity(new Intent(this, LoginActivity.class));
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }

        finish();
    }
}
