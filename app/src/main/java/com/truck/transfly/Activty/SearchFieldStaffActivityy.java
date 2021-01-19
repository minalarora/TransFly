package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.SearchFieldStafAdapter;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityFindFieldStaffActivityyBinding;
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

public class SearchFieldStaffActivityy extends AppCompatActivity {
    private static onClickListener onClickListener1;
    private ActivityFindFieldStaffActivityyBinding activity;

    private List<ResponseFieldStaff> responseFieldStaffList=new ArrayList<>();
    private SearchFieldStafAdapter searchFieldStafAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private Retrofit retrofit;
    private ApiEndpoints api;

    public interface onClickListener{

        void onClick(ResponseFieldStaff responseFieldStaff);

    }

    public static void setOnClickListener(onClickListener onClickListener){

        onClickListener1=onClickListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=DataBindingUtil.setContentView(SearchFieldStaffActivityy.this,R.layout.activity_find_field_staff_activityy);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading=findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.noDataFound.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchFieldStafAdapter=new SearchFieldStafAdapter(this,responseFieldStaffList);
        recyclerView.setAdapter(searchFieldStafAdapter);

        searchFieldStafAdapter.setOnClickListener(new SearchFieldStafAdapter.onClickListerner() {
            @Override
            public void onClick(ResponseFieldStaff responseFieldStaff) {

                onClickListener1.onClick(responseFieldStaff);

            }
        });

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseFieldStaffList.clear();
                no_internet_connection.setVisibility(View.GONE);
                searchFieldStafAdapter.notifyDataSetChanged();
                getAreaManagerFieldStaff(PreferenceUtil.getData(SearchFieldStaffActivityy.this,"token"));
            }
        });

        activity.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                 searchFieldStafAdapter.getFilter().filter(s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getAreaManagerFieldStaff(PreferenceUtil.getData(SearchFieldStaffActivityy.this,"token"));

    }

    private void getAreaManagerFieldStaff(String token)
    {
        no_internet_connection.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);
        activity.noDataFound.setVisibility(View.GONE);

        api.getAreaManagerFieldStaff(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    Type collectionType = new TypeToken<ArrayList<ResponseFieldStaff>>() {
                    }.getType();
                    try {
                        responseFieldStaffList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (responseFieldStaffList.isEmpty()) {

                        activity.noDataFound.setVisibility(View.VISIBLE);

                    } else {
                        //arraylist

                        activity.noDataFound.setVisibility(View.GONE);

                        searchFieldStafAdapter.notifyDataSetChanged();

                    }
                }
                else
                {

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