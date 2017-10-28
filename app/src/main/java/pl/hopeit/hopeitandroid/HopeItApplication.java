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

    public static String SERVICE_ENDPOINT = "http://10.99.130.101:4000";
    static public HopeRetrofitService retrofitService;
    static public LoginResponse loginResponse;
    public static String fbUserId, fbToken;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitService = retrofit.create(HopeRetrofitService.class);
    }
}
