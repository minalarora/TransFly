package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;
import com.truck.transfly.Adapter.ViewPagerImageCarouselAdapter;
import com.truck.transfly.Model.ResponseResale;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityShowOlxProductBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class ShowOlxProductActivity extends AppCompatActivity {

    private ResponseResale vehicleStore;
    private ArrayList<String> stringImage;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ActivityShowOlxProductBinding activity;
    private FrameLayout parent_of_loading;
    private boolean lease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_show_olx_product);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading=findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        Intent intent = getIntent();
        vehicleStore = intent.getParcelableExtra("vehicleStore");
        stringImage =  intent.getStringArrayListExtra("stringImage");
        lease =  intent.getBooleanExtra("lease",false);

        findViewById(R.id.contact_us).setOnClickListener(new View.OnClickListener() {
            private String leaseString;

            @Override
            public void onClick(View v) {

                if(lease)
                    leaseString="lease";
                else
                    leaseString="resale";

                contactForResale(PreferenceUtil.getData(ShowOlxProductActivity.this,"token"),vehicleStore.getVehicleName(),leaseString);

            }
        });

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

    private void contactForResale(String token, String vehicleName, String leaseString)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.contactResale(token,vehicleName,leaseString).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    Toast.makeText(ShowOlxProductActivity.this, "Thank you! your message has been sent and you will soon get a call", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(ShowOlxProductActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(ShowOlxProductActivity.this, "No Internet Connection! Try Again", Toast.LENGTH_SHORT).show();

            }
        });
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