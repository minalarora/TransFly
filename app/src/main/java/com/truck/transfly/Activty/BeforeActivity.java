package com.truck.transfly.Activty;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truck.transfly.Adapter.BeforeAdapter;
import com.truck.transfly.Adapter.CurrentBookingAdapter;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseLoading;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityBeforeBinding;
import com.truck.transfly.databinding.ActivityCurrentBookingBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Retrofit;

import static com.truck.transfly.Adapter.BeforeAdapter.*;

public class BeforeActivity extends AppCompatActivity implements onClickListener {

    private String loading ;
    private ActivityBeforeBinding activity;
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseMine> bookingList = new ArrayList<>();
    private BeforeAdapter currentBookingAdapter;
    private TextView no_booking_data;
    private RelativeLayout no_internet_connection;
    private String lat;
    private String aLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_before);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        bookingList = getIntent().getBundleExtra("bundle").getParcelableArrayList("mines");
        loading = getIntent().getBundleExtra("bundle").getString("loading");
        bookingList = getIntent().getParcelableArrayListExtra("mines2");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bookingList.sort(new Comparator<ResponseMine>() {
                @Override
                public int compare(ResponseMine o1, ResponseMine o2) {

                    ResponseLoading l1 = null;
                    for (ResponseLoading l : o1.getLoading()) {
                        if (l.getLoadingname().equalsIgnoreCase(loading)) {
                            l1 = l;
                            break;
                        }
                    }

                    ResponseLoading l2 = null;
                    for (ResponseLoading l : o2.getLoading()) {
                        if (l.getLoadingname().equalsIgnoreCase(loading)) {
                            l2 = l;
                            break;
                        }
                    }
                    try {
                        if(l1.getRate() == l2.getRate())
                        {
                            return  0;
                        }
                        else if(l1.getRate() >= l2.getRate())
                        {
                            return -1;
                        }
                        else
                        {
                            return  1;
                        }
                    }
                    catch (Exception e)
                    {
                        return 0;
                    }


                }
            });
        }

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        no_internet_connection = findViewById(R.id.no_internet_connection);

        no_booking_data = findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        RecyclerView current_booking_recycler = findViewById(R.id.current_booking_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        current_booking_recycler.setLayoutManager(linearLayoutManager);
        currentBookingAdapter = new BeforeAdapter(this, bookingList,loading);
        current_booking_recycler.setAdapter(currentBookingAdapter);
        currentBookingAdapter.setOnClickListener(this);

    }


    @Override
    public void createBooking(ResponseMine responseBooking) {
       // ArrayList<ResponseLoading> list = (ArrayList<ResponseLoading>) responseMineGlobal.getLoading();
        for(ResponseLoading l: responseBooking.getLoading())
        {
            if(l.getLoadingname().equalsIgnoreCase(loading))
            {
                if(l.getActive() == false)
                {
                    Toast.makeText(BeforeActivity.this, "This Mine is INACTIVE for the day, please try another Mine for your Loading.", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        }
        Intent intent = new Intent(BeforeActivity.this, SelectYourVehicleActivity.class);
        intent.putExtra("vehicle",true);
        ArrayList<ResponseLoading> arrayList = (ArrayList<ResponseLoading>) responseBooking.getLoading();
        int rate = 5;
        int etl = 0;
        for(ResponseLoading l: arrayList)
        {
            if(l.getLoadingname().equalsIgnoreCase(loading))
            {
                rate = l.getRate();
                etl = l.getEtl();

            }
        }

        //loading =>
        intent.putExtra("mineid", responseBooking.getId());
        intent.putExtra("minename", responseBooking.getName());
        intent.putExtra("loading", loading);
        intent.putExtra("tyres",responseBooking.getTyres());
        intent.putExtra("trailor",responseBooking.getTrailer());
        intent.putExtra("rate", rate);
        intent.putExtra("etl", etl);

        startActivity(intent);
    }
}