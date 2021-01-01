package com.truck.transfly.Activty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.ui.IconGenerator;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.Adapter.LocationAdapter;
import com.truck.transfly.Adapter.YourCoolAdapter;
import com.truck.transfly.Frament.ShowLoadingDialogFragment;
import com.truck.transfly.Model.PositionModel;
import com.truck.transfly.Model.RequestArea;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.Model.SliderModel;
import com.truck.transfly.MuUtils.MetalRecyclerViewPager;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private double LatituteOfTajMahal = 22.106364561666886;
    private double LongitudeOfTajMahal = 85.37934426698966;
    private Retrofit retrofit = null;
    private ArrayList<String> loadinglist = new ArrayList<>();

    private ApiEndpoints api = null;

    private ArrayList<RequestArea> arealist = new ArrayList<>();
    private double new1 = 22.163690366420184, new2 = 85.41610695158445;
    private double new3 = 22.018251428199196, new4 = 85.40548247296358;
    private double new5 = 21.908011041198012, new6 = 85.2509771962851;
    private double tulsipurLat = 26.216539779866235, tulsipurLong = 81.25079189586448;
    private double rugudi_lat = 21.887970812746232, rugudi_log = 85.81699947599559;
    private DrawerLayout drawerLayout;
    private ImageView viewById;
    private LocationCallback mLocationCallBack;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private FusedLocationProviderClient mLocationClient;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;
    private double Mylongitude;
    private double Mylatitude;
    private boolean isStart = true;
    private boolean isFirstTime = true;
    private LocationRequest locationRequest;
    private Marker marker;
    private Handler handler;
    private ArrayList<ResponseMine> mines = new ArrayList<>();
    private YourCoolAdapter fullMetalAdapter;
    private MetalRecyclerViewPager viewPager;
    private List<SliderModel> metalList = new ArrayList<>();
    private BottomNavigationViewEx navigation;
    private LocationAdapter locationAdapter;
    private RequestArea requestArea;
    private String loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        View headerLayout = navigationView.getHeaderView(0);
        TextView customerName = headerLayout.findViewById(R.id.customer_name);
        TextView number=headerLayout.findViewById(R.id.number);

        ResponseVehicleOwner responseFieldStaff = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

        customerName.setText(responseFieldStaff.getName());
        number.setText(responseFieldStaff.getMobile());


        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        navigationViewListener(navigationView);

        navigationView.setItemIconTintList(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this::onMapReady);

        findViewById(R.id.current_booking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, CurrentBookingActivity.class));

            }
        });

        navigation = findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableItemShiftingMode(false);
        navigation.enableShiftingMode(false);
        navigation.setTextSize(12);
        navigation.setPadding(0, 0, 0, 1);
        navigation.setIconSize(26, 26);
        navigation.setTextVisibility(true);

        drawerLayout = findViewById(R.id.drawer_layout);

        viewById = findViewById(R.id.drawer_icon);

        mLocationClient = LocationServices.getFusedLocationProviderClient(HomeActivity.this);

        findViewById(R.id.search_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, SearchBarActivity.class);

                startActivity(intent);

            }
        });

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

        List<PositionModel> positionModelList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {

            PositionModel positionModel = new PositionModel();
            positionModelList.add(positionModel);

        }

        RecyclerView recyclerView = findViewById(R.id.locationRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        locationAdapter = new LocationAdapter(HomeActivity.this, arealist);
        recyclerView.setAdapter(locationAdapter);

        locationAdapter.setOnClickListener(new LocationAdapter.onClickListener() {
            @Override
            public void onClick(RequestArea requestArea) {

                if (requestArea != null) {

                    HomeActivity.this.requestArea = requestArea;

                    showMarker(Double.parseDouble(requestArea.getArealatitude()), Double.parseDouble(requestArea.getArealongitude()), 1, requestArea.getName());

                    ShowLoadingDialogFragment showLoadingDialogFragment = new ShowLoadingDialogFragment();

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("loadingList", loadinglist);
                    showLoadingDialogFragment.setArguments(bundle);

                    showLoadingDialogFragment.show(getSupportFragmentManager(), "showLoadingFragment");

                    showLoadingDialogFragment.setOnClickListener(new ShowLoadingDialogFragment.onClickListener() {
                        @Override
                        public void onClick(String loading) {

                            HomeActivity.this.loading=loading;

                            ArrayList<ResponseMine> allMineOfSingleArea = getAllMineOfSingleArea(requestArea.getName(), loading);

                            for (ResponseMine responseMine : allMineOfSingleArea) {

                                showMarkerOfArea(Double.parseDouble(responseMine.getLatitude()), Double.parseDouble(responseMine.getLongitude()), responseMine, responseMine.getName());


                            }

                        }
                    });

                    goToLocationWithAnimation(Double.parseDouble(requestArea.getArealatitude()), Double.parseDouble(requestArea.getArealongitude()));

                }

            }
        });

        mLocationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location lastLocation = locationResult.getLastLocation();

                Mylatitude = lastLocation.getLatitude();
                Mylongitude = lastLocation.getLongitude();

                LatLng latLng = new LatLng(Mylatitude, Mylongitude);

                if (marker != null)
                    marker.remove();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("My Location");
                marker = mGoogleMap.addMarker(markerOptions);

                if (isStart) {
                    getoLocation(Mylatitude, Mylongitude);
                    isStart = false;
                }

            }
        };

        findViewById(R.id.my_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (permissionIsGranted() && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    if (Mylatitude != 0 && Mylongitude != 0)
                        goToLocationWithAnimation(Mylatitude, Mylongitude);


                } else {

                    if (Mylatitude != 0 && Mylongitude != 0)
                        goToLocationWithAnimation(Mylatitude, Mylongitude);

                    mLocationClient.removeLocationUpdates(mLocationCallBack);
                    locationRequest = null;
                    getCurrentLocation();

                }

            }
        });

        initViewImageCrousel();

        getCurrentLocation();

        getMinesFromServer(PreferenceUtil.getData(HomeActivity.this, "token"));

    }

    private void getMinesFromServer(String token) {

        api.getAllMineVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                    Type collectionType = new TypeToken<ArrayList<ResponseMine>>() {
                    }.getType();
                    try {
                        mines.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (mines.isEmpty()) {
                        Log.d("minal", "mine not found");
                    } else {
                        Log.d("minal", mines.toString());
                        //for area name
                        HashMap<String, RequestArea> areas = new HashMap<>();
                        //for destination
                        Set<String> loadings = new HashSet<>();
                        Set<RequestArea> areass = new HashSet<>();
                        for (ResponseMine mine : mines) {
                            areas.put(mine.getArea(), new RequestArea(mine.getArea(), mine.getArealatitude(), mine.getArealongitude()));
                            for (String loading : mine.getLoading()) {
                                loadings.add(loading);
                            }
                        }

                        for (Map.Entry<String, RequestArea> a : areas.entrySet()) {
                            areass.add(a.getValue());
                        }

                        loadinglist.addAll(loadings);
                        arealist.addAll(areass);
                        locationAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void initViewImageCrousel() {

        handler = new Handler(getMainLooper());

        for (int i = 0; i < 10; i++) {

            SliderModel sliderModel = new SliderModel();
            metalList.add(sliderModel);

        }

        DisplayMetrics metrics = getDisplayMetrics();
        fullMetalAdapter = new YourCoolAdapter(metrics, metalList, HomeActivity.this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(fullMetalAdapter);

        handler.postDelayed(runnable, 2500);

    }

    private DisplayMetrics getDisplayMetrics() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }

    static final int SPEED_SCROLL = 3000;
    final Runnable runnable = new Runnable() {
        int count = 0;
        boolean flag = true;

        @Override
        public void run() {
            if (count < fullMetalAdapter.getItemCount()) {
                if (count == fullMetalAdapter.getItemCount() - 1) {
                    flag = false;
                } else if (count == 0) {
                    flag = true;
                }
                if (flag) count++;
                else count--;

                viewPager.smoothScrollToPosition(count);
                handler.postDelayed(this, SPEED_SCROLL);
            }
        }
    };

    private ArrayList<ResponseMine> getAllMineOfSingleArea(String area, String loading) {
        ArrayList<ResponseMine> selectedmines = new ArrayList<>();
        for (ResponseMine m : mines) {
            if (m.getArea().equalsIgnoreCase(area)) {
                for (String l : m.getLoading()) {
                    if (l.equalsIgnoreCase(loading)) {
                        selectedmines.add(m);
                    }
                }
            }
        }
        return selectedmines;
    }

    private void navigationViewListener(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.profile_drawer:

                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        intent.putExtra("stringText","vehicleOwner");
                        startActivity(intent);

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent = new Intent(HomeActivity.this, VehicleOwnerKycActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(HomeActivity.this,EmergencyContactActivity.class));

                        break;

                    case R.id.add_vehicle:

                        Intent addVehicleActivity = new Intent(HomeActivity.this, AddVehicleActivity.class);
                        startActivity(addVehicleActivity);

                        break;

                    case R.id.my_vehicle:

                        Intent vehicleOwnerKyc = new Intent(HomeActivity.this, MyVehicleActivity.class);
                        startActivity(vehicleOwnerKyc);

                        break;

                    case R.id.ticket_drawer:

                        Intent ticket_complain = new Intent(HomeActivity.this, TicketComplaintActivity.class);
                        startActivity(ticket_complain);

                        break;

                    case R.id.feedback_drawer:

                        Intent feedback_intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.current_invoices:

                        Intent currentInvoices = new Intent(HomeActivity.this, CurrentInvoicesActivity.class);
                        startActivity(currentInvoices);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent = new Intent(HomeActivity.this, ReferActivity.class);
                        refer_intent.putExtra("keyword","refer");
                        startActivity(refer_intent);

                        break;

                    case R.id.reward_program:

                        Intent rewardIntent = new Intent(HomeActivity.this, ReferActivity.class);
                        rewardIntent.putExtra("keyword","reward");
                        startActivity(rewardIntent);

                        break;

                    case R.id.logout:

                        Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        finish();

                        PreferenceUtil.putData(HomeActivity.this,"token","");

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

        mSettingsClient = LocationServices.getSettingsClient(HomeActivity.this);

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
                                    rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
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

        if (locationRequest == null) {

            isStart = true;

            locationRequest = LocationRequest.create();
            locationRequest.setInterval(120000); // two minute interval
            locationRequest.setFastestInterval(10000);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationClient.requestLocationUpdates(locationRequest, mLocationCallBack, null);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        getoLocation(LatituteOfTajMahal, LongitudeOfTajMahal);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

//        showMarker(LatituteOfTajMahal, LongitudeOfTajMahal, 1, "Rate: 4\nEtl:4");
//        showMarker(new1, new2, 2, "Rate: 5\nEtl:5");
//        showMarker(new3, new4, 3, "Rate: 3\nEtl:3");
//        showMarker(new5, new6, 4, "Rate: 4\nEtl:4");
//        showMarker(tulsipurLat, tulsipurLong, 5, "Rate: 4\nEtl:4");
//        showMarker(rugudi_lat, rugudi_log, 6, "Rate: 4\nEtl:4");

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getTag() == null)
                    return false;

                ResponseMine responseMine = (ResponseMine) (marker.getTag());

                if (responseMine != null) {

                    Intent intent = new Intent(HomeActivity.this, SelectYourVehicleActivity.class);

                    intent.putExtra("mineid",responseMine.getId());
                    intent.putExtra("minename",responseMine.getName());
                    intent.putExtra("loading",loading);

                    startActivity(intent);

                }

                return false;
            }
        });

    }

    private void showMarkerOfArea(double latituteOfTajMahal, double longitudeOfTajMahal, ResponseMine responseMine, String locationAddress) {

        LatLng latLng = new LatLng(latituteOfTajMahal, longitudeOfTajMahal);

        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(locationAddress);
        markerOptions.snippet("This is my spot!");
        Marker marker = mGoogleMap.addMarker(markerOptions);

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(locationAddress)));

        marker.setTag(responseMine);

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

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Mylatitude != 0 && Mylongitude != 0)
            goToLocationWithAnimation(Mylatitude, Mylongitude);

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