package com.data.android.excelproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class takeAttendenceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    takeAttendenceAdapter takeAttendenceAdapter;
    EditText editText;
    List<model> list = new ArrayList<>();
    Button present, absent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);


        recyclerView = findViewById(R.id.calenderView);
        editText = findViewById(R.id.numberInput);
        present = findViewById(R.id.allPresentBtn);
        absent = findViewById(R.id.allAbsentBtn);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setVisibility(View.GONE);
        present.setVisibility(View.GONE);
        absent.setVisibility(View.GONE);


        findViewById(R.id.makeSheetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0) {
                    list.clear();
                    String i = editText.getText().toString();
                    int cc = Integer.parseInt(i);

                    for (int ii = 0; ii < cc; ii++) {
                        model model = new model("No Data", "n");
                        list.add(model);
                    }
                    takeAttendenceAdapter = new takeAttendenceAdapter(list);
                    recyclerView.setAdapter(takeAttendenceAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    absent.setVisibility(View.VISIBLE);
                    present.setVisibility(View.VISIBLE);
                }
            }
        });


        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() != null) {
                    list.clear();
                    String i = editText.getText().toString();
                    int cc = Integer.parseInt(i);

                    for (int ii = 0; ii < cc; ii++) {
                        model model = new model("Absent", "n");
                        list.add(model);
                    }
                    takeAttendenceAdapter = new takeAttendenceAdapter(list);
                    recyclerView.setAdapter(takeAttendenceAdapter);

                }
            }
        });

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() != null) {
                    list.clear();
                    String i = editText.getText().toString();
                    int cc = Integer.parseInt(i);

                    for (int ii = 0; ii < cc; ii++) {
                        model model = new model("Present", "n");
                        list.add(model);
                    }
                    takeAttendenceAdapter = new takeAttendenceAdapter(list);
                    recyclerView.setAdapter(takeAttendenceAdapter);

                }
            }
        });


    }
}