package com.appterm.mydietician;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ClientQuizFiveActivity extends AppCompatActivity {

    private ImageView mNext;

    private ImageView mEgg;
    private ImageView mMilk;
    private ImageView mTt;
    private ImageView mChee;
    ArrayList<String> selectedChose = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceClintInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_quiz_five);
        mNext = findViewById(R.id.next);
        mEgg = findViewById(R.id.egg);
        mMilk = findViewById(R.id.milk);
        mTt = findViewById(R.id.tt);
        mChee = findViewById(R.id.chee);



        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceClintInformation = firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("information");


        Intent intent = getIntent();
        ArrayList<String> vegetables = intent.getStringArrayListExtra("vegetables");
        ArrayList<String> fruits = intent.getStringArrayListExtra("fruits");
        ArrayList<String> starches = intent.getStringArrayListExtra("starches");
        ArrayList<String> meat = intent.getStringArrayListExtra("meat");


        mEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEgg.getAlpha() == 0.8f) {
                    mEgg.setAlpha(1f);
                    selectedChose.remove("??????");
                } else {
                    mEgg.setAlpha(0.8f);
                    selectedChose.add("??????");
                }

            }
        });

        mMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMilk.getAlpha() == 0.8f) {
                    mMilk.setAlpha(1f);
                    selectedChose.remove("??????");
                } else {
                    mMilk.setAlpha(0.8f);
                    selectedChose.add("??????");
                }

            }
        });

        mTt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTt.getAlpha() == 0.8f) {
                    mTt.setAlpha(1f);
                    selectedChose.remove("??????????");
                } else {
                    mTt.setAlpha(0.8f);
                    selectedChose.add("??????????");
                }


            }
        });

        mChee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mChee.getAlpha() == 0.8f) {
                    mChee.setAlpha(1f);
                    selectedChose.remove("??????");
                } else {
                    mChee.setAlpha(0.8f);
                    selectedChose.add("??????");
                }

            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedChose.size() == 0){
                    Toast.makeText(ClientQuizFiveActivity.this, "???????????? ?????????? ????????", Toast.LENGTH_SHORT).show();
                }else {
                    HashMap hashMap = new HashMap();
                    hashMap.put("vegetables",vegetables);
                    hashMap.put("fruits",fruits);
                    hashMap.put("starches",starches);
                    hashMap.put("meat",meat);
                    hashMap.put("dairy",selectedChose);
                    referenceClintInformation.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                //Toast.makeText(ClientQuizFiveActivity.this, "???? ?????????? ???????????????? ??????????", Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(ClientQuizFiveActivity.this)
                                        .setTitle("????????!")
                                        .setMessage("?????? ?????? ???????????? ?????????????????? ???????????? ?? ?????? ???????? ???????? ???????? ?????? ???????????? ???????????? ???? ????????????")
                                        .setCancelable(false)
                                        .setPositiveButton("????????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new AlertDialog.Builder(ClientQuizFiveActivity.this)
                                                        .setTitle("??????????")
                                                        .setMessage("?????????? ???????????????? ???? ?????????? ???????????????? ???????? ?????? ?????????? ???? ?????????? ?????????????? ?????????????? ?????? ???????????? ?? ?????? ????????...")
                                                        .setCancelable(false)
                                                        .setPositiveButton("????????", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                PreferenceManager.getDefaultSharedPreferences(ClientQuizFiveActivity.this).edit().putBoolean("hasPlan", false).commit();
                                                                Intent intent1 = new Intent(ClientQuizFiveActivity.this,HomeClientActivity.class);
                                                                startActivity(intent1);
                                                            }
                                                        }).show();

                                            }
                                        }).show();


                            }else{
                                Toast.makeText(ClientQuizFiveActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }

}