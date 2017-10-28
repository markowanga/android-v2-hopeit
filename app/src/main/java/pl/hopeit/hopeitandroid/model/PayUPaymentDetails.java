package pl.hopeit.hopeitandroid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by mwatroba on 11.08.2017.
 */

public class PayUPaymentDetails {
    public int totalAmount;
    public String currency;
    public String description;
    public String notifyUrl;
    public int orderId;

    

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public int getOrderId() {
        return orderId;
    }
}
