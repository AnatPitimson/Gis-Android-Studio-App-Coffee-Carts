package com.example.coffeetrail;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class Test_CustomInfoWindowAdapter extends MapsActivity implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    public Test_CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.

        View v = inflater.inflate(R.layout.test_data_per_position, null);

        ((TextView)v.findViewById(R.id.name)).setText(m.getTitle());
        ((TextView)v.findViewById(R.id.Place)).setText(m.getSnippet());


        return v;
    }



    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }
}
