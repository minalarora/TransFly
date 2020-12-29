package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.truck.transfly.Model.RequestUser;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivitySignUpBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private String mobileNo;
    private String userType;
    private ActivitySignUpBinding activity;
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

        mobileNo = getIntent().getStringExtra("mobileNo");
        userType = getIntent().getStringExtra("type");

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        findViewById(R.id.sign_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(activity.fullName.getText().toString())){

                    activity.fullName.setError("Full Name is Empty*");
                    activity.fullName.requestFocus();

                } else if(TextUtils.isEmpty(activity.email.getText().toString())){

                    activity.email.setError("Email is Empty*");
                    activity.email.requestFocus();

                } else if(!isValidEmail(activity.email.getText().toString())){

                    activity.email.setError("Email is Not Valid*");
                    activity.email.requestFocus();

                } else if(TextUtils.isEmpty(activity.password.getText().toString())){

                    activity.password.setError("Password is Empty*");
                    activity.password.requestFocus();

                } else if(TextUtils.isEmpty(activity.retypePassword.getText().toString())){

                    activity.retypePassword.setError("Retype password is Empty*");
                    activity.retypePassword.requestFocus();

                } else if(!activity.password.getText().toString().equals(activity.retypePassword.getText().toString())){

                    activity.retypePassword.setError("Password No Matched*");
                    activity.retypePassword.requestFocus();

                } else {

                    parent_of_loading.setVisibility(View.VISIBLE);

                    createUserByType();

                }

//                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));

            }
        });

    }

    private void createUserByType() {

        switch (userType) {
            case "vehicleowner": {
                createVehicleOwner(new RequestUser(activity.fullName.getText().toString(),activity.email.getText().toString(),mobileNo,activity.password.getText().toString()));
                break;
            }
            case "areamanager": {
                createAreaManager(new RequestUser(activity.fullName.getText().toString(),activity.email.getText().toString(),mobileNo,activity.password.getText().toString()));
                break;
            }
            case "transporter": {
                createTransporter(new RequestUser(activity.fullName.getText().toString(),activity.email.getText().toString(),mobileNo,activity.password.getText().toString()));
                break;
            }
            case "fieldstaff": {
                createFieldStaff(new RequestUser(activity.fullName.getText().toString(),activity.email.getText().toString(),mobileNo,activity.password.getText().toString()));
                break;
            }

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private  void createVehicleOwner(RequestUser user)
    {
        api.createVehicleOwner(user).enqueue(new Callback<ResponseVehicleOwner>() {
            @Override
            public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {
                if(response.code() == 200)
                {
                    ResponseVehicleOwner vehicleOwner = response.body();
                    PreferenceUtil.putData(SignUpActivity.this,"token",vehicleOwner.getToken());
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();
                    Log.d("minal",vehicleOwner.toString());

                    setError(false);

                }
                else
                {
                    setError(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {
                //no internet connection
            }
        });
    }

    private  void createFieldStaff(RequestUser user)
    {


        api.createFieldStaff(user).enqueue(new Callback<ResponseFieldStaff>() {
            @Override
            public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {
                if(response.code() == 200)
                {
                    ResponseFieldStaff fieldStaff = response.body();

                    PreferenceUtil.putData(SignUpActivity.this,"token",fieldStaff.getToken());

                    Intent intent = new Intent(SignUpActivity.this, FieldStafActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();

                    setError(false);

                }
                else
                {
                    setError(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {

            }
        });
    }

    private  void createTransporter(RequestUser user)
    {


        api.createTransporter(user).enqueue(new Callback<ResponseTransporter>() {
            @Override
            public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {
                if(response.code() == 200)
                {
                    ResponseTransporter transporter = response.body();
                    PreferenceUtil.putData(SignUpActivity.this,"token",transporter.getToken());
                    Intent intent = new Intent(SignUpActivity.this, TransporterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();
                    setError(false);

                }
                else
                {
                    setError(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseTransporter> call, Throwable t) {

            }
        });
    }

    private  void createAreaManager(RequestUser user)
    {
        api.createAreaManager(user).enqueue(new Callback<ResponseAreaManager>() {
            @Override
            public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {
                if(response.code() == 200)
                {
                    ResponseAreaManager areaManager = response.body();
                    PreferenceUtil.putData(SignUpActivity.this,"token",areaManager.getToken());

                    Intent intent = new Intent(SignUpActivity.this, AreaManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();

                    setError(false);

                }
                else
                {
                    setError(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseAreaManager> call, Throwable t) {

            }
        });



    }

    private void setError(boolean b) {

        if(b){

            Toast.makeText(this, "Email And Mobile is already exist! try Again", Toast.LENGTH_SHORT).show();

        }

        parent_of_loading.setVisibility(View.GONE);

    }

}