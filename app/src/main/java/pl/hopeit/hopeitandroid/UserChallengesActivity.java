package pl.hopeit.hopeitandroid;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.ChallengesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChallengesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_challenges);

        try {
            getChallenges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getChallenges() throws IOException {
        Call<ChallengesResponse> call = HopeItApplication.retrofitService.getChallenges(HopeItApplication.fbUserId);

        call.enqueue(new Callback<ChallengesResponse>() {
            @Override
            public void onResponse(Call<ChallengesResponse> call, Response<ChallengesResponse> response) {
                Log.d("response", "OKKKK " + response.body().acceptedChallenges.size());
                showList(response.body().acceptedChallenges);
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ChallengesResponse> call, Throwable t) {
                Log.d("response", "fail");
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }
        });
    }

    void showList(final List<Challenge> records) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        ChallengesToAcceptAdapter adapter = new ChallengesToAcceptAdapter(records);
        recyclerView.setAdapter(adapter);
    }

    private class ChallengesToAcceptAdapter extends RecyclerView.Adapter<ViewHolder> {
        List<Challenge> mRecords;

        public ChallengesToAcceptAdapter(List<Challenge> records) {
            mRecords = records;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.item_three_lines_image, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Challenge currentRecord = mRecords.get(position);
            Log.d("tah", currentRecord.imgUrl);
            holder.description.setText(currentRecord.description);
            holder.from.setText("Wyzwanie od " + currentRecord.inviter);
            Picasso.with(getApplicationContext()).load(HopeItApplication.SERVICE_ENDPOINT + currentRecord.imgUrl).into(holder.image);
            holder.title.setText(currentRecord.title);
            holder.image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(UserChallengesActivity.this, "Wyzwałeś znajomego", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }
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
}
