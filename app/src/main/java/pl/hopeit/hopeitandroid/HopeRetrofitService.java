package pl.hopeit.hopeitandroid;

import java.util.List;
import okhttp3.ResponseBody;
import pl.hopeit.hopeitandroid.model.ChallengesResponse;
import pl.hopeit.hopeitandroid.model.FbUser;
import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import pl.hopeit.hopeitandroid.model.MessagesResponse;
import pl.hopeit.hopeitandroid.model.PaymentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by marcinwatroba on 27.10.2017.
 */

public interface HopeRetrofitService {
    String SERVICE_ENDPOINT = "http://10.99.130.101:4000";

    @POST("/user/login")
    Call<LoginResponse> getUser(@Body LoginBody body);

    @GET("/user/{userId}/challenges")
    Call<ChallengesResponse> getChallenges(@Path("userId") String userId);

    @GET("/users/{userId}")
    Call<List<FbUser>> getFbUsers(@Path("userId") String userId);

    @GET("/user/{userId}/messages")
    Call<List<MessagesResponse>> getMessages(@Path("userId") String userId);

    @GET("/user/{userId}/payments")
    Call<List<PaymentResponse>> getPayments(@Path("userId") String userId);
}
