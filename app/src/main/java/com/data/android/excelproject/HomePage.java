package com.data.android.excelproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press home to exit...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        findViewById(R.id.accountBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, AccountProfile.class));
            }
        });

        findViewById(R.id.chooseFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, completedActivity.class));
            }
        });


        findViewById(R.id.takeAttendence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, SelectYearActivity.class));
            }
        });

        findViewById(R.id.savedData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePage.this, ShowSavedDataActivity.class));

            }
        });

        findViewById(R.id.timeTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePage.this, timeTableActivity.class));

            }
        });

        findViewById(R.id.lessAttendence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                builder.setTitle("Select Student Semester");

                String[] animals = {"Semester 1", "Semester 2", "Semester 3", "Semester 4",
                        "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(HomePage.this, lessAttendanceActivity.class)
                                .putExtra("semesterStudent", String.valueOf(which + 1)));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

//                startActivity(new Intent(HomePage.this, ShowSavedDataActivity.class));

            }
        });


    }
}