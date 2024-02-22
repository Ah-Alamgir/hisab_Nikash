package com.hanifsapp.hisabee.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.databinding.ActivitySignUpBinding;
import com.hanifsapp.hisabee.firebase_Db.GoogleSignUp;

public class SignUp extends AppCompatActivity {
    GoogleSignUp googleSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        googleSignUp = new GoogleSignUp(SignUp.this);
        binding.progressBar3.setVisibility(View.GONE);

        binding.SIGNUPBTNS.setOnClickListener(v -> {
            String Number = binding.signUpEmail.getText().toString();

            if (Number.isEmpty()) {
                Toast.makeText(SignUp.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }else{
                googleSignUp.sendVerificationCode(Number);
                binding.progressBar3.setVisibility(View.VISIBLE);


            }

        });

    }


}