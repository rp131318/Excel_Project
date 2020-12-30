package com.data.android.excelproject;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.PathUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class completedActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 100;
    String[] data;
    RecyclerView recyclerView;
    calenderAdapter calenderAdapter;
    LinearLayout detail_msg;
    CardView cardView;
//    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
//            btn21, btn22, btn23, btn0;

    ArrayList number = new ArrayList<>();
    ArrayList present = new ArrayList<>();
    List<model> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        Dexter.withActivity(this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                Log.e("TAG", "onPermissionsChecked: " + report.toString());
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

        recyclerView = findViewById(R.id.calenderView);
        detail_msg = findViewById(R.id.detail_msg);
        detail_msg.setVisibility(View.GONE);
        cardView = findViewById(R.id.cardLayout);
        cardView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));


        findViewById(R.id.chooseFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// choose file from internal storage calling function.............
                chooseFile();
            }
        });


        findViewById(R.id.takeAttendence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(completedActivity.this, takeAttendenceActivity.class));
            }
        });

        findViewById(R.id.savedData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(completedActivity.this, ShowSavedDataActivity.class));

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        // Get the Uri of the selected file
                        Uri uri = data.getData();
                        Log.e("TAG", "File Uri: " + uri.toString());

                        extractData(uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("TAG", "onActivityResult: " + e.toString());
                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //taking data from .csv file
    private void extractData(Uri path) {


        try {

            InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String csvLine;
            number.clear();
            present.clear();
            list.clear();

            while ((csvLine = reader.readLine()) != null) {
                data = csvLine.split(",");
                number.add(data[0]);
                present.add(data[1]);
                try {
                    Log.e("TAG", "onCreate: " + data[0] + "---" + data[1]);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            for (int i = 0; i < number.size(); i++) {
                model model = new model(number.get(i), present.get(i));
                list.add(model);
            }


            calenderAdapter = new calenderAdapter(list);
            recyclerView.setAdapter(calenderAdapter);
            cardView.setVisibility(View.VISIBLE);
            detail_msg.setVisibility(View.VISIBLE);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", "FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "IOException: " + e.toString());
        }


    }

    // choose file .csv from internal storage...........
    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select File"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // taking path of file..............................
    public static String TakePath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
