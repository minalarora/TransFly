package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.truck.transfly.Activty.TransporterActivity;
import com.truck.transfly.Adapter.TransporterAdapter;
import com.truck.transfly.Model.ResponseInvoice;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.yalantis.phoenix.PullToRefreshView;

import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PendingFragment extends Fragment implements SmoothDateRangePickerFragment.OnDateRangeSetListener {

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
    private FragmentActivity fragmentActivity;

    public PendingFragment() {
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

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        View inflate = inflater.inflate(R.layout.activity_current_invoices, container, false);

        areaManagerRecycler =inflate.findViewById(R.id.areaManagerRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(fragmentActivity,LinearLayoutManager.VERTICAL,false);
        areaManagerRecycler.setLayoutManager(linearLayoutManager);
        fieldStafAdapter=new TransporterAdapter(fragmentActivity,invoicesList);
        fieldStafAdapter.setDecideKeywords(1);
        areaManagerRecycler.setAdapter(fieldStafAdapter);

        parent_of_loading = inflate.findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_booking_data = inflate.findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        no_internet_connection = inflate.findViewById(R.id.no_internet_connection);
        inflate.findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invoicesList.clear();
                no_internet_connection.setVisibility(View.GONE);
                fieldStafAdapter.notifyDataSetChanged();
                getInvoiceVehicleOwner(PreferenceUtil.getData(fragmentActivity,"token"));

            }
        });

        CardView calenderSelected = inflate.findViewById(R.id.calender_selected);

        calenderSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
//                        TransporterActivity.this,
//                        now.get(Calendar.YEAR), // Initial year selection
//                        now.get(Calendar.MONTH), // Initial month selection
//                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
//                );

                Calendar now = Calendar.getInstance();

                SmoothDateRangePickerFragment smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(PendingFragment.this::onDateRangeSet, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                smoothDateRangePickerFragment.setAccentColor(R.color.project_color);

                smoothDateRangePickerFragment.setThemeDark(false);

                smoothDateRangePickerFragment.show(fragmentActivity.getFragmentManager(), "smoothDateRangePicker");


//                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_1);
//
//                dpd.setAccentColor(ContextCompat.getColor(TransporterActivity.this, R.color.quantum_pink));
//
//                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });

        pullToRefreshView=inflate.findViewById(R.id.pullToRefresh);

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                invoicesList.clear();
                getInvoiceVehicleOwner(PreferenceUtil.getData(fragmentActivity,"token"));

                fieldStafAdapter.notifyDataSetChanged();

                new Handler(fragmentActivity.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        pullToRefreshView.setRefreshing(false);

                    }
                },2500);

            }
        });

        getInvoiceVehicleOwner(PreferenceUtil.getData(fragmentActivity,"token"));

        return inflate;
    }

    private void getInvoiceVehicleOwner(String token)
    {
        no_internet_connection.setVisibility(View.GONE);
        no_booking_data.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getInvoiceVehicleOwner(token,"PENDING").enqueue(new Callback<ResponseBody>() {
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

                    Toast.makeText(fragmentActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(fragmentActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDateRangeSet(SmoothDateRangePickerFragment view, int yearStart, int monthStart, int dayStart, int yearEnd, int monthEnd, int dayEnd) {

        DateTime dateStart=new DateTime(yearStart,monthStart+1,dayStart,new DateTime().getHourOfDay(),new DateTime().getMinuteOfHour()).minusDays(1);

        DateTime dateEnd=new DateTime(yearEnd,monthEnd+1,dayEnd,new DateTime().getHourOfDay(),new DateTime().getMinuteOfHour());

        Log.i("TAG", "onDateRangeSet: "+dateStart.getMillis() +" :to:  "+dateEnd.getMillis()+" :current:"+new DateTime().getMillis());

        invoicesList.clear();
        fieldStafAdapter.notifyDataSetChanged();
        getInvoiceVehicleOwner2(PreferenceUtil.getData(fragmentActivity, "token"), String.valueOf(dateStart.getMillis()),String.valueOf(dateEnd.getMillis()));

    }

    private void getInvoiceVehicleOwner2(String token, String from, String to) {

        no_internet_connection.setVisibility(View.GONE);
        no_booking_data.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getInvoiceVehicleOwner2(token,"PENDING",from,to).enqueue(new Callback<ResponseBody>() {
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
//localhost:8080/allinvoice/vehicleowner/PENDING?from=1609524000000&to=1612116000000

                }else {

                    Toast.makeText(fragmentActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(fragmentActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

    }
}