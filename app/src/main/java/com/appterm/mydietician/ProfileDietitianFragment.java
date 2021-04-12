package com.appterm.mydietician;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class ProfileDietitianFragment extends Fragment {

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private ImageView image;
    private Button addCertification;
    private static final int ImageBack = 1;
    private StorageReference folder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_dietitian, container, false);
        folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        mName = view.findViewById(R.id.name);
        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        addCertification = view.findViewById(R.id.addCertification);
        image = view.findViewById(R.id.image);
        addCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,ImageBack);

            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dietitian dietitian = snapshot.getValue(Dietitian.class);
                mName.setText(dietitian.getName());
                mEmail.setText(dietitian.getEmail());
                mPassword.setText(dietitian.getPassword());
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                //  storageReference.child(products.get(position).getImage());

                storageReference.child(dietitian.getImageurl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'

                        // Toast.makeText(context, uri.toString()+"", Toast.LENGTH_SHORT).show();
                        Glide.with(getActivity())
                                .load(uri.toString())
                                .into(image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageBack) {
            if (resultCode == AppCompatActivity.RESULT_OK){
                Uri imageData = data.getData();
                image.setImageURI(imageData);
                StorageReference Imagename =  folder.child("image"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                                Toast.makeText(getActivity(),"تم التحميل بنجاح",Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });
            }
        }
    }
}