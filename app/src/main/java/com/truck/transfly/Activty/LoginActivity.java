package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_login);

        activity.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButton();
               // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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

            //c

            //fill mobile or password in this object

            api.login(credentials).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200)
                    {
                        //user found
                        String type = response.body().toString();
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
                                throw new Error("user not found");
                            }

                        }

                    }
                    else
                    {
                            throw new Error("user not found!");

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
            //user not found
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
                        ResponseVehicleOwner v = response.body();

                    }
                    else
                    {
                        throw new Error("wrong password");

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
            //wrong password
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
                        ResponseTransporter t =response.body();

                        ((TransflyApplication)getApplication()).setResponseTransporterOwner(t);

                       //ResponseTransporter t = ((TransflyApplication)getApplication()).getResponseTransporterOwner();





                        //user found
                        //ResponseTransporter

                    }
                    else
                    {
                        throw new Error("wrong password");

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
            //wrong password
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
                            //user found
                        }
                        else
                        {
                            throw new Error("wrong password");

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
                    //wrong password
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
                            //user found
                        }
                        else
                        {
                            throw new Error("wrong password");

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
                //wrong password
            }
    }



    private void serverError()
    {
        //no inernet
    }
}