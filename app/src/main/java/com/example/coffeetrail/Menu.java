package com.example.coffeetrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu extends MenuToolsBar {

    Button newUser;
    Button regUser;
    Button guestUser;
    Button aboutTheAppBtn;
    Button placesList;

    DatabaseReference databaseReference;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.menu);

        newUser=findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Menu.this, Register.class);
                startActivity(intent);
            }
        });


        regUser=findViewById(R.id.regUser);
        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Menu.this, Login.class);
                startActivity(intent);
            }
        });

        guestUser=findViewById(R.id.guest);
        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Menu.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        aboutTheAppBtn=findViewById(R.id.aboutTheAppBtn);
        aboutTheAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Menu.this, AboutTheApp.class);
                startActivity(intent);
            }
        });

    }

}
