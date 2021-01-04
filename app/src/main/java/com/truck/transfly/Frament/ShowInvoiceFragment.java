package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowInvoiceFragment extends Fragment implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private List<String> stringList = new ArrayList<>();
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseInvoice> invoicesList = new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private RecyclerView areaManagerRecycler;
    private TransporterAdapter fieldStafAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private PullToRefreshView pullToRefreshView;
    private TextView no_booking_data;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity = (FragmentActivity) context;

    }

    public ShowInvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_show_invoice, container, false);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        areaManagerRecycler = inflate.findViewById(R.id.areaManagerRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity, LinearLayoutManager.VERTICAL, false);
        areaManagerRecycler.setLayoutManager(linearLayoutManager);
        fieldStafAdapter = new TransporterAdapter(fragmentActivity, invoicesList);
        areaManagerRecycler.setAdapter(fieldStafAdapter);

        parent_of_loading = inflate.findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        CardView calenderSelected = inflate.findViewById(R.id.calender_selected);

        calenderSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        ShowInvoiceFragment.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_1);

                dpd.setAccentColor(ContextCompat.getColor(fragmentActivity, R.color.quantum_pink));

                dpd.show(fragmentActivity.getSupportFragmentManager(), "Datepickerdialog");
            }
        });

        no_internet_connection = inflate.findViewById(R.id.no_internet_connection);
        inflate.findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invoicesList.clear();
                no_internet_connection.setVisibility(View.GONE);
                fieldStafAdapter.notifyDataSetChanged();
                getInvoiceAreaManager(PreferenceUtil.getData(fragmentActivity, "token"));

            }
        });

        pullToRefreshView = inflate.findViewById(R.id.pullToRefresh);

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                invoicesList.clear();
                getInvoiceAreaManager(PreferenceUtil.getData(fragmentActivity, "token"));

                fieldStafAdapter.notifyDataSetChanged();

                new Handler(fragmentActivity.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        pullToRefreshView.setRefreshing(false);

                    }
                }, 2500);

            }
        });

        no_booking_data = inflate.findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        getInvoiceAreaManager(PreferenceUtil.getData(fragmentActivity, "token"));

        return inflate;

    }

    private void getInvoiceAreaManager(String token) {
        no_internet_connection.setVisibility(View.GONE);
        no_booking_data.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getInvoiceAreaManager(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                pullToRefreshView.setRefreshing(false);

                if (response.code() == 200) {
                    ArrayList<ResponseInvoice> invoices = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseInvoice>>() {
                    }.getType();
                    try {
                        invoices.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                        invoicesList.addAll(invoices);
                    } catch (IOException e) {

                    }
                    if (invoices.size()==0) {

                        fieldStafAdapter.notifyDataSetChanged();
                        Log.d("minal", "no vehicle");

                        no_booking_data.setVisibility(View.VISIBLE);
                    } else {
                        //['pan','aadhaar','bank']

                        fieldStafAdapter.notifyDataSetChanged();
                        Log.d("minal", invoicesList.toString());
                    }


                } else {

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
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String str_date = monthOfYear + "-" + dayOfMonth + "-" + year;
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null)

            return;

        long output = date.getTime() / 1000L;
        String str = Long.toString(output);
        long timestamp = Long.parseLong(str) * 1000;

        Log.i("TAG", "onDateSet: " + timestamp);

    }
}