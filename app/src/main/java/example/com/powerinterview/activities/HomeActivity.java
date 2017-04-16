package example.com.powerinterview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;

public class HomeActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    private void initComponents() {
        unbinder = ButterKnife.bind(this);

    }



    @OnClick(R.id.constructor_card)
    public void onMenuItemClicked(View view) {
        switch (view.getId()) {

            case R.id.constructor_card:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
