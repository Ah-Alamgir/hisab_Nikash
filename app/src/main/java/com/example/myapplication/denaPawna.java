package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class denaPawna extends AppCompatActivity {

    Button newDue;
    private EditText editText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchButtonGive, switchButtonTake;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = database.getReference().child("denapaona");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dena_pawna);
        setTitle("দেনা পাওনা");

        newDue = findViewById(R.id.newDue);

        newDue.setOnClickListener(view -> showTextInputDialog());

    }


    private void showTextInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("দেনা পাওনার হিসাব");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text_input, null);

        editText = view.findViewById(R.id.denaPawana_editText);
        switchButtonGive = view.findViewById(R.id.switchButtonGive);
        switchButtonTake = view.findViewById(R.id.switchButtonTake);

        builder.setView(view);

        builder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            String text = editText.getText().toString();
            if (switchButtonGive.isChecked()) {
                databaseRef.child("give").setValue(Integer.valueOf(text));
            } else if (switchButtonTake.isChecked()) {
                databaseRef.child("take").setValue(Integer.valueOf(text));
            }


        });

        builder.setNegativeButton("বাদ দিন", null);
        builder.show();
    }


    private void getDenapaonaData(){

    }


}
