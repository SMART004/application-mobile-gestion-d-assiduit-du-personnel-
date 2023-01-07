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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText Username, email, password, phone;
    Button submit;
    TextView signin;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    String user, email1, pass, phone1, statut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Username = findViewById(R.id.last);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.tel);
        submit = findViewById(R.id.Submit);
        signin = findViewById(R.id.signin);

        fAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email1 = email.getText().toString();
                pass = password.getText().toString();
                user = Username.getText().toString();
                phone1 = phone.getText().toString();

                Bundle extra = getIntent().getExtras();
                statut = extra.getString("statut");

                if(TextUtils.isEmpty(user)){
                    Username.setError("Last name is required");
                    return;
                }

                if(TextUtils.isEmpty(email1) || !Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    email.setError("email is required or invalid");
                    return;
                }

                if(TextUtils.isEmpty(phone1) || !Patterns.PHONE.matcher(phone1).matches()){
                    phone.setError("phone number is required or invalid");
                    return;
                }
                if(phone.length()<9){
                    phone.setError("phone number is  too short");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Password is required");
                    return;
                }
                if(Username.length()<3){
                    Username.setError("Username is  too short");
                    return;
                }

                if(password.length()<6){
                    password.setError("password is  too short");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email1,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(user).build();
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            firebaseUser.updateProfile(userProfileChangeRequest);
                            Toast.makeText(SignUp.this,"User created", Toast.LENGTH_LONG).show();
                            userModel usermodel = new userModel(FirebaseAuth.getInstance().getUid(), user, email1, pass, phone1, statut);
                            databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(usermodel);
                            startActivity(new Intent(getApplicationContext(), SignIn.class));
                            finish();
                        }else{
                            Toast.makeText(SignUp.this,"Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

   /*
    boolean isEmpty(EditText text){
        CharSequence a = text.getText().toString();
        return TextUtils.isEmpty(a);
    }*/

    }
