package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.databinding.ActivitySignInBinding;
import com.hanifsapp.hisabee.localDb.GoogleSignUp;

public class signIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignInBinding binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GoogleSignUp sg = new GoogleSignUp(signIn.this);

        binding.SIGNUINBTNS.setOnClickListener(v -> {
            String email = binding.signInEmail.getText().toString();
            String password = binding.signInpassword.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(signIn.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(signIn.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else{
                sg.signInWithEmailAndPassword(email,password);
            }
        });

        binding.SignUPBtn.setOnClickListener(v -> startActivity(new Intent(this, SignUp.class)));
    }
}