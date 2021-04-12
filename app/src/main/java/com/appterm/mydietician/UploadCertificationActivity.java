package com.appterm.mydietician;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UploadCertificationActivity extends AppCompatActivity {

    private Button mCertification;
    private static final int ImageBack = 1;
    private StorageReference folder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_certification);
        mCertification = findViewById(R.id.certification);
        folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        mCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,ImageBack);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageBack) {
            if (resultCode == RESULT_OK){
                Uri imageData = data.getData();
                StorageReference Imagename =  folder.child("image"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                ProgressDialog progressDialog = new ProgressDialog(UploadCertificationActivity.this);
                progressDialog.setMessage("تحميل...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Imagename.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        HashMap hashMap = new HashMap();
                        hashMap.put("imageurl", Imagename.getPath());
                        imagestore.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(UploadCertificationActivity.this,"تم التحميل بنجاح",Toast.LENGTH_SHORT).show();
                               // startActivity(new Intent(getApplicationContext(), HomeDietitianActivity.class));

                                new AlertDialog.Builder(UploadCertificationActivity.this)
                                        .setTitle("تنبية!")
                                        .setMessage("شكرا لتسجيلك معنا في MyDietitian، حسابك تحت المراجعة حاليا ويمكنك تسجيل الدخول بعد التأكد من صلاحية شهادتك.")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation

                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(getApplicationContext(), RegisterAndLoginActivity.class));
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.

                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();



                            }
                        });


                    }
                });
            }
        }
    }
}