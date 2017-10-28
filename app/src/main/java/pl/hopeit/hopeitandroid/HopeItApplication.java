package pl.hopeit.hopeitandroid;

import android.app.Application;

import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import pl.hopeit.hopeitandroid.model.User;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by marcinwatroba on 27.10.2017.
 */

public class HopeItApplication extends Application {

    public static final String PREF_NAME = "FacebookSharedPreferences";
    public static final String PREF_USER_ID_KEY = "hopeItAndroid.USER_ID";
    public static final String PREF_ACCESS_TOKEN_KEY = "hopeItAndroid.ACCESS_TOKEN";

    public static String SERVICE_ENDPOINT = "http://10.99.130.101:4000";
    static public HopeRetrofitService retrofitService;
    static public LoginResponse loginResponse;
    public static String fbUserId, fbToken;
    public static Challenge challenge;

    // load image
    public static boolean loadImage = false;
    public static String loadImageId;

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
