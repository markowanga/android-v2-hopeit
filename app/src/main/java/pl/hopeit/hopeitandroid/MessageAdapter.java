package pl.hopeit.hopeitandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pavlospt.roundedletterview.RoundedLetterView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final List<Message> mMessages;

    public MessageAdapter(List<Message> messages) {
        this.mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mMessages.get(position);
        holder.mRoundedLetterView.setTitleText(mMessages.get(position).getTitle().substring(0, 1));
        holder.mTitleView.setText(mMessages.get(position).getTitle());
        holder.mSummaryView.setText(mMessages.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final RoundedLetterView mRoundedLetterView;
        public final TextView mTitleView;
        public final TextView mSummaryView;
        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.msg_title);
            mSummaryView = view.findViewById(R.id.msg_summary);
            mRoundedLetterView = view.findViewById(R.id.msg_round_letter);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSummaryView.getText() + "'";
        }
    }
}
