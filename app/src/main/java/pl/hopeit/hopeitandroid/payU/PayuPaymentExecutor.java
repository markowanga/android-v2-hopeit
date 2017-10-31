package pl.hopeit.hopeitandroid.payU;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.payu.android.sdk.payment.PaymentEventBus;
import com.payu.android.sdk.payment.PaymentService;
import com.payu.android.sdk.payment.event.AbsentSelectedPaymentMethodEvent;
import com.payu.android.sdk.payment.event.PaymentFailedEvent;
import com.payu.android.sdk.payment.event.PaymentSuccessEvent;
import com.payu.android.sdk.payment.event.PresentSelectedPaymentMethodEvent;
import com.payu.android.sdk.payment.model.Order;

/**
 * Created by marcinwatroba on 01.09.2017.
 */

public class PayuPaymentExecutor implements OnChoosePaymentMethodChooserResult {

    private static final String TAG = "PayuPaymentExecutor";

    private PaymentService mPaymentService;
    private PaymentEventBus mPaymentEventBus;
    private Order mOrder;
    private FragmentManager mFragmentManager;
    private ChoosePaymentMethodDialog dialog;
    private PayuPaymentResult mResultListener;
    private Context mContext;

    public PayuPaymentExecutor(Context context, FragmentManager fragmentManager, Order order, PayuPaymentResult resultListener) {
        mContext = context;
        mPaymentService = PaymentService.createInstance(mContext);
        mPaymentEventBus = new PaymentEventBus();
        mOrder = order;
        mFragmentManager = fragmentManager;
        mResultListener = resultListener;
    }

    public void register() {
        mPaymentEventBus.register(this);
    }

    public void unregister() {
        mPaymentEventBus.unregister(this);
    }

    public void executePayment() {
        mPaymentService.pay(mOrder);
    }

    public void logout() {
        mPaymentService.notifyUserLogout();
    }

    public void onPaymentProcessEventMainThread(PaymentFailedEvent paymentFailedEvent) {
        Log.d("PayU result", "negative" + paymentFailedEvent.getBusinessError().toString());

        mResultListener.paymentResult(false);
    }

    public void onPaymentProcessEventMainThread(PaymentSuccessEvent event) {
        Log.d("PayU result", "positive");
        mResultListener.paymentResult(true);
    }

    public void onPaymentProcessEventMainThread(PresentSelectedPaymentMethodEvent event) {
        Log.d("PayU result", "PresentSelectedPaymentMethodEvent");
        dialog.setPositiveButton(true);
    }

    public void onPaymentProcessEventMainThread(AbsentSelectedPaymentMethodEvent event) {
        Log.d("PayU result", "AbsentSelectedPaymentMethodEvent");
        dialog.setPositiveButton(false);
    }

    public void startPayment() {
        dialog = ChoosePaymentMethodDialog.newInstance(this);
        dialog.show(mFragmentManager, "dialog");
    }

    @Override
    public void result(boolean chosen) {
        if (chosen)
            executePayment();
        else mResultListener.paymentResult(false);
    }
}
