package com.data.android.excelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ShowSavedDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SavedDataClass savedDataClass;
    DatabaseReference reference;

    List<String> keyName = new ArrayList<>();
    List<String> dateList1 = new ArrayList<String>();
    List<String> yrList1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_data);

        recyclerView = findViewById(R.id.calenderView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = currentFirebaseUser.getUid();


        reference = FirebaseDatabase.getInstance().getReference().child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                yrList1.clear();
                dateList1.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String clubkey = childSnapshot.getKey();
                    StringTokenizer stringTokenizer = new StringTokenizer(clubkey, "~~");
                    yrList1.add(stringTokenizer.nextToken());
                    dateList1.add(stringTokenizer.nextToken());

                }

                Log.e("TAG", "onDataChange:yr " + yrList1);
                Log.e("TAG", "onDataChange:date " + dateList1);

                savedDataClass = new SavedDataClass(dateList1, yrList1);
                recyclerView.setAdapter(savedDataClass);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}