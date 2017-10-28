package pl.hopeit.hopeitandroid;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.LoginBody;
import pl.hopeit.hopeitandroid.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marcinwatroba on 27.10.2017.
 */

public class ChallengeDialogToAccept extends DialogFragment {

    Challenge challenge;
    private View view;

    public static ChallengeDialogToAccept newInstance(Challenge challenge) {
        ChallengeDialogToAccept frag = new ChallengeDialogToAccept();
        frag.challenge = challenge;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.challenge_dialog_to_accept, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(challenge.title)
                .setView(view)
                .setPositiveButton("Akceptuj",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d("akcepyuje", challenge._id);
                                Call<ResponseBody> call =
                                        HopeItApplication.retrofitService.
                                                acceptChallenge(HopeItApplication.fbUserId,
                                                        challenge._id);

                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Log.d("response", "accepted");
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.d("response", "fail");
                                    }
                                });
                            }
                        }
                )
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView description = view.findViewById(R.id.description_text_view);
                ImageView picture = view.findViewById(R.id.challenge_image_view);
                description.setText(challenge.description);
                Picasso.with(getContext()).load(HopeItApplication.SERVICE_ENDPOINT + challenge.imgUrl).into(picture);

            }
        });

        return dialog;
    }
}