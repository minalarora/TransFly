package com.truck.transfly.Activty;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String token;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        getFireBaseTokenInit();

        getFireBaseToken();

        progressBar =findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        RelativeLayout refresh_button=findViewById(R.id.no_internet_connection);
        refresh_button.setVisibility(View.GONE);
        TextView noConnectionText=findViewById(R.id.no_connection_text);
        noConnectionText.setVisibility(View.GONE);

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refresh_button.setVisibility(View.GONE);
                noConnectionText.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(isNetworkAvailable(getApplication())) {

                            validateToken();

                            progressBar.setVisibility(View.VISIBLE);

                        } else {

                            progressBar.setVisibility(View.GONE);
                            refresh_button.setVisibility(View.VISIBLE);
                            noConnectionText.setVisibility(View.VISIBLE);

                            Toast.makeText(SplashActivity.this, "no Connection Available", Toast.LENGTH_SHORT).show();

                        }

                    }
                },1000);

            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                token = PreferenceUtil.getData(SplashActivity.this, "token");
                if (token == null) {

                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            finish();

                        }
                    }, 2500);

                    return;

                }

                if(isNetworkAvailable(getApplication())) {

                    validateToken();

                    progressBar.setVisibility(View.VISIBLE);

                } else {

                     progressBar.setVisibility(View.GONE);
                    refresh_button.setVisibility(View.VISIBLE);
                    noConnectionText.setVisibility(View.VISIBLE);

                }

            }
        },1000);
    }


    private Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    private void getFireBaseToken() {

        FirebaseMessaging.getInstance().setAutoInitEnabled(false);

    }

    private void validateToken() {
        try {

            token = PreferenceUtil.getData(this, "token");
            if (token == null) {

                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();

                    }
                }, 2500);

                return;

            }
            String type = token.split(":")[0];
            switch (type) {
                case "vehicleowner": {

                    api.getVehicleOwner(token).enqueue(new Callback<ResponseVehicleOwner>() {
                        @Override
                        public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {

                            if (response.code() == 200) {
///
                                ResponseVehicleOwner responseVehicleOwner = response.body();
                                ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                                new Handler(getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();

                                    }
                                }, 1000);


                            } else {
                                serverError();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {
                            serverError();
                        }
                    });

                    break;
                }
                case "transporter": {
                    api.getTransporter(token).enqueue(new Callback<ResponseTransporter>() {
                        @Override
                        public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {

                            if (response.code() == 200) {

                                ResponseTransporter responseTransporter = response.body();
                                ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporter);

                                new Handler(getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(SplashActivity.this, TransporterActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();

                                    }
                                }, 1000);


                            } else {

                                serverError();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseTransporter> call, Throwable t) {
                            serverError();
                        }
                    });
                    break;
                }
                case "areamanager": {
                    api.getAreaManager(token).enqueue(new Callback<ResponseAreaManager>() {
                        @Override
                        public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {

                            if (response.code() == 200) {

                                ResponseAreaManager responseAreaManager = response.body();
                                ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);

                                new Handler(getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(SplashActivity.this, AreaManagerActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();

                                    }
                                }, 1000);


                            } else {
                                serverError();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseAreaManager> call, Throwable t) {
                            serverError();
                        }
                    });
                    break;
                }
                case "fieldstaff": {
                    api.getFieldStaff(token).enqueue(new Callback<ResponseFieldStaff>() {
                        @Override
                        public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {

                            if (response.code() == 200) {

                                ResponseFieldStaff responseFieldStaff = response.body();
                                ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);

                                new Handler(getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(SplashActivity.this, FieldStafActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();

                                    }
                                }, 1000);


                            } else {
                                serverError();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {
                            serverError();
                        }
                    });
                    break;
                }
                default: {
                    serverError();
                }
            }
        } catch (Exception e) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void getFireBaseTokenInit() {

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    }

    private void serverError() {

        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        finish();
    }

}