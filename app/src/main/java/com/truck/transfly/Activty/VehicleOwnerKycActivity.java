package com.truck.transfly.Activty;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityKycEditBinding;

public class VehicleOwnerKycActivity extends AppCompatActivity {

    private ActivityKycEditBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_kyc_edit);


        activity.registerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activity.panEditLayout.setVisibility(View.GONE);
                activity.bankParent.setVisibility(View.GONE);
                activity.tdsEditLayout.setVisibility(View.GONE);

                if (position == 0) {

                    activity.tdsEditLayout.setVisibility(View.VISIBLE);

                } else if (position == 1) {

                    activity.panEditLayout.setVisibility(View.VISIBLE);

                } else if (position == 2) {

                    activity.bankParent.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}