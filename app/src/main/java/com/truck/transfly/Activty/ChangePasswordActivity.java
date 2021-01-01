package com.truck.transfly.Activty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ChangePasswordActivtyBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ChangePasswordActivtyBinding activity;
    private String mobileNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mobileNo = intent.getStringExtra("mobileNo");

        activity = DataBindingUtil.setContentView(this,R.layout.change_password_activty);

        activity.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(TextUtils.isEmpty(activity.password.getText().toString()) || activity.password.getText().length()<4){

                   activity.password.setError("Password Must Be 4 digits*");
                   activity.password.requestFocus();

               } else if(TextUtils.isEmpty(activity.reenterPassword.getText().toString())){

                   activity.reenterPassword.setError("Re Enter Password is Required*");
                   activity.reenterPassword.requestFocus();

               } else if(!activity.reenterPassword.getText().toString().equals(activity.password.getText().toString())) {

                   activity.reenterPassword.setError("Password is not matched*");
                   activity.reenterPassword.requestFocus();

               } else {



               }

            }
        });

    }
}
