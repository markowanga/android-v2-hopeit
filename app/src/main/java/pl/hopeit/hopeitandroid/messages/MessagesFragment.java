package pl.hopeit.hopeitandroid.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.hopeit.hopeitandroid.ChallengesToAcceptFragment;
import pl.hopeit.hopeitandroid.HopeItApplication;
import pl.hopeit.hopeitandroid.R;
import pl.hopeit.hopeitandroid.RecyclerItemClickListener;
import pl.hopeit.hopeitandroid.model.Challenge;
import pl.hopeit.hopeitandroid.model.Message;

import pl.hopeit.hopeitandroid.model.MessagesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesFragment extends Fragment {

    RecyclerView recyclerView;

    public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<List<MessagesResponse>> call = HopeItApplication.retrofitService.getMessages(HopeItApplication.fbUserId);
        call.enqueue(new Callback<List<MessagesResponse>>() {
            @Override
            public void onResponse(Call<List<MessagesResponse>> call, Response<List<MessagesResponse>> response) {
               List<MessagesResponse> messages = response.body();
               List<Message> msgs = new ArrayList<>();
               for(int i = 0; i < messages.size(); i++) {
                   MessagesResponse resp = messages.get(i);
                   Message msg = new Message(resp.topic, resp.text, resp.image_url);
                   msgs.add(msg);
               }
               showList(msgs);
                Log.d("MSG", "Ok");
            }

            @Override
            public void onFailure(Call<List<MessagesResponse>> call, Throwable t) {
                Log.d("MSG", "Fail");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        recyclerView = (RecyclerView) view;
        return view;
    }

    void showList(final List<Message> records) {
        Context context = getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MessageAdapter(records));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Message msg = records.get(position);
                        MessageDialogFragment dialogFragment = MessageDialogFragment.newInstance(msg);
                        dialogFragment.show(getFragmentManager(), "message_dialog");
                    }
                })
        );
    }
}
