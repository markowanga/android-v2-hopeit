package pl.hopeit.hopeitandroid.model;

import android.util.Log;

/**
 * Created by marcinwatroba on 27.10.2017.
 */

public class LoginBody {
    public String userId;
    public String accessTokenId;
    public String firebaseToken;

    public LoginBody(String userId, String token, String firebaseToken) {
        this.userId = userId;
        this.accessTokenId = token;
        this.firebaseToken = firebaseToken;
    }
}
