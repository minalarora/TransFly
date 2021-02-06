package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;
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

        SpaceNavigationView spaceNavigationView = findViewById(R.id.bottom_space);
        spaceNavigationView.showIconOnly();
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.cargo_truck));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.new_booking_icon));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.release_lease_icon_image));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.help_attain_icon));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

                startActivity(new Intent(OlxPageActivity.this, HomeActivity.class));

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {

                switch (itemIndex) {

                    case 0:

                        Intent currentBookingActivity = new Intent(OlxPageActivity.this, CurrentBookingActivity.class);

                        Toast.makeText(OlxPageActivity.this,"CURRENT BOOKINGS",Toast.LENGTH_LONG).show();

                        startActivity(currentBookingActivity);

                        break;

                    case 1:

                        Intent searchBarAcivity = new Intent(OlxPageActivity.this, SearchBarActivity.class);

                        searchBarAcivity.putExtra("vehicle", true);
                        Toast.makeText(OlxPageActivity.this,"CREATE BOOKING",Toast.LENGTH_LONG).show();


                        startActivity(searchBarAcivity);

                        break;


                    case 2:

                        break;

                    case 3:

                        startActivity(new Intent(OlxPageActivity.this, TicketComplaintActivity.class));
                        Toast.makeText(OlxPageActivity.this,"ON-ROAD ASSISTANCE",Toast.LENGTH_LONG).show();


                        break;

                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

                switch (itemIndex) {

                    case 0:

                        Intent currentBookingActivity = new Intent(OlxPageActivity.this, CurrentBookingActivity.class);

                        Toast.makeText(OlxPageActivity.this,"CURRENT BOOKINGS",Toast.LENGTH_LONG).show();

                        startActivity(currentBookingActivity);

                        break;

                    case 1:

                        Intent searchBarAcivity = new Intent(OlxPageActivity.this, SearchBarActivity.class);

                        searchBarAcivity.putExtra("vehicle", true);

                        startActivity(searchBarAcivity);

                        break;


                    case 2:

                        break;

                    case 3:

                        startActivity(new Intent(OlxPageActivity.this, TicketComplaintActivity.class));

                        break;

                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


    }

}