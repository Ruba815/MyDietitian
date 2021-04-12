package com.appterm.mydietician;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClientQuizTwoActivity extends AppCompatActivity {

    private ImageView mNext;
    private ImageView mAppl;
    private ImageView mTot;
    private ImageView mAnab;
    private ImageView mKewe;
    private ImageView mStro;
    private ImageView mOrn;
    ArrayList<String>  selectedChose = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_quiz_two);
        mNext = findViewById(R.id.next);
        mAppl = findViewById(R.id.appl);
        mTot = findViewById(R.id.tot);
        mAnab = findViewById(R.id.anab);
        mKewe = findViewById(R.id.kewe);
        mStro = findViewById(R.id.stro);
        mOrn = findViewById(R.id.orn);

        Intent intent = getIntent();
        ArrayList<String> vegetables = intent.getStringArrayListExtra("vegetables");
        mAppl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  selectedChose = "تفاح" ;

                if(mAppl.getAlpha() == 0.8f) {
                    mAppl.setAlpha(1f);
                    selectedChose.remove("تفاح");
                } else{
                    mAppl.setAlpha(0.8f);
                    selectedChose.add("تفاح");
                }


            }
        });

        mTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTot.getAlpha() == 0.8f) {
                    mTot.setAlpha(1f);
                    selectedChose.remove("توت");
                } else{
                    mTot.setAlpha(0.8f);
                    selectedChose.add("توت");
                }


            }
        });
        mAnab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mAnab.getAlpha() == 0.8f) {
                    mAnab.setAlpha(1f);
                    selectedChose.remove("عنب");
                } else{
                    mAnab.setAlpha(0.8f);
                    selectedChose.add("عنب");
                }



            }
        });
        mKewe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mKewe.getAlpha() == 0.8f) {
                    mKewe.setAlpha(1f);
                    selectedChose.remove("كيوي");
                } else{
                    mKewe.setAlpha(0.8f);
                    selectedChose.add("كيوي");
                }



            }
        });
        mStro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStro.getAlpha() == 0.8f) {
                    mStro.setAlpha(1f);
                    selectedChose.remove("فراولة");
                } else{
                    mStro.setAlpha(0.8f);
                    selectedChose.add("فراولة");
                }


            }
        });
        mOrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrn.getAlpha() == 0.8f) {
                    mOrn.setAlpha(1f);
                    selectedChose.remove("برتقال");
                } else{
                    mOrn.setAlpha(0.8f);
                    selectedChose.add("برتقال");
                }
            }
        });



        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedChose.size() == 0){
                    Toast.makeText(ClientQuizTwoActivity.this, "الرجاء تحديد خيار", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(v.getContext(),ClientQuizThreeActivity.class);
                    intent.putExtra("vegetables",vegetables);
                    intent.putExtra("fruits",selectedChose);
                    startActivity(intent);
                }
            }
        });
    }
}