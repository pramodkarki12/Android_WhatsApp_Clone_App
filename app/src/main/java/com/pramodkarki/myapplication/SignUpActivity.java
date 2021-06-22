package com.pramodkarki.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pramodkarki.myapplication.databinding.ActivitySignUpBinding;
import com.pramodkarki.myapplication.models.Users;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hide the actionBar
        getSupportActionBar().hide();

        // Firebase Authentication
        auth = FirebaseAuth.getInstance();
        // Real-time database
        database = FirebaseDatabase.getInstance();

        // progress dialog box
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");


        /* When the user click the SignUp button,
           firebase authenticate the email, and password,
           and store them into the database as a new user
          */
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /* When user click on signUp button,
                    it displays the progress dialog box,
                     displaying messages */
                progressDialog.show();

                /*
                 * Create a new account by passing the new user's email address
                 * and password to "createUserWithEmailAndPassword":
                 * */
                auth.createUserWithEmailAndPassword
                        (binding.etEmail.getText().toString(), binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                /* close the progress dialog box */
                                progressDialog.dismiss();

                                /* if user is successful created */
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Users user = new Users(binding.etUsername.getText().toString(),
                                            binding.etEmail.getText().toString(),
                                            binding.etPassword.getText().toString());

                                    // Extract the user id
                                    String id = task.getResult().getUser().getUid();

                                    // Save the user information inside the firebase real-time database
                                    database.getReference().child("Users").child(id).setValue(user);

                                    Toast.makeText(SignUpActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        /* Once the user click on signIn option,
            then redirect into the signIn activity */
        binding.txtClickForSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}