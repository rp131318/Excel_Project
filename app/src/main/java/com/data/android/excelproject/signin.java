package com.data.android.excelproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signin extends AppCompatActivity {

    EditText em, pa;
    Button si;
    TextView reg, fp;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mFirebaseAuth = FirebaseAuth.getInstance();
        em = findViewById(R.id.emailId);
        pa = findViewById(R.id.Password);
        si = findViewById(R.id.sign);
        reg = findViewById(R.id.account);
        fp = findViewById(R.id.forgotpass);


        // forgot password code to ask user to give register email so we can send code from firebase..............
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetmail = new EditText(v.getContext());
                AlertDialog.Builder PasswordResetDialog = new AlertDialog.Builder(v.getContext());
                PasswordResetDialog.setTitle("Reset Password");
                PasswordResetDialog.setMessage("Enter you email to receive reset Link.");
                PasswordResetDialog.setView(resetmail);

                PasswordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extract the email and send reset Link

                        String mail = resetmail.getText().toString();
                        mFirebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(signin.this, "Reset Link sent to your email.", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signin.this, "Error! Reset Link is not sent" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });

                PasswordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog

                    }
                });

                PasswordResetDialog.create().show();


            }
        });

//sign in button click all data will verify from database....................
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = em.getText().toString();
                String pwd = pa.getText().toString();

                if (email.isEmpty()) {
                    em.setError("Email is required");
                    em.requestFocus();
                } else if (pwd.isEmpty()) {
                    pa.setError("Password is required");
                    pa.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(signin.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                Toast.makeText(signin.this, "Error!, Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent ihome = new Intent(signin.this, HomePage.class);
                                startActivity(ihome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(signin.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iregister = new Intent(signin.this, MainActivity.class);
                startActivity(iregister);

            }
        });

    }

    protected void onStart() {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null) {
            Toast.makeText(signin.this, "Sign In successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(signin.this, HomePage.class);
            startActivity(i);
        }

//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }
}