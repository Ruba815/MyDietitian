package com.appterm.mydietician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientInformationActivity extends AppCompatActivity {

    private EditText mAge;
    private EditText mWeight;
    private EditText mHeight;

    private RadioButton mRadioButtonLoss;
    private RadioButton mRadioButtonUp;
    private RadioGroup mRadioGroup;
    private EditText mUpDown;
    private Button mNext;
    
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceClintInformation;
    String losRgain = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_information);
        getSupportActionBar().setTitle("اكمل بيناتك");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAge = findViewById(R.id.age);
        mWeight = findViewById(R.id.weight);
        mHeight = findViewById(R.id.height);
        mRadioButtonLoss = findViewById(R.id.radioButtonLoss);
        mRadioButtonUp = findViewById(R.id.radioButtonUp);
        mRadioGroup = findViewById(R.id.radioGroup);
        mUpDown = findViewById(R.id.up_down);
        mNext = findViewById(R.id.next);

        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceClintInformation = firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("information");


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonLoss:
                        losRgain = "انقاص الوزن";
                        break;
                    case R.id.radioButtonUp:
                        losRgain = "زيادة الوزن";
                        break;
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mAge.getText())){
                    mAge.setError("الرجاء إدخال عمرك!");
                    mAge.requestFocus();
                }else if(Integer.parseInt(mAge.getText().toString()) < 18) {
                    mAge.setError("عفوا البرنامج مخصص لعمر 18 فما فوق!");
                    mAge.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(mWeight.getText())){
                    mWeight.setError("الرجاء إدخال وزنك!");
                    mWeight.requestFocus();
                }else if(TextUtils.isEmpty(mHeight.getText())){
                    mHeight.setError("الرجاء إدخال طولك!");
                    mHeight.requestFocus();
                }else if(TextUtils.isEmpty(losRgain)){

                    Toast.makeText(ClientInformationActivity.this, "الرجاء تحديد الهدف", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(mUpDown.getText())){
                    mUpDown.setError("الرجاء إدخال الوزن الذي تريد انقاصه/زيادته !");
                    mUpDown.requestFocus();
                }else{
                    ClientInformation clientInformation = new ClientInformation();
                    clientInformation.setAge(mAge.getText().toString());
                    clientInformation.setWeight(mWeight.getText().toString());
                    clientInformation.setHeight(mHeight.getText().toString());
                    clientInformation.setLoseORgain(losRgain);
                    clientInformation.setNumber(mUpDown.getText().toString());
                    ProgressDialog progressDialog = new ProgressDialog(ClientInformationActivity.this);
                    progressDialog.setMessage("تحميل...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    referenceClintInformation.setValue(clientInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(ClientInformationActivity.this, "تم تحديث البينات بنجاح", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClientInformationActivity.this,ClientQuizOneActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ClientInformationActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}