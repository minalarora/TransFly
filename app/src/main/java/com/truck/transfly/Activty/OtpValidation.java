package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;
import com.msg91.sendotpandroid.library.roots.RetryType;
import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityOtpValidationBinding;

public class OtpValidation extends AppCompatActivity implements VerificationListener {

    private ActivityOtpValidationBinding activity;
    private boolean fromForgot;
    private String mobileNo;
    private boolean isTimerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_otp_validation);

        Intent intent = getIntent();

        fromForgot = intent.getBooleanExtra("fromForgot", false);
        mobileNo = intent.getStringExtra("mobileNo");

        sendOtpConfirmation(mobileNo);

        SendOTP.getInstance().getTrigger().initiate();

        TextView resendOtp =findViewById(R.id.resend_otp);

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTimerOn){

                    Toast.makeText(OtpValidation.this, "Wait For Otp!", Toast.LENGTH_SHORT).show();

                } else if(!isTimerOn){

                    sendOtpOnMobile();

                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            resendOtp.setText("remaining: " + millisUntilFinished / 1000);

                            isTimerOn=true;

                        }

                        public void onFinish() {
                            resendOtp.setText("resendOtp");

                            isTimerOn=false;

                        }

                    }.start();

                }

            }
        });


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

    private void sendOtpOnMobile() {

        SendOTP.getInstance().getTrigger().resend(RetryType.TEXT);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendOTP.getInstance().getTrigger().stop();
    }

    private void confirmOtp(String text) {

        if (true) {

            if (!fromForgot) {

                Intent intent = new Intent(OtpValidation.this, UserChangedActivity.class);
                intent.putExtra("mobileNo", mobileNo);
                startActivity(intent);
                return;
            }

            Intent intent = new Intent(OtpValidation.this, ChangePasswordActivity.class);
            intent.putExtra("mobileNo", mobileNo);
            startActivity(intent);

        } else {
            //invalid otp
        }

    }

    private void sendOtpConfirmation(String number) {

        new SendOTPConfigBuilder()
                .setCountryCode(91)
                .setMobileNumber(number)
                .setVerifyWithoutOtp(true)//direct verification while connect with mobile network
                .setAutoVerification(OtpValidation.this)//Auto read otp from Sms And Verify
                .setSenderId("ABCDEF")
                .setMessage("##OTP## is Your verification digits.")
                .setOtpLength(4)
                .setOtpExpireInMinute(10)
                .setVerificationCallBack(this).build();

    }

    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "onSendOtpResponse: " + responseCode.getCode() + "=======" + message);
                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                    //otp verified OR direct verified by send otp 2.O
                } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {
                    //Auto read otp from sms successfully
                    // you can get otp form message filled
                } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {
                    // Otp send to number successfully
                } else {
                    //exception found
                }
            }
        });
    }
}