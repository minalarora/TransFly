package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;
import com.msg91.sendotpandroid.library.roots.RetryType;
import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;
import com.mukesh.OtpView;
import com.truck.transfly.R;

public class OtpDialogFragment extends DialogFragment implements VerificationListener {

    private String mobileNo;
    private OtpView otpView;
    private FragmentActivity fragmentActivity;
    private boolean isTimerOn;
    private String otp;
    private onClickListener onClickListener;
    private String lastFourDigits;

    public OtpDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;

    }

    public interface onClickListener{

        void onClick(int i);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle arguments = getArguments();

        if(arguments!=null){

            mobileNo = arguments.getString("mobileNo");

        }

        View inflate = inflater.inflate(R.layout.fragment_otp_dialog, container, false);

        if(mobileNo.length()>4) {

            lastFourDigits = mobileNo.substring(mobileNo.length() - 4);

        } else {

            lastFourDigits=mobileNo;

        }

        sendOtpConfirmation(mobileNo);

        SendOTP.getInstance().getTrigger().initiate();

        otpView = inflate.findViewById(R.id.otp_view);

        TextView resendOtp =inflate.findViewById(R.id.resend_otp);

        TextView otp_string_text=inflate.findViewById(R.id.otp_string_text);

        otp_string_text.setText("We have sent the OTP to your registered mobile number xxxxxx"+lastFourDigits);

        inflate.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        inflate.findViewById(R.id.otpSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(otpView.getText().toString())){

                    Toast.makeText(fragmentActivity, "Fill OTP, which is send on your number!", Toast.LENGTH_SHORT).show();

                } else {

                    SendOTP.getInstance().getTrigger().verify(otpView.getText().toString());

                }

            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTimerOn){

                    Toast.makeText(fragmentActivity, "Wait For OTP!", Toast.LENGTH_SHORT).show();

                } else if(!isTimerOn){

                    SendOTP.getInstance().getTrigger().resend(RetryType.TEXT);

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

        return inflate;
    }



    private void sendOtpConfirmation(String number) {

        new SendOTPConfigBuilder()
                .setCountryCode(91)
                .setMobileNumber(number)
                .setVerifyWithoutOtp(true)//direct verification while connect with mobile network
                .setAutoVerification(fragmentActivity)//Auto read otp from Sms And Verify
                .setSenderId("TFIKJR")
                .setMessage("##OTP## is your OTP. TransFly")
                .setOtpLength(4)
                .setOtpExpireInMinute(10)
                .setVerificationCallBack(this).build();

    }

    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        fragmentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.i("TAG", "run_code: "+responseCode.getCode()+"=======>"+message);

                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                    //otp verified OR direct verified by send otp 2.O

                    Toast.makeText(fragmentActivity, "Mobile Number Verified Successfully", Toast.LENGTH_SHORT).show();

                    confirmOtp("ffjfjfj");

                    dismiss();

                } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {

                    otp=message;
                    otpView.setText(message);

                } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {

                    Toast.makeText(fragmentActivity, "OTP sent to your mobile number", Toast.LENGTH_SHORT).show();

                } else {

                    if(responseCode.getCode()==38){

                        Toast.makeText(fragmentActivity, ""+message, Toast.LENGTH_SHORT).show();

                        onClickListener.onClick(0);

                        dismiss();

                    } else {

                        Toast.makeText(fragmentActivity, "Wrong OTP! Try Again", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }

    private void confirmOtp(String ffjfjfj) {

        onClickListener.onClick(1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SendOTP.getInstance().getTrigger().stop();

    }
}