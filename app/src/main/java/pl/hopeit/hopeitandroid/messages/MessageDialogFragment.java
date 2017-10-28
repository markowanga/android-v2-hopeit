package pl.hopeit.hopeitandroid.messages;

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

import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.model.Message;

public class MessageDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_PICTURE_URL = "url";
    private static final String ARG_CONTENT = "content";

    public MessageDialogFragment() {
    }

    public static MessageDialogFragment newInstance(Message msg) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, msg.getTitle());
        args.putString(ARG_PICTURE_URL, msg.getPictureURL());
        args.putString(ARG_CONTENT, msg.getContent());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle args = getArguments();

        View view = inflater.inflate(R.layout.message_dialog, null);
        ImageView imageView = view.findViewById(R.id.msg_dialog_image);
        TextView titleView = view.findViewById(R.id.msg_dialog_title);
        TextView contentView = view.findViewById(R.id.msg_dialog_content);

        Picasso.with(getActivity())
                .load(args.getString(ARG_PICTURE_URL))
                .resize(60, 60)
                .centerCrop()
                .into(imageView);

        titleView.setText(args.getString(ARG_TITLE));
        contentView.setText(args.getString(ARG_CONTENT));

        builder.setView(view)
                .setPositiveButton("UKRYJ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        return builder.create();
    }
}
