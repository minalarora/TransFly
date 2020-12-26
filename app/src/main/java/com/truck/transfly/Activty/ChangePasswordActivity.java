package com.truck.transfly.Activty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ChangePasswordActivtyBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ChangePasswordActivtyBinding activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = DataBindingUtil.setContentView(this,R.layout.change_password_activty);

        activity.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }
}
