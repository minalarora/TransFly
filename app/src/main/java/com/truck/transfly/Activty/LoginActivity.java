package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tapadoo.alerter.Alerter;
import com.truck.transfly.Frament.ForgotPasswordDialog;
import com.truck.transfly.Model.RequestCredentials;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseToken;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityLoginBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ActivityLoginBinding activity;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_login);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(activity.mobileNumber.getText().toString())) {

                    activity.mobileNumber.setError("Mobile number is Required*");
                    activity.mobileNumber.requestFocus();

                } else if (TextUtils.isEmpty(activity.password.getText().toString())) {

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

                ForgotPasswordDialog forgotPasswordDialog = new ForgotPasswordDialog();
                forgotPasswordDialog.setCancelable(false);
                forgotPasswordDialog.show(getSupportFragmentManager(), "forgotPasswordDialog");

            }
        });

        activity.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, EnterMobileNumberActivty.class));
            }
        });

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }


    }


    private void onLoginButton() {

        RequestCredentials credentials = new RequestCredentials();
        credentials.setMobile(activity.mobileNumber.getText().toString());
        credentials.setPassword(activity.password.getText().toString());

        onLogin(credentials);

    }

    private void onLogin(RequestCredentials credentials)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.login(credentials).enqueue(new Callback<ResponseToken>() {
            @Override
            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {

                parent_of_loading.setVisibility(View.GONE);

                activity.password.setError(null);
                activity.mobileNumber.setError(null);

                if(response.code() == 200)
                {
                    ResponseToken tokenobj = response.body();
                    String token  = tokenobj.getToken();

                    validateToken(token);

                    PreferenceUtil.putData(LoginActivity.this,"token",token);

                } else if(response.code()==202) {

                    Alerter.create(LoginActivity.this)
                            .setTitle("Password Miss matched")
                            .setText("Try different Password! Password seems to wrong!")
                            .setIcon(R.drawable.ic_action_password_lock)
                            .setBackgroundColorRes(R.color.quantum_pink)
                            .enableSwipeToDismiss()
                            .setDuration(3000)
                            .show();

                    parent_of_loading.setVisibility(View.GONE);


                } else if(response.code()==205){

                    Alerter.create(LoginActivity.this)
                            .setTitle("User Not Found!")
                            .setText("Mobile or Email And Password are miss matched!")
                            .setIcon(R.drawable.ic_action_password_lock)
                            .setBackgroundColorRes(R.color.quantum_pink)
                            .enableSwipeToDismiss()
                            .setDuration(3000)
                            .show();

                    parent_of_loading.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ResponseToken> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);
                serverError();

            }
        });
    }


    private void validateToken(String token) {
        try {
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

                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();


                            } else {

                                Alerter.create(LoginActivity.this)
                                        .setTitle("Password Miss matched")
                                        .setText("Try different Password! Password seems to wrong!")
                                        .setIcon(R.drawable.ic_action_password_lock)
                                        .setBackgroundColorRes(R.color.quantum_pink)
                                        .enableSwipeToDismiss()
                                        .setDuration(3000)
                                        .show();

                                parent_of_loading.setVisibility(View.GONE);
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

                                        Intent intent = new Intent(LoginActivity.this, TransporterActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();


                            } else {

                                Alerter.create(LoginActivity.this)
                                        .setTitle("Password Miss matched")
                                        .setText("Try different Password! Password seems to wrong!")
                                        .setIcon(R.drawable.ic_action_password_lock)
                                        .setBackgroundColorRes(R.color.quantum_pink)
                                        .enableSwipeToDismiss()
                                        .setDuration(3000)
                                        .show();

                                parent_of_loading.setVisibility(View.GONE);
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

                                        Intent intent = new Intent(LoginActivity.this, AreaManagerActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();

                            } else {

                                Alerter.create(LoginActivity.this)
                                        .setTitle("Password Miss matched")
                                        .setText("Try different Password! Password seems to wrong!")
                                        .setIcon(R.drawable.ic_action_password_lock)
                                        .setBackgroundColorRes(R.color.quantum_pink)
                                        .enableSwipeToDismiss()
                                        .setDuration(3000)
                                        .show();

                                parent_of_loading.setVisibility(View.GONE);
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

                                        Intent intent = new Intent(LoginActivity.this, FieldStafActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();


                            } else {

                                Alerter.create(LoginActivity.this)
                                        .setTitle("Password Miss matched")
                                        .setText("Try different Password! Password seems to wrong!")
                                        .setIcon(R.drawable.ic_action_password_lock)
                                        .setBackgroundColorRes(R.color.quantum_pink)
                                        .enableSwipeToDismiss()
                                        .setDuration(3000)
                                        .show();

                                parent_of_loading.setVisibility(View.GONE);

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

            Toast.makeText(this, "Somthing Went Wrong! Try Again", Toast.LENGTH_SHORT).show();
        }
    }


    private void onVehicleOwnerFound(RequestCredentials credentials) {

        try {
            api.vehicleOwnerLogin(credentials).enqueue(new Callback<ResponseVehicleOwner>() {
                @Override
                public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {
                    if (response.code() == 200) {
                        //user found
                        ResponseVehicleOwner responseVehicleOwner = response.body();
                        ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();


                    } else {
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
        } catch (Exception e) {
            activity.password.setError("Password is Wrong");
            activity.password.requestFocus();

            parent_of_loading.setVisibility(View.GONE);

        }
    }


    private void onTransporterFound(RequestCredentials credentials) {
        try {
            api.transporterLogin(credentials).enqueue(new Callback<ResponseTransporter>() {
                @Override
                public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {
                    if (response.code() == 200) {
                        ResponseTransporter responseTransporter = response.body();
                        ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporter);


                        Intent intent = new Intent(LoginActivity.this, TransporterActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();

                    } else {
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
        } catch (Exception e) {
            activity.password.setError("Password is Wrong");
            activity.password.requestFocus();

            parent_of_loading.setVisibility(View.GONE);

        }
    }

    private void onAreaManagerFound(RequestCredentials credentials) {
        try {
            api.areaManagerLogin(credentials).enqueue(new Callback<ResponseAreaManager>() {
                @Override
                public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {
                    if (response.code() == 200) {

                        ResponseAreaManager responseAreaManager = response.body();
                        ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);


                        Intent intent = new Intent(LoginActivity.this, AreaManagerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();
                    } else {
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
        } catch (Exception e) {
            activity.password.setError("Password is Wrong");
            activity.password.requestFocus();

            parent_of_loading.setVisibility(View.GONE);


        }
    }

    private void onFieldStaffFound(RequestCredentials credentials) {
        try {
            api.fieldStaffLogin(credentials).enqueue(new Callback<ResponseFieldStaff>() {
                @Override
                public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {
                    if (response.code() == 200) {
                        ResponseFieldStaff responseFieldStaff = response.body();
                        ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);


                        Intent intent = new Intent(LoginActivity.this, FieldStafActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();
                    } else {
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
        } catch (Exception e) {
            activity.password.setError("Password is Wrong");
            activity.password.requestFocus();

            parent_of_loading.setVisibility(View.GONE);

        }
    }


    private void serverError() {

        parent_of_loading.setVisibility(View.GONE);

        Alerter.create(LoginActivity.this)
                .setTitle("No Internet Connection!")
                .setText("Check your connection and try again!")
                .setIcon(R.drawable.ic_action_no_connection)
                .setBackgroundColorRes(R.color.quantum_pink)
                .enableSwipeToDismiss()
                .setDuration(3000)
                .show();
    }
}