package com.appterm.mydietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeAdminActivity extends AppCompatActivity {

    ArrayList<Dietitian> dietitians = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Home");
        setContentView(R.layout.activity_home_admin);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        AdapterDitetitian adapterDitetitian = new AdapterDitetitian(this,dietitians);
        recyclerView.setAdapter(adapterDitetitian);
        FirebaseDatabase.getInstance().getReference("users").orderByChild("accepted").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dietitians.clear();
              for (DataSnapshot snap : snapshot.getChildren()){
                  Dietitian dietitian = snap.getValue(Dietitian.class);
                  dietitians.add(dietitian);
              }
                adapterDitetitian.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {

            new AlertDialog.Builder(HomeAdminActivity.this)
                    .setTitle("تسجيل خروج")
                    .setMessage("هل تريد تسجيل الخروج؟")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            FirebaseAuth.getInstance().signOut();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeAdminActivity.this);
                            sharedPreferences.edit().clear().commit();
                            Intent intent = new Intent(HomeAdminActivity.this, RegisterAndLoginActivity.class);
                            startActivity(intent);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            {
                                //this is make permition colse
                                //   ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                                finishAffinity();
                            }

                            else
                            {
                                finish();
                            }
                            ///finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("لا", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }
        return super.onOptionsItemSelected(item);
    }
}