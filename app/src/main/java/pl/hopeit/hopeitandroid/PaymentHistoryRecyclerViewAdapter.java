package pl.hopeit.hopeitandroid;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class PaymentHistoryRecyclerViewAdapter extends RecyclerView.Adapter<PaymentHistoryRecyclerViewAdapter.ViewHolder> {

    private List<PaymentHistoryItem> mValues;

    public PaymentHistoryRecyclerViewAdapter(List<PaymentHistoryItem> items) {
        mValues = items;
//        mValues = Arrays.asList(new PaymentHistoryItem("ICe bucket", "5", "214124"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_paymenthistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Log.i("PAYMENT", mValues.get(position).getChallengeTitle());
        holder.mChallengeTitle.setText(mValues.get(position).getChallengeTitle());
        holder.mAmount.setText(mValues.get(position).getAmount());
        holder.mDate.setText(mValues.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mChallengeTitle;
        public final TextView mAmount;
        public final TextView mDate;
        public PaymentHistoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mChallengeTitle = view.findViewById(R.id.ph_challenge_title);
            mAmount = view.findViewById(R.id.ph_amount);
            mDate = view.findViewById(R.id.ph_date);
        }
    }
}
