package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

                if(TextUtils.isEmpty(activity.mobileNumber.getText().toString())){

                    activity.mobileNumber.setError("Enter Mobile Number");
                    activity.mobileNumber.requestFocus();

                    return;

                }


                sendOtpConfirmation(activity.mobileNumber.getText().toString());

                Intent intent = new Intent(EnterMobileNumberActivty.this, OtpValidation.class);
                intent.putExtra("mobileNo",activity.mobileNumber.getText().toString());
                startActivity(intent);

            }
        });


    }

     private void sendOtpConfirmation(String number)
     {
         //send otp
     }
}