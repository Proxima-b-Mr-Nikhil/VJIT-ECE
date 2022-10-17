package com.nikhil.vjitece;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    private EditText inputEmail, inputPassword,fname,froll,fphone;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Spinner fyear,fsection;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        fname = (EditText) findViewById(R.id.name);
        froll = (EditText) findViewById(R.id.roll);
        fyear = (Spinner) findViewById(R.id.year);
        fsection = (Spinner) findViewById(R.id.section);
        fphone = (EditText) findViewById(R.id.phone);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        String[] year = new String[] {
                "1","2","3","4"

        };

        ArrayAdapter<String> yea = new ArrayAdapter<String>(register.this,
                android.R.layout.simple_spinner_item, year);
        yea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fyear.setAdapter(yea);

        String[] section = new String[] {
                "A","B","C","D"

        };

        ArrayAdapter<String> sec = new ArrayAdapter<String>(register.this,
                android.R.layout.simple_spinner_item, section);
        sec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fsection.setAdapter(sec);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                   inputEmail.setError("Enter email address!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Enter password!");
                    return;
                }
                if (fname.getText().toString().isEmpty()) {
                    fname.setError("Enter Full Name!");
                    return;
                }
                if (froll.getText().toString().isEmpty()) {
                    froll.setError("Enter roll number!");
                    return;
                }
                if (fname.getText().toString().isEmpty()) {
                    fname.setError("Enter Full Name!");
                    return;
                }
                if (fyear.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(register.this, "Selet Year",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fsection.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(register.this, "Selet Section",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (fphone.getText().toString().isEmpty()) {
                    fphone.setError("invalid!");
                    return;
                }
                if (fphone.length()!=10){
                    fphone.setError("invalid!");
                    return;
                }
                if (password.length() < 6) {
                   inputPassword.setError("Password too short, enter minimum 6 characters!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                } else {
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    assert firebaseUser != null;
                                    String userid = firebaseUser.getUid();
                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                   String name= fname.getText().toString();
                                    String roll= froll.getText().toString();
                                    String year= fyear.getSelectedItem().toString();
                                    String section= fsection.getSelectedItem().toString();
                                    String phone= fphone.getText().toString();


                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("id", userid);
                                    hashMap.put("email", email);
                                    hashMap.put("status", "offline");
                                    hashMap.put("name", name);
                                    hashMap.put("roll", roll);
                                    hashMap.put("year", year);
                                    hashMap.put("section", section);
                                    hashMap.put("phone", phone);
                                    hashMap.put("verification","unverified");
                                    hashMap.put("imageurl", "default");


                                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(register.this, "Authentication completed", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(register.this, MainActivity.class));
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}