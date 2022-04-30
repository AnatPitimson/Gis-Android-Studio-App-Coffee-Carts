package com.example.coffeetrail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

public class PlaceData extends MenuToolsBar implements OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;

    RelativeLayout relativeLayout;

    String searchString;
    String baseWazeUrl = "https://waze.com/ul?ll=";
    private LatLng latLng;

    String latS;
    String lonS;
    String nameS;
    String placeS;
    String areaS;
    String kosher_S;
    String accessible_S;
    String vegan_S;
    String gluten_free_S;
    String activityTimeS;
    String phoneS;
    String picUrlS;
    String idS;

    ImageView pic_p;
    ImageView pic_a;
    ImageButton comment;

    DatabaseReference databaseReference_Travel;

    double lat;
    double lon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_data);

        relativeLayout=(RelativeLayout)findViewById(R.id.btnsLayout);
        TextView name=(TextView)findViewById(R.id.name);
        TextView place=(TextView)findViewById(R.id.place);
        TextView area=(TextView)findViewById(R.id.area);

        CheckBox kosher_=(CheckBox)findViewById(R.id.kosher);
        CheckBox accessible_=(CheckBox)findViewById(R.id.accessible);
        CheckBox vegan_=(CheckBox)findViewById(R.id.vegan);
        CheckBox gluten_free_=(CheckBox)findViewById(R.id.gluten_free);

        TextView activity_time=(TextView)findViewById(R.id.activity_time);
        TextView phone=(TextView)findViewById(R.id.phone);
        ImageButton waze=(ImageButton)findViewById(R.id.waze);


        pic_p=(ImageView)findViewById(R.id.img_p);
        pic_a=(ImageView)findViewById(R.id.img_a);

        latS = getIntent().getStringExtra("lat");
        lonS = getIntent().getStringExtra("lon");
        nameS = getIntent().getStringExtra("Name");
        placeS = getIntent().getStringExtra("Place");
        areaS = getIntent().getStringExtra("Area");
        kosher_S = getIntent().getStringExtra("Kosher_");
        accessible_S = getIntent().getStringExtra("Accessible_");
        vegan_S = getIntent().getStringExtra("Vegan_");
        gluten_free_S = getIntent().getStringExtra("Gluten_free_");
        activityTimeS = getIntent().getStringExtra("ActivityTime");
        phoneS = getIntent().getStringExtra("Phone");
        picUrlS=getIntent().getStringExtra("picUrl");
        idS=getIntent().getStringExtra("id");

        Picasso.get().load(picUrlS).into(pic_p);
        Picasso.get().load(picUrlS).into(pic_a);

        pic_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic_a.setVisibility(View.VISIBLE);
                //pic_p.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
        });

        pic_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic_a.setVisibility(View.INVISIBLE);
            }
        });

        lat=Double.parseDouble(latS);
        lon=Double.parseDouble(lonS);
        latLng=new LatLng(lat,lon);

        waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchString = baseWazeUrl+latLng.latitude+", "+latLng.longitude+"&z=10";
                try
                {
                    // Launch Waze to look for our address:
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( searchString ) );
                    startActivity( intent );
                }
                catch ( ActivityNotFoundException ex  )
                {
                    // If Waze is not installed, open it in Google Play:
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                    startActivity(intent);
                }

            }
        });


        name.setText(nameS);
        place.setText(placeS);
        area.setText(areaS);
        if(kosher_S!=null) {
            kosher_.setChecked(true);
        }
        if(accessible_S!=null)
        {
            accessible_.setChecked(true);
        }
        if(vegan_S!=null)
        {
            vegan_.setChecked(true);
        }
        if(gluten_free_S!=null)
        {
            gluten_free_.setChecked(true);
        }
        activity_time.setText("שעות פעילות: "+ activityTimeS);
        phone.setText("טלפון: "+phoneS);

        mapView=(MapView)findViewById(R.id.mapMarker);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        databaseReference_Travel= FirebaseDatabase.getInstance().getReference().child("Trails&Nature");

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic_a.setVisibility(View.INVISIBLE);
            }
        });

        comment=(ImageButton)findViewById(R.id.comment);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceData.this, Comments.class);
                intent.putExtra("id",idS);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        mapView.onResume();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f));
        MarkerOptions markerOptions = (new MarkerOptions()
                .position(latLng).title(nameS))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.__coffee_marker_)));
        Marker m = map.addMarker(markerOptions);
        nearByTrailsAndNature(m);


    }

    public void nearByTrailsAndNature(Marker marker) {
        double pi=Math.PI;
        int radius = 3700; //equatorial radius


        databaseReference_Travel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    TravelAndNature travelAndNature = snapshot1.getValue(TravelAndNature.class);

                    double lat2 = travelAndNature.getLat();
                    double lon2 = travelAndNature.getLon();
                    String nameT=travelAndNature.getName();

                    LatLng location=new LatLng(lat2,lon2);

                    double chLat = lat2-lat;
                    double chLon = lon2-lon;

                    double dLat = chLat*(pi/180);
                    double dLon = chLon*(pi/180);

                    double rLat1 = lat*(pi/180);
                    double rLat2 = lat2*(pi/180);


                    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                            Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(rLat1) * Math.cos(rLat2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double d = radius * c;


                    //להתאים את המרחק לרדיוס שנרצה פחות או יותר
                    if(d<37) {
                        MarkerOptions markerOptions = (new MarkerOptions()
                                .position(location).title(" "+nameT))
                                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.drawable.icon_)));
                        Marker m = map.addMarker(markerOptions);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}
