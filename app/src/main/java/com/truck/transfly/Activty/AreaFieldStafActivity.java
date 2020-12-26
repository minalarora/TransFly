package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityAreaFieldStafBinding;

public class AreaFieldStafActivity extends AppCompatActivity {

    private ActivityAreaFieldStafBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_area_field_staf);

        activity.registerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activity.tdsEditLayout.setVisibility(View.GONE);
                activity.bankParent.setVisibility(View.GONE);

                if(position==0){

                    activity.tdsEditLayout.setHint("Enter Pan Number");
                    activity.tdsEditLayout.setVisibility(View.VISIBLE);

                } else if(position==1){

                    activity.tdsEditLayout.setHint("Enter Aadhar Number");
                    activity.tdsEditLayout.setVisibility(View.VISIBLE);

                } else if(position==2) {

                    activity.bankParent.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}