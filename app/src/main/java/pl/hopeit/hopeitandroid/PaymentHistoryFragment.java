package pl.hopeit.hopeitandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class PaymentHistoryFragment extends Fragment {

    private List<PaymentHistoryItem> mItems;

    public void showPayments(List<PaymentHistoryItem> items) {
        this.mItems = items;
    }

    public PaymentHistoryFragment() {
    }

    public static PaymentHistoryFragment newInstance(int columnCount) {
        PaymentHistoryFragment fragment = new PaymentHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paymenthistory_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new PaymentHistoryRecyclerViewAdapter(mItems));
        }
        return view;
    }
}
