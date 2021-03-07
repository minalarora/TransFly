package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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
import com.mukesh.OtpView;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityOtpValidationBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtpValidation extends AppCompatActivity implements VerificationListener {

    private ActivityOtpValidationBinding activity;
    private boolean fromForgot;
    private String mobileNo;
    private boolean isTimerOn;
    private OtpView otpView;
    private String otp;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_otp_validation);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        Intent intent = getIntent();



        fromForgot = intent.getBooleanExtra("fromForgot", false);
        mobileNo = intent.getStringExtra("mobileNo");

        String lastFourDigits = mobileNo.substring(mobileNo.length() - 4);

       sendOtpConfirmation(mobileNo);

       // SendOTP.getInstance().getTrigger().initiate();

        otpView = findViewById(R.id.otp_view);

        TextView resendOtp =findViewById(R.id.resend_otp);

        TextView otp_string_text=findViewById(R.id.otp_string_text);

        otp_string_text.setText("We have sent the OTP to your mobile number xxxxxx"+lastFourDigits);

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTimerOn){

                    Toast.makeText(OtpValidation.this, "Wait For OTP!", Toast.LENGTH_SHORT).show();

                } else if(!isTimerOn){

                   // SendOTP.getInstance().getTrigger().resend(RetryType.TEXT);

                    api.resendOtp(mobileNo).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                    otp_string_text.setText("OTP Sent to registered Mobile Number "+lastFourDigits);

                    Toast.makeText(OtpValidation.this, "OTP Sent to registered Mobile Number "+lastFourDigits, Toast.LENGTH_SHORT).show();

                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            resendOtp.setText("Remaining: " + millisUntilFinished / 1000);

                            isTimerOn=true;

                        }

                        public void onFinish() {
                            resendOtp.setText("Resend OTP");

                            isTimerOn=false;

                        }

                    }.start();

                }

            }
        });

        activity.otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(otpView.getText().toString())){

                    Toast.makeText(OtpValidation.this, "Fill OTP, which is send on your number!", Toast.LENGTH_SHORT).show();

                } else {

                    api.verifyOtp(mobileNo,otpView.getText().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code() == 200)
                            {
//                             //   confirmOtp("SD");
                                Toast.makeText(OtpValidation.this, "Mobile Number Verified Successfully", Toast.LENGTH_SHORT).show();
                                confirmOtp("ffjfjfj");
                            }
                            else
                            {
                                Toast.makeText(OtpValidation.this, "Wrong OTP! Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(OtpValidation.this, "Wrong OTP! Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //SendOTP.getInstance().getTrigger().verify(otpView.getText().toString());

                }

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
        //SendOTP.getInstance().getTrigger().stop();
    }

    private void confirmOtp(String text) {

        if (true) {

            if (!fromForgot) {

                Intent intent = new Intent(OtpValidation.this, UserChangedActivity.class);
                intent.putExtra("mobileNo", mobileNo);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                return;
            }

            Intent intent = new Intent(OtpValidation.this, ChangePasswordActivity.class);
            intent.putExtra("mobileNo", mobileNo);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();


        } else {
            //invalid otp
        }

    }

    private void sendOtpConfirmation(String number) {

//        new SendOTPConfigBuilder()
//                .setCountryCode(91)
//                .setMobileNumber(number)
//                .setVerifyWithoutOtp(true)//direct verification while connect with mobile network
//                .setAutoVerification(OtpValidation.this)//Auto read otp from Sms And Verify
//                .setSenderId("ABCDEF")
//                .setMessage("##OTP## is your confirmation on OTP, Please do not share your OTP and confidential info with anyone.TransFly")
//                .setOtpLength(4)
//                .setOtpExpireInMinute(10)
//                .setVerificationCallBack(this).build();


        api.sendOtpLogin(number).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                    //otp verified OR direct verified by send otp 2.O

                    Toast.makeText(OtpValidation.this, "Mobile Number Verified Successfully", Toast.LENGTH_SHORT).show();

                    confirmOtp("ffjfjfj");

                }
                else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {

                    otp=message;
                    otpView.setText(message);

                }
                else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {

                    Toast.makeText(OtpValidation.this, "OTP sent to your mobile number", Toast.LENGTH_SHORT).show();

                } else {

                    if(responseCode.getCode()==38){

                        Toast.makeText(OtpValidation.this, ""+message, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(OtpValidation.this, "Wrong OTP! Try Again", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }
}