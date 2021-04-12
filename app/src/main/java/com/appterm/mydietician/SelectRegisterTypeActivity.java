package com.appterm.mydietician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SelectRegisterTypeActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference referenceUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_register_type);

        database = FirebaseDatabase.getInstance();
        referenceUsers = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("type");

        getSupportActionBar().setTitle("اختر");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button dietician = findViewById(R.id.dietician);
        Button client = findViewById(R.id.client);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "أخصائي تغذية";
                ProgressDialog progressDialog = new ProgressDialog(SelectRegisterTypeActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Map map = new HashMap();
                map.put("type",data);
                map.put("accepted",false);
                FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SelectRegisterTypeActivity.this, UploadCertificationActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SelectRegisterTypeActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "مستخدم";
                ProgressDialog progressDialog = new ProgressDialog(SelectRegisterTypeActivity.this);
                progressDialog.setMessage("تحميل...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                referenceUsers.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SelectRegisterTypeActivity.this, ClientInformationActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SelectRegisterTypeActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}