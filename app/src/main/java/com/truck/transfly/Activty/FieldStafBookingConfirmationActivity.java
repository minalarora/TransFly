package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityFieldStafBookingConfirmationBinding;

public class FieldStafBookingConfirmationActivity extends AppCompatActivity {

    private ActivityFieldStafBookingConfirmationBinding activity;
    private ArrayAdapter<String> adapter;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_field_staf_booking_confirmation);

        Intent intent = getIntent();
        ResponseBooking responseBooking = intent.getParcelableExtra("responseBooking");

        activity.mobileNumber.setText(responseBooking.getVehicleownermobile());
        activity.ownerName.setText(responseBooking.getVehicleowner());
        activity.toFromDest.setText(responseBooking.getLoading() +" - "+responseBooking.getMinename());

        parent_of_loading =findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, pendingList);

        activity.registerCategory.setAdapter(adapter);

        activity.registerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activity.creaateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(activity.tonnege.getText().toString())){

                    activity.tonnege.setError("Tonnege is Required*");
                    activity.tonnege.requestFocus();

                } else if(TextUtils.isEmpty(activity.rate.getText().toString())){

                    activity.rate.setError("Rate is Required*");
                    activity.rate.requestFocus();

                } else if(TextUtils.isEmpty(activity.hsd.getText().toString())){

                    activity.hsd.setError("HSD is Required*");
                    activity.hsd.requestFocus();

                } else if(TextUtils.isEmpty(activity.amount.getText().toString())){

                    activity.amount.setError("Amount is Required*");
                    activity.amount.requestFocus();

                } else if(TextUtils.isEmpty(activity.cash.getText().toString())){

                    activity.cash.setError("Cash is Required*");
                    activity.cash.requestFocus();

                } else {



                }

            }
        });

    }
}