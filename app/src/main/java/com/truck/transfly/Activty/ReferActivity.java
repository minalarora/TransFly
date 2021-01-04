package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.ReferAdapter;
import com.truck.transfly.Model.ResponseReward;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReferActivity extends AppCompatActivity {

    ArrayList<ResponseReward> i = new ArrayList<>();
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String keyword;
    private ReferAdapter referAdapter;
    private FrameLayout parent_of_loading;
    private TextView no_booking_data;
    private RelativeLayout no_internet_connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        Intent intent = getIntent();
        if (intent != null) {

            keyword = intent.getStringExtra("keyword");

        }

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        TextView toolbarHeading=findViewById(R.id.toolbarHeading);

        if (keyword != null && keyword.equals("refer"))
            toolbarHeading.setText("Referral Program");
        else
            toolbarHeading.setText("Reward");

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i.clear();
                referAdapter.notifyDataSetChanged();
                no_internet_connection.setVisibility(View.GONE);
                decideByKeywords(PreferenceUtil.getData(ReferActivity.this, "token"));

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        no_booking_data = findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReferActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        referAdapter = new ReferAdapter(ReferActivity.this, i);
        recyclerView.setAdapter(referAdapter);

        decideByKeywords(PreferenceUtil.getData(ReferActivity.this, "token"));

    }

    private void decideByKeywords(String token) {

        if (keyword != null && keyword.equals("refer"))
            getReferral(token);
        else
            getReward(token);

    }

    private void getReward(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.getRewardList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseReward>>() {
                    }.getType();
                    try {
                        i.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (i.isEmpty()) {

                        referAdapter.notifyDataSetChanged();
                        no_booking_data.setVisibility(View.VISIBLE);

                        Log.d("minal", "no vehicle");
                    } else {
                        //['pan','aadhaar','bank']

                        no_booking_data.setVisibility(View.GONE);
                        referAdapter.notifyDataSetChanged();

                        for (ResponseReward reward : i) {

                            if (reward.getStatus() == 1) {

                                reward.setIm("https://transfly-ftr2t.ondigitalocean.app/rewardimage/" + reward.getId());

                            } else if (reward.getStatus() == 0) {

                                reward.setIm("https://transfly-ftr2t.ondigitalocean.app/rewardimage/" + reward.getId());

                            }
                        }
                        Log.d("minal", i.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ReferActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                parent_of_loading.setVisibility(View.GONE);

                no_internet_connection.setVisibility(View.VISIBLE);

            }
        });
    }

    private void getReferral(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.getReferralList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                Type collectionType = new TypeToken<ArrayList<ResponseReward>>() {
                }.getType();
                try {
                    i.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                } catch (IOException e) {

                }
                if (i.isEmpty()) {

                    referAdapter.notifyDataSetChanged();
                    no_booking_data.setVisibility(View.VISIBLE);

                } else {
                    //['pan','aadhaar','bank']

                    no_booking_data.setVisibility(View.GONE);
                    referAdapter.notifyDataSetChanged();


                    for (ResponseReward reward : i) {

                        if (reward.getStatus() == 1) {

                            reward.setIm("https://transfly-ftr2t.ondigitalocean.app/referralimage/" + reward.getId());

                        } else if (reward.getStatus() == 0) {

                            reward.setIm("https://transfly-ftr2t.ondigitalocean.app/referralimage/" + reward.getId());

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ReferActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                no_internet_connection.setVisibility(View.VISIBLE);

                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }


}