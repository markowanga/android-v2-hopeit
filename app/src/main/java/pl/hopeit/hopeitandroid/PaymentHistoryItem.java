package pl.hopeit.hopeitandroid;

/**
 * Created by akruk on 28.10.17.
 */

public class PaymentHistoryItem {

    private String challengeTitle;
    private String amount;
    private String date;

    public PaymentHistoryItem(String challengeTitle, String amount, String date) {
        this.challengeTitle = challengeTitle;
        this.amount = amount;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
