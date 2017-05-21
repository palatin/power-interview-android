package example.com.powerinterview.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.components.AuthComponent;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.ConvertException;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.model.User;
import example.com.powerinterview.network.AuthClient;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.Converter;
import example.com.powerinterview.utils.Encrypt;

public class LoginActivity extends BaseWorkerActivity implements Validator.ValidationListener {


    @NotEmpty
    @Email
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @Password(min = 6, scheme = Password.Scheme.ANY, message = "Password should contain at least 6 symbols")
    @BindView(R.id.passwordEditText) EditText passwordEditText;

    private Validator validator;
    private Unbinder unbinder;

    private AuthComponent authComponent;
    private AuthClient client;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        initComponents();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            User user = (User) bundle.getSerializable("user");
            if(user != null) {
                emailEditText.setText(user.getEmail());
                passwordEditText.setText(user.getPassword());
                login();
            }
        }

    }

    private void initComponents() {

        unbinder = ButterKnife.bind(this);

        authComponent = ((PowerInterviewApp) getApplication()).getAuthComponent();
        client = authComponent.authClient();
        accountManager = authComponent.accountManager();

        validator = new Validator(this);
        validator.setValidationListener(this);



    }

    @OnClick(R.id.sign_in_button)
    public void onLoginClicked() {
        validator.validate();
    }

    private void login() {

        final User user = new User();
        user.setEmail(emailEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        try {

            showProgressDialog("Loading account....");

            final String key = Encrypt.generateRandomAESKey();

            client.login(user, key, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    dismissProgressDialog();

                    try {
                        JSONObject obj = Converter.bytesToJSON(responseBody);
                        if(obj.getBoolean("result")) {
                            accountManager.storeAccount(user, LoginActivity.this);
                            accountManager.setToken(Encrypt.decryptAES(obj.getString("token"), key));
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                        else
                        {
                            showToast(obj.getString("msg"), CustomToast.ToastType.TOAST_ALERT);
                            accountManager.removeAccount();
                        }
                    } catch (ConvertException | JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                        accountManager.removeAccount();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    dismissProgressDialog();
                    Log.v("Server error message", Arrays.toString(responseBody));
                    showToast("Server error", CustomToast.ToastType.TOAST_ALERT);
                }
            });
        } catch (EncryptionException e) {
            dismissProgressDialog();
            e.printStackTrace();
        }
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
                showToast(message, CustomToast.ToastType.TOAST_ALERT);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
