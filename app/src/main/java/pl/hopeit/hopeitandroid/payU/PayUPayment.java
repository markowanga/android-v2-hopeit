package pl.hopeit.hopeitandroid.payU;

import android.content.Context;

import com.payu.android.sdk.payment.PaymentService;
import com.payu.android.sdk.payment.model.Currency;
import com.payu.android.sdk.payment.model.Order;

/**
 * Created by mwatroba on 10.08.2017.
 */

public class PayUPayment {

    private static final String NOTIFY_URL = "http://e-kiosk.pl/ajax.php?page=payUResponse";

    public static void pay(PaymentService paymentService, Context context, String notifyUrl, int amountInGroszy, Currency currency, String description, int extOrderId) {
        paymentService
                .pay(new Order.Builder()
                        .withAmount(amountInGroszy)
                        .withNotifyUrl(notifyUrl)
                        .withExtOrderId(String.valueOf(extOrderId))
                        .withCurrency(currency)
                        .withDescription(description)
                        .build());
    }
}
