package com.hanifsapp.hisabee.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.localDb.GoogleSignUp;

public class SignUp extends AppCompatActivity {
    GoogleSignUp googleSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        googleSignUp = new GoogleSignUp(SignUp.this);

        EditText emailTextview =  findViewById(R.id.signUpEmail);
        Button signUpButton = findViewById(R.id.button5);
        signUpButton.setOnClickListener(v -> {
            String email = emailTextview.getText().toString();
            googleSignUp.sendSignInLink(email);

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        googleSignUp.verifySignInLink(getIntent());
    }
}