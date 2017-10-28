package pl.hopeit.hopeitandroid.fbUserAdapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.List;

import pl.hopeit.hopeitandroid.ChallengesAcceptedFragment;
import pl.hopeit.hopeitandroid.HopeItApplication;
import pl.hopeit.hopeitandroid.Main2Activity;
import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.RecyclerItemClickListener;
import pl.hopeit.hopeitandroid.UserChallengesActivity;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.FbUser;
import pl.hopeit.hopeitandroid.model.FbUsersResponse;
import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersChallangesFragment extends Fragment {


    public UsersChallangesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_challanges, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // request
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getData() throws IOException {
        Call<FbUsersResponse> call = HopeItApplication.retrofitService.getFbUsers(HopeItApplication.fbUserId);

        call.enqueue(new Callback<FbUsersResponse>() {
            @Override
            public void onResponse(Call<FbUsersResponse> call, Response<FbUsersResponse> response) {
                showList(response.body().fbFriends);
            }

            @Override
            public void onFailure(Call<FbUsersResponse> call, Throwable t) {
                Log.d("response", "fail");
            }
        });
    }

    void showList(final List<FbUser> records) {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        FbUserRecycleAdapter adapter = new FbUserRecycleAdapter(records, getContext());
//        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HopeItApplication.loadImageId = records.get(position).id;
                        startActivity(new Intent(getContext(), UserChallengesActivity.class));
                    }
                })
        );
    }
}