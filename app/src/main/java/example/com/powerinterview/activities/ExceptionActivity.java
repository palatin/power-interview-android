package example.com.powerinterview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;

public class ExceptionActivity extends AppCompatActivity {


    @BindView(R.id.errorInfo)
    TextView errorInfo;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);

        unbinder = ButterKnife.bind(this);



        Throwable throwable = (Exception) getIntent().getSerializableExtra("exception");
        analyzeException(throwable);



    }


    /**
     * Method than analyze exception and print it to the user
     * @param throwable - current system throwable
     */
    private void analyzeException(Throwable throwable) {
        errorInfo.setText(throwable.getMessage());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
