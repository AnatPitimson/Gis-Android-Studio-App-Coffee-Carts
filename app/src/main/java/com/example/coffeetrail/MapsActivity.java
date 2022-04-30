package com.example.coffeetrail;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.coffeetrail.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FusedLocationProviderClient client;
    SupportMapFragment supportMapFragment;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference_Travel;

    MarkerOptions options;

    int dataCounter;

    HashMap<String, Carts> markerHolderMap = new HashMap<String, Carts>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else
        {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        getCurrentLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        databaseReference_Travel= FirebaseDatabase.getInstance().getReference().child("Trails&Nature");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Carts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    Carts carts = snapshot1.getValue(Carts.class);
                    LatLng location=new LatLng(carts.getLat(),carts.getLon());

                    MarkerOptions markerOptions=(new MarkerOptions()
                            .position(location).title("שם: "+ carts.getName()+"\n"+ "מיקום: "+carts.getPlace())
                            .snippet("שעות פתיחה: "+"\n"+carts.getActivitytime()))
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.drawable.coffee_marker_a)));

                    Marker m =mMap.addMarker(markerOptions);

                    Carts mHolder = new Carts(carts.getName(), carts.getPlace(),
                            carts.getArea(),carts.getKosher_(),carts.getAccessible_(),
                            carts.getVegan_(),carts.getGluten_free_(),carts.getActivitytime(),
                            carts.getSurl(), carts.getLat(), carts.getLon(),carts.getPhone(),carts.getId());

                    markerHolderMap.put(m.getId(), mHolder); //Add info to HashMap

                }
                dataCounter=markerHolderMap.size();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            options=new MarkerOptions().position(latLng).title("My Location")
                                    .icon(getMarkerIcon("#408F2B"));

                            //SET THE MARKERS DATA AS WE WANT, AFTER TOUCHING ON AN POSITION ICON
                            mMap.setInfoWindowAdapter(new Test_CustomInfoWindowAdapter(LayoutInflater.from(getApplicationContext())));

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

                            googleMap.addMarker(options);

                        }
                    });
                }
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

        if(marker.getPosition().longitude!=options.getPosition().longitude
        &&marker.getPosition().latitude!=options.getPosition().latitude) {
            Carts mHolder = markerHolderMap.get(marker.getId()); //use the ID to get the info


            Intent i = new Intent(MapsActivity.this, PlaceData.class);

            double lat = marker.getPosition().latitude;
            double lon = marker.getPosition().longitude;

            String latC = String.valueOf(lat);
            String lonC = String.valueOf(lon);

            i.putExtra("lat", latC);
            i.putExtra("lon", lonC);
            i.putExtra("Name", mHolder.getName());
            i.putExtra("Place", mHolder.getPlace());
            i.putExtra("Area", mHolder.getArea());
            i.putExtra("Kosher_", mHolder.getKosher_());
            i.putExtra("Accessible_", mHolder.getAccessible_());
            i.putExtra("Vegan_", mHolder.getVegan_());
            i.putExtra("Gluten_free_", mHolder.getGluten_free_());
            i.putExtra("ActivityTime", mHolder.getActivitytime());
            i.putExtra("Phone", mHolder.getPhone());
            i.putExtra("picUrl", mHolder.getSurl());
            i.putExtra("id",mHolder.getId());

            startActivity(i);
        }
        else
        {
        }
    }
}

