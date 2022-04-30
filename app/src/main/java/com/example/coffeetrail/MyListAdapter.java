package com.example.coffeetrail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Carts> carts;

    public MyListAdapter(Context c,ArrayList<Carts> ca)
    {
        context=c;
        carts=ca;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_in_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(carts.get(position).getName());
        holder.place.setText(carts.get(position).getPlace());
        holder.area.setText(carts.get(position).getArea());
        Picasso.get().load(carts.get(position).getSurl()).into(holder.imageView);

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Toast.makeText(context, " "+carts.get(position).getSurl(), Toast.LENGTH_SHORT).show();
                        FirebaseStorage.getInstance().getReferenceFromUrl(carts.get(position).getSurl()).delete();


                        FirebaseDatabase .getInstance().getReference().child("Carts")
                                .child(carts.get(position).getId()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,place,area;
        ImageView imageView;
        Button del;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.nameText);
            place=(TextView) itemView.findViewById(R.id.placeText);
            area=(TextView) itemView.findViewById(R.id.areaText);
            imageView=(ImageView) itemView.findViewById(R.id.img1);
            del=(Button) itemView.findViewById(R.id.deleteBtn);

        }
    }
}
