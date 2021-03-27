package com.data.android.excelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class lessAttendanceActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    SavedDataClass2 savedDataClass;
    String sem;
    List<String> studentData = new ArrayList<String>();
    List<String> allStudentData = new ArrayList<String>();
    String finalSem;
    ProgressBar progressBar;
     String id;

    int numberOfStudents = 0;
    List<String> presentAbsentDataPercentage = new ArrayList<String>();
    List<String> presentAbsentDataPercentageData = new ArrayList<String>();
    List<String> studentEnNumber = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_less_attendance);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        id = currentFirebaseUser.getUid();

        recyclerView = findViewById(R.id.calenderView);
        progressBar = findViewById(R.id.loading);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        sem = bundle.getString("semesterStudent");
        convertSem(sem);

        reference = FirebaseDatabase.getInstance().getReference().child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentData.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String clubkey = childSnapshot.getKey();
                    if (clubkey.contains(finalSem)) {
                        studentData.add(clubkey);
                    }
                }
                Log.e("Student Data", "Student Data: " + studentData.toString());
                if (studentData.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(lessAttendanceActivity.this);
                    builder.setTitle("Select Student Semester");
                    builder.setMessage("No Data Found...");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onBackPressed();
                        }
                    });
                    builder.setCancelable(false);
                    progressBar.setVisibility(View.GONE);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                FirebaseDatabase.getInstance().getReference().child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("TAG", "onDataChange: Key " + snapshot.getKey().length());
                        Log.e("TAG", "onDataChange: Value " + snapshot.getValue());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



//                for (int i = 0; i < studentData.size(); i++) {
//                    FirebaseDatabase.getInstance().getReference().child(id).child(studentData.get(i)).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                                int keyLength = childSnapshot.getKey().length();
//                                Log.e("TAG", "keyLength: " + keyLength );
//
//                            }
//
//                            Log.e("TAG", "keyLength: Out " + snapshot.getKey().length());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                }

                allStudentData.clear();
                for (int i = 0; i < studentData.size(); i++) {


//                    Log.e("TAG", "onDataChange: " + studentData.get(i));
                    final int finalI = i;
                    FirebaseDatabase.getInstance().getReference().child(id).child(studentData.get(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String clubkey = childSnapshot.getValue().toString();
                                allStudentData.add(clubkey);
                            }
                            if (finalI == studentData.size() - 1) {
                                Log.e("Student", "onDataChange: " + allStudentData);
                                makePercentageList();
                            }
//                            Log.e("Student", "onDataChange: " + allStudentData);
                            allStudentData.add("Student Day " + String.valueOf(finalI + 1));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    Log.e("all Student Data", "all Student Data ::" + allStudentData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void makePercentageList() {
        try {
            presentAbsentDataPercentageData.clear();
            numberOfStudents = allStudentData.indexOf("Student Day 1");
            Log.e("TAG", "makePercentageList: " + numberOfStudents);


            if (numberOfStudents > 0) {

                for (int i = 0; i < numberOfStudents; i++) {

                    int baseValue = 100 / studentData.size();
                    presentAbsentDataPercentage.clear();
                    int percentage = 0;
                    for (int j = 0; j < studentData.size(); j++) {
                        presentAbsentDataPercentage.add(allStudentData.get(i + (j * numberOfStudents)));
                    }
                    for (int k = 0; k < studentData.size(); k++) {
                        if (presentAbsentDataPercentage.get(k).equals("Present")) {
                            percentage = percentage + baseValue;
                        }
                    }
                    Log.e("TAG", "makePercentageList: index " + i);
                    if (percentage < 75) {
                        Log.e("TAG", "makePercentageList: Added " + percentage);
                        presentAbsentDataPercentageData.add("Attendance : " + String.valueOf(percentage) + "%");
                        if (i < 10) {
                            studentEnNumber.add("16009011100" + String.valueOf(i + 1));

                        } else {
                            studentEnNumber.add("1600901110" + String.valueOf(i + 1));
                        }
                    }

                }
                Log.e("TAG", "makePercentageList: " + presentAbsentDataPercentageData);

                savedDataClass = new SavedDataClass2(studentEnNumber, presentAbsentDataPercentageData);
                recyclerView.setAdapter(savedDataClass);
                progressBar.setVisibility(View.GONE);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(lessAttendanceActivity.this);
                builder.setTitle("Select Student Semester");
                builder.setMessage("We need at least two sheet to give attendance percentage ");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                });
                builder.setCancelable(false);
                progressBar.setVisibility(View.GONE);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } catch (Exception e) {
            Log.e("TAG", "Exception: " + e.toString());
        }
    }

    private void convertSem(String sem) {
        switch (sem) {
            case "1":
                finalSem = "1st Semester";
                break;
            case "2":
                finalSem = "2nd Semester";
                break;
            case "3":
                finalSem = "3rd Semester";
                break;
            case "4":
                finalSem = "4th Semester";
                break;
            case "5":
                finalSem = "5th Semester";
                break;
            case "6":
                finalSem = "6th Semester";
                break;
            case "7":
                finalSem = "7th Semester";
                break;
            case "8":
                finalSem = "8th Semester";
                break;
        }

    }
}