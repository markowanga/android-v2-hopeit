package pl.hopeit.hopeitandroid.model;

/**
 * Created by akruk on 28.10.17.
 */

public class PaymentResponse {
    String date;
    String challengeTitle;
    String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChallengeTitle() {
        return challengeTitle;
    }

    public void setChallengeTitle(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
