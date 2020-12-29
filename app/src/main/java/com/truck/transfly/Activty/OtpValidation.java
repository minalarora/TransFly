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
    private boolean fromForgot;
    private String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_otp_validation);

        Intent intent = getIntent();

         fromForgot = intent.getBooleanExtra("fromForgot", false);
         mobileNo = intent.getStringExtra("mobileNo");

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

        if(true) {

            if(!fromForgot) {

                Intent intent = new Intent(OtpValidation.this, UserChangedActivity.class);
                intent.putExtra("mobileNo",mobileNo);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(OtpValidation.this, ChangePasswordActivity.class);
            intent.putExtra("mobileNo",mobileNo);
            startActivity(intent);

        }
        else {
            //invalid otp
        }

    }
}