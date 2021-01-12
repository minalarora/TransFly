package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.SpinnerTraspoterAdapter;
import com.truck.transfly.Model.RequestInvoice;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseTransporter;
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
    private ArrayList<ResponseTransporter> transportersList = new ArrayList<>();
    private ApiEndpoints api = null;
    private ResponseBooking responseBooking;
    private SpinnerTraspoterAdapter spinnerTraspoterAdapter;
    private RelativeLayout no_internet_connection;

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

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_internet_connection.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                 spinnerTraspoterAdapter.notifyDataSetChanged();

                 vehicleList.clear();
                 transportersList.clear();

                getVehicleFieldStaff(PreferenceUtil.getData(FieldStafBookingConfirmationActivity.this, "token"), responseBooking.getOwner());

            }
        });

        activity.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

//        vehi/leList.add(responseBooking.getVehiclename());

        spinnerTraspoterAdapter = new SpinnerTraspoterAdapter(this, transportersList);

        activity.transporterName.setAdapter(spinnerTraspoterAdapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, vehicleList);

        activity.registerCategory.setAdapter(adapter);
        activity.registerCategory.setSelection(vehicleList.indexOf(responseBooking.getVehiclename()));

        getVehicleFieldStaff(PreferenceUtil.getData(FieldStafBookingConfirmationActivity.this, "token"), responseBooking.getOwner());

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

                    RequestInvoice requestInvoice = new RequestInvoice();
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
                    requestInvoice.setOwner(responseBooking.getOwner());

                    ResponseTransporter selectedItem = (ResponseTransporter) activity.transporterName.getSelectedItem();

                    requestInvoice.setTransporterMobile(selectedItem.getId());

                    confirmBooking(PreferenceUtil.getData(FieldStafBookingConfirmationActivity.this, "token"), requestInvoice);

                }

            }
        });

    }

    private void onTransporterList(String token) {
        api.getTransporterList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseTransporter>>() {
                    }.getType();
                    try {
                        transportersList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (transportersList.isEmpty()) {

                        activity.transporterName.setVisibility(View.GONE);
                        Log.d("minal", "no vehicle");
                    } else {
                        //['pan','aadhaar','bank']

                        spinnerTraspoterAdapter.notifyDataSetChanged();
                        Log.d("minal", transportersList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                no_internet_connection.setVisibility(View.VISIBLE);
                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }

    private void confirmBooking(String token, RequestInvoice invoice) {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.confirmBooking(token, invoice).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
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

        parent_of_loading.setVisibility(View.VISIBLE);

        api.getVehicleFieldStaff(token, mobileofvehicleowner).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                onTransporterList(PreferenceUtil.getData(FieldStafBookingConfirmationActivity.this,"token"));

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

                no_internet_connection.setVisibility(View.VISIBLE);

                parent_of_loading.setVisibility(View.GONE);

            }
        });

    }
}