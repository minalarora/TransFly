package com.truck.transfly.Activty;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityTransporterKycBinding;

public class TransporterKycActivity extends AppCompatActivity {
    private TextView mTextView;

    private ActivityTransporterKycBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_transporter_kyc);

        activity.registerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    activity.tdsEditLayout.setHint("Enter Aadhar Number");

                } else if(position==1){

                    activity.tdsEditLayout.setHint("Enter Pan Number");

                } else if(position==2) {

                    activity.tdsEditLayout.setHint("Enter Gst Number");

                }else if(position==3) {

                    activity.tdsEditLayout.setHint("Enter STA Number");

                }else if(position==4) {

                    activity.tdsEditLayout.setHint("Enter Mining License Number");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}