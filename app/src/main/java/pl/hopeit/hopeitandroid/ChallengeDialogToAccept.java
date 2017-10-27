package pl.hopeit.hopeitandroid;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.hopeit.hopeitandroid.model.Challenge;

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
                                // todo accept send
                            }
                        }
                )
                .setNegativeButton("Wróć",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {}
                        }
                )
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView description = view.findViewById(R.id.description_text_view);
                TextView from = view.findViewById(R.id.from_text_view);
                ImageView picture = view.findViewById(R.id.challenge_image_view);
                description.setText(challenge.description);
                from.setText("Zadaie od: " + challenge.from);
                Picasso.with(getContext()).load(challenge.photoUrl).into(picture);

            }
        });

        return dialog;
    }
}