package pl.hopeit.hopeitandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.util.Arrays;

import pl.hopeit.hopeitandroid.model.LoginBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    private String userId;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login result", "success");
                userId = loginResult.getAccessToken().getUserId();
                token = loginResult.getAccessToken().getToken();

                try {
                    login(userId, token);
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
                Log.d("login result", "error");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
            }
        });
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    void login(String userId, String token) throws IOException {
        Call<String> call = HopeItApplication.retrofitService.getUser(new LoginBody(userId, token));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("response", "OKK");
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("response", "fail");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}