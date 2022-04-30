package com.example.coffeetrail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends MenuToolsBar {

    EditText editTextMail;
    EditText editTextPassword;
    TextView textView;

    FirebaseAuth mAuth;

    ImageButton back;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.register);

        editTextMail=findViewById(R.id.mail);
        editTextPassword=findViewById(R.id.password);

        mAuth=FirebaseAuth.getInstance();

        textView=findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        Button register =findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });


        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Menu.class);
                startActivity(intent);
            }
        });

    }

    private void createUser() {
        String mail=editTextMail.getText().toString();
        String password=editTextPassword.getText().toString();

        if(TextUtils.isEmpty(mail)){
            editTextMail.setError("Mail can not be empty");
            editTextMail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Last Name can not be empty");
            editTextPassword.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Register.this,Login.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Register.this, "Register Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
