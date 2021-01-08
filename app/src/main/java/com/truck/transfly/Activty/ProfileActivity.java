package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.truck.transfly.Frament.GmailUpdateFragment;
import com.truck.transfly.Frament.MobileUpdateDailogFragment;
import com.truck.transfly.Frament.OtpDialogFragment;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityProfileBinding;
import com.truck.transfly.utils.PreferenceUtil;
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

        activity.editMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateMobileNumber();

            }
        });

        activity.editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GmailUpdateFragment gmailUpdateFragment = new GmailUpdateFragment();
                gmailUpdateFragment.setCancelable(false);

                gmailUpdateFragment.setonClickListener(new GmailUpdateFragment.onClickListener() {
                    @Override
                    public void onClick(String email) {

                        activity.email.setText(email);

                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("email", activity.email.getText().toString());
                gmailUpdateFragment.setArguments(bundle);

                gmailUpdateFragment.show(getSupportFragmentManager(), "");

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        activity.kycButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stringText.equals("vehicleOwner")){

                    Intent vehicleKyc=new Intent(ProfileActivity.this,VehicleOwnerKycActivity.class);
                    startActivity(vehicleKyc);

                } else if(stringText.equals("fieldStaff")){

                    Intent fieldStaffKyc=new Intent(ProfileActivity.this,AreaFieldStafActivity.class);
                    startActivity(fieldStaffKyc);

                } else if(stringText.equals("areaManager")){

                    Intent areamanager=new Intent(ProfileActivity.this,AreaFieldStafActivity.class);
                    startActivity(areamanager);

                } else if(stringText.equals("transporter")) {

                    Intent transporter=new Intent(ProfileActivity.this,TransporterKycActivity.class);
                    startActivity(transporter);

                }

            }
        });

        if (stringText.equals("vehicleOwner")) {

            ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            String substring = responseVehicleOwner.getName().substring(0, 2);
            activity.profileTwo.setText(substring);

            activity.panNumber.setText(responseVehicleOwner.getPan());

            if(responseVehicleOwner.getStatus()==0){

                activity.completeKycParent.setVisibility(View.VISIBLE);

            }


        } else if (stringText.equals("fieldStaff")) {

            ResponseFieldStaff responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseFieldStaff();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            String substring = responseVehicleOwner.getName().substring(0, 2);
            activity.profileTwo.setText(substring);

            if(responseVehicleOwner.getStatus()==0){

                activity.completeKycParent.setVisibility(View.VISIBLE);

            }

        } else if (stringText.equals("areaManager")) {

            ResponseAreaManager responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseAreaManager();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            String substring = responseVehicleOwner.getName().substring(0, 2);
            activity.profileTwo.setText(substring);

            if(responseVehicleOwner.getStatus()==0){

                activity.completeKycParent.setVisibility(View.VISIBLE);

            }

        } else if (stringText.equals("transporter")) {

            ResponseTransporter responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            String substring = responseVehicleOwner.getName().substring(0, 2);
            activity.profileTwo.setText(substring);

            if(responseVehicleOwner.getStatus()==0){

                activity.completeKycParent.setVisibility(View.VISIBLE);

            }

        }

    }

    private void updateMobileNumber() {

        MobileUpdateDailogFragment mobileUpdateDailogFragment = new MobileUpdateDailogFragment();
        mobileUpdateDailogFragment.setCancelable(false);
        mobileUpdateDailogFragment.show(getSupportFragmentManager(), "mobileUpdate");

        mobileUpdateDailogFragment.setOnClickListener(new MobileUpdateDailogFragment.onClickListener() {
            @Override
            public void onClick(String s) {

                OtpDialogFragment otpDialogFragment = new OtpDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("mobileNo", s);
                otpDialogFragment.setArguments(bundle);
                otpDialogFragment.setCancelable(false);
                otpDialogFragment.show(getSupportFragmentManager(), "otpValidation");

                otpDialogFragment.setOnClickListener(new OtpDialogFragment.onClickListener() {
                    @Override
                    public void onClick(int i) {

                        if(i==0){

                            // update soon

                        } else {

                            activity.phone.setText(s);

                            setDataOnModels(s);

                        }

                    }
                });

            }
        });


    }

    private void setDataOnModels(String mobile) {

        String token = PreferenceUtil.getData(ProfileActivity.this, "token");

        String s = token.split(":")[0];

        switch (s){

            case "vehicleowner":

                ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

                responseVehicleOwner.setMobile(mobile);

                ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                break;

            case "areamanager":

                ResponseAreaManager responseAreaManager = ((TransflyApplication) getApplication()).getResponseAreaManager();

                responseAreaManager.setMobile(mobile);

                ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);


                break;

            case "fieldstaff":

                ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();

                responseFieldStaff.setMobile(mobile);

                ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);


                break;

            case "transporter":

                ResponseTransporter responseTransporterOwner = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

                responseTransporterOwner.setMobile(mobile);

                ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporterOwner);

                break;

        }

    }


}