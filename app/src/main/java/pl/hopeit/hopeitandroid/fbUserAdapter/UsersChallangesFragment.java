package pl.hopeit.hopeitandroid.fbUserAdapter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.hopeit.hopeitandroid.ChallengesAcceptedFragment;
import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.RecyclerItemClickListener;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.FbUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersChallangesFragment extends Fragment {


    public UsersChallangesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_challanges, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // request
        // TODO: 28.10.2017
    }

    void showList(final List<FbUser> records) {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        FbUserRecycleAdapter adapter = new FbUserRecycleAdapter(records, getContext());
//        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // todo open new activity
                    }
                })
        );
    }
}
