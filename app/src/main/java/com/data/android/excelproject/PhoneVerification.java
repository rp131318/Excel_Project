package com.data.android.excelproject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {

    String verificationId;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    String name, email, city , phone, age, gender, password,codenum;
    EditText numberentered;
    Button verify;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences("filesper", MODE_PRIVATE);

        phone = sharedPreferences.getString("phonesh", "");
        name = sharedPreferences.getString("namesh", "");
        email = sharedPreferences.getString("emailsh", "");
        city = sharedPreferences.getString("citysh", "");
        age = sharedPreferences.getString("agesh", "");
        gender = sharedPreferences.getString("gendersh", "");
        password = sharedPreferences.getString("passwordsh", "");


        progressBar = findViewById(R.id.progressbar);
        verify = findViewById(R.id.verify_btn);
        numberentered = findViewById(R.id.verification_code_entered_by_user);


        String PhoneNum = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(PhoneNum);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codenum = numberentered.getText().toString();

                if (codenum.isEmpty() || codenum.length() < 6) {
                    numberentered.setError("Enter code...");
                    numberentered.requestFocus();
                    return;
                }
                verifyCode(codenum);

            }
        });

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to login page? You will loose all your data.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent back = new Intent(PhoneVerification.this, MainActivity.class);
                        startActivity(back);
                    }
                }).create().show();
    }


    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeByUser);
        signInWithCredential(credential);
    }

//    private void linkPhoneAuthToCurrentUser(PhoneAuthCredential credential) {
//        mAuth.getInstance().getCurrentUser().linkWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//
////                            Toast.makeText(PhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                            //link auth success update ui
//                        } else {
//                            Toast.makeText(PhoneVerification.this, "Enter correct phone number ", Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//                });
//    }
// taking otp and verifying...................
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneVerification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            emailauth();
                            auth();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

//                            Toast.makeText(PhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(PhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void emailauth() {

        AuthCredential credentialem = EmailAuthProvider.getCredential(email, password);

        mAuth.getCurrentUser().linkWithCredential(credentialem)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
//                            Toast.makeText(PhoneVerification.this, "step 1 pass", Toast.LENGTH_LONG).show();
                            Log.e("TAG", "onComplete: step 1 pass " );
                        }
                        else {

//                            Toast.makeText(PhoneVerification.this, "step 1 fail", Toast.LENGTH_LONG).show();
                            Log.e("TAG", "onComplete: step 1 fail " );

                        }
                    }

                });
    }

    private void auth(){

        Toast.makeText(PhoneVerification.this, "step1", Toast.LENGTH_SHORT).show();
        User user = new User(
                name,
                email,
                phone,
                age,
                city,
                gender
        );
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    Toast.makeText(PhoneVerification.this, "Try Again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PhoneVerification.this, "Succesfull", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendVerificationCode(String PhoneNum) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + PhoneNum,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }



        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            signInWithCredential(credential);
            String code = credential.getSmsCode();
            Toast.makeText(PhoneVerification.this, code, Toast.LENGTH_LONG).show();

            if (code != null) {
                numberentered.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(PhoneVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}