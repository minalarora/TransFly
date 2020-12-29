package com.truck.transfly.Activty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.SmallIconsAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_select_your_vehicle);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        mineid = getIntent().getIntExtra("mineid", 0);
        minename = getIntent().getStringExtra("minename");
        loading = getIntent().getStringExtra("loading");

        initSmallIconAdapter();

        getAllVehicles(PreferenceUtil.getData(SelectYourVehicleActivity.this, "token"));

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
                    } else {
                        //['pan','aadhaar','bank']

                        smallIconsAdapter.notifyDataSetChanged();
                        Log.d("minal", vehicleList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

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

                if(responseVehicle!=null)
                sendDataOnServer(Objects.requireNonNull(responseVehicle.getActive()));

            }
        });

    }

    private void sendDataOnServer(Boolean active) {

        if(active){


        } else {



        }

    }
}