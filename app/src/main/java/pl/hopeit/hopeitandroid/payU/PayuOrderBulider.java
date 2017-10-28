package pl.hopeit.hopeitandroid.payU;

import com.payu.android.sdk.payment.model.Currency;
import com.payu.android.sdk.payment.model.Order;

import pl.hopeit.hopeitandroid.model.PayUPaymentDetails;

/**
 * Created by mwatroba on 05.09.2017.
 */

public class PayuOrderBulider {

    public static Order getOrder(PayUPaymentDetails payUPaymentDetails) {
        return new Order.Builder()
                .withAmount(payUPaymentDetails.getTotalAmount())
                .withNotifyUrl(payUPaymentDetails.getNotifyUrl())
                .withExtOrderId(String.valueOf(payUPaymentDetails.getOrderId()))
                .withCurrency(Currency.PLN)
                .withDescription(payUPaymentDetails.getDescription())
                .build();
    }
}
