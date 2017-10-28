package pl.hopeit.hopeitandroid.payU;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.payu.android.sdk.payment.widget.PaymentMethodWidget;

import pl.hopeit.hopeitandroid.R;

/**
 * Created by marcinwatroba on 02.09.2017.
 */

public class ChoosePaymentMethodDialog extends DialogFragment {

    public View view;
    public PaymentMethodWidget paymentMethodWidget;
    public Button positiveButton;
    public OnChoosePaymentMethodChooserResult chooserResult;

    public static ChoosePaymentMethodDialog newInstance(OnChoosePaymentMethodChooserResult listener) {
        ChoosePaymentMethodDialog instance = new ChoosePaymentMethodDialog();
        instance.chooserResult = listener;
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.choose_payment_method_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Wybierz metodę płatności")
                .setView(view)
                .setPositiveButton("Zapłać",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                chooserResult.result(true);
                            }
                        }
                )
                .setNegativeButton("Powrót",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                chooserResult.result(false);
                            }
                        }
                )
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                paymentMethodWidget = view.findViewById(R.id.payment_widget);
                positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

                if (!paymentMethodWidget.isPaymentMethodPresent()) {
                    positiveButton.setEnabled(false);
                }
            }
        });

        return dialog;
    }


    public void setPositiveButton(boolean enable) {
        Log.d("Dialog", "setPositiveButton");
        positiveButton.setEnabled(enable);
    }

}