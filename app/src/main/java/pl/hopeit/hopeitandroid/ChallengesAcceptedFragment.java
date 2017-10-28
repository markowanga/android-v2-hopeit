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

import com.facebook.CallbackManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.ChallengesResponse;
import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import pl.hopeit.hopeitandroid.model.PhotoBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesAcceptedFragment extends Fragment {


    public ChallengesAcceptedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges_accepted, container, false);


    }

    @Override
    public void onResume() {
        Log.d("res", "RESUME");
        super.onResume();
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

    void getChallenges() throws IOException {
        Call<ChallengesResponse> call = HopeItApplication.retrofitService.getChallenges(HopeItApplication.fbUserId);

        call.enqueue(new Callback<ChallengesResponse>() {
            @Override
            public void onResponse(Call<ChallengesResponse> call, Response<ChallengesResponse> response) {
                Log.d("response", "OKKKK " + response.body().acceptedChallenges.size());
                showList(response.body().acceptedChallenges);
            }

            @Override
            public void onFailure(Call<ChallengesResponse> call, Throwable t) {
                Log.d("response", "fail");
            }
        });
    }

    void showList(final List<Challenge> records) {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        ChallengesToAcceptAdapter adapter = new ChallengesToAcceptAdapter(records);
//        progressBar.setVisibility(View.GONE);
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
            View v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_three_lines_image, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Challenge currentRecord = mRecords.get(position);
            Log.d("tah", currentRecord.imgUrl);
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
        DialogFragment newFragment = ChallengeDialogActual.newInstance(challenge, this);
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

    private final static int OPEN_GALLERY_PICTURE = 1003;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPEN_GALLERY_PICTURE: {
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        data.getData(); // uri
                        Log.d("FOTO", "ZALADOWANO");
                        try {
                            loadPhoto(data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            default:break;
        }
    }

    public void openChooseFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), OPEN_GALLERY_PICTURE);
    }

    private void loadPhoto(Uri uri) throws IOException {

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        PhotoBody body = new PhotoBody();
        body.image = "data:image/jpeg;base64," + Base64.encodeToString(b, Base64.DEFAULT);
        body.userChallengeId = HopeItApplication.loadImageId;

        Call<ResponseBody> call = HopeItApplication.retrofitService.uploadPhoto(HopeItApplication.fbUserId, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("response", "ok loaded");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("response", "fail");
            }
        });
    }

}
