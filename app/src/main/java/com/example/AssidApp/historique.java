package com.example.AssidApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historique extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<savescan> arrayList;
    scanAdapter scanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        recyclerView = (RecyclerView) findViewById(R.id.hist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<savescan>();

        databaseReference = FirebaseDatabase.getInstance().getReference("save");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    savescan s = dataSnapshot.getValue(savescan.class);
                    arrayList.add(s);
                }
                scanAdapter = new scanAdapter(historique.this, arrayList);
                recyclerView.setAdapter(scanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(historique.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}