package com.truck.transfly.Activty;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.Model.RequestEmergencyContact;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityEmergencyContactVehicleBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmergencyContactVehicleActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ActivityEmergencyContactVehicleBinding activity;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_emergency_contact_vehicle);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();
//        activity.phoneNumber.setText(responseVehicleOwner.getEmergencycontact());

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(activity.phoneNumber.getText().toString())) {

                    activity.phoneNumber.setError("Phone Number is Empty*");
                    activity.phoneNumber.requestFocus();

                } else {

                    RequestEmergencyContact requestEmergencyContact = new RequestEmergencyContact();
                    requestEmergencyContact.setEmergencyContact(activity.phoneNumber.getText().toString());

                    updateEmergencyContact(PreferenceUtil.getData(EmergencyContactVehicleActivity.this, "token"), requestEmergencyContact);

                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

    }

    private void updateEmergencyContact(String token, RequestEmergencyContact contact) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.updateEmergencyContact(token, contact).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    parent_of_loading.setVisibility(View.GONE);

                    setOnModels();

                    Toast.makeText(EmergencyContactVehicleActivity.this, "Update Conatct Successfully", Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    parent_of_loading.setVisibility(View.GONE);

                    Toast.makeText(EmergencyContactVehicleActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(EmergencyContactVehicleActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }

    private void setOnModels() {

        ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();
//        responseVehicleOwner.setEmergencycontact(activity.phoneNumber.getText().toString());
        ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

    }

}