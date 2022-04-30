package com.example.coffeetrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartsList extends MenuToolsBar {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Carts> carts;
    MyListAdapter myListAdapter;

    ImageButton back;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.carts_list_view);

        recyclerView=(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carts=new ArrayList<Carts>();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Carts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    Carts c=dataSnapshot1.getValue(Carts.class);
                    carts.add(c);
                }
                myListAdapter=new MyListAdapter(CartsList.this,carts);
                recyclerView.setAdapter(myListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartsList.this, "Oops something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartsList.this,LoggedInUserMenu.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str)
    {
        ArrayList<Carts> searchCarts=new ArrayList<Carts>();
        for(Carts object: carts) {
            if (object.getName().contains(str) || object.getPlace().contains(str) || object.getArea().contains(str)) {
                searchCarts.add(object);
            }
        }

        myListAdapter=new MyListAdapter(CartsList.this,searchCarts);
        recyclerView.setAdapter(myListAdapter);

    }
}
