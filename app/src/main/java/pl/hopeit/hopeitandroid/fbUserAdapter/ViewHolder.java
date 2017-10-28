package pl.hopeit.hopeitandroid.fbUserAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.hopeit.hopeitandroid.R;

/**
 * Created by marcinwatroba on 28.10.2017.
 */

class ViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    ImageView image;

    ViewHolder(View v) {
        super(v);
        name = (TextView) v.findViewById(R.id.name);
        image = (ImageView) v.findViewById(R.id.image);
    }
}