package com.truck.transfly.Activty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.ui.IconGenerator;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.R;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private double LatituteOfTajMahal = 27.429707;
    private double LongitudeOfTajMahal = 82.176804;

    private double new1 = 27.333574, new2 = 82.697655;
    private double new3 = 	26.822845, new4 = 82.763443;
    private double new5 = 28.975210, new6 = 78.942970;
    private double tulsipurLat=26.934889, tulsipurLong=82.499924;
    private DrawerLayout drawerLayout;
    private CardView viewById;
    private LocationCallback mLocationCallBack;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private FusedLocationProviderClient mLocationClient;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;
    private double Mylongitude;
    private double Mylatitude;
    private boolean isStart=true;
   private boolean isFirstTime=true;
    private LocationRequest locationRequest;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        navigationViewListener(navigationView);

        navigationView.setItemIconTintList(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this::onMapReady);

        drawerLayout = findViewById(R.id.drawer_layout);

        viewById = findViewById(R.id.drawer_icon);

        mLocationClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);

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

        mLocationCallBack=new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location lastLocation = locationResult.getLastLocation();

                Mylatitude = lastLocation.getLatitude();
                Mylongitude = lastLocation.getLongitude();

                LatLng latLng = new LatLng(Mylatitude, Mylongitude);

                if(marker!=null)
                    marker.remove();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("My Location");
                marker = mGoogleMap.addMarker(markerOptions);

                if(isStart) {
                    getoLocation(Mylatitude, Mylongitude);
                    isStart=false;
                }

            }
        };

        findViewById(R.id.my_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (permissionIsGranted() && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    if(Mylatitude!=0 && Mylongitude!=0)
                        goToLocationWithAnimation(Mylatitude,Mylongitude);


                } else {

                    if(Mylatitude!=0 && Mylongitude!=0)
                        goToLocationWithAnimation(Mylatitude,Mylongitude);

                    mLocationClient.removeLocationUpdates(mLocationCallBack);
                    locationRequest=null;
                    getCurrentLocation();

                }

            }
        });

        getCurrentLocation();

    }

    private void navigationViewListener(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile_drawer:

                        Intent intent=new Intent(MapActivity.this,ProfileActivity.class);
                        startActivity(intent);

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent=new Intent(MapActivity.this,KycEditActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.ticket_drawer:

                        Intent ticket_complain=new Intent(MapActivity.this,TicketComplaintActivity.class);
                        startActivity(ticket_complain);

                        break;

                    case R.id.feedback_drawer:

                        Intent feedback_intent=new Intent(MapActivity.this,FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent=new Intent(MapActivity.this,ReferActivity.class);
                        startActivity(refer_intent);

                        break;


                    case R.id.logout:

                        Intent logout_intent=new Intent(MapActivity.this,LoginActivity.class);
                        startActivity(logout_intent);

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }

    private void goToLocationWithAnimation(double latitude, double longitude) {

        LatLng latLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 9);

        mGoogleMap.animateCamera(cameraUpdateFactory, 500, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onCancel() {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    doLocationWork();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.e("GPS", "User denied to access location");
                    openGpsEnableSetting();
                    break;
            }
        } else if (requestCode == REQUEST_ENABLE_GPS) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGpsEnabled) {
                openGpsEnableSetting();
            } else {

                doLocationWork();
            }
        }
    }

    private void openGpsEnableSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_ENABLE_GPS);
    }

    private void getCurrentLocation() {

        if (permissionIsGranted()) {

            openDialogOfSettingGps();

        } else {

            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            String rationale = "Please provide location permission so that you can ...";
            Permissions.Options options = new Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning");

            Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
                @Override
                public void onGranted() {

                    openDialogOfSettingGps();

                }

                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                    // permission denied, block the feature.
                }
            });

        }

    }

    private void openDialogOfSettingGps() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        mSettingsClient = LocationServices.getSettingsClient(MapActivity.this);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        doLocationWork();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("GPS", "Unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.e("GPS", "Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.e("GPS", "checkLocationSettings -> onCanceled");
                    }
                });

    }

    public boolean permissionIsGranted() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void doLocationWork() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);

        if(locationRequest==null) {

            isStart=true;

            locationRequest = LocationRequest.create();
            locationRequest.setInterval(120000); // two minute interval
            locationRequest.setFastestInterval(10000);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationClient.requestLocationUpdates(locationRequest,mLocationCallBack,null);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        getoLocation(LatituteOfTajMahal, LongitudeOfTajMahal);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        showMarker(LatituteOfTajMahal, LongitudeOfTajMahal, 1, "Balrampur 500 rupees");
        showMarker(new1, new2, 2, "Itwa 100 rupess");
        showMarker(new3, new4, 3, "Basti 3000 rupees");
        showMarker(new5, new6, 4, "Nanital");
        showMarker(tulsipurLat, tulsipurLong, 5, "Babhnan Mines\nPricing 500 Rupees");

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getTag() == null)
                    return false;

                int position = (int) (marker.getTag());

                Toast.makeText(MapActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    private void showMarker(double latituteOfTajMahal, double longitudeOfTajMahal, int postion, String locationAddress) {

        LatLng latLng = new LatLng(latituteOfTajMahal, longitudeOfTajMahal);

        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(locationAddress);
        markerOptions.snippet("This is my spot!");
        Marker marker = mGoogleMap.addMarker(markerOptions);

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(locationAddress)));

        marker.setTag(postion);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Mylatitude!=0 && Mylongitude!=0)
            goToLocationWithAnimation(Mylatitude,Mylongitude);

    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    private void getoLocation(double latituteOfTajMahal, double longitudeOfTajMahal) {

        LatLng latLng = new LatLng(latituteOfTajMahal, longitudeOfTajMahal);

        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 9);

        mGoogleMap.moveCamera(cameraUpdateFactory);

    }
}