package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.hanifsapp.hisabee.databinding.ActivitySignUpBinding;
import com.hanifsapp.hisabee.localDb.GoogleSignUp;

public class SignUp extends AppCompatActivity {
    GoogleSignUp googleSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        googleSignUp = new GoogleSignUp(SignUp.this);


        binding.SIGNUPBTNS.setOnClickListener(v -> {
            String email = binding.signUpEmail.getText().toString();
            String password = binding.signUppassword.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(SignUp.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(SignUp.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else{
                googleSignUp.createUserWithEmailAndPassword(email, password);
            }

        });


        binding.SignIn.setOnClickListener(v -> startActivity(new Intent(SignUp.this, signIn.class)));

    }


}