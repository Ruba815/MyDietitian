package com.appterm.mydietician;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClientQuizThreeActivity extends AppCompatActivity {

    private ImageView mNext;
    private ImageView mBre;
    private ImageView mPasta;
    private ImageView mRais;
    ArrayList<String> selectedChose = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_quiz_three);
        mNext = findViewById(R.id.next);
        mBre = findViewById(R.id.bre);
        mPasta = findViewById(R.id.pasta);
        mRais = findViewById(R.id.rais);

        Intent intent = getIntent();
        ArrayList<String> vegetables = intent.getStringArrayListExtra("vegetables");
        ArrayList<String> fruits = intent.getStringArrayListExtra("fruits");

        mBre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mBre.getAlpha() == 0.8f) {
                    mBre.setAlpha(1f);
                    selectedChose.remove("خبز");
                } else{
                    mBre.setAlpha(0.8f);
                    selectedChose.add("خبز");
                }

            }
        });


        mPasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(mPasta.getAlpha() == 0.8f) {
                    mPasta.setAlpha(1f);
                    selectedChose.remove("معكرونة");
                } else{
                    mPasta.setAlpha(0.8f);
                    selectedChose.add("معكرونة");
                }


            }
        });


        mRais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mRais.getAlpha() == 0.8f) {
                    mRais.setAlpha(1f);
                    selectedChose.remove("ارز");
                } else{
                    mRais.setAlpha(0.8f);
                    selectedChose.add("ارز");
                }
            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedChose.size() == 0){
                    Toast.makeText(ClientQuizThreeActivity.this, "الرجاء تحديد خيار", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(v.getContext(),ClientQuizFourActivity.class);
                    intent.putExtra("vegetables",vegetables);
                    intent.putExtra("fruits",fruits);
                    intent.putExtra("starches",selectedChose);
                    startActivity(intent);
                }
            }
        });
    }
}