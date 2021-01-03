package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.truck.transfly.Adapter.ViewPagerImageCarouselAdapter;
import com.truck.transfly.Model.ResponseResale;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityShowOlxProductBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class ShowOlxProductActivity extends AppCompatActivity {

    private ResponseResale vehicleStore;
    private ArrayList<String> stringImage;
    private ActivityShowOlxProductBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_show_olx_product);

        Intent intent = getIntent();
        vehicleStore = intent.getParcelableExtra("vehicleStore");
        stringImage =  intent.getStringArrayListExtra("stringImage");

        activity.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        initViewPager();

        activity.vehicleName.setText(vehicleStore.getVehicleName());
        activity.dateName.setText(vehicleStore.getCompany()+" . "+vehicleStore.getYear());
        setAvailable(activity.ownership,vehicleStore.getOwnership());
        setAvailable(activity.insurance,vehicleStore.getInsurancepapers());
        setAvailable(activity.resold,vehicleStore.getCheckAndClearances());
        setAvailable(activity.vehicleService,vehicleStore.getVehicleServicesBook());
        setAvailable(activity.loanClearence,vehicleStore.getLoanClearanceCheck());
        setAvailable(activity.pollutionCertificate,vehicleStore.getPollutionCertificate());
        setAvailable(activity.engineChassis,vehicleStore.getEngineAndChassisCheck());
        setAvailable(activity.body,vehicleStore.getBody());
        setAvailable(activity.cabin,vehicleStore.getCabin());
        setAvailable(activity.tyres,vehicleStore.getTyres());
        setAvailable(activity.otherDeclaration,vehicleStore.getOtherImportantDeclarations());
        setAvailable(activity.readTax,vehicleStore.getRoadTaxReceipt());

    }

    private void initViewPager() {

        ViewPager viewPager=findViewById(R.id.viewPager);
        ViewPagerImageCarouselAdapter viewPagerImageCarouselAdapter=new ViewPagerImageCarouselAdapter(ShowOlxProductActivity.this,stringImage);
        viewPager.setAdapter(viewPagerImageCarouselAdapter);
        ScrollingPagerIndicator scrollingPagerIndicator=findViewById(R.id.indicatorCondition);
        scrollingPagerIndicator.attachToPager(viewPager);
        scrollingPagerIndicator.setDotCount(0);
        scrollingPagerIndicator.setDotColor(Color.parseColor("#ffffff"));
        scrollingPagerIndicator.setSelectedDotColor(ContextCompat.getColor(Objects.requireNonNull(ShowOlxProductActivity.this),R.color.quantum_pink));
        scrollingPagerIndicator.setVisibleDotCount(7);

        // load images in viewpager together
        viewPager.setOffscreenPageLimit(6);


    }

    private void setAvailable(TextView textView,boolean bool){

        if(bool){

            textView.setText("Available");
            textView.setTextColor(ContextCompat.getColor(ShowOlxProductActivity.this,R.color.quantum_googgreen900));

        } else {

            textView.setText("Not Available");
            textView.setTextColor(ContextCompat.getColor(ShowOlxProductActivity.this,R.color.quantum_pink));

        }

    }


}