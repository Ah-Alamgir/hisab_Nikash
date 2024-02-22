package com.hanifsapp.hisabee.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.databinding.ActivitySignInBinding;
import com.hanifsapp.hisabee.firebase_Db.GoogleSignUp;

public class signIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignInBinding binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar4.setVisibility(View.GONE);

        binding.SIGNUINBTNS.setOnClickListener(v -> {
            String Number = binding.signInEmail.getText().toString();
            if (Number.isEmpty()) {
                Toast.makeText(signIn.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else{
                GoogleSignUp.signInWithVerificationCode(Number);
                binding.progressBar4.setVisibility(View.VISIBLE);

            }



        });




    }
}