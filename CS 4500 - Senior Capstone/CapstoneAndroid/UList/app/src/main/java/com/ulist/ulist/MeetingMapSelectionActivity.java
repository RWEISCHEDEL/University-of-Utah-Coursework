package com.ulist.ulist;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

/**
 * This class creates the MeetingMapSelectionActivity which allows the user to select and see what location they wish to meet the seller at.
 */
public class MeetingMapSelectionActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private double lat;
    private double lng;
    Button confirmButton;
    String coordinates;
    Marker mark;
    private Bundle b;
    Toolbar toolbar;

    /**
     * Create the Activity with the toolbar, button and map fragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = this.getIntent().getExtras();

        setContentView(R.layout.activity_meeting_map_selection);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        confirmButton = (Button)findViewById(R.id.confirmLocButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                b.putSerializable("lat", (Serializable) lat);
                b.putSerializable("lng", (Serializable) lng);
                intent.putExtras(b);

                intent.setClass(MeetingMapSelectionActivity.this, PurchaseProductActivity.class);

                startActivity(intent);
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                System.out.println("Made it: " + latLng.toString());

                if(mark != null){
                    mark.remove();
                }

                mark = mMap.addMarker(new MarkerOptions().position(latLng).title("Meeting Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                lat = latLng.latitude;
                lng = latLng.longitude;
            }
        });

        Bundle b = this.getIntent().getExtras();

        if(b != null && b.containsKey("lat")){
            lat = (double) b.get("lat");
            lng = (double) b.get("lng");
            LatLng displayMeeting = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(displayMeeting).title("Meeting Location"));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(displayMeeting, zoomLevel));
        }
        else{
            // Add a marker in Sydney and move the camera
            LatLng presCircle = new LatLng(40.765025465354796, -111.85010980814695);
            //mMap.addMarker(new MarkerOptions().position(presCircle).title("President's Circle"));
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(presCircle, zoomLevel));
        }
    }

}
