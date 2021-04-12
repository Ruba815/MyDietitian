package com.appterm.mydietician;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shunan.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;


public class ClientPlanFragment extends Fragment {


    ArrayList<Food> breakfastRelatedPlan = new ArrayList<>();
    ArrayList<Food> dinnerRelatedPlan = new ArrayList<>();
    ArrayList<Food> lunchRelatedPlan = new ArrayList<>();

    int isEatenCount = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_plan, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        CircularProgressBar circularProgressBar = view.findViewById(R.id.progressBar);
        circularProgressBar.setProgress(0);
        TextView textView = view.findViewById(R.id.textView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        AdapterPlan adapterPlan = new AdapterPlan(getActivity(),breakfastRelatedPlan,dinnerRelatedPlan,lunchRelatedPlan);
        recyclerView.setAdapter(adapterPlan);
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client client = snapshot.getValue(Client.class);
                breakfastRelatedPlan.addAll(client.getPlan().getBreakfast());
                lunchRelatedPlan.addAll(client.getPlan().getLunch());
                dinnerRelatedPlan.addAll(client.getPlan().getDinner());
                adapterPlan.notifyDataSetChanged();

                for(Food food : breakfastRelatedPlan){
                    if(food.isEaten() == true){
                        isEatenCount ++;
                    }
                }

                for(Food food : lunchRelatedPlan){
                    if(food.isEaten() == true){
                        isEatenCount ++;
                    }
                }

                for(Food food : lunchRelatedPlan){
                    if(food.isEaten() == true){
                        isEatenCount ++;
                    }

                }

                int k = (int)(isEatenCount*(90/100.0f));
                circularProgressBar.setProgress((int) k);

                textView.setText("عمل رائع! انت اقرب ب "+k+"%"+" الى هدفك");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}