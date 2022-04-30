package com.example.coffeetrail;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Comments extends MenuToolsBar {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private ArrayList<SingleComment> singleComments;
    EditText comment;
    TextView post;
    long i=0;

    String id;
    String comment_text;

    CommentAdapter commentAdapter;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.comments);

        singleComments = new ArrayList<SingleComment>();

        id=getIntent().getStringExtra("id");

        recyclerView=findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Carts").child(id).child("Comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    i = ((snapshot).getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }
        });

        comment=findViewById(R.id.add_comment);


        post=findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment.getText().toString().isEmpty())
                {
                    Toast.makeText(Comments.this, "No comment added!", Toast.LENGTH_SHORT).show();
                } else {
                    putComment();
                }
            }
        });


        getComment();
    }


    private void getComment() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Carts").child(id).child("Comments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                singleComments.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    SingleComment comment = dataSnapshot1.getValue(SingleComment.class);
                    singleComments.add(comment);
                }
                commentAdapter = new CommentAdapter(Comments.this, singleComments);
                recyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }
        });

    }


    private void putComment() {

        //HashMap<String, Object> map = new HashMap<>();
        SingleComment singleComment=new SingleComment();
        comment_text=comment.getText().toString();

        singleComment.setCom(comment_text);

        databaseReference.child(String.valueOf(i + 1)).setValue(singleComment);

        //map.put("comment", addComment.getText().toString());

        comment.setText("");

    }
}


