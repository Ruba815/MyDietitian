package com.appterm.mydietician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ChangeFoodActivity extends AppCompatActivity {

    static ArrayList<Food> foods;
    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_food);

        Intent intent = getIntent();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        AdapterChangeFood adapterChangeFood = new AdapterChangeFood(this,name,foods);
        recyclerView.setAdapter(adapterChangeFood);
    }
}