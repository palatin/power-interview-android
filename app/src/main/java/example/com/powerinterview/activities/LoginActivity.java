package example.com.powerinterview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.com.powerinterview.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }
}
