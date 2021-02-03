package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.truck.transfly.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        EditText mobileNumber = findViewById(R.id.mobileNumber);

        findViewById(R.id.send_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(mobileNumber.getText().toString())){

                    mobileNumber.setError("Enter Mobile Number*");
                    mobileNumber.requestFocus();
                    return;

                }

                Intent intent = new Intent(ResetPasswordActivity.this, OtpValidation.class);
                intent.putExtra("fromForgot",true);
                intent.putExtra("mobileNo",mobileNumber.getText().toString());
                startActivity(intent);

            }
        });

    }
}