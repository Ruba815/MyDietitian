package com.appterm.mydietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ChatViewHolder> {
    Context context;
    ArrayList<Chat> chats;

    public AdapterChat(Context context, ArrayList<Chat> chats) {
        this.context = context;
        this.chats = chats;

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);

        return new AdapterChat.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        if(chats.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

            holder.sender.setVisibility(View.VISIBLE);

            holder.sender.setText(chats.get(position).getText());
        }else{
            holder.receiver.setVisibility(View.VISIBLE);

            holder.receiver.setText(chats.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {


        TextView receiver,sender;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);


            sender = itemView.findViewById(R.id.sender);

            receiver = itemView.findViewById(R.id.receiver);

        }
    }


}
