package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

        validateToken();
//        new Handler(getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                finish();
//
//            }
//        },2000);

    }


    private void validateToken()
    {
        try {

            token = PreferenceUtil.getData(this,"token");
            if(token == null)
            {
                throw new Error("Token not available");
            }
            String type = token.split(":")[0];
            switch (type)
            {
                case "vehicleowner": {

                    api.getVehicleOwner(token).enqueue(new Callback<ResponseVehicleOwner>() {
                        @Override
                        public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {

                            if(response.code() == 200)
                            {

                            }
                            else
                            {
                                throw new Error("Token not available");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {
                                serverError();
                        }
                    });

                    break;
                }
                case "transporter":{
                    api.getTransporter(token).enqueue(new Callback<ResponseTransporter>() {
                        @Override
                        public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {

                            if(response.code() == 200)
                            {

                            }
                            else
                            {
                                throw new Error("Token not available");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseTransporter> call, Throwable t) {
                                serverError();
                        }
                    });
                    break;
                }
                case "areamanager":{
                    api.getAreaManager(token).enqueue(new Callback<ResponseAreaManager>() {
                        @Override
                        public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {

                            if(response.code() == 200)
                            {

                            }
                            else
                            {
                                throw new Error("Token not available");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseAreaManager> call, Throwable t) {
                                serverError();
                        }
                    });
                    break;
                }
                case "fieldstaff":
                {
                    api.getFieldStaff(token).enqueue(new Callback<ResponseFieldStaff>() {
                        @Override
                        public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {

                            if(response.code() == 200)
                            {

                            }
                            else
                            {
                                throw new Error("Token not available");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {
                                serverError();
                        }
                    });
                    break;
                }
                default:
                {
                    throw new Error("Token not available");
                }
            }
        }
        catch(Exception e)
        {
                //redirect to login screen
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void serverError()
    {
        //no inernet
    }

}