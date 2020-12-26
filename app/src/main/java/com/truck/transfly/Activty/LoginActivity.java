package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,MapActivity.class));

            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
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
}