package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.Frament.ForgotPasswordDialog;
import com.truck.transfly.Model.RequestCredentials;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityLoginBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.TransflyApplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String token;
    private ActivityLoginBinding activity;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_login);

        parent_of_loading=findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(activity.mobileNumber.getText().toString())){

                    activity.mobileNumber.setError("Mobile number is Required*");
                    activity.mobileNumber.requestFocus();

                } else if(TextUtils.isEmpty(activity.password.getText().toString())){

                    activity.password.setError("password is Required*");
                    activity.password.requestFocus();

                } else {

                    parent_of_loading.setVisibility(View.VISIBLE);
                    onLoginButton();

                }

            }
        });

        activity.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgotPasswordDialog forgotPasswordDialog=new ForgotPasswordDialog();
                forgotPasswordDialog.setCancelable(false);
                forgotPasswordDialog.show(getSupportFragmentManager(),"forgotPasswordDialog");

            }
        });

        activity.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,EnterMobileNumberActivty.class));
            }
        });

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }


    }


    private void onLoginButton()
    {
        try {
            RequestCredentials credentials = new RequestCredentials();
            credentials.setMobile(activity.mobileNumber.getText().toString());
            credentials.setPassword(activity.password.getText().toString());

            api.login(credentials).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200)
                    {
                        //user found

                        String type = response.body().toString();

                        Toast.makeText(LoginActivity.this, ""+type, Toast.LENGTH_SHORT).show();

                        switch (type)
                        {
                            case "vehicleowner" :{
                                onVehicleOwnerFound(credentials);
                                break;
                            }
                            case "areamanager":
                            {
                                onAreaManagerFound(credentials);
                                break;
                            }
                            case "transporter":
                            {
                                onTransporterFound(credentials);
                                break;
                            }
                            case "fieldstaff":
                            {
                                onFieldStaffFound(credentials);
                                break;
                            }
                            default:
                            {
                                activity.mobileNumber.setError("User not found");
                                activity.mobileNumber.requestFocus();

                                parent_of_loading.setVisibility(View.GONE);
                            }

                        }

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Something went wrong! Try Again", Toast.LENGTH_SHORT).show();

                        parent_of_loading.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    serverError();
                }
            });
        }
        catch (Exception e)
        {
            activity.mobileNumber.setError("User not found");
            activity.mobileNumber.requestFocus();

            parent_of_loading.setVisibility(View.GONE);

        }
    }

    private void onVehicleOwnerFound(RequestCredentials credentials)
    {

        try {
            api.vehicleOwnerLogin(credentials).enqueue(new Callback<ResponseVehicleOwner>() {
                @Override
                public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {
                    if(response.code() == 200)
                    {
                        //user found
                        ResponseVehicleOwner responseVehicleOwner = response.body();
                        ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();


                    }
                    else
                    {
                        activity.password.setError("Password is Wrong");
                        activity.password.requestFocus();

                        parent_of_loading.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {
                    serverError();
                }
            });
        }
        catch (Exception e)
        {
            activity.password.setError("Password is Wrong");
            activity.password.requestFocus();

            parent_of_loading.setVisibility(View.GONE);

        }
    }


    private void onTransporterFound(RequestCredentials credentials)
    {
        try {
            api.transporterLogin(credentials).enqueue(new Callback<ResponseTransporter>() {
                @Override
                public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {
                    if(response.code() == 200)
                    {
                        ResponseTransporter responseTransporter = response.body();
                        ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporter);


                        Intent intent = new Intent(LoginActivity.this, TransporterActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();

                    }
                    else
                    {
                        activity.password.setError("Password is Wrong");
                        activity.password.requestFocus();

                        parent_of_loading.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseTransporter> call, Throwable t) {
                        serverError();
                }
            });
        }
        catch (Exception e)
        {
            activity.password.setError("Password is Wrong");
            activity.password.requestFocus();

            parent_of_loading.setVisibility(View.GONE);

        }
    }

    private void onAreaManagerFound(RequestCredentials credentials)
    {
            try {
                api.areaManagerLogin(credentials).enqueue(new Callback<ResponseAreaManager>() {
                    @Override
                    public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {
                        if(response.code() == 200)
                        {

                            ResponseAreaManager responseAreaManager = response.body();
                            ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);


                            Intent intent = new Intent(LoginActivity.this, AreaManagerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            finish();
                        }
                        else
                        {
                            activity.password.setError("Password is Wrong");
                            activity.password.requestFocus();

                            parent_of_loading.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAreaManager> call, Throwable t) {
                            serverError();
                    }
                });
            }
            catch (Exception e)
            {
                activity.password.setError("Password is Wrong");
                activity.password.requestFocus();

                parent_of_loading.setVisibility(View.GONE);


            }
    }

    private void onFieldStaffFound(RequestCredentials credentials)
    {
            try {
                api.fieldStaffLogin(credentials).enqueue(new Callback<ResponseFieldStaff>() {
                    @Override
                    public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {
                        if(response.code() == 200)
                        {
                            ResponseFieldStaff responseFieldStaff = response.body();
                            ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);


                            Intent intent = new Intent(LoginActivity.this, FieldStafActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            finish();
                        }
                        else
                        {
                            activity.password.setError("Password is Wrong");
                            activity.password.requestFocus();

                            parent_of_loading.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {
                        serverError();
                    }
                });
            }
            catch(Exception e)
            {
                activity.password.setError("Password is Wrong");
                activity.password.requestFocus();

                parent_of_loading.setVisibility(View.GONE);

            }
    }



    private void serverError() {

        parent_of_loading.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
    }
}