package com.truck.transfly.Activty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.NotificationAdapter;
import com.truck.transfly.Model.ResponseNotification;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ApiEndpoints api;
    private List<ResponseNotification> responseNotificationList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private TextView no_booking_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseNotificationList.clear();
                no_internet_connection.setVisibility(View.GONE);
                getNotification(PreferenceUtil.getData(NotificationActivity.this, "token"));

            }
        });

        findViewById(R.id.appSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

            }
        });

        no_booking_data = findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        initNotifications();

    }

    private void initNotifications() {

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager gridLayoutManage = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
        notificationAdapter = new NotificationAdapter(NotificationActivity.this, responseNotificationList);
        recyclerView.setLayoutManager(gridLayoutManage);
        recyclerView.setAdapter(notificationAdapter);

        getNotification(PreferenceUtil.getData(NotificationActivity.this, "token"));


    }


    private void getNotification(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.getNotifications(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseNotification>>() {
                    }.getType();
                    try {
                        responseNotificationList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (responseNotificationList.isEmpty()) {

                        no_booking_data.setVisibility(View.VISIBLE);
                    } else {

                        no_booking_data.setVisibility(View.GONE);

                        notificationAdapter.notifyDataSetChanged();

                    }
                } else {
                    no_internet_connection.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //failed

                no_internet_connection.setVisibility(View.VISIBLE);

                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }

}