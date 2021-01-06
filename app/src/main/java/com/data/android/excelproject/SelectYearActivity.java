package com.data.android.excelproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class SelectYearActivity extends AppCompatActivity {

//    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);


//        Bundle bundle = getIntent().getExtras();
//        assert bundle != null;
//        check = bundle.getInt("check");

        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (check == 1) {

                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "1st Semester"));

//                } else if (check == 2) {
//                    startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
//                            .putExtra("sem", "1st Semester"));
//
//                }


            }
        });

        findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "2nd Semester"));
            }
        });

        findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "3rd Semester"));
            }
        });

        findViewById(R.id.four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "4th Semester"));
            }
        });

        findViewById(R.id.five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "5th Semester"));
            }
        });

        findViewById(R.id.six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "6th Semester"));
            }
        });

        findViewById(R.id.seven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "7th Semester"));
            }
        });

        findViewById(R.id.eight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYearActivity.this, takeAttendenceActivity.class)
                        .putExtra("sem", "8th Semester"));
            }
        });


    }
}