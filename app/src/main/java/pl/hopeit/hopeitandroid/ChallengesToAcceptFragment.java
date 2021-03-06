package pl.hopeit.hopeitandroid;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.ChallengesResponse;
import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesToAcceptFragment extends Fragment {

    List<Challenge> challengeList;
    ChallengesToAcceptAdapter adapter;


    public ChallengesToAcceptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges_to_accept, container, false);

        // todo request
    }

    void getChallenges() throws IOException {
        Call<ChallengesResponse> call = HopeItApplication.retrofitService.getChallenges(HopeItApplication.fbUserId);

        call.enqueue(new Callback<ChallengesResponse>() {
            @Override
            public void onResponse(Call<ChallengesResponse> call, Response<ChallengesResponse> response) {
                Log.d("response", "OKKKK " + response.body().notAcceptedChallenges.size());
                challengeList = response.body().notAcceptedChallenges;
                showList(response.body().notAcceptedChallenges);
                getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ChallengesResponse> call, Throwable t) {
                Log.d("response", "fail");
                getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getChallenges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showList(final List<Challenge> records) {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChallengesToAcceptAdapter(records);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showDialog(records.get(position));
                    }
                })
        );
    }

    private class ChallengesToAcceptAdapter extends RecyclerView.Adapter<ViewHolder> {
        List<Challenge> mRecords;

        public ChallengesToAcceptAdapter(List<Challenge> records) {
            mRecords = records;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_three_lines_image, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Challenge currentRecord = mRecords.get(position);
            holder.description.setText(currentRecord.description);
            holder.from.setText("Wyzwanie od " + currentRecord.inviter);
            Picasso.with(getContext()).load(HopeItApplication.SERVICE_ENDPOINT + currentRecord.imgUrl).into(holder.image);
            holder.title.setText(currentRecord.title);
        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }
    }

    void showDialog(Challenge challenge) {
        DialogFragment newFragment = ChallengeDialogToAccept.newInstance(challenge, this);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, from, description;
        ImageView image;
        View view;

        ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.title);
            from = (TextView) v.findViewById(R.id.from);
            description = (TextView) v.findViewById(R.id.description);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }

    public void removeChallenge(Challenge challenge) {
        int index = challengeList.indexOf(challenge);
        challengeList.remove(index);
        adapter.notifyItemRemoved(index);
    }

}
