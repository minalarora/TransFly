package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityEnterMobileNumberActivtyBinding;

public class EnterMobileNumberActivty extends AppCompatActivity {

    private ActivityEnterMobileNumberActivtyBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_enter_mobile_number_activty);

        activity.mobileNumberEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtpConfirmation("8871748278");
                startActivity(new Intent(EnterMobileNumberActivty.this,OtpValidation.class));

            }
        });


    }

     private void sendOtpConfirmation(String number)
     {
         //send otp
     }
}