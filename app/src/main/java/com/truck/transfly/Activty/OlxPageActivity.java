package com.truck.transfly.Activty;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.truck.transfly.Adapter.OlxRecyclerAdapter;
import com.truck.transfly.Adapter.ViewPagerAdapter;
import com.truck.transfly.Frament.LeaseFragment;
import com.truck.transfly.Frament.ResaleFragment;
import com.truck.transfly.Frament.ShowBooking;
import com.truck.transfly.Frament.ShowInvoiceFragment;
import com.truck.transfly.Model.ResponseResale;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiEndpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class OlxPageActivity extends AppCompatActivity {

    private List<String> stringList = new ArrayList<>();
    private Retrofit retrofit = null;
    ArrayList<ResponseResale> responseList = new ArrayList<>();
    private ApiEndpoints api = null;
    private OlxRecyclerAdapter olxRecyclerAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private TextView no_vehicle_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olx_page);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(new ResaleFragment(), "Resale");
        viewPagerAdapter.addFragment(new LeaseFragment(), "Lease");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


    }

}