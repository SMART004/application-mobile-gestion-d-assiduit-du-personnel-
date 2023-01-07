package com.example.AssidApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText useremail, password;
    TextView signup, resetpassword;
    Button connexion;
    FirebaseAuth fAuth;
    String email1, pass;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        useremail = findViewById(R.id.useremail);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        resetpassword = findViewById(R.id.reset);
        connexion = findViewById(R.id.connexion);

        fAuth= FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ob = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(ob);
                finish();
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email1 = useremail.getText().toString().trim();
                pass = password.getText().toString().trim();

                if(TextUtils.isEmpty(email1) || !Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    useremail.setError("email is required or invalid");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    password.setError("password is  too short");
                    return;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                fAuth.signInWithEmailAndPassword(email1,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignIn.this,"Logged is successfuly", Toast.LENGTH_LONG).show();
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String fstatut = snapshot.child(FirebaseAuth.getInstance().getUid()).child("userStatut").getValue(String.class);
                                    if (fstatut.equals("admin")){
                                        startActivity(new Intent(getApplicationContext(), DashboardAdmin.class));
                                        finish();
                                    }else if (fstatut.equals("staff")){
                                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            Toast.makeText(SignIn.this,"Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2 = useremail.getText().toString().trim();

                if(TextUtils.isEmpty(email2) || !Patterns.EMAIL_ADDRESS.matcher(email2).matches()){
                    useremail.setError("email is required or invalid");
                    return;
                }

                fAuth.sendPasswordResetEmail(email2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SignIn.this,"Reset link send to your Email", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    }