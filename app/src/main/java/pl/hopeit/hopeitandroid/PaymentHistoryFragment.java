package pl.hopeit.hopeitandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.hopeit.hopeitandroid.messages.MessageAdapter;
import pl.hopeit.hopeitandroid.messages.MessageDialogFragment;
import pl.hopeit.hopeitandroid.model.Message;
import pl.hopeit.hopeitandroid.model.MessagesResponse;
import pl.hopeit.hopeitandroid.model.PaymentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentHistoryFragment extends Fragment {

    RecyclerView recyclerView;

    public PaymentHistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<List<PaymentResponse>> call = HopeItApplication.retrofitService.getPayments(HopeItApplication.fbUserId);

        call.enqueue(new Callback<List<PaymentResponse>>() {
            @Override
            public void onResponse(Call<List<PaymentResponse>> call, Response<List<PaymentResponse>> response) {
                List<PaymentResponse> payments = response.body();
                List<PaymentHistoryItem> records = new ArrayList<>();

                for(int i = 0; i < payments.size(); i++) {
                    PaymentResponse resp = payments.get(i);
                    String amount = resp.getAmount();
                    String data = resp.getDate();
                    data = data.substring(0, 10);

                    PaymentHistoryItem item = new PaymentHistoryItem(resp.getChallengeTitle(), amount + " zł", data);
                    records.add(item);
                }

                showList(records);
                Log.d("PAY", "Ok");
            }

            @Override
            public void onFailure(Call<List<PaymentResponse>> call, Throwable t) {
                Log.d("PAY", "Fail");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paymenthistory_list, container, false);
        recyclerView = (RecyclerView) view;
        return view;
    }

   public void showList(final List<PaymentHistoryItem> records) {
       Context context = getActivity();
       recyclerView.setLayoutManager(new LinearLayoutManager(context));
       recyclerView.setAdapter(new PaymentHistoryRecyclerViewAdapter(records));
    }
}
