package com.truck.transfly.Activty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.truck.transfly.Adapter.ViewPagerAdapter;
import com.truck.transfly.Frament.ShowBooking;
import com.truck.transfly.Frament.ShowInvoiceFragment;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.R;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

public class AreaManagerActivity extends AppCompatActivity {

    private ImageView viewById;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_manager2);

        TabLayout tabLayout=findViewById(R.id.tabLayout);

        ViewPager viewPager =findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(new ShowBooking(),"Bookings");
        viewPagerAdapter.addFragment(new ShowInvoiceFragment(),"Invoices");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        View headerLayout = navigationView.getHeaderView(0);
        TextView customerName = headerLayout.findViewById(R.id.customer_name);
        TextView number=headerLayout.findViewById(R.id.number);

        headerLayout.findViewById(R.id.appSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

            }
        });

        ResponseAreaManager responseFieldStaff = ((TransflyApplication) getApplication()).getResponseAreaManager();

        customerName.setText(responseFieldStaff.getName());
        number.setText(responseFieldStaff.getMobile());

        navigationViewListener(navigationView);

        Menu menu = navigationView.getMenu();
        MenuItem kyc_drawer = menu.findItem(R.id.kyc_drawer);

        if(responseFieldStaff.getStatus()==0){

            kyc_drawer.setTitle("KYC Details (Pending)");

        } else if(responseFieldStaff.getStatus()==1){

            kyc_drawer.setTitle("KYC Details (Under Process)");

        } else {

            kyc_drawer.setTitle("KYC Details (Completed)");

        }

//        Menu menu = navigationView.getMenu();
//
//        menu.findItem()

        viewById = findViewById(R.id.drawer_icon);

        drawerLayout = findViewById(R.id.drawer_layout);

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

                    drawerLayout.closeDrawer(GravityCompat.START);

                } else {

                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

    }

    private void navigationViewListener(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile_drawer:

                        Intent intent = new Intent(AreaManagerActivity.this, ProfileActivity.class);
                        intent.putExtra("stringText","areaManager");
                        startActivity(intent);

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent=new Intent(AreaManagerActivity.this, AreaFieldStafActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.ticket_drawer:

                        Intent ticket_complain=new Intent(AreaManagerActivity.this,TicketComplaintActivity.class);
                        startActivity(ticket_complain);

                        break;

                    case R.id.bank_details:

                        Intent bankDetails=new Intent(AreaManagerActivity.this,BankDetailsActivity.class);
                        startActivity(bankDetails);

                        break;

                    case R.id.feedback_drawer:

                        Intent feedback_intent=new Intent(AreaManagerActivity.this,FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent = new Intent(AreaManagerActivity.this, ReferActivity.class);
                        refer_intent.putExtra("keyword","refer");
                        startActivity(refer_intent);

                        break;

                    case R.id.reward_program:

                        Intent rewardIntent = new Intent(AreaManagerActivity.this, ReferActivity.class);
                        rewardIntent.putExtra("keyword","reward");
                        startActivity(rewardIntent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(AreaManagerActivity.this,EmergencyContactActivity.class));

                        break;

                    case R.id.contact_us:


                        break;

                    case R.id.logout:

                        Intent logoutIntent = new Intent(AreaManagerActivity.this, LoginActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        finish();

                        PreferenceUtil.putData(AreaManagerActivity.this,"token","");

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });


    }
}