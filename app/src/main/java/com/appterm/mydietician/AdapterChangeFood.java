package com.appterm.mydietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterChangeFood extends RecyclerView.Adapter<AdapterChangeFood.ChangeFoodViewHolder> {

    Context context;
    String name;
    ArrayList<Food> foods;

    public AdapterChangeFood(Context context, String name, ArrayList<Food> foods) {
        this.context = context;
        this.name = name;
        this.foods = foods;
    }

    @NonNull
    @Override
    public ChangeFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_change_food,parent,false);
        return new ChangeFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeFoodViewHolder holder, int position) {

        holder.name.setText(name);
        holder.content.setText(foods.get(position).getFoodName() + "\n" + foods.get(position).getKcal() + "KCL \n" + foods.get(position).getSize());
        holder.isEaten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(name.equals("الافطار")){
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").child("breakfast").child(String.valueOf(position)).child("eaten").setValue(isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }else if(name.equals("الغداء")){
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").child("lunch").child(String.valueOf(position)).child("eaten").setValue(isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }else if(name.equals("العشاء")){
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").child("dinner").child(String.valueOf(position)).child("eaten").setValue(isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class ChangeFoodViewHolder extends RecyclerView.ViewHolder{
        TextView content,name;
        CheckBox isEaten;
        public ChangeFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            name = itemView.findViewById(R.id.name);
            isEaten = itemView.findViewById(R.id.isEaten);

        }
    }
}
