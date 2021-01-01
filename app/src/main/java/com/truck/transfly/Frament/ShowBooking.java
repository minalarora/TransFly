package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Activty.CurrentBookingActivity;
import com.truck.transfly.Activty.FieldStafActivity;
import com.truck.transfly.Adapter.FieldStafAdapter;
import com.truck.transfly.Model.ResponseBooking;
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

public class ShowBooking extends Fragment {

    private List<String> stringList=new ArrayList<>();
    private View inflate;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseBooking> responseBookingList=new ArrayList<>();
    private RecyclerView fieldStafRecylcer;
    private FragmentActivity fragmentActivity;
    private FrameLayout parent_of_loading;
    private TextView noDataFound;
    private FieldStafAdapter fieldStafAdapter;
    private PullToRefreshView pullToRefreshView;
    private RelativeLayout no_internet_connection;

    public ShowBooking() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        inflate = inflater.inflate(R.layout.fragment_show_booking, container, false);

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading = inflate.findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_internet_connection = inflate.findViewById(R.id.no_internet_connection);
        inflate.findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseBookingList.clear();
                fieldStafAdapter.notifyDataSetChanged();
                no_internet_connection.setVisibility(View.GONE);
                getAllBookingFieldStaff(PreferenceUtil.getData(fragmentActivity,"token"));
            }
        });

        noDataFound =inflate.findViewById(R.id.no_data_found);
        noDataFound.setVisibility(View.GONE);

        pullToRefreshView=inflate.findViewById(R.id.pullToRefresh);

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                responseBookingList.clear();
                getAllBookingFieldStaff(PreferenceUtil.getData(fragmentActivity,"token"));

                new Handler(fragmentActivity.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        pullToRefreshView.setRefreshing(false);

                    }
                },2500);

            }
        });

        fieldStafRecylcer =inflate.findViewById(R.id.fieldStafRecylcer);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        fieldStafRecylcer.setLayoutManager(linearLayoutManager);
        fieldStafAdapter=new FieldStafAdapter(fragmentActivity,responseBookingList);
        fieldStafRecylcer.setAdapter(fieldStafAdapter);

        getAllBookingFieldStaff(PreferenceUtil.getData(fragmentActivity,"token"));

        return inflate;
    }

    private void getAllBookingFieldStaff(String token)
    {

        parent_of_loading.setVisibility(View.VISIBLE);
        no_internet_connection.setVisibility(View.GONE);

        api.getBookingFieldStaff(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                pullToRefreshView.setRefreshing(false);

                if(response.code() == 200)
                {
                    Type collectionType = new TypeToken<ArrayList<ResponseBooking>>(){}.getType();
                    try {
                        responseBookingList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(responseBookingList.isEmpty())
                    {

                        noDataFound.setVisibility(View.VISIBLE);
                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        noDataFound.setVisibility(View.GONE);
                        fieldStafAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(fragmentActivity, "No internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}