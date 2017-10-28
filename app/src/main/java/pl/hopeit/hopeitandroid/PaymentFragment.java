package pl.hopeit.hopeitandroid;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.payu.android.sdk.payment.model.Currency;

import pl.hopeit.hopeitandroid.model.PayUPaymentDetails;
import pl.hopeit.hopeitandroid.payU.PayuOrderBulider;
import pl.hopeit.hopeitandroid.payU.PayuPaymentExecutor;
import pl.hopeit.hopeitandroid.payU.PayuPaymentResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements PayuPaymentResult {

    private PayuPaymentExecutor mPayuPaymentExecutor;
    private int orderId;
    View view;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button startPayment = view.findViewById(R.id.btnPay);
        startPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayUPaymentDetails payUPaymentDetails = new PayUPaymentDetails();
                payUPaymentDetails.currency = Currency.PLN.toString();
                payUPaymentDetails.description= "kurwa";
                payUPaymentDetails.notifyUrl="KK";
                payUPaymentDetails.totalAmount=10000;
                payUPaymentDetails.orderId=234;

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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onStartPayment(PayUPaymentDetails payUPaymentDetails) {
        orderId = payUPaymentDetails.getOrderId();
        mPayuPaymentExecutor = new PayuPaymentExecutor(getActivity(), getChildFragmentManager(),
                PayuOrderBulider.getOrder(payUPaymentDetails), this);
        mPayuPaymentExecutor.register();
        mPayuPaymentExecutor.startPayment();
    }

    @Override
    public void paymentResult(boolean isCorrect) {
        if (isCorrect) Log.d("pay", "is correst");
        else  Log.d("pay", "not correst");
    }
}
