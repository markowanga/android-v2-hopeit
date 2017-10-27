package pl.hopeit.hopeitandroid.login;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import pl.hopeit.hopeitandroid.HopeItApplication;
import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.model.LoginBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userId = "rfrws";
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        try {
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void login() throws IOException {
        Call<String> call = HopeItApplication.retrofitService.getUser(new LoginBody("fsd", "fwefsd"));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("response", "OKK");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("response", "fail");
            }
        });
    }


}
