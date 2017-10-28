package pl.hopeit.hopeitandroid;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by akruk on 28.10.17.
 */

public class FirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FIREABASE", "Refreshed token: " + refreshedToken);
        //sendRegistrationToServer(refreshedToken);
    }
}
