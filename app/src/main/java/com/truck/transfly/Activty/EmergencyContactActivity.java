package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.truck.transfly.Model.RequestEmergencyContact2;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityEmergencyContactBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmergencyContactActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ActivityEmergencyContactBinding activity;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_emergency_contact);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading=findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        String token = PreferenceUtil.getData(EmergencyContactActivity.this, "token");

        String s = token.split(":")[0];

        switch (s){

            case "areamanager":

                ResponseAreaManager responseAreaManager = ((TransflyApplication) getApplication()).getResponseAreaManager();

                activity.name.setText(responseAreaManager.getEname());
                activity.phoneNumber.setText(responseAreaManager.getEmobile());
                activity.relative.setText(responseAreaManager.getErelation());

                break;

            case "fieldstaff":

                ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();

                activity.name.setText(responseFieldStaff.getEname());
                activity.phoneNumber.setText(responseFieldStaff.getEmobile());
                activity.relative.setText(responseFieldStaff.getErelation());

                break;

            case "transporter":

                ResponseTransporter responseTransporter = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

                activity.name.setText(responseTransporter.getEname());
                activity.phoneNumber.setText(responseTransporter.getEmobile());
                activity.relative.setText(responseTransporter.getErelation());

                break;

            case "vehicleowner":

                ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

                activity.name.setText(responseVehicleOwner.getEname());
                activity.phoneNumber.setText(responseVehicleOwner.getEmobile());
                activity.relative.setText(responseVehicleOwner.getErelation());

                break;

        }

        activity.submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(activity.name.getText().toString())){

                    activity.name.setError("name is Empty*");
                    activity.name.requestFocus();

                } else if(TextUtils.isEmpty(activity.phoneNumber.getText().toString())){

                    activity.phoneNumber.setError("Phone Number is Empty*");
                    activity.phoneNumber.requestFocus();

                } else if(TextUtils.isEmpty(activity.relative.getText().toString())){

                    activity.relative.setError("Enter Your Relationship Like Mother , Father ,friend*");
                    activity.relative.requestFocus();

                } else {

                    RequestEmergencyContact2 contact=new RequestEmergencyContact2();
                    contact.setEmergencyName(activity.name.getText().toString());
                    contact.setEmergencyMobile(activity.phoneNumber.getText().toString());
                    contact.setEmergencyRelation(activity.relative.getText().toString());

                    updateEmergencyContact(PreferenceUtil.getData(EmergencyContactActivity.this,"token"),contact);

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

    private void updateEmergencyContact(String token, RequestEmergencyContact2 contact) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.updateEmergencyContact2(token, contact).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    parent_of_loading.setVisibility(View.GONE);

                    setDataOnModels();

                    Toast.makeText(EmergencyContactActivity.this, "Emergency Contact Change Successful", Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    parent_of_loading.setVisibility(View.GONE);

                    Toast.makeText(EmergencyContactActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(EmergencyContactActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }

    private void setDataOnModels() {

        String token = PreferenceUtil.getData(EmergencyContactActivity.this, "token");

        String s = token.split(":")[0];

        switch (s){

            case "transporter":

                ResponseTransporter responseTransporterOwner = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

                responseTransporterOwner.setEmobile(activity.phoneNumber.getText().toString());
                responseTransporterOwner.setEname(activity.name.getText().toString());
                responseTransporterOwner.setErelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporterOwner);

                break;

            case "areamanager":

                ResponseAreaManager responseAreaManager = ((TransflyApplication) getApplication()).getResponseAreaManager();

                responseAreaManager.setEmobile(activity.phoneNumber.getText().toString());
                responseAreaManager.setEname(activity.name.getText().toString());
                responseAreaManager.setErelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);

                break;

            case "fieldstaff":

                ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();
                responseFieldStaff.setEmobile(activity.phoneNumber.getText().toString());
                responseFieldStaff.setEname(activity.name.getText().toString());
                responseFieldStaff.setErelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);

                break;

            case "vehicleowner":

                ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();
                responseVehicleOwner.setEmobile(activity.phoneNumber.getText().toString());
                responseVehicleOwner.setEname(activity.name.getText().toString());
                responseVehicleOwner.setErelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                break;


        }

    }


}