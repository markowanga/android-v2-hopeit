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

import com.payu.android.sdk.payment.model.Currency;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.PayUPaymentDetails;
import pl.hopeit.hopeitandroid.model.PaymentChallengeRestBody;
import pl.hopeit.hopeitandroid.payU.PayUPayment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marcinwatroba on 28.10.2017.
 */

public class ChallengeDialogActual extends DialogFragment {

    Challenge challenge;
    private View view;
    ChallengesAcceptedFragment fragment;

    public static ChallengeDialogActual newInstance(Challenge challenge, ChallengesAcceptedFragment fragment) {
        ChallengeDialogActual frag = new ChallengeDialogActual();
        frag.fragment = fragment;
        frag.challenge = challenge;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.challenge_dialog_to_accept, null);

        if (challenge.image==null || challenge.image.isEmpty()) {
            final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(challenge.title)
                    .setView(view)
                    .setPositiveButton("wpłać 5zł",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Log.d("wpłacam", challenge._id);
                                    HopeItApplication.loadImageId = challenge._id;
                                    HopeItApplication.challenge = challenge;
                                    fragment.payUPaymentDetails = new PayUPaymentDetails();
                                    fragment.payUPaymentDetails.orderId = 543;
                                    fragment.payUPaymentDetails.totalAmount = 500;
                                    fragment.payUPaymentDetails.notifyUrl="frewsd";
                                    fragment.payUPaymentDetails.description="vgjhnm";
                                    fragment.payUPaymentDetails.currency = Currency.PLN.toString();
                                    fragment.onStartPayment(fragment.payUPaymentDetails);
                                }
                            }
                    ).setNegativeButton("Wykonaj zadanie",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    HopeItApplication.loadImage = true;
                                    HopeItApplication.loadImageId = challenge._id;
                                    HopeItApplication.challenge = challenge;
                                    fragment.openChooseFromGallery();
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
        else {
            final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(challenge.title)
                    .setView(view)
                    .setPositiveButton("wpłać 5zł",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Log.d("wpłacam", challenge._id);
                                    HopeItApplication.loadImageId = challenge._id;
                                    HopeItApplication.challenge = challenge;
                                    fragment.payUPaymentDetails = new PayUPaymentDetails();
                                    fragment.payUPaymentDetails.orderId = 543;
                                    fragment.payUPaymentDetails.totalAmount = 500;
                                    fragment.payUPaymentDetails.notifyUrl="frewsd";
                                    fragment.payUPaymentDetails.description="vgjhnm";
                                    fragment.payUPaymentDetails.currency = Currency.PLN.toString();
                                    fragment.onStartPayment(fragment.payUPaymentDetails);
                                }
                            }
                    ).create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    TextView description = view.findViewById(R.id.description_text_view);
                    ImageView picture = view.findViewById(R.id.challenge_image_view);
                    description.setText(challenge.description);
                    Picasso.with(getContext()).load(HopeItApplication.SERVICE_ENDPOINT + challenge.image).into(picture);

                }
            });

            return dialog;
        }
    }
}