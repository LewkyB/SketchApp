package com.example.jraw_test_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private TextInputLayout editTextEmail, editTextPassword;

    private Button registerButton;
    private Button signinButton;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "LoginFragment onCreateView()");

        View view = inflater.inflate(R.layout.activity_login, container, false);

        editTextEmail = view.findViewById(R.id.edit_register_email);
        editTextPassword = view.findViewById(R.id.edit_register_password);

        registerButton = (Button) view.findViewById(R.id.registerUser_button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        signinButton = (Button) view.findViewById(R.id.registerUser_button_signin);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "LoginFragment onCreate()");
        mAuth = FirebaseAuth.getInstance(); // start FirebaseAuth
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "LoginFragment onStart()");
        FirebaseUser currentUser = mAuth.getCurrentUser(); // check to see if logged in
    }

    private void registerUser() {
        Log.d(TAG, "LoginFragment registerUser()");

        String email = editTextEmail.getEditText().getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();

        Log.d(TAG, "registerUser: " + email);

        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Firebase createUserWithEmailAndPassword:success");

                            // create UID in database and put user's email into UID as child
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email")
                                    .setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Firebase createUserWithEmailAndPassword:success");
                                        Toast.makeText(LoginActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG, "FirebaseDatabase write createUserWithEmailandPassword:failure", task.getException());
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {

        String email = editTextEmail.getEditText().getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();

        Log.d(TAG, "signIn: " + email);

        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    Toast.makeText(LoginActivity.this, "Welcome to SketchApp", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                } else {
                    Log.d(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "signin failure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {

        Log.d(TAG, "validating form");
        String email = editTextEmail.getEditText().getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();

        boolean valid = true;

        if (email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            valid = false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            valid = false;
        }
        if (password.length() < 6) {
            editTextPassword.setError("more than 6 characters required");
            editTextPassword.requestFocus();
            valid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Provide valid email");
            editTextEmail.requestFocus();
            valid = false;
        }

        return valid;
    }

}