package com.example.coffeetrail;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddCarts extends MenuToolsBar implements AdapterView.OnItemSelectedListener {

    DatabaseReference databaseReference;
    DatabaseReference imgDatabaseReference;
    EditText editTextName;
    EditText editTextPlace;
    EditText editTextLat;
    EditText editTextLon;
    EditText editTextActivitytime;
    EditText editTextPhone;
    Button btnSave;
    Button btnMap;
    Button uimg;
    Button uploadImg;
    Carts carts;
    TextView logout;
    Spinner spinner;
    Spinner phonek;
    CheckBox kosher_, accessible_, vegan_, gluten_free_;
    long i = 0;

    int x=0;

    FirebaseAuth mAuth;
    StorageReference storageReference=FirebaseStorage.getInstance().getReference();
    Uri uri;
    String imgUrl;
    //FirebaseStorage storage;

    //Uri imageUri;
    ProgressBar progressBar;

    ImageButton back;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.add_cart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Carts");
        imgDatabaseReference=FirebaseDatabase.getInstance().getReference().child("images");
        editTextName = (EditText) findViewById(R.id.name);
        editTextPlace = (EditText) findViewById(R.id.Place);
        editTextLat = (EditText) findViewById(R.id.lat);
        editTextLon = (EditText) findViewById(R.id.lon);
        editTextActivitytime = (EditText) findViewById(R.id.activity_time);
        editTextPhone = (EditText) findViewById(R.id.phone);
        //editTextUrl = (EditText) findViewById(R.id.surl);
        btnSave = (Button) findViewById(R.id.save);
        btnMap = (Button) findViewById(R.id.map);
        logout = (TextView) findViewById(R.id.logout);
        spinner = (Spinner) findViewById(R.id.spinner1);
        phonek = (Spinner) findViewById(R.id.phonek);
        kosher_ = (CheckBox) findViewById(R.id.kosher);
        accessible_ = (CheckBox) findViewById(R.id.accessible_);
        vegan_ = (CheckBox) findViewById(R.id.vegan);
        gluten_free_ = (CheckBox) findViewById(R.id.gluten_free);
        uimg = (Button) findViewById(R.id.choose_pic);
        uploadImg=(Button)findViewById(R.id.uploadImg);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        //uploadPic = (Button) findViewById(R.id.upload);
        //storage = FirebaseStorage.getInstance();

        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddCarts.this,LoggedInUserMenu.class);
                startActivity(intent);
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x=1;
                if(uri!=null) {
                    uploadImageToFirebase(uri);
                }
                else
                {
                    Toast.makeText(AddCarts.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    mGetContent.launch("image/*");
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
                uploadImg.setVisibility(View.VISIBLE);
            }
        });
/*
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.areas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> phonek_adapter = ArrayAdapter
                .createFromResource(this, R.array.phone_k, android.R.layout.simple_spinner_item);
        phonek_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phonek.setAdapter(phonek_adapter);
        phonek.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        carts = new Carts();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    i = ((snapshot).getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the edit text is empty.
                if(x!=1)
                    Toast.makeText(AddCarts.this, "Please upload picture", Toast.LENGTH_SHORT).show();
                else if (editTextName.length() == 0 || editTextLat.length() == 0 || editTextLon.length() == 0 || editTextPlace.length() == 0)
                    Toast.makeText(AddCarts.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                else {
                    String name = editTextName.getText().toString().trim();
                    String place = editTextPlace.getText().toString().trim();
                    String area = spinner.getSelectedItem().toString().trim();
                    String phone_number = phonek.getSelectedItem().toString().trim();
                    String activitytime = editTextActivitytime.getText().toString().trim();
                    //String surl = editTextUrl.getText().toString().trim();

                    if (kosher_.isChecked()) {
                        String _kosher = kosher_.getText().toString().trim();
                        carts.setKosher_(_kosher);
                    }
                    if (accessible_.isChecked()) {
                        String _accessible = accessible_.getText().toString().trim();
                        carts.setAccessible_(_accessible);
                    }
                    if (vegan_.isChecked()) {
                        String _vegan = vegan_.getText().toString().trim();
                        carts.setVegan_(_vegan);
                    }
                    if (gluten_free_.isChecked()) {
                        String _gluten_free = gluten_free_.getText().toString().trim();
                        carts.setGluten_free_(_gluten_free);
                    }

                    double lat = Double.parseDouble(editTextLat.getText().toString().trim());
                    double lon = Double.parseDouble(editTextLon.getText().toString().trim());
                    String phone = (editTextPhone.getText().toString().trim());

                    phone_number = phone_number + phone;
                    carts.setName(name);
                    carts.setPlace(place);
                    carts.setLat(lat);
                    carts.setLon(lon);
                    carts.setArea(area);
                    carts.setActivitytime(activitytime);
                    carts.setPhone(phone_number);
                    carts.setSurl(imgUrl);
                    String i_=Long.toString(i+1);
                    carts.setId(i_);
                    //uploadImage();
                    databaseReference.child(String.valueOf(i + 1)).setValue(carts);
                    //progressBar.setVisibility(View.INVISIBLE);



                    Toast.makeText(AddCarts.this, "Saved", Toast.LENGTH_SHORT).show();
                    editTextName.getText().clear();
                    editTextPlace.getText().clear();
                    editTextLat.getText().clear();
                    editTextLon.getText().clear();
                    editTextActivitytime.getText().clear();
                    editTextPhone.getText().clear();
                    spinner.setSelection(0);
                    kosher_.setChecked(false);
                    accessible_.setChecked(false);
                    vegan_.setChecked(false);
                    gluten_free_.setChecked(false);
                    phonek.setSelection(0);
                    uploadImg.setVisibility(View.INVISIBLE);

                    //editTextUrl.getText().clear();
                    //progressBar.setVisibility(View.INVISIBLE);

                }
            }
    });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddCarts.this, MapsActivity.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(AddCarts.this, Menu.class);
                startActivity(i);
            }
        });


    }

    private void uploadImageToFirebase(Uri uri) {
        StorageReference file=storageReference.child(System.currentTimeMillis()+", "+getFileExtension(uri));
        file.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgUrl=uri.toString();
                        Toast.makeText(AddCarts.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap map=MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /*
        private void uploadImage() {

            if(imageUri!=null)
            {
                StorageReference reference=storage.getReference().child("images/"+ UUID.randomUUID().toString());
                reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AddCarts.this, "Image Uploaded successfully", Toast.LENGTH_SHORT).show();
                            Uri imageUrl=task.getResult().getUploadSessionUri();
                           // Toast.makeText(AddCarts.this, " "+imageUrl, Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(AddCarts.this, task.getException().getMessage()+" ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


  /*  ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        imageUri = result;
                    }
                }
            });
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK&&data!=null) {
            uri=data.getData();

        }
    }
}
