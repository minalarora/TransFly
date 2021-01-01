package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.TransporterAdapter;
import com.truck.transfly.Model.ResponseInvoice;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CurrentInvoicesActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseInvoice> invoicesList = new ArrayList<>();
    private RecyclerView areaManagerRecycler;
    private TransporterAdapter fieldStafAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private PullToRefreshView pullToRefreshView;
    private TextView no_booking_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_invoices);
        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        areaManagerRecycler =findViewById(R.id.areaManagerRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CurrentInvoicesActivity.this,LinearLayoutManager.VERTICAL,false);
        areaManagerRecycler.setLayoutManager(linearLayoutManager);
        fieldStafAdapter=new TransporterAdapter(CurrentInvoicesActivity.this,invoicesList);
        areaManagerRecycler.setAdapter(fieldStafAdapter);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_booking_data = findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invoicesList.clear();
                no_internet_connection.setVisibility(View.GONE);
                fieldStafAdapter.notifyDataSetChanged();
                getInvoiceVehicleOwner(PreferenceUtil.getData(CurrentInvoicesActivity.this,"token"));

            }
        });

        pullToRefreshView=findViewById(R.id.pullToRefresh);

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                invoicesList.clear();
                getInvoiceVehicleOwner(PreferenceUtil.getData(CurrentInvoicesActivity.this,"token"));

                fieldStafAdapter.notifyDataSetChanged();

                new Handler(CurrentInvoicesActivity.this.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        pullToRefreshView.setRefreshing(false);

                    }
                },2500);

            }
        });

        getInvoiceVehicleOwner(PreferenceUtil.getData(CurrentInvoicesActivity.this,"token"));

    }

    private void getInvoiceVehicleOwner(String token)
    {
        no_internet_connection.setVisibility(View.GONE);
        no_booking_data.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getInvoiceVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                pullToRefreshView.setRefreshing(false);

                if(response.code() == 200)
                {
                    Type collectionType = new TypeToken<ArrayList<ResponseInvoice>>(){}.getType();
                    try {
                        invoicesList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(invoicesList.isEmpty())
                    {

                        fieldStafAdapter.notifyDataSetChanged();
                        Log.d("minal","no vehicle");

                        no_booking_data.setVisibility(View.VISIBLE);                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        fieldStafAdapter.notifyDataSetChanged();
                        Log.d("minal", String.valueOf(invoicesList.size()));
                    }


                }else {

                    Toast.makeText(CurrentInvoicesActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(CurrentInvoicesActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


}