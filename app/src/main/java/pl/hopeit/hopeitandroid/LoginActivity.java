package pl.hopeit.hopeitandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.Arrays;

import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private final static int OPEN_GALLERY_PICTURE = 1003;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        final Button loginButton = (Button) findViewById(R.id.login_button);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login result", "success");
                HopeItApplication.fbUserId = loginResult.getAccessToken().getUserId();
                HopeItApplication.fbToken = loginResult.getAccessToken().getToken();


                try {
                    login(HopeItApplication.fbUserId, HopeItApplication.fbToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
                Log.d("login result", "cancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("login result", "error " + error.toString());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
            }
        });
    }

    void login(String userId, String token) throws IOException {
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        Call<LoginResponse> call = HopeItApplication.retrofitService.getUser(new LoginBody(userId, token, firebaseToken));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                HopeItApplication.loginResponse = response.body();
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("response", "fail");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
