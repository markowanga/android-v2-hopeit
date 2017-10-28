package pl.hopeit.hopeitandroid.fbUserAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.FbUser;

/**
 * Created by marcinwatroba on 28.10.2017.
 */

class FbUserRecycleAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<FbUser> mRecords;
    Context context;

    public FbUserRecycleAdapter(List<FbUser> records, Context context) {
        mRecords = records;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_facebook_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FbUser currentRecord = mRecords.get(position);
        holder.name.setText(currentRecord.name);
        Picasso.with(context).load(currentRecord.imgUrl).resize(50, 50).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }
}