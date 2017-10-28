package pl.hopeit.hopeitandroid;

import java.util.List;

import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import pl.hopeit.hopeitandroid.model.MessagesResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by marcinwatroba on 27.10.2017.
 */

public interface HopeRetrofitService {
    String SERVICE_ENDPOINT = "http://10.99.130.101:4000";

    @POST("/user/login")
    Call<LoginResponse> getUser(@Body LoginBody body);

    @GET("/user/{userId}/messages")
    Call<List<MessagesResponse>> getMessages();
}
