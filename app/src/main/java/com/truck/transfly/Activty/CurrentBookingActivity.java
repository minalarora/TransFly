package com.truck.transfly.Activty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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

        RecyclerView current_booking_recycler = findViewById(R.id.current_booking_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        current_booking_recycler.setLayoutManager(linearLayoutManager);
        currentBookingAdapter = new CurrentBookingAdapter(this, bookingList);
        current_booking_recycler.setAdapter(currentBookingAdapter);

        currentBookingAdapter.setOnClickListener(new CurrentBookingAdapter.onClickListener() {
            @Override
            public void onClick(ResponseBooking responseBooking, int position) {

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

                    Toast.makeText(CurrentBookingActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();

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

                        Log.d("minal", "no vehicle");
                    } else {
                        //['pan','aadhaar','bank']

                        currentBookingAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(CurrentBookingActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

}