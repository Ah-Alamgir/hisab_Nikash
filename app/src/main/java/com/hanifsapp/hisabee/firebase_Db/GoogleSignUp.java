package com.hanifsapp.hisabee.firebase_Db;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hanifsapp.hisabee.activity.homePage;
import com.hanifsapp.hisabee.activity.signIn;
import com.hanifsapp.hisabee.utility.logs;

import java.util.concurrent.TimeUnit;


public class GoogleSignUp {
    private static final String TAG = "FirebasePhoneAuth";
    private static FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private static String verificationId = "";
    private static Activity activity;

    public GoogleSignUp(Activity activity) {
        this.activity = activity;
        firebaseAuth = FirebaseAuth.getInstance();
        setupVerificationCallbacks();
    }

    private void setupVerificationCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId = s;
                logs.showLog(verificationId);
                activity.startActivity(new Intent(activity, signIn.class));
            }
        };
    }

    public void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(verificationCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public static void signInWithVerificationCode(String verificationCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        signInWithPhoneAuthCredential(credential);
    }

    private static void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                       activity.startActivity(new Intent(activity, homePage.class));
                    } else {
                        // Phone authentication failed.
                        Toast.makeText(activity.getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


