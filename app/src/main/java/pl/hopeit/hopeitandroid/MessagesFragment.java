package pl.hopeit.hopeitandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesFragment extends Fragment {

    private List<Message> messages = Arrays.asList(
            new Message("Title 1", "Lorem ipsum dolor sit amet, Lorem Ipsum Dolor Sit amet", "https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/13886935_1802592993361246_6946750506052487703_n.jpg?oh=13733efc8c1fdb7c3da5b4345633dc64&oe=5AA8BDEC"),
            new Message("Title 2", "Lorem ipsum dolor sit amet, Lorem Ipsum Dolor Sit amet"),
            new Message("Title 3", "Lorem ipsum dolor sit amet, Lorem Ipsum Dolor Sit amet"),
            new Message("Title 4", "Lorem ipsum dolor sit amet, Lorem Ipsum Dolor Sit amet")
    );

    public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MessageAdapter(messages));
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            Message msg = messages.get(position);
                            MessageDialogFragment dialogFragment = MessageDialogFragment.newInstance(msg);
                            dialogFragment.show(getFragmentManager(), "message_dialog");
                        }
                    })
            );
        }
        return view;
    }

    public void showMessages(List<Message> messages) {
        this.messages = messages;
    }
}
