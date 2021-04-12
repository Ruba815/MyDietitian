package com.appterm.mydietician;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClientQuizFourActivity extends AppCompatActivity {

    private ImageView mNext;
    private ImageView mFis;
    private ImageView mChe;
    private ImageView mMet;
    ArrayList<String> selectedChose = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_quiz_four);
        mNext = findViewById(R.id.next);
        mFis = findViewById(R.id.fis);
        mChe = findViewById(R.id.che);
        mMet = findViewById(R.id.met);

        Intent intent = getIntent();
        ArrayList<String> vegetables = intent.getStringArrayListExtra("vegetables");
        ArrayList<String> fruits = intent.getStringArrayListExtra("fruits");
        ArrayList<String> starches = intent.getStringArrayListExtra("starches");


        mFis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFis.getAlpha() == 0.8f) {
                    mFis.setAlpha(1f);
                    selectedChose.remove("سمك");
                } else {
                    mFis.setAlpha(0.8f);
                    selectedChose.add("سمك");
                }


            }
        });

        mChe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChe.getAlpha() == 0.8f) {
                    mChe.setAlpha(1f);
                    selectedChose.remove("دجاج");
                } else {
                    mChe.setAlpha(0.8f);
                    selectedChose.add("دجاج");
                }

            }
        });

        mMet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mMet.getAlpha() == 0.8f) {
                    mMet.setAlpha(1f);
                    selectedChose.remove("لحم");
                } else {
                    mMet.setAlpha(0.8f);
                    selectedChose.add("لحم");
                }

            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedChose.size() == 0){
                    Toast.makeText(ClientQuizFourActivity.this, "الرجاء تحديد خيار", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(v.getContext(),ClientQuizFiveActivity.class);
                    intent.putExtra("vegetables",vegetables);
                    intent.putExtra("fruits",fruits);
                    intent.putExtra("starches",starches);
                    intent.putExtra("meat",selectedChose);
                    startActivity(intent);
                }
            }
        });


    }
}