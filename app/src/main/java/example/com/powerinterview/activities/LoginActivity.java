package example.com.powerinterview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.ui.CustomToast;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty
    @Email
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @Password(min = 6, scheme = Password.Scheme.ANY, message = "Password should contain at least 6 symbols")
    @BindView(R.id.passwordEditText) EditText passwordEditText;

    private Validator validator;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        unbinder = ButterKnife.bind(this);


        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void login() {

    }

    @Override
    public void onValidationSucceeded() {
        login();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);


            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                new CustomToast(context, message, Toast.LENGTH_LONG, CustomToast.TOAST_ALERT).show();
            }
        }
    }
}
