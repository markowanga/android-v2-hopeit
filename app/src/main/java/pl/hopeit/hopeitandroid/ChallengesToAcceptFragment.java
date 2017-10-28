package pl.hopeit.hopeitandroid;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.model.Challenge;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesToAcceptFragment extends Fragment {


    public ChallengesToAcceptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges_to_accept, container, false);

        // todo request
    }

    void showList(final List<Challenge> records) {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        ChallengesToAcceptAdapter adapter = new ChallengesToAcceptAdapter(records);
//        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showDialog(records.get(position));
                    }
                })
        );
    }

    private class ChallengesToAcceptAdapter extends RecyclerView.Adapter<ViewHolder> {
        List<Challenge> mRecords;

        public ChallengesToAcceptAdapter(List<Challenge> records) {
            mRecords = records;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_three_lines_image, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Challenge currentRecord = mRecords.get(position);
            holder.description.setText(currentRecord.description);
            holder.from.setText("Wyzwanie od " + currentRecord.from);
            Picasso.with(getContext()).load(currentRecord.photoUrl).into(holder.image);
            holder.title.setText(currentRecord.title);
        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }
    }

    void showDialog(Challenge challenge) {
        DialogFragment newFragment = ChallengeDialogToAccept.newInstance(challenge);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, from, description;
        ImageView image;
        View view;

        ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.title);
            from = (TextView) v.findViewById(R.id.from);
            description = (TextView) v.findViewById(R.id.description);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }

}
