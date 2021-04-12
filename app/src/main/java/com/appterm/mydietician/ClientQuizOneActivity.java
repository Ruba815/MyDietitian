package com.appterm.mydietician;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClientQuizOneActivity extends AppCompatActivity {

    private ImageView mNext;

    private ImageView mTmeto;
    private ImageView mYar;
    private ImageView mAvo;
    private ImageView mFater;
    private ImageView mPotet;
    private ImageView mProk;

    ArrayList<String> selectedChose = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_quiz_one);
        mNext = findViewById(R.id.next);

        mTmeto = findViewById(R.id.tmeto);
        mYar = findViewById(R.id.yar);
        mAvo = findViewById(R.id.avo);
        mFater = findViewById(R.id.fater);
        mPotet = findViewById(R.id.potet);
        mProk = findViewById(R.id.prok);

        mTmeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTmeto.getAlpha() == 0.8f) {
                    mTmeto.setAlpha(1f);
                    selectedChose.remove("طماطم");
                } else{
                    mTmeto.setAlpha(0.8f);
                    selectedChose.add("طماطم");
                }
                Log.e("TAH==>",selectedChose.toString()+"");

            }
        });
        mYar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mYar.getAlpha() == 0.8f) {
                    mYar.setAlpha(1f);
                    selectedChose.remove("خيار");
                } else{
                    mYar.setAlpha(0.8f);
                    selectedChose.add("خيار");
                }
                Log.e("TAH==>",selectedChose.toString()+"");
            }
        });
        mAvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAvo.getAlpha() == 0.8f) {
                    mAvo.setAlpha(1f);
                    selectedChose.remove("افوكادو");
                } else{
                    mAvo.setAlpha(0.8f);
                    selectedChose.add("افوكادو");
                }
                Log.e("TAH==>",selectedChose.toString()+"");

            }
        });
        mFater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFater.getAlpha() == 0.8f) {
                    mFater.setAlpha(1f);
                    selectedChose.remove("فطر");
                } else{
                    mFater.setAlpha(0.8f);
                    selectedChose.add("فطر");
                }
                Log.e("TAH==>",selectedChose.toString()+"");

            }
        });
        mPotet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPotet.getAlpha() == 0.8f) {
                    mPotet.setAlpha(1f);
                    selectedChose.remove("بطاطا");
                } else{
                    mPotet.setAlpha(0.8f);
                    selectedChose.add("بطاطا");
                }
                Log.e("TAH==>",selectedChose.toString()+"");

            }
        });
        mProk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProk.getAlpha() == 0.8f) {
                    mProk.setAlpha(1f);
                    selectedChose.remove("بروكلي");
                } else{
                    mProk.setAlpha(0.8f);
                    selectedChose.add("بروكلي");
                }
                Log.e("TAH==>",selectedChose.toString()+"");

            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedChose.size() == 0){
                    Toast.makeText(ClientQuizOneActivity.this, "الرجاء تحديد خيار", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(v.getContext(),ClientQuizTwoActivity.class);
                    intent.putExtra("vegetables",selectedChose);
                    startActivity(intent);
                }
            }
        });
    }
}