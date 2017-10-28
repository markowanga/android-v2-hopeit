package pl.hopeit.hopeitandroid.model;

/**
 * Created by marcinwatroba on 27.10.2017.
 */

public class LoginBody {

    public String userId;
    public String accessTokenId;

    public LoginBody(String userId, String accessTokenId) {
        this.userId = userId;
        this.accessTokenId = accessTokenId;
    }
}
