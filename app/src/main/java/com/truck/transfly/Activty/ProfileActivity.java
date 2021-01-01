package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityProfileBinding;
import com.truck.transfly.utils.TransflyApplication;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        activity = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        Intent intent = getIntent();
        String stringText = intent.getStringExtra("stringText");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        if(stringText.equals("vehicleOwner")){

            ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

        } else if(stringText.equals("fieldStaff")){

            ResponseFieldStaff responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseFieldStaff();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());


        } else if(stringText.equals("areaManager")){

            ResponseAreaManager responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseAreaManager();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());


        } else if(stringText.equals("transporter")){

            ResponseTransporter responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());


        }



    }
}