package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.SpinnerMinesAdapter;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseFieldStaffForMine;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityFindFieldStafBinding;
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

public class FindFieldStafActivity extends AppCompatActivity implements SearchFieldStaffActivityy.onClickListener {

    private ActivityFindFieldStafBinding activity;
    private FrameLayout parent_of_loading;
    private List<ResponseMine> responseMineList = new ArrayList<>();
    private RelativeLayout no_internet_connection;
    private SpinnerMinesAdapter spinnerMinesAdapter;
    private Retrofit retrofit;
    private ApiEndpoints api;
    private ResponseFieldStaff responseFieldStaffGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(FindFieldStafActivity.this, R.layout.activity_find_field_staf);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        SearchFieldStaffActivityy.setOnClickListener(this::onClick);

        spinnerMinesAdapter = new SpinnerMinesAdapter(FindFieldStafActivity.this, responseMineList);
        activity.spinnerMines.setAdapter(spinnerMinesAdapter);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.noDataFound.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseMineList.clear();
                no_internet_connection.setVisibility(View.GONE);
                spinnerMinesAdapter.notifyDataSetChanged();
                getAreaManagerByMines(PreferenceUtil.getData(FindFieldStafActivity.this,"token"));

            }
        });

        activity.findFieldStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FindFieldStafActivity.this, SearchFieldStaffActivityy.class));

            }
        });

        activity.fieldstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FindFieldStafActivity.this, SearchFieldStaffActivityy.class));

            }
        });

        activity.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(activity.fieldstaff.getText().toString())) {

                    activity.fieldstaff.setError("Field Staff is Required*");
                    activity.fieldstaff.requestFocus();

                } else if(responseFieldStaffGlobal==null){

                    Toast.makeText(FindFieldStafActivity.this, "Select A Field Staff!", Toast.LENGTH_SHORT).show();

                } else {

                    ResponseMine responseMine = (ResponseMine) activity.spinnerMines.getSelectedItem();

                    ResponseFieldStaffForMine responseFieldStaffForMine=new ResponseFieldStaffForMine();
                    responseFieldStaffForMine.setFieldstaffid(responseFieldStaffGlobal.getId());
                    responseFieldStaffForMine.setMineid(String.valueOf(responseMine.getId()));

                    giveAccess(PreferenceUtil.getData(FindFieldStafActivity.this,"token"),responseFieldStaffForMine);

                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        getAreaManagerByMines(PreferenceUtil.getData(FindFieldStafActivity.this,"token"));

    }

    private void giveAccess(String token, ResponseFieldStaffForMine mine)
    {
        parent_of_loading.setVisibility(View.VISIBLE);
        no_internet_connection.setVisibility(View.GONE);

        api.getAreaManagerAccess(token,mine).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    parent_of_loading.setVisibility(View.GONE);

                    Toast.makeText(FindFieldStafActivity.this, "Mines Allot Successfully", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    parent_of_loading.setVisibility(View.GONE);

                    Toast.makeText(FindFieldStafActivity.this, "Something Went wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(FindFieldStafActivity.this, "No Internet Connection! Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onClick(ResponseFieldStaff responseFieldStaff) {

        activity.fieldstaff.setText(responseFieldStaff.getName());

        this.responseFieldStaffGlobal=responseFieldStaff;

    }

    private void getAreaManagerByMines(String token) {

        no_internet_connection.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);
        activity.noDataFound.setVisibility(View.GONE);

        api.getAreaManagerMines(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseMine>>() {
                    }.getType();
                    try {
                        responseMineList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (responseMineList.isEmpty()) {

                        activity.noDataFound.setVisibility(View.VISIBLE);

                    } else {
                        //arraylist

                        activity.noDataFound.setVisibility(View.GONE);

                        spinnerMinesAdapter.notifyDataSetChanged();

                    }

                } else {

                    no_internet_connection.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                no_internet_connection.setVisibility(View.VISIBLE);

                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }

}