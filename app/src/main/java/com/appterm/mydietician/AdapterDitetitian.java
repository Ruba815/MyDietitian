package com.appterm.mydietician;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterDitetitian extends RecyclerView.Adapter<AdapterDitetitian.DiteViewHolder> {

    Context context;
    ArrayList<Dietitian> dietitians;


    public AdapterDitetitian(Context context, ArrayList<Dietitian> dietitians) {
        this.context = context;
        this.dietitians = dietitians;
    }

    @NonNull
    @Override
    public DiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ditetitian,parent,false);
        return new DiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiteViewHolder holder, int position) {
        holder.name.setText(dietitians.get(position).getName());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        //  storageReference.child(products.get(position).getImage());

        storageReference.child(dietitians.get(position).getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                // Toast.makeText(context, uri.toString()+"", Toast.LENGTH_SHORT).show();
                Glide.with(context)
                        .load(uri.toString())
                        .into(holder.image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("users").child(dietitians.get(position).getUID()).child("accepted").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Accepted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return dietitians.size();
    }

    class DiteViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;
        Button accept;
        public DiteViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            accept = itemView.findViewById(R.id.accept);



        }
    }


}
