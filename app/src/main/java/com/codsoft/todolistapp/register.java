package com.codsoft.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText name,mail,pwd,cpwd;
    Button register;
    TextView login;
    ProgressBar pbar;

    FirebaseAuth fauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //EditText
        name = findViewById(R.id.fullname);
        mail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        cpwd = findViewById(R.id.cpassword);

        //Btn
        register = findViewById(R.id.registerBtn);
        //TextView
        login = findViewById(R.id.loginBtn);
        //Progress bar
        pbar = findViewById(R.id.progressbar);


        fauth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (register.this,login.class);
                startActivity(intent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = pwd.getText().toString().trim();
                final String email = mail.getText().toString().trim();
                final String fname = name.getText().toString();
                final String cpassword = cpwd.getText().toString();

                if (TextUtils.isEmpty(fname)) {
                    name.setError("Fullname Is Required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mail.setError("Email Is Required");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mail.setError("Email is Invalid");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    pwd.setError("Password Is Required");
                    return;
                }

                if (password.length() < 6) {
                    pwd.setError("Password Must be 6 Characters Long");
                    return;
                }

                if (!password.equals(cpassword)) {
                    pwd.setError("Password And Confirm Password Must be SAME!!!");
                    return;

                }

                pbar.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser fuser = fauth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                //Success (Account Created) Failure (Mail Not Sent)
                                public void onSuccess(Void unused) {
                                    Toast.makeText(register.this, "REGISTRATION SUCCESSFULLY COMPLETED!!!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());

                                }
                            });

                            Intent intent = new Intent(getApplicationContext(), login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(register.this, "ERROR!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
