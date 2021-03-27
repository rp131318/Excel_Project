package com.data.android.excelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class AttendenceDataFirebaseActivity extends AppCompatActivity {

    String date, yr;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<String> presentAbsentArrayList = new ArrayList<>();
    List<model> list = new ArrayList<>();
    takeAttendenceAdapter takeAttendenceAdapter;
    DatabaseReference reference;
    TextView dateyrText;


    @Override
    public void onBackPressed() {

        List<String> aa = takeAttendenceAdapter.presentAbsentList;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = currentFirebaseUser.getUid();

        Log.e("TAG", "onClick: presentAbsentList " + aa);

//                reference.child(id).child(yr + "~~" + date).removeValue();


        for (int i = 0; i < aa.size(); i++) {
            Log.e("TAG", "onClick: 1");
            reference.child(id).child(yr + "~~" + date).child(String.valueOf(i + 1)).setValue(aa.get(i).toString());
            Log.e("TAG", "onClick: 1");
        }

//        Toast.makeText(AttendenceDataFirebaseActivity.this, "Data Changed...", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_data_firebase);
        recyclerView = findViewById(R.id.calenderView);
        dateyrText = findViewById(R.id.dateYrText);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        list.clear();



        Bundle bundle = getIntent().getExtras();
        reference = FirebaseDatabase.getInstance().getReference();
        assert bundle != null;
        date = bundle.getString("date");
        yr = bundle.getString("yr");

        dateyrText.setText("Date : " + date + " \nSem : " + yr);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = currentFirebaseUser.getUid();

        findViewById(R.id.editDataBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAttendenceAdapter.isClickable = true;
            }
        });
        TooltipCompat.setTooltipText(findViewById(R.id.editDataBtn), "Click to edit");

        databaseReference = FirebaseDatabase.getInstance().getReference().child(id).child(yr + "~~" + date);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presentAbsentArrayList.clear();
                list.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String clubkey = childSnapshot.getValue().toString();
                    presentAbsentArrayList.add(clubkey);
                }

                for (int ii = 0; ii < presentAbsentArrayList.size(); ii++) {
                    model model = new model(presentAbsentArrayList.get(ii), yr);
                    list.add(model);
                }
                takeAttendenceAdapter = new takeAttendenceAdapter(list);
                recyclerView.setAdapter(takeAttendenceAdapter);
                takeAttendenceAdapter.isClickable = false;

                Log.e("TAG", "onDataChange: " + presentAbsentArrayList);
                Log.e("TAG", "onDataChange: " + list);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}