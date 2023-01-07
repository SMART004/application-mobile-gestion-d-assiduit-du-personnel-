package com.example.AssidApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Dashboard extends AppCompatActivity {
    ImageView qrcode;
    Button logout, profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        logout = findViewById(R.id.logout);
        profil = findViewById(R.id.profil);
        qrcode = findViewById(R.id.qrcode);

        Query databaseReference = FirebaseDatabase.getInstance().getReference("users").orderByChild("userID").equalTo(FirebaseAuth.getInstance().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String uid = snapshot.getKey();

                    if (!uid.equals(FirebaseAuth.getInstance().getUid())){

                       String name = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userName").getValue(String.class);
                       String email = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userEmail").getValue(String.class);
                       String phone = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userPhone").getValue(String.class);


                        //Toast.makeText(Dashboard.this, "Name: "+name+"\n"+"Email: "+email+"\n"+"Phone: "+phone, Toast.LENGTH_LONG).show();

                       MultiFormatWriter writer = new MultiFormatWriter();
                        try{
                            BitMatrix matrix = writer.encode("Name: "+name+"\n"+"Email: "+email+"\n"+"Phone: "+phone, BarcodeFormat.QR_CODE, 800, 800);

                            BarcodeEncoder encoder = new BarcodeEncoder();
                            Bitmap bitmap = encoder.createBitmap(matrix);
                            qrcode.setImageBitmap(bitmap);

                        }catch (WriterException e){
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(Dashboard.this, "ID does no exist", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Dashboard.this, "data does no exist", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(Dashboard.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        //userModel usermodel = new userModel(FirebaseAuth.getInstance().getUid(), s.user, s.email1, s.pass, s.phone1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), SignIn.class));
                finish();
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profil.class));
            }
        });
    }
}