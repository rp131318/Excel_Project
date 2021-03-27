package com.data.android.excelproject;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    // variables declare here bellow.............

    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone, editTextage;
    private TextView editTextcity;
    private ProgressBar progressBar;
    Button button_signin;
    RadioButton select;
    RadioGroup selectgender;
    String name, email, password, age, phone, gender, city;
    private FirebaseAuth mAuth;
    LocationManager locationManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // id defined bellow of all the variables....

        editTextage = findViewById(R.id.age);
        editTextcity = findViewById(R.id.city);
        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextPhone = findViewById(R.id.edit_text_phone);
        button_signin = findViewById(R.id.btsi);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        selectgender = findViewById(R.id.gender);
        mAuth = FirebaseAuth.getInstance();


        // GPS location permission..............
        GrantPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();

        findViewById(R.id.button_register).setOnClickListener(this);

        selectgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {

                select = selectgender.findViewById(i);
                switch (i) {

                    case R.id.male:
                        gender = select.getText().toString();
                        break;

                    case R.id.female:
                        gender = select.getText().toString();
                        break;

                    default:
                }
                sharedPreferences = MainActivity.this.getSharedPreferences("filesper", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("gendersh", gender);
                editor.apply();
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent si = new Intent(MainActivity.this, signin.class);
                startActivity(si);
            }
        });

    }


// taking information of user and storing in database.......................

    private void registerUser() {

        name = editTextName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        age = editTextage.getText().toString().trim();
        city = editTextcity.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        phone = editTextPhone.getEditableText().toString();

        if (!phone.isEmpty() && !email.isEmpty() && !city.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !gender.isEmpty() && !age.isEmpty()) {

            sharedPreferences = MainActivity.this.getSharedPreferences("filesper", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("namesh", name);
            editor.putString("emailsh", email);
            editor.putString("agesh", age);
            editor.putString("citysh", city);
            editor.putString("passwordsh", password);
            editor.putString("phonesh", phone);
            editor.apply();

            Intent num = new Intent(getApplicationContext(), PhoneVerification.class);
//            num.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            num.putExtra("phoneNumber", phone);
            startActivity(num);
        } else {

            if (phone.isEmpty() || phone.length() < 10) {
                editTextPhone.setError("Enter a valid mobile");
                editTextPhone.requestFocus();
                return;
            }


            if (name.isEmpty()) {
                editTextName.setError(getString(R.string.input_error_name));
                editTextName.requestFocus();
                return;
            }

            if (age.isEmpty()) {
                editTextage.setError(getString(R.string.input_error_name));
                editTextage.requestFocus();
                return;
            }

            if (age.length() < 0 || age.length() > 3) {
                editTextage.setError("Enter a Valid Age");
                editTextage.requestFocus();
                return;

            }

            if (email.isEmpty()) {
                editTextEmail.setError(getString(R.string.input_error_email));
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError(getString(R.string.input_error_email_invalid));
                editTextEmail.requestFocus();
                return;
            }


            if (password.isEmpty()) {
                editTextPassword.setError(getString(R.string.input_error_password));
                editTextPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editTextPassword.setError(getString(R.string.input_error_password_length));
                editTextPassword.requestFocus();
                return;
            }

        }
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void locationEnabled() {
        @SuppressLint({"NewApi", "LocalSuppress"}) LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void GrantPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    public void onLocationChanged(Location location) {

        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Log.e("TAG", "onLocationChanged: " + location.getLatitude() + location.getLongitude() + addresses.get(0).getLocality());
            String locality = addresses.get(0).getLocality();
            Log.e("locality", "onLocationChanged: " + locality);
            editTextcity.setText(addresses.get(0).getLocality());

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            Log.e("TAG", "onLocationChanged: " + user);

            String userId = user.getUid();
            Log.e("TAG", "onLocationChanged: " + userId);
            DatabaseReference mRef = database.getReference().child("Users").child(userId);
            mRef.child("city").setValue(editTextcity);

            Log.e("TAG", "onLocationChanged: " + addresses.get(0).getLocality());
            Log.e("TAG", "onLocationChanged: " + editTextcity.getText().toString());


        } catch (Exception e) {

            Log.e("TAG", "onLocationChanged: " + e.getMessage());
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                registerUser();
        }
    }
}
