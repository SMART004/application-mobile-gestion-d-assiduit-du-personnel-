package com.example.AssidApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView admin, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ob = new Intent(getApplicationContext(), SignUp.class);
                ob.putExtra("statut", "admin");
                startActivity(ob);
                finish();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ob = new Intent(getApplicationContext(), SignUp.class);
                ob.putExtra("statut", "staff");
                startActivity(ob);
                finish();
            }
        });
    }
}