package com.example.AssidApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class profil extends AppCompatActivity {
    TextView name, phone, email, statut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.useEmail);
        statut = findViewById(R.id.statut);

        Query databaseReference = FirebaseDatabase.getInstance().getReference("users").orderByChild("userID").equalTo(FirebaseAuth.getInstance().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String uid = snapshot.getKey();

                    if (!uid.equals(FirebaseAuth.getInstance().getUid())){

                        String name1 = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userName").getValue(String.class);
                        String email1 = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userEmail").getValue(String.class);
                        String phone1 = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userPhone").getValue(String.class);
                        String statut1 = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userStatut").getValue(String.class);

                        name.setText("Name: "+name1);
                        email.setText("Email: "+email1);
                        phone.setText("Phone: "+phone1);
                        statut.setText("Statut: "+statut1);
                    }else {
                        Toast.makeText(profil.this, "ID does no exist", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(profil.this, "data does no exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profil.this, "Error", Toast.LENGTH_LONG).show();

            }
        });
    }
}