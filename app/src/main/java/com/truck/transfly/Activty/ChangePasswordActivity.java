package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.Model.RequestPassword;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ChangePasswordActivtyBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {

    private ChangePasswordActivtyBinding activity;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String mobileNo;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mobileNo = intent.getStringExtra("mobileNo");

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        activity = DataBindingUtil.setContentView(this, R.layout.change_password_activty);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(activity.password.getText().toString()) || activity.password.getText().length() < 4) {

                    activity.password.setError("Password Must Be 4 digits*");
                    activity.password.requestFocus();

                } else if (TextUtils.isEmpty(activity.reenterPassword.getText().toString())) {

                    activity.reenterPassword.setError("Re Enter Password is Required*");
                    activity.reenterPassword.requestFocus();

                } else if (!activity.reenterPassword.getText().toString().equals(activity.password.getText().toString())) {

                    activity.reenterPassword.setError("Password is not matched*");
                    activity.reenterPassword.requestFocus();

                } else {

                    RequestPassword requestPassword = new RequestPassword();
                    requestPassword.setPassword(activity.password.getText().toString());

                    updatePassword(PreferenceUtil.getData(ChangePasswordActivity.this, "token"), requestPassword);

                }

            }
        });

    }

    private void updatePassword(String token, RequestPassword password) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.updatePassword(token, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();

                } else {

                    Toast.makeText(ChangePasswordActivity.this, "Unable to change password! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
