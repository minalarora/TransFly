package com.truck.transfly.Activty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.ImageViewCompat;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.ui.IconGenerator;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.Adapter.LocationAdapter;
import com.truck.transfly.Adapter.YourCoolAdapter;
import com.truck.transfly.Frament.MineChooseFragment;
import com.truck.transfly.Frament.ShowLoadingDialogFragment;
import com.truck.transfly.Model.PositionModel;
import com.truck.transfly.Model.RequestArea;
import com.truck.transfly.Model.RequestCoordinates;
import com.truck.transfly.Model.ResponseBanner;
import com.truck.transfly.Model.ResponseFirebase;
import com.truck.transfly.Model.ResponseLoading;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.Model.SliderModel;
import com.truck.transfly.MuUtils.MetalRecyclerViewPager;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.MyUtils;
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
    private ArrayList<ResponseBanner> responseBannerArrayList = new ArrayList<>();
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
    private MyUtils myUtils = new MyUtils();
    private String loading;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private String areaname;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        handler = new Handler(getMainLooper());

        Menu menu = navigationView.getMenu();
        MenuItem kyc_drawer = menu.findItem(R.id.kyc_drawer);

        ResponseVehicleOwner responseFieldStaff = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

        View headerLayout = navigationView.getHeaderView(0);
        TextView customerName = headerLayout.findViewById(R.id.customer_name);
        TextView number = headerLayout.findViewById(R.id.number);

        headerLayout.findViewById(R.id.appSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);

            }
        });

        customerName.setText(responseFieldStaff.getName());
        number.setText(responseFieldStaff.getMobile());

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadinglist.clear();
                mines.clear();
                responseBannerArrayList.clear();
                fullMetalAdapter.notifyDataSetChanged();
                locationAdapter.notifyDataSetChanged();
                no_internet_connection.setVisibility(View.GONE);
                getMinesFromServer(PreferenceUtil.getData(HomeActivity.this, "token"));

            }
        });


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        ResponseFirebase responseFirebase=new ResponseFirebase();
                        responseFirebase.setFirebase(token);

                        updateFirebase(PreferenceUtil.getData(HomeActivity.this,"token"),responseFirebase);

                    }
                });


        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        navigationViewListener(navigationView);

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
        navigation.setTextSize(9);
        navigation.setPadding(0, 0, 0, 1);
        navigation.setIconSize(26, 26);
        navigation.setTextVisibility(true);

        ImageView iconAt = navigation.getIconAt(2);
        ImageViewCompat.setImageTintList(iconAt, ColorStateList.valueOf(ContextCompat.getColor(HomeActivity.this, R.color.quantum_grey2)));

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.vehicle_seller:

                        startActivity(new Intent(HomeActivity.this, OlxPageActivity.class));

                        return false;

                    case R.id.booking_status:

                        startActivity(new Intent(HomeActivity.this, CurrentBookingActivity.class));

                        return false;

                    case R.id.new_booking:

                        Intent searchBarAcivity = new Intent(HomeActivity.this, SearchBarActivity.class);

                        searchBarAcivity.putExtra("vehicle",true);

                        startActivity(searchBarAcivity);

                        return false;

                    case R.id.need_help:

                        startActivity(new Intent(HomeActivity.this, TicketComplaintActivity.class));

                        return false;

                }

                return true;
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        viewById = findViewById(R.id.drawer_icon);

        mLocationClient = LocationServices.getFusedLocationProviderClient(HomeActivity.this);

        findViewById(R.id.search_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent searchBarAcivity = new Intent(HomeActivity.this, SearchBarActivity.class);

                searchBarAcivity.putExtra("vehicle",true);

                startActivity(searchBarAcivity);

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

        recyclerView = findViewById(R.id.locationRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        locationAdapter = new LocationAdapter(HomeActivity.this, arealist);
        recyclerView.setAdapter(locationAdapter);


        locationAdapter.setOnClickListener(new LocationAdapter.onClickListener() {
            @Override
            public void onClick(RequestArea requestArea) {

                if (requestArea != null) {

                    mGoogleMap.clear();

                    HomeActivity.this.requestArea = requestArea;

//                    showMarker(Double.parseDouble(requestArea.getArealatitude()), Double.parseDouble(requestArea.getArealongitude()), 1, requestArea.getName());

                    ShowLoadingDialogFragment showLoadingDialogFragment = new ShowLoadingDialogFragment();

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("loadingList", loadinglist);
                    showLoadingDialogFragment.setArguments(bundle);

                    showLoadingDialogFragment.show(getSupportFragmentManager(), "showLoadingFragment");

                    showLoadingDialogFragment.setOnClickListener(new ShowLoadingDialogFragment.onClickListener() {
                        @Override
                        public void onClick(String loading) {

                            HomeActivity.this.loading = loading;

                            ArrayList<ResponseMine> allMineOfSingleArea = getAllMineOfSingleArea(requestArea.getName(), loading);

                            for (ResponseMine responseMine : allMineOfSingleArea) {

                                showMarkerOfArea(Double.parseDouble(responseMine.getLatitude()), Double.parseDouble(responseMine.getLongitude()), responseMine,loading);


                            }

                        }
                    });

                    goToLocationWithAnimation(Double.parseDouble(requestArea.getArealatitude()), Double.parseDouble(requestArea.getArealongitude()), 12);

                } else {

                    RequestCoordinates requestCoordinates = new RequestCoordinates();
                    requestCoordinates.setLatitude(Mylatitude);
                    requestCoordinates.setLongitude(Mylongitude);
                    getNearmeArea(PreferenceUtil.getData(HomeActivity.this, "token"), requestCoordinates);

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

                SharedPreferences sharedPreferences = getSharedPreferences("currentLocation", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("lat", String.valueOf(Mylatitude));
                edit.putString("long", String.valueOf(Mylongitude));
                edit.apply();

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
                        goToLocationWithAnimation(Mylatitude, Mylongitude, 9);


                } else {

                    if (Mylatitude != 0 && Mylongitude != 0)
                        goToLocationWithAnimation(Mylatitude, Mylongitude, 9);

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


    private void getBanners(String token) {
        api.getBanners(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);
                no_internet_connection.setVisibility(View.GONE);

                if (response.code() == 200) {

                    Type collectionType = new TypeToken<ArrayList<ResponseBanner>>() {
                    }.getType();
                    try {
                        responseBannerArrayList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (responseBannerArrayList.isEmpty()) {

                        Toast.makeText(HomeActivity.this, "No Banner Found", Toast.LENGTH_SHORT).show();


                    } else {
                        fullMetalAdapter.notifyDataSetChanged();

                        handler.postDelayed(runnable,SPEED_SCROLL);

                        viewPager.setVisibility(View.VISIBLE);

                        Log.d("minal", responseBannerArrayList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);
                no_internet_connection.setVisibility(View.VISIBLE);

            }
        });
    }

    private void getMinesFromServer(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);
        no_internet_connection.setVisibility(View.GONE);

        api.getAllMineVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    getBanners(PreferenceUtil.getData(HomeActivity.this, "token"));

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
                            areas.put(mine.getArea(), new RequestArea(mine.getArea(), mine.getArealatitude(), mine.getArealongitude(), mine.getAreaimageurl()));
                            for (ResponseLoading loading : mine.getLoading()) {
                                loadings.add(loading.getLoadingName());
                            }
                        }

                        for (Map.Entry<String, RequestArea> a : areas.entrySet()) {
                            areass.add(a.getValue());
                        }

                        loadinglist.addAll(loadings);
                        arealist.addAll(areass);
                        locationAdapter.notifyDataSetChanged();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                // reseller recyclerView.getChildAt(1).findViewById(R.id.postion_any)
                                myUtils.spotLightOnProfile(navigation.getIconAt(3), "1001", HomeActivity.this, "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem ipum");

                                myUtils.setListener(new MyUtils.listener() {
                                    @Override
                                    public void clickedSpotlight(String perfect) {

                                        if (perfect != null && perfect.equals("1001")) {

                                            myUtils.spotLightOnProfile(navigation.getIconAt(2), "1002", HomeActivity.this, "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem ipum");

                                        } else if (perfect != null && perfect.equals("1002")) {

                                            myUtils.spotLightOnProfile(viewById, "1003", HomeActivity.this, "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem ipum");

                                        } else if (perfect != null && perfect.equals("1003")) {

                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    myUtils.spotLightOnProfile(recyclerView.getChildAt(1).findViewById(R.id.postion_any), "1004", HomeActivity.this, "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "Lorem ipum");

                                                }
                                            },500);

                                        }

                                    }
                                });

                            }
                        }, 2000);


                    }

                } else {

                    parent_of_loading.setVisibility(View.GONE);
                    no_internet_connection.setVisibility(View.VISIBLE);

                    Toast.makeText(HomeActivity.this, "Somrthing Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);
                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(HomeActivity.this, "Somrthing Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void initViewImageCrousel() {

        DisplayMetrics metrics = getDisplayMetrics();
        fullMetalAdapter = new YourCoolAdapter(metrics, responseBannerArrayList, HomeActivity.this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(fullMetalAdapter);

    }

    private DisplayMetrics getDisplayMetrics() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }

    private void updateFirebase(String token, ResponseFirebase firebase)
    {
        api.updateFirebase(token,firebase).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void deleteFirebase(String token, ResponseFirebase firebase)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.deleteFirebase(token,firebase).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(HomeActivity.this, "No Internet Connection! Try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getNearmeArea(String token, RequestCoordinates coordinates) {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.nearmeArea(token, coordinates).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    try {
                        areaname = response.body().string().toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mGoogleMap.clear();

                    ShowLoadingDialogFragment showLoadingDialogFragment = new ShowLoadingDialogFragment();

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("loadingList", loadinglist);
                    showLoadingDialogFragment.setArguments(bundle);

                    showLoadingDialogFragment.show(getSupportFragmentManager(), "showLoadingFragment");

                    showLoadingDialogFragment.setOnClickListener(new ShowLoadingDialogFragment.onClickListener() {
                        @Override
                        public void onClick(String loading) {

                            HomeActivity.this.loading = loading;

                            ArrayList<ResponseMine> allMineOfSingleArea = getAllMineOfSingleArea(areaname, loading);

                            for (ResponseMine responseMine : allMineOfSingleArea) {

                                showMarkerOfArea(Double.parseDouble(responseMine.getLatitude()), Double.parseDouble(responseMine.getLongitude()), responseMine,loading);

                            }

                        }
                    });

                } else {

                    parent_of_loading.setVisibility(View.GONE);

                    Toast.makeText(HomeActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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
                for (ResponseLoading l : m.getLoading()) {
                    if (l.getLoadingName().equalsIgnoreCase(loading)) {
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
                        intent.putExtra("stringText", "vehicleOwner");
                        startActivity(intent);

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent = new Intent(HomeActivity.this, VehicleOwnerKycActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(HomeActivity.this, EmergencyContactActivity.class));

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
                        refer_intent.putExtra("keyword", "refer");
                        startActivity(refer_intent);

                        break;

                    case R.id.reward_program:

                        Intent rewardIntent = new Intent(HomeActivity.this, ReferActivity.class);
                        rewardIntent.putExtra("keyword", "reward");
                        startActivity(rewardIntent);

                        break;


                    case R.id.contact_us:

                        String phone = "7847072064";
                        Intent phoneCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(phoneCall);

                        break;

                    case R.id.bank_details:

                        Intent bank_details=new Intent(HomeActivity.this,BankDetailsActivity.class);
                        startActivity(bank_details);

                        break;


                    case R.id.logout:

                        PreferenceUtil.putData(HomeActivity.this, "token", "");

                        ResponseFirebase responseFirebase=new ResponseFirebase();
                        responseFirebase.setFirebase(token);

                        deleteFirebase(PreferenceUtil.getData(HomeActivity.this, "token"),responseFirebase);

                        break;

                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }

    private void goToLocationWithAnimation(double latitude, double longitude, int i) {

        LatLng latLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, i);

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

                    MineChooseFragment mineChooseFragment=new MineChooseFragment();

                    Bundle bundle=new Bundle();
                    bundle.putString("minename",responseMine.getName());
                    bundle.putString("loading",loading);

                    mineChooseFragment.setArguments(bundle);

                    mineChooseFragment.setCancelable(false);

                    mineChooseFragment.show(getSupportFragmentManager(),"mineChooseFragment");

                    mineChooseFragment.setOnClickListener(new MineChooseFragment.onClickListener() {
                        @Override
                        public void onClick() {

                            Intent intent = new Intent(HomeActivity.this, SelectYourVehicleActivity.class);
                            intent.putExtra("vehicle",true);
                            ArrayList<ResponseLoading> arrayList = responseMine.getLoading();
                            int rate = 5;
                            int etl = 0;
                            for(ResponseLoading l: arrayList)
                            {
                                if(l.getLoadingName().equalsIgnoreCase(loading))
                                {
                                    rate = l.getRate();
                                    etl = l.getEtl();

                                }
                            }

                            intent.putExtra("mineid", responseMine.getId());
                            intent.putExtra("minename", responseMine.getName());
                            intent.putExtra("loading", loading);
                            intent.putExtra("rate", rate);
                            intent.putExtra("etl", etl);

                            startActivity(intent);

                        }
                    });

                }

                return false;
            }
        });

    }

    private void showMarkerOfArea(double latituteOfTajMahal, double longitudeOfTajMahal, ResponseMine responseMine,String loadingname) {

        LatLng latLng = new LatLng(latituteOfTajMahal, longitudeOfTajMahal);

        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(String.valueOf(responseMine.getName()));
        markerOptions.snippet("This is my spot!");
        Marker marker = mGoogleMap.addMarker(markerOptions);

        ArrayList<ResponseLoading> arrayList = responseMine.getLoading();
        int rate = 5;
        int etl = 0;
        for(ResponseLoading l: arrayList)
        {
            if(l.getLoadingName().equalsIgnoreCase(loadingname))
            {
                rate = l.getRate();
                etl = l.getEtl();

            }
        }
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Rate : " + rate + "\n" + "Etl : " + etl)));

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
            goToLocationWithAnimation(Mylatitude, Mylongitude, 9);

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