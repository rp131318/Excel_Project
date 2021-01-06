package com.data.android.excelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID = "";
    private ProgressBar progressBar;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        logout = findViewById(R.id.save);
        progressBar = findViewById(R.id.profile_progress);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent imain = new Intent(AccountProfile.this, signin.class);
                imain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(imain);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();


        reference = FirebaseDatabase.getInstance().getReference("Users");


        final TextView nametext = (TextView) findViewById(R.id.namexml);
        final TextView emailtext = (TextView) findViewById(R.id.emailxml);
        final TextView numbertext = (TextView) findViewById(R.id.phonexml);
        final TextView agetext = (TextView) findViewById(R.id.age);
        final TextView citytext = (TextView) findViewById(R.id.city);
        final TextView gendertext = (TextView) findViewById(R.id.gender);

        final EditText editText = new EditText(this);
        final EditText editText1 = new EditText(this);


        nametext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AccountProfile.this)
                        .setTitle("Change User Name")
                        .setNegativeButton(android.R.string.no, null)
                        .setView(editText)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                if (editText.getText().length() > 0) {

                                    reference.child(userID).child("name").setValue(editText.getText().toString());
                                    nametext.setText(editText.getText().toString());


                                }
                                ((ViewGroup) editText.getParent()).removeView(editText);
                            }
                        }).create().show();
            }
        });

        agetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(AccountProfile.this)
                        .setTitle("Change User Age")
                        .setNegativeButton(android.R.string.no, null)
                        .setView(editText1)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                if (editText1.getText().length() > 0) {

                                    reference.child(userID).child("age").setValue(editText1.getText().toString());
                                    agetext.setText(editText1.getText().toString());


                                }
                                ((ViewGroup) editText1.getParent()).removeView(editText1);
                            }
                        }).create().show();

            }
        });


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String username = userProfile.name;
                    String useremail = userProfile.email;
                    String userphone = userProfile.phone;
                    String userage = userProfile.age;
                    String usercity = userProfile.city;
                    String usergender = userProfile.gender;

                    nametext.setText(username);
                    emailtext.setText(useremail);
                    numbertext.setText(userphone);
                    agetext.setText(userage);
                    citytext.setText(usercity);
                    gendertext.setText(usergender);
                    progressBar.setVisibility(View.GONE);

//                    Log.e("namevalu", String.valueOf(nametext));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountProfile.this, "Error! check your network connectivity", Toast.LENGTH_LONG).show();
            }
        });


    }
}