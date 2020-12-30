package com.truck.transfly.Activty;

import android.os.Bundle;
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
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivitySelectYourVehicleBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_select_your_vehicle);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);
        activity.noVehicleFound.setVisibility(View.GONE);

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

        activity.fromDest.setText(minename);
        activity.fromDest.setEnabled(false);

        activity.toDest.setText(loading);
        activity.toDest.setEnabled(false);

        initSmallIconAdapter();

        getAllVehicles(PreferenceUtil.getData(SelectYourVehicleActivity.this, "token"));

        activity.creaateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(responseVehicle!=null)
                sendDataOnServer(responseVehicle.getActive(),responseVehicle.getStatus());

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

                SelectYourVehicleActivity.this.responseVehicle=responseVehicle;

            }
        });

    }

    private void sendDataOnServer(Boolean active, int status) {

        if(status==0){

            Toast.makeText(this, "This vehicle is Not Approved", Toast.LENGTH_SHORT).show();

        } else if(!active) {

            Toast.makeText(this, "This vehicle is Already in booking", Toast.LENGTH_SHORT).show();

        } else {

            RequestBooking requestBooking=new RequestBooking();
            requestBooking.setLoading(loading);
            requestBooking.setMineid(mineid);
            requestBooking.setMinename(minename);
            requestBooking.setVehiclenumber(responseVehicle.getNumber());
            createBooking(PreferenceUtil.getData(SelectYourVehicleActivity.this,"token"),requestBooking);

        }

    }

    private void createBooking(String token, RequestBooking booking)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.createBooking(token,booking).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    Toast.makeText(SelectYourVehicleActivity.this, "Booking Created Successfully", Toast.LENGTH_SHORT).show();
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

}