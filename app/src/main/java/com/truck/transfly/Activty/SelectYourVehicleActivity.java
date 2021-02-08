package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.SmallIconsAdapter;
import com.truck.transfly.Model.RequestBooking;
import com.truck.transfly.Model.ResponseVehicle;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivitySelectYourVehicleBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectYourVehicleActivity extends AppCompatActivity {

    private List<String> stringList = new ArrayList<>();
    private ActivitySelectYourVehicleBinding activity;
    private SmallIconsAdapter smallIconsAdapter;
    private int mineid;
    private String minename;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String loading;
    private ArrayList<ResponseVehicle> vehicleList = new ArrayList<>();
    private FrameLayout parent_of_loading;
    private ResponseVehicle responseVehicle;
    private RelativeLayout no_internet_connection;
    private int rate, etl;
    private boolean vehicleOwner;
    private int tyres;
    private boolean trailor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_select_your_vehicle);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);
        activity.noVehicleFound.setVisibility(View.GONE);

        activity.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        if (getIntent() != null) {

            vehicleOwner = getIntent().getBooleanExtra("vehicle", false);

            if (!vehicleOwner) {

                activity.showSmallIcons.setVisibility(View.GONE);
                activity.toolbarText.setText("Rate And ETL");
                activity.creaateBooking.setVisibility(View.GONE);
                activity.noVehicleFound.setVisibility(View.GONE);
                activity.parentOfNumber.setVisibility(View.GONE);

            }

        }

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicleList.clear();
                no_internet_connection.setVisibility(View.GONE);
                smallIconsAdapter.notifyDataSetChanged();
                getAllVehicles(PreferenceUtil.getData(SelectYourVehicleActivity.this, "token"));

            }
        });

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        mineid = getIntent().getIntExtra("mineid", 0);
        minename = getIntent().getStringExtra("minename");
        loading = getIntent().getStringExtra("loading");
        rate = getIntent().getIntExtra("rate", 0);
        etl = getIntent().getIntExtra("etl", 0);
        tyres = getIntent().getIntExtra("tyres", 0);
        trailor = getIntent().getBooleanExtra("trailor", false);

        activity.fromDest.setText(minename);
        activity.fromDest.setEnabled(false);

        activity.toDest.setText(loading);
        activity.toDest.setEnabled(false);

        activity.rate.setText(String.valueOf(rate));
        activity.timeOfLoading.setText(String.valueOf(etl+" hrs"));

        initSmallIconAdapter();

        getAllVehicles(PreferenceUtil.getData(SelectYourVehicleActivity.this, "token"));

        activity.creaateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (responseVehicle != null)
                    sendDataOnServer(responseVehicle.getActive(), responseVehicle.getStatus());

            }
        });

    }

    private void getAllVehicles(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.getAllVehicles(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseVehicle>>() {
                    }.getType();
                    try {
                        vehicleList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (vehicleList.isEmpty()) {

                        Log.d("minal", "no vehicle");

                        if (vehicleOwner)
                            activity.noVehicleFound.setVisibility(View.VISIBLE);

                    } else {
                        //['pan','aadhaar','bank']

                        smallIconsAdapter.notifyDataSetChanged();
                        Log.d("minal", vehicleList.toString());

                        activity.noVehicleFound.setVisibility(View.GONE);

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

    private void initSmallIconAdapter() {

        RecyclerView recyclerView = findViewById(R.id.show_small_icons);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(SelectYourVehicleActivity.this, 1, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        smallIconsAdapter = new SmallIconsAdapter(SelectYourVehicleActivity.this, vehicleList);
        recyclerView.setAdapter(smallIconsAdapter);

        smallIconsAdapter.setOnClickListener(new SmallIconsAdapter.onClickListener() {
            @Override
            public void onClick(ResponseVehicle responseVehicle) {

                SelectYourVehicleActivity.this.responseVehicle = responseVehicle;
                activity.mobileNumber.setText(responseVehicle.getContact());

            }
        });

    }

    private void sendDataOnServer(Boolean active, int status) {

        ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

        if (status == 0) {

            Toast.makeText(this, "This vehicle is getting approved, ensure KYC completion under 'My Profile'", Toast.LENGTH_SHORT).show();

        }
        else if(activity.noVehicleFound.getVisibility()==View.VISIBLE)
        {
            Toast.makeText(this, "Please Add Your Vehicles First", Toast.LENGTH_SHORT).show();
        }
        else if (!active) {

            Toast.makeText(this, "This Vehicle is Already in booking", Toast.LENGTH_SHORT).show();

        } else if (responseVehicleOwner.getStatus() != 2) {

            Toast.makeText(this, "Complete Your KYC First!", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(activity.mobileNumber.getText().toString())) {

            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (activity.mobileNumber.length() < 10) {

            Toast.makeText(this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (responseVehicle != null && responseVehicle.getTyres() != null && !isValid(tyres, trailor, responseVehicle.getTyres())) {

            Toast.makeText(this, "Trailer not allowed in this mine, find more mines from Search field", Toast.LENGTH_SHORT).show();

        } else {

            RequestBooking requestBooking = new RequestBooking();
            requestBooking.setLoading(loading);
            requestBooking.setMineid(mineid);
            requestBooking.setMinename(minename);
            requestBooking.setContact(activity.mobileNumber.getText().toString());
            requestBooking.setVehiclenumber(responseVehicle.getNumber());
            createBooking(PreferenceUtil.getData(SelectYourVehicleActivity.this, "token"), requestBooking);

        }

    }

    private void createBooking(String token, RequestBooking booking) {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.createBooking(token, booking).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Toast.makeText(SelectYourVehicleActivity.this, "Booking Created Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SelectYourVehicleActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); //todo flAGS SETS IN FUTURE
                    startActivity(intent);

                    finish();


                } else {

                    Toast.makeText(SelectYourVehicleActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(SelectYourVehicleActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isValid(int tyresMine, boolean mineTrailer, String vehicle) {
        int vehicleTyres = Integer.parseInt(vehicle.split("-")[0].trim());
        String trailerString = "";
        try {
            trailerString = vehicle.split("-")[1].trim();
        } catch (Exception e) {

        }

        boolean vehicleTrailer = true;
        if (vehicleTyres < 18) {
            if (trailerString.equalsIgnoreCase("TRAILOR")) {

            } else {
                vehicleTrailer = false;
            }
        }
        if (vehicleTyres > tyresMine) {
            return false;
        } else {
            if (mineTrailer) {
                return true;
            } else {
                return (vehicleTrailer == mineTrailer);
            }
        }


    }


    public void gotoAdd(View view) {
        Intent i =new Intent(SelectYourVehicleActivity.this,AddVehicleActivity.class);
        startActivity(i);

        finish();

    }
}