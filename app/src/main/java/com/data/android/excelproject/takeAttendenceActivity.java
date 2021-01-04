package com.data.android.excelproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class takeAttendenceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    takeAttendenceAdapter takeAttendenceAdapter;
    EditText editText, yearId;
    List<model> list = new ArrayList<>();
    Button present, absent;
    String date;
    List<String> aa;
    boolean check = false;

    DatabaseReference reference;


    @Override
    public void onBackPressed() {

        if (editText.getText().length() > 0 && yearId.getText().length() > 0 && check) {
            Log.e("TAG", "onBackPressed: ");
            aa = takeAttendenceAdapter.presentAbsentList;
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String id = currentFirebaseUser.getUid();

            Log.e("TAG", "onClick: presentAbsentList " + aa);

            String yrTemp = yearId.getText().toString();
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(new Date());
            reference.child(id).child(yrTemp + "~~" + date).removeValue();


            for (int i = 0; i < aa.size(); i++) {
                Log.e("TAG", "onClick: 1");
                reference.child(id).child(yrTemp + "~~" + date).child(String.valueOf(i + 1)).setValue(aa.get(i).toString());
                Log.e("TAG", "onClick: 1");
            }
            Toast.makeText(this, "Data Saved in Database...", Toast.LENGTH_SHORT).show();
            super.onBackPressed();

        } else {
            super.onBackPressed();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);


        recyclerView = findViewById(R.id.calenderView);
        yearId = findViewById(R.id.yearId);
        editText = findViewById(R.id.numberInput);
        present = findViewById(R.id.allPresentBtn);
        absent = findViewById(R.id.allAbsentBtn);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setVisibility(View.GONE);
        present.setVisibility(View.GONE);
        absent.setVisibility(View.GONE);

        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(new Date());

        reference = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.makeSheetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0 && yearId.getText().length() > 0) {
                    list.clear();
                    String i = editText.getText().toString();
                    int cc = Integer.parseInt(i);

                    for (int ii = 0; ii < cc; ii++) {
                        model model = new model("No Data", yearId);
                        list.add(model);
                    }
                    takeAttendenceAdapter = new takeAttendenceAdapter(list);
                    recyclerView.setAdapter(takeAttendenceAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    absent.setVisibility(View.VISIBLE);
                    present.setVisibility(View.VISIBLE);
                    check = true;
                }
            }
        });


        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0 && yearId.getText().length() > 0) {
                    list.clear();
                    String i = editText.getText().toString();
                    int cc = Integer.parseInt(i);

                    for (int ii = 0; ii < cc; ii++) {
                        model model = new model("Absent", yearId);
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
                if (editText.getText().length() > 0 && yearId.getText().length() > 0) {
                    list.clear();
                    String i = editText.getText().toString();
                    int cc = Integer.parseInt(i);

                    for (int ii = 0; ii < cc; ii++) {
                        model model = new model("Present", yearId);
                        list.add(model);
                    }
                    takeAttendenceAdapter = new takeAttendenceAdapter(list);
                    recyclerView.setAdapter(takeAttendenceAdapter);

                }
            }
        });


    }
}