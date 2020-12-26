package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityUserChangedBinding;

public class UserChangedActivity extends AppCompatActivity {

    private ImageView check1, check2, check3, check4;
    private int i=0;
    private ActivityUserChangedBinding activity;
    private String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_user_changed);

        check1 = activity.check1;
        check2 = activity.check2;
        check3 =activity.check3;
        check4 = activity.check4;

        activity.checkedRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i==0){

                    Toast.makeText(UserChangedActivity.this, "Please Select a user", Toast.LENGTH_SHORT).show();

                    return;

                }

                startActivity(new Intent(UserChangedActivity.this,SignUpActivity.class));

            }
        });

        activity.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check1.setVisibility(View.VISIBLE);

                i=1;

            }
        });

        activity.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check2.setVisibility(View.VISIBLE);

                i=2;

            }
        });

        activity.card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check3.setVisibility(View.VISIBLE);

                i=3;

            }
        });

        activity.card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check4.setVisibility(View.VISIBLE);

                i=4;

            }
        });

    }

    private void hideAllCheckImages() {

        check1.setVisibility(View.GONE);
        check2.setVisibility(View.GONE);
        check3.setVisibility(View.GONE);
        check4.setVisibility(View.GONE);

    }


    private void onSelectedUser(String type)
    {
        //give user type to next activity
    }
}