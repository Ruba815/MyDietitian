package com.appterm.mydietician;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shunan.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {


    private TextView mName;
    private ImageView chat;

    ArrayList<Food> breakfastRelated = new ArrayList<>();
    ArrayList<Food> breakfastNotRelated = new ArrayList<>();
    ArrayList<Food> dinnerRelated = new ArrayList<>();
    ArrayList<Food> dinnerNotRelated = new ArrayList<>();
    ArrayList<Food> lunchRelated = new ArrayList<>();
    ArrayList<Food> lunchNotRelated = new ArrayList<>();
    Client client;

    ArrayList<Food> breakfastRelatedPlan = new ArrayList<>();
    ArrayList<Food> dinnerRelatedPlan = new ArrayList<>();
    ArrayList<Food> lunchRelatedPlan = new ArrayList<>();


    boolean isAddToBreakfastRelated = false;
    boolean isAddToDinnerRelated = false;
    boolean isAddToLunchRelated = false;

    int indexBreakfast = 0;
    int indexDinner = 0;
    int indexLunch = 0;

    int progress = 0;
    ValueEventListener valueEventListener;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mName = root.findViewById(R.id.name);
        chat = root.findViewById(R.id.chat);
        CircularProgressBar circularProgressBar = root.findViewById(R.id.progressBar);
        circularProgressBar.setProgress(0);

        ;
        TextView breakfastText = root.findViewById(R.id.breakfastText);
        TextView lunchText = root.findViewById(R.id.lunchText);
        TextView dinnerText = root.findViewById(R.id.dinnerText);

        CheckBox isEatenBreakfast = root.findViewById(R.id.isEatenBreakfast);
        CheckBox isEatenLunch = root.findViewById(R.id.isEatenLunch);
        CheckBox isEatenDiner = root.findViewById(R.id.isEatenDiner);



        Button breakfast = root.findViewById(R.id.breakfast);
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChangeFoodActivity.class);
                ChangeFoodActivity.foods = client.getPlan().getBreakfast();
                ChangeFoodActivity.name = "الافطار";
                startActivity(intent);
            }
        });
        Button lunch = root.findViewById(R.id.lunch);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChangeFoodActivity.class);
                ChangeFoodActivity.foods = client.getPlan().getBreakfast();
                ChangeFoodActivity.name = "الغداء";
                startActivity(intent);
            }
        });
        Button dinner = root.findViewById(R.id.dinner);
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChangeFoodActivity.class);
                ChangeFoodActivity.foods = client.getPlan().getBreakfast();
                ChangeFoodActivity.name = "العشاء";
                startActivity(intent);
            }
        });


        isEatenBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    progress=progress+33;
                    circularProgressBar.setProgress(progress);
                }else{
                    progress=progress-33;
                    circularProgressBar.setProgress(progress);
                }
                FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").child("breakfast").child(String.valueOf(indexBreakfast)).child("eaten").setValue(isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });
            }
        });

        isEatenLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    progress=progress+33;
                    circularProgressBar.setProgress(progress);
                }else{
                    progress=progress-33;
                    circularProgressBar.setProgress(progress);
                }
                FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").child("lunch").child(String.valueOf(indexLunch)).child("eaten").setValue(isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });
        isEatenDiner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    progress=progress+33;
                    circularProgressBar.setProgress(progress);
                }else{
                    progress=progress-33;
                    circularProgressBar.setProgress(progress);
                }
                FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").child("dinner").child(String.valueOf(indexLunch)).child("eaten").setValue(isChecked).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AllDietitiansActivity.class);
                startActivity(intent);
            }
        });




        valueEventListener = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                client = snapshot.getValue(Client.class);
                FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(valueEventListener);
                mName.setText("مرحبا بك , " + client.getName());

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                String dateToday = format.format(calendar.getTime());

                if(client.getPlan() == null){
                    return;
                }

                for (int i = 0; i < client.getPlan().getBreakfast().size() - 1; i++) {
                    if (dateToday.equals(client.getPlan().getBreakfast().get(i).getDate())) {

                        isEatenBreakfast.setChecked(client.getPlan().getBreakfast().get(i).isEaten());
                        if(client.getPlan().getBreakfast().get(i).isEaten()){
                            progress = progress + 33;
                            circularProgressBar.setProgress(progress);
                        }
                        indexBreakfast = i;
                        breakfastText.setText(client.getPlan().getBreakfast().get(i).getFoodName() + "\n" + client.getPlan().getBreakfast().get(i).getKcal() + "KCL \n" + client.getPlan().getBreakfast().get(i).getSize());
                        break;
                    }
                }

                for (int i = 0; i < client.getPlan().getLunch().size() - 1; i++) {
                    if (dateToday.equals(client.getPlan().getLunch().get(i).getDate())) {
                        isEatenLunch.setChecked(client.getPlan().getLunch().get(i).isEaten());
                        if(client.getPlan().getLunch().get(i).isEaten()){
                            progress = progress + 33;
                            circularProgressBar.setProgress( progress );
                        }
                        indexLunch = i;
                        lunchText.setText(client.getPlan().getLunch().get(i).getFoodName() + "\n" + client.getPlan().getLunch().get(i).getKcal() + "KCL \n" + client.getPlan().getLunch().get(i).getSize());
                        break;
                    }
                }
                for (int i = 0; i < client.getPlan().getDinner().size() - 1; i++) {
                    if (dateToday.equals(client.getPlan().getDinner().get(i).getDate())) {
                        isEatenDiner.setChecked(client.getPlan().getDinner().get(i).isEaten());
                        if(client.getPlan().getDinner().get(i).isEaten()){
                            circularProgressBar.setProgress(0);
                            progress = progress + 33;
                            circularProgressBar.setProgress(progress);
                        }
                        indexDinner = i ;
                        dinnerText.setText(client.getPlan().getDinner().get(i).getFoodName() + "\n" + client.getPlan().getDinner().get(i).getKcal() + "KCL \n" + client.getPlan().getDinner().get(i).getSize());
                        break;
                    }
                }


                // هدا السطر بس بدو يكون هنا بدل فوق بس
                // انقلي السطر هدا تحت هنا و جربي تمام يا صبايا


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
             if (!PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("hasPlan", false)) {
            makePlan();
            }

        }catch (Exception e){

        }



        return root;

    }

    // when upload NewFoods Foods
    private void makePlan() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                client = snapshot.getValue(Client.class);
                FirebaseDatabase.getInstance().getReference("NewFoods").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Food food = snap.getValue(Food.class);
                            Log.e("TAG====>", food.getFoodName());
                            switch (food.getType()) {
                                case "breakfast":
                                    isAddToBreakfastRelated = false;
                                    for (String fruit : client.getInformation().getFruits()) {
                                        if (food.getFoodName().contains(fruit)) {
                                            breakfastRelated.add(food);
                                            isAddToBreakfastRelated = true;
                                            break;
                                        } else {
                                            // breakfastNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToBreakfastRelated) {
                                        continue;
                                    }


                                    for (String vegetable : client.getInformation().getVegetables()) {
                                        if (food.getFoodName().contains(vegetable)) {
                                            breakfastRelated.add(food);
                                            isAddToBreakfastRelated = true;
                                            break;
                                        } else {
                                            // breakfastNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToBreakfastRelated) {
                                        continue;
                                    }
                                    for (String starche : client.getInformation().getStarches()) {
                                        if (food.getFoodName().contains(starche)) {
                                            breakfastRelated.add(food);
                                            isAddToBreakfastRelated = true;
                                            break;
                                        } else {
                                            //  breakfastNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToBreakfastRelated) {
                                        continue;
                                    }
                                    for (String dairy : client.getInformation().getDairy()) {
                                        if (food.getFoodName().contains(dairy)) {
                                            breakfastRelated.add(food);
                                            isAddToBreakfastRelated = true;
                                            break;
                                        } else {
                                            //breakfastNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToBreakfastRelated) {
                                        continue;
                                    }
                                    for (String meat : client.getInformation().getMeat()) {
                                        if (food.getFoodName().contains(meat)) {
                                            breakfastRelated.add(food);
                                            isAddToBreakfastRelated = true;
                                            break;
                                        } else {
                                            breakfastNotRelated.add(food);
                                        }
                                    }


                                    break;
                                case "lunch":

                                    isAddToLunchRelated = false;

                                    for (String fruit : client.getInformation().getFruits()) {
                                        if (food.getFoodName().contains(fruit)) {
                                            lunchRelated.add(food);
                                            isAddToLunchRelated = true;
                                            break;
                                        } else {
                                            //lunchNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToLunchRelated) {
                                        continue;
                                    }
                                    for (String vegetable : client.getInformation().getVegetables()) {
                                        if (food.getFoodName().contains(vegetable)) {
                                            lunchRelated.add(food);
                                            isAddToLunchRelated = true;
                                            break;
                                        } else {
//                                    lunchNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToLunchRelated) {
                                        continue;
                                    }

                                    for (String starche : client.getInformation().getStarches()) {
                                        if (food.getFoodName().contains(starche)) {
                                            lunchRelated.add(food);
                                            isAddToLunchRelated = true;
                                            break;
                                        } else {
//                                    lunchNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToLunchRelated) {
                                        continue;
                                    }
                                    for (String dairy : client.getInformation().getDairy()) {
                                        if (food.getFoodName().contains(dairy)) {
                                            lunchRelated.add(food);
                                            isAddToLunchRelated = true;
                                            break;
                                        } else {
                                            //lunchNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToLunchRelated) {
                                        continue;
                                    }
                                    for (String meat : client.getInformation().getMeat()) {
                                        if (food.getFoodName().contains(meat)) {
                                            lunchRelated.add(food);
                                            break;
                                        } else {
                                            lunchNotRelated.add(food);
                                        }
                                    }
//                            if(food.getFoodName().contains(client.getInformation().getFruits()) ||
//                                    food.getFoodName().contains(client.getInformation().getVegetables()) ||
//                                    food.getFoodName().contains(client.getInformation().getStarches()) ||
//                                    food.getFoodName().contains(client.getInformation().getMeat()) || food.getFoodName().contains(client.getInformation().getDairy())){
//                                lunchRelated.add(food);
//
//                            }else{
//                                lunchNotRelated.add(food);
//
//                            }
                                    break;

                                case "dinner":


                                    isAddToDinnerRelated = false;
                                    for (String fruit : client.getInformation().getFruits()) {
                                        if (food.getFoodName().contains(fruit)) {
                                            dinnerRelated.add(food);
                                            isAddToDinnerRelated = true;
                                            break;
                                        } else {
                                            // dinnerNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToDinnerRelated) {
                                        continue;
                                    }
                                    for (String vegetable : client.getInformation().getVegetables()) {
                                        if (food.getFoodName().contains(vegetable)) {
                                            dinnerRelated.add(food);
                                            isAddToDinnerRelated = true;
                                            break;
                                        } else {
                                            // dinnerNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToDinnerRelated) {
                                        continue;
                                    }
                                    for (String starche : client.getInformation().getStarches()) {
                                        if (food.getFoodName().contains(starche)) {
                                            dinnerRelated.add(food);
                                            isAddToDinnerRelated = true;
                                            break;
                                        } else {
                                            // dinnerNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToDinnerRelated) {
                                        continue;
                                    }
                                    for (String dairy : client.getInformation().getDairy()) {
                                        if (food.getFoodName().contains(dairy)) {
                                            dinnerRelated.add(food);
                                            isAddToDinnerRelated = true;
                                            break;
                                        } else {
                                            //dinnerNotRelated.add(food);
                                        }
                                    }
                                    if (isAddToDinnerRelated) {
                                        continue;
                                    }
                                    for (String meat : client.getInformation().getMeat()) {
                                        if (food.getFoodName().contains(meat)) {
                                            dinnerRelated.add(food);
                                            isAddToDinnerRelated = true;
                                            break;
                                        } else {
                                            dinnerNotRelated.add(food);
                                        }
                                    }

                                    break;

                            }

                        }
                        Log.e("breakfastRelated ==> ", breakfastRelated.size() + "");
                        Log.e("breakNotRelated ==> ", breakfastNotRelated.size() + "");
                        Log.e("lunchRelated ==> ", lunchRelated.size() + "");
                        Log.e("lunchNotRelated ==> ", lunchNotRelated.size() + "");
                        Log.e("dinnerRelated ==> ", dinnerRelated.size() + "");
                        Log.e("dinnerNotRelated ==> ", dinnerNotRelated.size() + "");

                        // Define the format in which you want dates
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

//Get calendar instance
                        Calendar calendar = Calendar.getInstance();

//Set today's date to calendar instance
                        calendar.setTime(new Date());

//Initialize a list to get dates
                        List<String> dates = new ArrayList<>();

//Get today's date in the format we defined above and add it to list
                        String date = format.format(calendar.getTime());
                        dates.add(date);

//run a for loop six times to get 1 day added each time
                        for (int i = 0; i <= 30; i++) {
//this will take care of month and year when adding 1 day to current date
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            dates.add(format.format(calendar.getTime()));
                        }

//Then to show the dates to your text-
//                String listString = TextUtils.join(",\n ", dates);
//                Log.e("Dates==>",listString);


                        if (breakfastRelated.size() < 30) {
                            for (int i = 0; i < breakfastRelated.size() - 1; i++) {
                                Food food = breakfastRelated.get(i);
                                food.setDate(dates.get(i));
                                breakfastRelatedPlan.add(food);
                            }
                            //  breakfastRelatedPlan.addAll(breakfastRelated);
                            int index = 0;
                            for (int i = breakfastRelated.size() - 1; i < 30; i++) {
                                index++;
//                        try {
//                            breakfastRelatedPlan.add(breakfastNotRelated.get(index));
//                        }catch (Exception e){
//                            breakfastRelatedPlan.add(breakfastNotRelated.get(0));
//                        }

                                try {
                                    Food food = breakfastNotRelated.get(index);
                                    food.setDate(dates.get(breakfastRelatedPlan.size() - 1));
                                    breakfastRelatedPlan.add(food);
                                } catch (Exception e) {
                                    // dinnerRelatedPlan.add(dinnerNotRelated.get(0));
                                    try {
                                        Food food = breakfastNotRelated.get(0);
                                        food.setDate(dates.get(breakfastRelatedPlan.size() - 1));
                                        breakfastRelatedPlan.add(food);
                                    }catch (Exception ed){}

                                }
                            }
                        } else {
//                    for(int i=0; i<30; i++){
//                        breakfastRelatedPlan.add(breakfastRelated.get(i));
//                    }

                            for (int i = 0; i < 30; i++) {
                                Log.e("index==>", i + "");
                                Food food = breakfastRelated.get(i);
                                food.setDate(dates.get(i));
                                breakfastRelatedPlan.add(food);
                            }
                        }


                        if (lunchRelated.size() < 30) {
                            for (int i = 0; i < lunchRelated.size() - 1; i++) {
                                Food food = lunchRelated.get(i);
                                food.setDate(dates.get(i));
                                lunchRelatedPlan.add(food);
                            }
                            //  lunchRelatedPlan.addAll(lunchRelated);
                            int index = 0;
                            for (int i = lunchRelated.size() - 1; i < 30; i++) {
                                index++;
                                try {
                                    Food food = lunchNotRelated.get(index);
                                    food.setDate(dates.get(lunchRelatedPlan.size() - 1));
                                    lunchRelatedPlan.add(food);
                                } catch (Exception e) {
                                    // dinnerRelatedPlan.add(dinnerNotRelated.get(0));
                                    Food food = lunchNotRelated.get(0);
                                    food.setDate(dates.get(lunchRelatedPlan.size() - 1));
                                    lunchRelatedPlan.add(food);
                                }
                            }
                        } else {
//                    for(int i=0; i<30; i++){
//                        lunchRelatedPlan.add(lunchRelated.get(i));
//                    }

                            for (int i = 0; i < 30; i++) {
                                Log.e("index==>", i + "");
                                Food food = lunchRelated.get(i);
                                food.setDate(dates.get(i));
                                lunchRelatedPlan.add(food);
                            }
                        }

                        if (dinnerRelated.size() < 30) {
                            for (int i = 0; i < dinnerRelated.size() - 1; i++) {
                                Food food = dinnerRelated.get(i);
                                food.setDate(dates.get(i));
                                dinnerRelatedPlan.add(food);
                            }
                            //dinnerRelatedPlan.addAll(dinnerRelated);
                            int index = 0;
                            for (int i = dinnerRelated.size() - 1; i < 30; i++) {
                                index++;
                                try {
                                    Food food = dinnerNotRelated.get(index);
                                    food.setDate(dates.get(dinnerRelatedPlan.size() - 1));
                                    dinnerRelatedPlan.add(food);
                                } catch (Exception e) {
                                    // dinnerRelatedPlan.add(dinnerNotRelated.get(0));
                                    Food food = dinnerNotRelated.get(0);
                                    food.setDate(dates.get(dinnerRelatedPlan.size() - 1));
                                    dinnerRelatedPlan.add(food);
                                }
                            }
                        } else {
                            for (int i = 0; i < 30; i++) {
                                Log.e("index==>", i + "");
                                Food food = dinnerRelated.get(i);
                                food.setDate(dates.get(i));
                                dinnerRelatedPlan.add(food);
                            }
                        }


                        Log.e("breakfRelatedPlan ==> ", breakfastRelatedPlan.size() + "");
                        Log.e("lunchRelatedPlan ==> ", lunchRelatedPlan.size() + "");
                        Log.e("dinnerRelatedPlan ==> ", dinnerRelatedPlan.size() + "");

                        Plan plan = new Plan();
                        plan.setBreakfast(breakfastRelatedPlan);
                        plan.setLunch(lunchRelatedPlan);
                        plan.setDinner(dinnerRelatedPlan);
                        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plan").setValue(plan).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    try {
                                        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("hasPlan", true).commit();
                                    }catch (Exception e){

                                    }
                                } else {
                                    try {
                                        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("hasPlan", false).commit();
                                    }catch (Exception e){

                                    }
                                }
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}