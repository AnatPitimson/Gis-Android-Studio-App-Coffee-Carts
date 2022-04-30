package com.example.coffeetrail;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuToolsBar extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.explanation:

                Dialog dialog_exp=new Dialog(this);
                dialog_exp.setContentView(R.layout.explanation);
                Button b=dialog_exp.findViewById(R.id.b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_exp.cancel();
                    }
                });
                dialog_exp.show();
                return true;

            case R.id.aboutUs:

                Dialog dialog_about=new Dialog(this);
                dialog_about.setContentView(R.layout.about_us);
                Button b_about=dialog_about.findViewById(R.id.b);
                b_about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_about.cancel();
                    }
                });
                dialog_about.show();
                return true;

            case R.id.bibliography:

                Dialog dialog_bib=new Dialog(this);
                dialog_bib.setContentView(R.layout.bibliography);
                Button b_bib=dialog_bib.findViewById(R.id.b);
                b_bib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_bib.cancel();
                    }
                });
                dialog_bib.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
