package com.appterm.mydietician;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ClientProfileFragment extends Fragment {


    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mWeight;
    private EditText mHeight;
    private EditText mBMI;
    private TextView mTarget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_profile, container, false);
        mName = view.findViewById(R.id.name);
        mEmail = view.findViewById(R.id.email);
        mPassword = view.findViewById(R.id.password);
        mWeight = view.findViewById(R.id.weight);
        mHeight = view.findViewById(R.id.height);
        mBMI = view.findViewById(R.id.BMI);
        mTarget = view.findViewById(R.id.target);

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client client = snapshot.getValue(Client.class);
                mName.setText(client.getName());
                mEmail.setText(client.getEmail());
                mPassword.setText(client.getPassword());
                mWeight.setText(client.getInformation().getWeight());
                mHeight.setText(client.getInformation().getHeight());
                mBMI.setText(Integer.parseInt(client.getInformation().getHeight()) - Integer.parseInt(client.getInformation().getWeight()) + "");
                mTarget.setText(client.getInformation().getLoseORgain() + " "+client.getInformation().getNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }
}