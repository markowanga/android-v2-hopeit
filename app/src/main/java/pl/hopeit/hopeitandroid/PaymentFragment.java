package pl.hopeit.hopeitandroid;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payu.android.sdk.payment.model.Currency;

import java.util.Calendar;

import okhttp3.ResponseBody;
import pl.hopeit.hopeitandroid.model.PayUPaymentDetails;
import pl.hopeit.hopeitandroid.model.PaymentChallengeRestBody;
import pl.hopeit.hopeitandroid.payU.PayuOrderBulider;
import pl.hopeit.hopeitandroid.payU.PayuPaymentExecutor;
import pl.hopeit.hopeitandroid.payU.PayuPaymentResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements PayuPaymentResult {

    private PayuPaymentExecutor mPayuPaymentExecutor;
    PayUPaymentDetails payUPaymentDetails;
    View view;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startPayment = view.findViewById(R.id.btnPay);
        final EditText editText = view.findViewById(R.id.edValue);
        startPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payUPaymentDetails = new PayUPaymentDetails();
                payUPaymentDetails.currency = Currency.PLN.toString();
                payUPaymentDetails.description= "opis";
                payUPaymentDetails.notifyUrl="url";
                payUPaymentDetails.totalAmount=Integer.parseInt(editText.getText().toString()) * 100;
                payUPaymentDetails.orderId= (int)Calendar.getInstance().getTime().getTime();

                onStartPayment(payUPaymentDetails);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        return view;
    }

    public void onStartPayment(PayUPaymentDetails payUPaymentDetails) {
        int orderId = payUPaymentDetails.getOrderId();
        mPayuPaymentExecutor = new PayuPaymentExecutor(getActivity(), getChildFragmentManager(),
                PayuOrderBulider.getOrder(payUPaymentDetails), this);
        mPayuPaymentExecutor.register();
        mPayuPaymentExecutor.startPayment();
    }

    @Override
    public void paymentResult(boolean isCorrect) {
        mPayuPaymentExecutor.logout();
        mPayuPaymentExecutor.unregister();
        if (isCorrect) {
            Log.d("pay", "is correst");
            PaymentChallengeRestBody body = new PaymentChallengeRestBody();
            body.amount = String.valueOf(payUPaymentDetails.getTotalAmount()/100);
            body.challenge_id = null;
            body.user_challenge_id = null;
            Call<ResponseBody> call =
                    HopeItApplication.retrofitService.
                            commitPaymentChellange(HopeItApplication.fbUserId, body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("response", "accepted");
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Zapłacono", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("response", "fail");
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mPayuPaymentExecutor != null)
            mPayuPaymentExecutor.register();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mPayuPaymentExecutor != null)
            mPayuPaymentExecutor.unregister();
    }
}
