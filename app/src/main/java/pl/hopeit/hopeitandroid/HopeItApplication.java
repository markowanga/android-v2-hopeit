package pl.hopeit.hopeitandroid;

import android.app.Application;

import pl.hopeit.hopeitandroid.model.LoginResponse;
import pl.hopeit.hopeitandroid.model.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by marcinwatroba on 27.10.2017.
 */

public class HopeItApplication extends Application {

    static public HopeRetrofitService retrofitService;
    static public LoginResponse loginResponse;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HopeRetrofitService.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitService = retrofit.create(HopeRetrofitService.class);
    }
}
