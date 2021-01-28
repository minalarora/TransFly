package com.truck.transfly.Activty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.truck.transfly.Adapter.ViewPagerAdapter;
import com.truck.transfly.Frament.ClearedFragment;
import com.truck.transfly.Frament.PendingFragment;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.utils.TransflyApplication;

import org.joda.time.DateTime;

import java.util.Calendar;

public class CurrentInvoicesActivity extends AppCompatActivity implements SmoothDateRangePickerFragment.OnDateRangeSetListener {

    private boolean fieldstaffKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_cleared_activity);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        Intent intent = getIntent();
        fieldstaffKeyword = intent.getBooleanExtra("fieldstaff", false);

        findViewById(R.id.export_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar now = Calendar.getInstance();

                SmoothDateRangePickerFragment smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(CurrentInvoicesActivity.this::onDateRangeSet, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                smoothDateRangePickerFragment.setAccentColor(R.color.project_color);

                smoothDateRangePickerFragment.setThemeDark(false);

                smoothDateRangePickerFragment.show(getFragmentManager(), "smoothDateRangePicker");

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(new PendingFragment(), "Pending");
        viewPagerAdapter.addFragment(new ClearedFragment(), "Completed");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onDateRangeSet(SmoothDateRangePickerFragment view, int yearStart, int monthStart, int dayStart, int yearEnd, int monthEnd, int dayEnd) {

        ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

        if (fieldstaffKeyword) {

            Uri uri = Uri.parse("https://transfly-ftr2t.ondigitalocean.app/mobinvoicefieldstaff"+"?mobile="+responseVehicleOwner.getMobile()+"&from="+yearStart+"-"+(monthStart+1)+"-"+dayStart+"&"+"to="+yearEnd+"-"+(monthEnd+1)+"-"+dayEnd);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);


        } else{

            Uri uri = Uri.parse("https://transfly-ftr2t.ondigitalocean.app/mobinvoicevehicleowner"+"?mobile="+responseVehicleOwner.getMobile()+"&from="+yearStart+"-"+(monthStart+1)+"-"+dayStart+"&"+"to="+yearEnd+"-"+(monthEnd+1)+"-"+dayEnd);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }


    }
}