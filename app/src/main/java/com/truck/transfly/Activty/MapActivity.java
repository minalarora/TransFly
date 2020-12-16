package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.ui.IconGenerator;
import com.truck.transfly.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private double LatituteOfTajMahal=27.173891;
    private double LongitudeOfTajMahal=78.042068;

    private double new1 = 27.209745239842444, new2 = 78.32392058136335;
    private double new3 = 27.56650835174797, new4 = 78.65772736253567;
    private double new5 = 27.52156896657124, new6 = 78.57223839225884;
    private DrawerLayout drawerLayout;
    private CardView viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        navigationView.setItemIconTintList(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this::onMapReady);

        drawerLayout = findViewById(R.id.drawer_layout);

         viewById = findViewById(R.id.drawer_icon);

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

                    drawerLayout.closeDrawer(GravityCompat.START);

                } else {

                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap=googleMap;

        getoLocation(LatituteOfTajMahal,LongitudeOfTajMahal);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        showMarker(LatituteOfTajMahal,LongitudeOfTajMahal,1,"Allahabad");
        showMarker(new1,new2,2,"Faizabad");
        showMarker(new3,new4,3,"Raibraily");
        showMarker(new5,new6,4,"Nanital");

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.getTag()==null)
                    return false;

                int position = (int)(marker.getTag());

                Toast.makeText(MapActivity.this, ""+position, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    private void showMarker(double latituteOfTajMahal, double longitudeOfTajMahal, int postion, String locationAddress) {

        LatLng latLng=new LatLng(latituteOfTajMahal,longitudeOfTajMahal);

        TextView text = new TextView(MapActivity.this);
        text.setTypeface(Typeface.DEFAULT_BOLD);

        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setColor(Color.parseColor("#ff0000"));
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(locationAddress);
        markerOptions.snippet("This is my spot!");
        Marker marker = mGoogleMap.addMarker(markerOptions);

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(locationAddress)));

        marker.setTag(postion);

    }

    private void getoLocation(double latituteOfTajMahal, double longitudeOfTajMahal) {

        LatLng latLng=new LatLng(latituteOfTajMahal,longitudeOfTajMahal);

        CameraUpdate cameraUpdateFactory=CameraUpdateFactory.newLatLngZoom(latLng,9);

        mGoogleMap.moveCamera(cameraUpdateFactory);

    }
}