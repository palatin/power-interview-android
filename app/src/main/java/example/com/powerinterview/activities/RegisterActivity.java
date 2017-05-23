package example.com.powerinterview.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.components.AuthComponent;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.model.Profile;
import example.com.powerinterview.model.User;
import example.com.powerinterview.network.AuthClient;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.Converter;

public class RegisterActivity extends BaseWorkerActivity implements Validator.ValidationListener {

    private int REQUEST_CODE_PICKER = 2000;
    private final Context context = this;
    private AuthClient client;
    private AccountManager accountManager;


    @NotEmpty
    @Email
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @Password(min = 6, scheme = Password.Scheme.ANY, message = "Password should contain at least 6 symbols")
    @BindView(R.id.passwordEditText) EditText passwordEditText;

    @ConfirmPassword
    @BindView(R.id.repasswordEditText) EditText confirmPasswordEditText;

    @NotEmpty
    @BindView(R.id.nameEditText) EditText nameEditText;

    @BindView(R.id.organizationEditText) EditText organizationEditText;

    @BindView(R.id.job_drop_down)
    MaterialSpinner jobSpinner;


    Unbinder unbinder;

    Validator validator;

    Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        jobSpinner.setItems(getResources().getStringArray(R.array.job_list));

        validator = new Validator(this);
        validator.setValidationListener(this);

        AuthComponent authComponent = ((PowerInterviewApp) getApplication()).getAuthComponent();
        client = authComponent.authClient();
        accountManager = authComponent.accountManager();
    }

    @OnClick(R.id.register_button)
    public void registerClicked() {

        validator.validate();

    }

    public void addAvatarClicked(View view) {
        ImagePicker.create(this)
                .folderMode(true)
                .folderTitle("Folder")
                .single()
                .start(REQUEST_CODE_PICKER);
    }

    public void removeAvatarClicked(View view) {
        removeAvatar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            setAvatar((Image) data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES).get(0));
        }
    }

    void setAvatar(Image image)
    {
        try {
            Bitmap bitmap = Converter.bitmapFromPath(image.getPath());
            ((BootstrapCircleThumbnail) findViewById(R.id.avatar)).setImageBitmap(bitmap);
            profile.setImagePath(image.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void removeAvatar()
    {
        ((BootstrapCircleThumbnail) findViewById(R.id.avatar)).setImageBitmap(null);
        profile.setImagePath(null);
    }


    @Override
    public void onValidationSucceeded() {

        profile.getUser().setEmail(emailEditText.getText().toString());
        profile.getUser().setPassword(passwordEditText.getText().toString());
        profile.setName(nameEditText.getText().toString());
        profile.setJob(jobSpinner.getText().toString());
        profile.setOrganization(organizationEditText.getText().toString());
        try {
            showProgressDialog("Please wait...");
            try {
                client.register(profile, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        dismissProgressDialog();
                        try {
                            if(displayResult(response)) {

                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        showToast("Server error", CustomToast.ToastType.TOAST_ALERT);
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (EncryptionException e) {
            e.printStackTrace();
        }
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
