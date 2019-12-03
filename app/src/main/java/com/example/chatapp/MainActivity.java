package com.example.chatapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static EditText editMessage,editUsername;
    private DatabaseReference mDatabase;
    private RecyclerView mMessageList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMessage = (EditText) findViewById(R.id.editMessageE);
        editUsername = (EditText)findViewById(R.id.editUsernameE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
        mMessageList = findViewById(R.id.messageRec);
        mMessageList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager);


    }

    public void sendButtonClicked(View view)
    {
        final String messageValue = editMessage.getText().toString().trim();
        final String username = editUsername.getText().toString().trim();

        if (!TextUtils.isEmpty(messageValue)){

            DatabaseReference newPost =  mDatabase.push();
            newPost.child("content").setValue(messageValue);
            newPost.child("username").setValue(username);
            newPost.child("time").setValue(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));

        }
    }

    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerAdapter < Message, MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                Message.class,
                R.layout.singlemessagelayout,
                MessageViewHolder.class,
                mDatabase
        )  {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                viewHolder.setContent(model.getContent());
                viewHolder.setUsername(model.getUsername());
                viewHolder.setTime(model.getTime());

            }
        };
        mMessageList.setAdapter(FBRA);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public MessageViewHolder (View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setContent(String content)
        {
            TextView message_content = (TextView) mView.findViewById(R.id.messageText);
            message_content.setText(content);
        }

        public void setUsername(String username)
        {
            TextView username_content = (TextView) mView.findViewById(R.id.usernameText);
            username_content.setText(username);
        }
        public void setTime(String time)
        {
            TextView time_content = (TextView) mView.findViewById(R.id.timeText);
            time_content.setText(time);

        }
    }

}
