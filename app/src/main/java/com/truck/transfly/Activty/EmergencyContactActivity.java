package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.truck.transfly.Model.RequestEmergencyContact;
import com.truck.transfly.Model.RequestEmergencyContact2;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicle;
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

                activity.name.setText(responseAreaManager.getEmergencyName());
                activity.phoneNumber.setText(responseAreaManager.getEmergencyMobile());
                activity.relative.setText(responseAreaManager.getEmergencyRelation());

                break;

            case "fieldstaff":

                ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();

                activity.name.setText(responseFieldStaff.getEmergencyName());
                activity.phoneNumber.setText(responseFieldStaff.getEmergencyMobile());
                activity.relative.setText(responseFieldStaff.getEmergencyRelation());

                break;

            case "transporter":

                ResponseTransporter responseTransporter = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

                activity.name.setText(responseTransporter.getEmergencyName());
                activity.phoneNumber.setText(responseTransporter.getEmergencyMobile());
                activity.relative.setText(responseTransporter.getEmergencyRelation());

                break;

            case "vehicleowner":

                ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

                activity.name.setText(responseVehicleOwner.getEmergencyName());
                activity.phoneNumber.setText(responseVehicleOwner.getEmergencyMobile());
                activity.relative.setText(responseVehicleOwner.getEmergencyRelation());

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

                responseTransporterOwner.setEmergencyMobile(activity.phoneNumber.getText().toString());
                responseTransporterOwner.setEmergencyName(activity.name.getText().toString());
                responseTransporterOwner.setEmergencyRelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporterOwner);

                break;

            case "areamanager":

                ResponseAreaManager responseAreaManager = ((TransflyApplication) getApplication()).getResponseAreaManager();

                responseAreaManager.setEmergencyMobile(activity.phoneNumber.getText().toString());
                responseAreaManager.setEmergencyName(activity.name.getText().toString());
                responseAreaManager.setEmergencyRelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);

                break;

            case "fieldstaff":

                ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();
                responseFieldStaff.setEmergencyMobile(activity.phoneNumber.getText().toString());
                responseFieldStaff.setEmergencyName(activity.name.getText().toString());
                responseFieldStaff.setEmergencyRelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);

                break;

            case "vehicleowner":

                ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();
                responseVehicleOwner.setEmergencyMobile(activity.phoneNumber.getText().toString());
                responseVehicleOwner.setEmergencyName(activity.name.getText().toString());
                responseVehicleOwner.setEmergencyRelation(activity.relative.getText().toString());

                ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                break;


        }

    }


}