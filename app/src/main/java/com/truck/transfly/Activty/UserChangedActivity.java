package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.truck.transfly.R;

public class UserChangedActivity extends AppCompatActivity {

    private ImageView check1, check2, check3, check4;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_changed);

        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);

        findViewById(R.id.checkedRound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i==0){

                    Toast.makeText(UserChangedActivity.this, "Please Select a user", Toast.LENGTH_SHORT).show();

                    return;

                }

                startActivity(new Intent(UserChangedActivity.this,SignUpActivity.class));

            }
        });

        findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check1.setVisibility(View.VISIBLE);

                i=1;

            }
        });

        findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check2.setVisibility(View.VISIBLE);

                i=2;

            }
        });

        findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideAllCheckImages();

                check3.setVisibility(View.VISIBLE);

                i=3;

            }
        });

        findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
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
}