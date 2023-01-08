package com.example.AssidApp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardAdmin extends AppCompatActivity {
    Button scan, histo, profil, group, logout;
    DatabaseReference mhist;
    String date, resultat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        scan = findViewById(R.id.scanner);
        histo = findViewById(R.id.historique);
        profil = findViewById(R.id.profil1);
        group = findViewById(R.id.group);
        logout = findViewById(R.id.logout1);
        mhist = FirebaseDatabase.getInstance().getReference("save");

       histo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), historique.class));
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profil.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), SignIn.class));
                finish();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setBeepEnabled(true);
                options.setOrientationLocked(true);
                options.setCaptureActivity(CaptureAct.class);
                barLauncher.launch(options);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), recycler.class));
            }
        });
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null) {
            resultat = result.getContents();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat stext = new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
            date = stext.format(cal.getTime());

            savescan scanner = new savescan(resultat, date);
            mhist.push().setValue(scanner);
            startActivity(new Intent(getApplicationContext(), historique.class));

        }

    });
}