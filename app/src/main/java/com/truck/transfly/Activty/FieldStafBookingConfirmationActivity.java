package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Model.RequestInvoice;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityFieldStafBookingConfirmationBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FieldStafBookingConfirmationActivity extends AppCompatActivity {

    private ActivityFieldStafBookingConfirmationBinding activity;
    private ArrayAdapter<String> adapter;
    private FrameLayout parent_of_loading;
    private ArrayList<String> vehicleList = new ArrayList<>();
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ResponseBooking responseBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_field_staf_booking_confirmation);

        Intent intent = getIntent();
        responseBooking = intent.getParcelableExtra("responseBooking");

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        activity.mobileNumber.setText(responseBooking.getVehicleownermobile());
        activity.ownerName.setText(responseBooking.getVehicleowner());
        activity.toFromDest.setText(responseBooking.getLoading() + " - " + responseBooking.getMinename());

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        vehicleList.add(responseBooking.getVehiclename());

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, vehicleList);

        activity.registerCategory.setAdapter(adapter);

        getVehicleFieldStaff(PreferenceUtil.getData(FieldStafBookingConfirmationActivity.this,"token"),responseBooking.getVehicleownermobile());

        activity.creaateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(activity.tonnege.getText().toString())) {

                    activity.tonnege.setError("Tonnege is Required*");
                    activity.tonnege.requestFocus();

                } else if (TextUtils.isEmpty(activity.rate.getText().toString())) {

                    activity.rate.setError("Rate is Required*");
                    activity.rate.requestFocus();

                } else if (TextUtils.isEmpty(activity.hsd.getText().toString())) {

                    activity.hsd.setError("HSD is Required*");
                    activity.hsd.requestFocus();

                } else if (TextUtils.isEmpty(activity.cash.getText().toString())) {

                    activity.cash.setError("Cash is Required*");
                    activity.cash.requestFocus();

                } else {

                    RequestInvoice requestInvoice=new RequestInvoice();
                    requestInvoice.setId(responseBooking.getId());
                    requestInvoice.setMineid(responseBooking.getMineid());
                    requestInvoice.setMinename(responseBooking.getMinename());
                    requestInvoice.setLoading(responseBooking.getLoading());
                    requestInvoice.setHsd(Integer.valueOf(activity.hsd.getText().toString()));
                    requestInvoice.setRate(Integer.valueOf(activity.rate.getText().toString()));
                    requestInvoice.setVehiclenumber(activity.registerCategory.getSelectedItem().toString());
                    requestInvoice.setVehicleowner(responseBooking.getVehicleowner());
                    requestInvoice.setVehicleownermobile(responseBooking.getVehicleownermobile());
                    requestInvoice.setTonnage(Integer.valueOf(activity.tonnege.getText().toString()));
                    requestInvoice.setCash(Integer.valueOf(activity.cash.getText().toString()));
                    confirmBooking(PreferenceUtil.getData(FieldStafBookingConfirmationActivity.this,"token"),requestInvoice);

                }

            }
        });

    }

    private void confirmBooking(String token, RequestInvoice invoice)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.confirmBooking(token,invoice).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    Toast.makeText(FieldStafBookingConfirmationActivity.this, "Booking Confirmation Successful", Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    Toast.makeText(FieldStafBookingConfirmationActivity.this, "Something went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(FieldStafBookingConfirmationActivity.this, "No Internet COnnection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getVehicleFieldStaff(String token, String mobileofvehicleowner) {
        api.getVehicleFieldStaff(token, mobileofvehicleowner).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    try {
                        vehicleList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (vehicleList.isEmpty()) {

                        //no vehicle
                        //show booking vehicle
                        activity.registerCategory.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();

                    } else {
                        //list of all vehicle

                        adapter.notifyDataSetChanged();


                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}