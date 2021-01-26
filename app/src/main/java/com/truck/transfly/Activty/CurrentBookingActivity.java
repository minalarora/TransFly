package com.truck.transfly.Activty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.CurrentBookingAdapter;
import com.truck.transfly.Frament.RemoveDialogFragment;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityCurrentBookingBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CurrentBookingActivity extends AppCompatActivity {

    private List<String> stringList = new ArrayList<>();
    private ActivityCurrentBookingBinding activity;
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseBooking> bookingList = new ArrayList<>();
    private CurrentBookingAdapter currentBookingAdapter;
    private TextView no_booking_data;
    private RelativeLayout no_internet_connection;
    private String lat;
    private String aLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_current_booking);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        SharedPreferences sharedPreferences=getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
        lat = sharedPreferences.getString("lat", "");
        aLongitude = sharedPreferences.getString("long", "");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookingList.clear();
                no_internet_connection.setVisibility(View.GONE);
                getAllBookingVehicleOwner(PreferenceUtil.getData(CurrentBookingActivity.this,"token"));

            }
        });

        no_booking_data = findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        RecyclerView current_booking_recycler = findViewById(R.id.current_booking_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        current_booking_recycler.setLayoutManager(linearLayoutManager);
        currentBookingAdapter = new CurrentBookingAdapter(this, bookingList);
        current_booking_recycler.setAdapter(currentBookingAdapter);

        currentBookingAdapter.setOnClickListener(new CurrentBookingAdapter.onClickListener() {
            @Override
            public void onClick(ResponseBooking responseBooking, int position, boolean b) {

                if(b){

                    getSingleMine(PreferenceUtil.getData(CurrentBookingActivity.this,"token"), responseBooking.getMineid());

                    return;
                }

                RemoveDialogFragment removeDialogFragment=new RemoveDialogFragment();
                removeDialogFragment.setCancelable(false);
                removeDialogFragment.show(getSupportFragmentManager(),"removeDialogFragment");

                removeDialogFragment.setOnClickListener(new RemoveDialogFragment.onClickListener() {
                    @Override
                    public void onClick() {

                         deleteBooking(PreferenceUtil.getData(CurrentBookingActivity.this,"token"),responseBooking.getId(),position);

                    }
                });

            }
        });

        getAllBookingVehicleOwner(PreferenceUtil.getData(CurrentBookingActivity.this,"token"));

    }

    private void getSingleMine(String token, int id)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getSingleMine(token,id).enqueue(new Callback<ResponseMine>() {
            @Override
            public void onResponse(Call<ResponseMine> call, Response<ResponseMine> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    ResponseMine mine  = response.body();

                    if(mine==null){

                        parent_of_loading.setVisibility(View.GONE);
                        Toast.makeText(CurrentBookingActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                        return;

                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    String uri = "http://maps.google.com/maps?saddr=" + lat + "," + aLongitude + "&daddr=" + mine.getLatitude() + "," + mine.getLongitude();

                    intent.setData(Uri.parse(uri));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                } else {

                    parent_of_loading.setVisibility(View.GONE);
                    Toast.makeText(CurrentBookingActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMine> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);
                Toast.makeText(CurrentBookingActivity.this, "No Internet Connection! Try Again", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void deleteBooking(String token, int id, int position)
    {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.deleteBooking(token,id).enqueue(new Callback<ResponseBooking>() {
            @Override
            public void onResponse(Call<ResponseBooking> call, Response<ResponseBooking> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    bookingList.remove(position);
                    currentBookingAdapter.notifyDataSetChanged();

                    Toast.makeText(CurrentBookingActivity.this, "Booking deleted successfully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBooking> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(CurrentBookingActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAllBookingVehicleOwner(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.getBookingVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseBooking>>() {
                    }.getType();
                    try {
                        bookingList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (bookingList.isEmpty()) {

                        no_booking_data.setVisibility(View.VISIBLE);

                        Log.d("minal", "no vehicle");
                    } else {
                        //['pan','aadhaar','bank']

                        no_booking_data.setVisibility(View.GONE);

                        currentBookingAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(CurrentBookingActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

}