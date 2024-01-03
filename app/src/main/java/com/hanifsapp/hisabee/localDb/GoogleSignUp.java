package com.hanifsapp.hisabee.localDb;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hanifsapp.hisabee.activity.homePage;

import java.util.concurrent.Executor;


public class GoogleSignUp {
    Activity activity;
    private FirebaseAuth mAuth;

    public GoogleSignUp(Activity ac) {
        activity = ac;
    }


    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        activity.startActivity(new Intent(activity, homePage.class));
                    } else {

                        Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void signInWithEmailAndPassword(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                        activity.startActivity(new Intent(activity, homePage.class));
                    } else {
                        Toast.makeText(activity, "signInWithEmail:failure", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void forgetPassword(String email){
        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener( task -> Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show());
    }


}
