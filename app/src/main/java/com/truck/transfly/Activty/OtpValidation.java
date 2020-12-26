package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityOtpValidationBinding;

public class OtpValidation extends AppCompatActivity {

    private ActivityOtpValidationBinding activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_otp_validation);

        activity.otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOtp("otpno");
            }
        });
//        findViewById(R.id.otpSubmit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(OtpValidation.this,UserChangedActivity.class));
//
//            }
//        });

    }

    private void confirmOtp(String text)
    {

        if(true)
        {
            startActivity(new Intent(OtpValidation.this,UserChangedActivity.class));
        }
        else
        {
            //invalid otp
        }

    }
}