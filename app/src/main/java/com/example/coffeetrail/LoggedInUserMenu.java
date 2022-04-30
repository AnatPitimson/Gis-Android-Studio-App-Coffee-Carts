package com.example.coffeetrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoggedInUserMenu extends MenuToolsBar {

    Button mapB,cartsList,addCart;
    ImageButton back;
    TextView helloTxt;
    TextView logout;
    FirebaseAuth mAuth;
    String name;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.logged_in_user_menu);

        logout = (TextView) findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        mapB=findViewById(R.id.mapBtn);
        cartsList=findViewById(R.id.cartsListBtn);
        addCart=findViewById(R.id.addCartBtn);
        helloTxt=findViewById(R.id.hello_name);
        back=findViewById(R.id.backBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(LoggedInUserMenu.this, Menu.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoggedInUserMenu.this,Login.class);
                startActivity(intent);
            }
        });

        mapB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoggedInUserMenu.this,MapsActivity.class);
                startActivity(i);
            }
        });

        cartsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoggedInUserMenu.this,CartsList.class);
                startActivity(i);
            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoggedInUserMenu.this,AddCarts.class);
                startActivity(i);
            }
        });

        name=getIntent().getStringExtra("Name");

        String txt=helloTxt.getText().toString();
        helloTxt.setText(txt+" "+name);

    }
}
