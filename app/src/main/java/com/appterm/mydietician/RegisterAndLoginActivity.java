package com.appterm.mydietician;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.appterm.mydietician.Notification.NotificationReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class RegisterAndLoginActivity extends AppCompatActivity {

    private TextView mTextView5;
    private ImageView mImageView;
    private Button mLoginChose;
    private Button mRegisterChose;
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private RadioButton mFemale;
    private RadioButton mMale;
    private RadioGroup mGender;
    private Button mRegisterButton;
    private Button mLoginButton;

    String gender = "";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference referenceUsers;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onStart() {
        super.onStart();
        myAlarm();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getString("type","").equals("مستخدم")){
            Intent intent = new Intent(RegisterAndLoginActivity.this,HomeClientActivity.class);
            startActivity(intent);
            finish();
        }else if(sharedPreferences.getString("type","").equals("أخصائي تغذية")){
            Intent intent = new Intent(RegisterAndLoginActivity.this,HomeDietitianActivity.class);
            startActivity(intent);
            finish();
        }else if(sharedPreferences.getString("type","").equals("مدير")){
            Intent intent = new Intent(RegisterAndLoginActivity.this,HomeAdminActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        referenceUsers = database.getReference("users");
        mTextView5 = findViewById(R.id.textView5);
        mImageView = findViewById(R.id.imageView);
        mLoginChose = findViewById(R.id.loginChose);
        mRegisterChose = findViewById(R.id.registerChose);
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mFemale = findViewById(R.id.female);
        mMale = findViewById(R.id.male);
        mGender = findViewById(R.id.gender);
        mRegisterButton = findViewById(R.id.registerButton);
        mLoginButton = findViewById(R.id.loginButton);
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male:
                        gender = "ذكر";
                        break;
                    case R.id.female:
                        gender = "انثى";
                        break;
                }
            }
        });
        mLoginChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender.setVisibility(View.GONE);
                mName.setVisibility(View.GONE);
                mRegisterButton.setVisibility(View.GONE);
                mLoginButton.setVisibility(View.VISIBLE);

               // mLoginButton.getBackground().setTint();
                ViewCompat.setBackgroundTintList(
                        mLoginChose,
                        ColorStateList.valueOf(getResources().getColor(R.color.white)));
                ViewCompat.setBackgroundTintList(
                        mRegisterChose,
                        ColorStateList.valueOf(getResources().getColor(R.color.chose)));

               // mLoginButton.setBackgroundTintList(getResources().getColorStateList(R.color.my_color));

                //  mLoginButton.setBackgroundTintList(ColorStateList.valueOf(getr.getColor(R.id.blue_100)));
            }
        });
        mRegisterChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender.setVisibility(View.VISIBLE);
                mName.setVisibility(View.VISIBLE);
                mRegisterButton.setVisibility(View.VISIBLE);
                mLoginButton.setVisibility(View.GONE);
                ViewCompat.setBackgroundTintList(
                        mRegisterChose,
                        ColorStateList.valueOf(getResources().getColor(R.color.white)));
                ViewCompat.setBackgroundTintList(
                        mLoginChose,
                        ColorStateList.valueOf(getResources().getColor(R.color.chose)));
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mName.getText())){
                    mName.setError("اسم المستخدم مطلوب!");
                    mName.requestFocus();
                }else if(TextUtils.isEmpty(mEmail.getText())){
                    mEmail.setError("البريد الإلكتروني مطلوب!");
                    mEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches()) {
                    mEmail.setError("الرجاء إخال بريد إلكتروني صحيح!");
                    mEmail.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(mPassword.getText())){
                    mPassword.setError("كلمة المرور مطلوبه!");
                    mPassword.requestFocus();
                }else if(mPassword.getText().length() < 6){
                    mPassword.setError("كلمة المرور مطلوبه ستة حروف او ارقام!");
                    mPassword.requestFocus();
                }else if (TextUtils.isEmpty(gender)){
                    Toast.makeText(RegisterAndLoginActivity.this, "الرجاء اختيار جنسك", Toast.LENGTH_SHORT).show();
                }else{
                    ProgressDialog progressDialog = new ProgressDialog(RegisterAndLoginActivity.this);
                    progressDialog.setMessage("تحميل...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user = new User();
                                user.setUID(mAuth.getCurrentUser().getUid());
                                user.setName(mName.getText().toString());
                                user.setEmail(mEmail.getText().toString());
                                user.setPassword(mPassword.getText().toString());
                                user.setGender(gender);
                                user.setType("");
                                referenceUsers.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        if(task.isSuccessful()){

                                            Toast.makeText(RegisterAndLoginActivity.this, "تم إنشاء مستخدم بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterAndLoginActivity.this,SelectRegisterTypeActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(RegisterAndLoginActivity.this,""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{

                            }
                        }
                    });


                }

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEmail.getText())){
                    mEmail.setError("البريد الإلكتروني مطلوب!");
                    mEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches()) {
                    mEmail.setError("الرجاء إخال بريد إلكتروني صحيح!");
                    mEmail.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(mPassword.getText())){
                    mPassword.setError("كلمة المرور مطلوبه!");
                    mPassword.requestFocus();
                }else{
                    ProgressDialog progressDialog = new ProgressDialog(RegisterAndLoginActivity.this);
                    progressDialog.setMessage("تحميل...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                referenceUsers.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        progressDialog.dismiss();
                                        User user = snapshot.getValue(User.class);

                                        if(user.getType().equals("مستخدم")){
                                            editor.putString("type",user.getType());
                                            editor.commit();
                                            Intent intent = new Intent(RegisterAndLoginActivity.this,HomeClientActivity.class);
                                            startActivity(intent);
                                        }else if(user.getType().equals("أخصائي تغذية")){
                                            editor.putString("type",user.getType());
                                            editor.commit();
                                            Intent intent = new Intent(RegisterAndLoginActivity.this,HomeDietitanFragment.class);
                                            startActivity(intent);
                                        }else {
                                            editor.putString("type","مدير");
                                            editor.commit();
                                            Intent intent = new Intent(RegisterAndLoginActivity.this,HomeAdminActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Intent intent = new Intent();
                            }else{
                                Log.e("TTTT",task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

    }

    public void myAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 47);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
//https://stackoverflow.com/questions/33055129/how-to-show-a-notification-everyday-at-a-certain-time-even-when-the-app-is-close
    }
}