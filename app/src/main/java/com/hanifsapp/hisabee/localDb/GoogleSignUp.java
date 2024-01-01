package com.hanifsapp.hisabee.localDb;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hanifsapp.hisabee.activity.homePage;


public class GoogleSignUp {
    Context context;
    public GoogleSignUp(Context conto){
        context = conto;
    }





    public ActionCodeSettings buildActionCodeSettings() {
        return ActionCodeSettings.newBuilder()
                .setUrl("hisab-c55d9.firebaseapp.com")
                .setHandleCodeInApp(true)
                .setAndroidPackageName(
                        "com.hanifsapp.hisabee",
                        true, /* installIfNotAvailable */
                        "11"    /* minimumVersion */)
                .build();
    }

    public void sendSignInLink(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail(email, buildActionCodeSettings())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Email Sent. Plase check your Inbox or Junk folder", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void verifySignInLink(Intent getIntent) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent;
        String emailLink = intent.getData().toString();

        if (auth.isSignInWithEmailLink(emailLink)) {
            String email = "someemail@domain.com";

            auth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            AuthResult result = task.getResult();
                            if(result.getAdditionalUserInfo().isNewUser()){
                                context.startActivity(new Intent(context, homePage.class));
                            }
                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}
