package com.appterm.mydietician;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClientChat extends RecyclerView.Adapter<AdapterClientChat.DietitianChatViewHolder> {

    Context context;
    ArrayList<Client> clients ;

    public AdapterClientChat(Context context, ArrayList<Client> clients) {
        this.context = context;
        this.clients = clients;
    }

    @NonNull
    @Override
    public DietitianChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_client_to_chat,parent,false);
        return new DietitianChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietitianChatViewHolder holder, int position) {
        holder.name.setText(clients.get(position).getName());
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChatActivity.class);
                intent.putExtra("otherUserId",clients.get(position).getUID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    class DietitianChatViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout chat;
        TextView name;
        public DietitianChatViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            chat = itemView.findViewById(R.id.chat);
        }
    }
}
