package com.example.coffeetrail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class Login extends MenuToolsBar {

    EditText editTextMail;
    EditText editTextPassword;
    TextView textView;
    CheckBox rememberMeCB;
    FirebaseAuth mAuth;

    ImageButton back;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextMail = findViewById(R.id.mail);
        editTextPassword = findViewById(R.id.password);
        textView = findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Menu.class);
                startActivity(intent);
            }
        });


    }

    private void loginUser() {
        String mail = editTextMail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            editTextMail.setError("Mail can not be empty");
            editTextMail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password can not be empty");
            editTextPassword.requestFocus();
        } else {
            String[] split = mail.split("@");
            String name=split[0];
            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, LoggedInUserMenu.class);
                        intent.putExtra("Name",name);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
