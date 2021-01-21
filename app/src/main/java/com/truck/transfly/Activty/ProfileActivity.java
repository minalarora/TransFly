package com.truck.transfly.Activty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;
import com.truck.transfly.Frament.GmailUpdateFragment;
import com.truck.transfly.Frament.MobileUpdateDailogFragment;
import com.truck.transfly.Frament.OtpDialogFragment;
import com.truck.transfly.Model.RequestMobile;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseProfile;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityProfileBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activity;
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private String token;
    private String image = null;
    public Uri fileUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.VISIBLE);

        validateToken();

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

                if (stringText.equals("vehicleOwner")) {

                    Intent vehicleKyc = new Intent(ProfileActivity.this, VehicleOwnerKycActivity.class);
                    startActivity(vehicleKyc);

                } else if (stringText.equals("fieldStaff")) {

                    Intent fieldStaffKyc = new Intent(ProfileActivity.this, AreaFieldStafActivity.class);
                    startActivity(fieldStaffKyc);

                } else if (stringText.equals("areaManager")) {

                    Intent areamanager = new Intent(ProfileActivity.this, AreaFieldStafActivity.class);
                    startActivity(areamanager);

                } else if (stringText.equals("transporter")) {

                    Intent transporter = new Intent(ProfileActivity.this, TransporterKycActivity.class);
                    startActivity(transporter);

                }

            }
        });

    }

    private void CheckKycParent(String stringText) {

        if (stringText.equals("vehicleowner")) {

            ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());


            /**
             *  if (!user.profile.isNullOrBlank()) {
             *             if (imageString.isNullOrBlank()) {
             *                 var bytes = Base64.decode(user.profile, Base64.DEFAULT)
             *                 activity.pic.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
             *                 Log.d("which", "if")
             *             } else if (imageString != user.profile) {
             *                 var bytes = Base64.decode(imageString, Base64.DEFAULT)
             *                 activity.pic.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
             *                 Log.d("which", "else")
             *             } else {
             *                 var bytes = Base64.decode(user.profile, Base64.DEFAULT)
             *                 activity.pic.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
             *                 Log.d("which", "if2")
             *
             *             }
             *         }
             */

            if (responseVehicleOwner.getProfile() != null) {

                Glide.with(ProfileActivity.this).load(responseVehicleOwner.getProfile()).placeholder(R.drawable.dummy_user).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.pic);

            }

            String substring = responseVehicleOwner.getName().substring(0, 2);


            activity.panNumber.setText(responseVehicleOwner.getPan());

            if (responseVehicleOwner.getStatus() == 0) {

                activity.completeKycParent.setVisibility(View.VISIBLE);

            } else if (responseVehicleOwner.getStatus() == 1) {

                activity.pendingKycParent.setVisibility(View.VISIBLE);

            }


        } else if (stringText.equals("fieldstaff")) {

            ResponseFieldStaff responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseFieldStaff();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            if (responseVehicleOwner.getProfile() != null) {

                Glide.with(ProfileActivity.this).load(responseVehicleOwner.getProfile()).placeholder(R.drawable.dummy_user).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.pic);

            }

            String substring = responseVehicleOwner.getName().substring(0, 2);


            activity.panNumber.setText(responseVehicleOwner.getPan());

            if (responseVehicleOwner.getStatus() == 0) {

                activity.completeKycParent.setVisibility(View.VISIBLE);

            } else if (responseVehicleOwner.getStatus() == 1) {

                activity.pendingKycParent.setVisibility(View.VISIBLE);

            }

        } else if (stringText.equals("areamanager")) {

            ResponseAreaManager responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseAreaManager();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            Log.i("TAG", "profileImage: "+responseVehicleOwner.getProfile());

            if (responseVehicleOwner.getProfile() != null) {

                Glide.with(ProfileActivity.this).load(responseVehicleOwner.getProfile()).placeholder(R.drawable.dummy_user).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.pic);

            }

            String substring = responseVehicleOwner.getName().substring(0, 2);


            activity.panNumber.setText(responseVehicleOwner.getPan());

            if (responseVehicleOwner.getStatus() == 0) {

                activity.completeKycParent.setVisibility(View.VISIBLE);

            } else if (responseVehicleOwner.getStatus() == 1) {

                activity.pendingKycParent.setVisibility(View.VISIBLE);

            }

        } else if (stringText.equals("transporter")) {

            ResponseTransporter responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

            activity.nameOfUser.setText(responseVehicleOwner.getName());
            activity.email.setText(responseVehicleOwner.getEmail());
            activity.phone.setText(responseVehicleOwner.getMobile());

            if (responseVehicleOwner.getProfile() != null) {

//                Picasso.get().load(responseVehicleOwner.getProfile()).placeholder(R.drawable.dummy_user).into(activity.pic);

                Glide.with(ProfileActivity.this).load(responseVehicleOwner.getProfile()).placeholder(R.drawable.dummy_user).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.pic);

            }

            String substring = responseVehicleOwner.getName().substring(0, 2);

            activity.panNumber.setText(responseVehicleOwner.getPan());

            if (responseVehicleOwner.getStatus() == 0) {

                activity.completeKycParent.setVisibility(View.VISIBLE);

            } else if (responseVehicleOwner.getStatus() == 1) {

                activity.pendingKycParent.setVisibility(View.VISIBLE);

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

                        if (i == 0) {

                            // update soon

                        } else {

                            activity.phone.setText(s);

                            RequestMobile requestMobile = new RequestMobile();
                            requestMobile.setMobile(s);

                            updateMobile(PreferenceUtil.getData(ProfileActivity.this, "token"), requestMobile);

                        }

                    }
                });

            }
        });


    }

    private void updateMobile(String token, RequestMobile mobile) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.updateMobile(token, mobile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                setDataOnModels(mobile.getMobile());

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(ProfileActivity.this, "Update SuccessFul", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(ProfileActivity.this, "Something Went Wrong Try Again", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void setDataOnModels(String mobile) {

        String token = PreferenceUtil.getData(ProfileActivity.this, "token");

        String s = token.split(":")[0];

        switch (s) {

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

    private void validateToken() {
        try {

            parent_of_loading.setVisibility(View.VISIBLE);

            token = PreferenceUtil.getData(ProfileActivity.this, "token");

            String type = this.token.split(":")[0];
            switch (type) {
                case "vehicleowner": {

                    api.getVehicleOwner(this.token).enqueue(new Callback<ResponseVehicleOwner>() {
                        @Override
                        public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {

                            parent_of_loading.setVisibility(View.GONE);

                            if (response.code() == 200) {
///
                                ResponseVehicleOwner responseVehicleOwner = response.body();
                                ((TransflyApplication) getApplication()).setResponseVehicleOwner(responseVehicleOwner);

                                CheckKycParent(type);

                                setStatusOnProfile(String.valueOf(responseVehicleOwner.getStatus()));


                            } else {
                                serverError();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {
                            serverError();
                        }
                    });

                    break;
                }
                case "transporter": {
                    api.getTransporter(this.token).enqueue(new Callback<ResponseTransporter>() {
                        @Override
                        public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {

                            parent_of_loading.setVisibility(View.GONE);

                            if (response.code() == 200) {

                                ResponseTransporter responseTransporter = response.body();
                                ((TransflyApplication) getApplication()).setResponseTransporterOwner(responseTransporter);

                                CheckKycParent(type);

                                setStatusOnProfile(String.valueOf(responseTransporter.getStatus()));

                            } else {

                                serverError();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseTransporter> call, Throwable t) {
                            serverError();
                        }
                    });
                    break;
                }
                case "areamanager": {
                    api.getAreaManager(this.token).enqueue(new Callback<ResponseAreaManager>() {
                        @Override
                        public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {

                            parent_of_loading.setVisibility(View.GONE);

                            if (response.code() == 200) {

                                ResponseAreaManager responseAreaManager = response.body();
                                ((TransflyApplication) getApplication()).setResponseAreaManager(responseAreaManager);

                                CheckKycParent(type);

                                setStatusOnProfile(String.valueOf(responseAreaManager.getStatus()));

                            } else {
                                serverError();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseAreaManager> call, Throwable t) {
                            serverError();
                        }
                    });
                    break;
                }
                case "fieldstaff": {
                    api.getFieldStaff(this.token).enqueue(new Callback<ResponseFieldStaff>() {
                        @Override
                        public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {

                            parent_of_loading.setVisibility(View.GONE);

                            if (response.code() == 200) {

                                ResponseFieldStaff responseFieldStaff = response.body();
                                ((TransflyApplication) getApplication()).setResponseFieldStaff(responseFieldStaff);

                                CheckKycParent(type);

                                setStatusOnProfile(String.valueOf(responseFieldStaff.getStatus()));

                            } else {
                                serverError();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {
                            serverError();
                        }
                    });
                    break;
                }
                default: {
                    serverError();
                }
            }
        } catch (Exception e) {


        }
    }

    private void setStatusOnProfile(@NonNull String responseFieldStaff) {

        if (responseFieldStaff.equals("0")) {

            activity.kycStatus.setText("PENDING");

        } else if (responseFieldStaff.equals("1")) {

            activity.kycStatus.setText("UNDER PROCESS");

        } else {

            activity.kycStatus.setText("COMPLETED");

        }


    }

    private void serverError() {

        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        parent_of_loading.setVisibility(View.GONE);

    }


    public void changePic(View view) {
        ImagePicker.Companion.with(ProfileActivity.this)//Crop image(Optional), Check Customization for more option
                .compress(256)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK && requestCode == 1) {


//            activity.doc1.setImageURI(fileUri)


            try {
                fileUri = data.getData();

                Glide.with(ProfileActivity.this).load(fileUri).placeholder(R.drawable.dummy_user).into(activity.pic);

                File file = ImagePicker.Companion.getFile(data);
                image = Base64.encodeToString(Files.readAllBytes(file.toPath()), Base64.NO_WRAP);
                ((TransflyApplication) getApplication()).getResponseVehicleOwner().setProfile(image);

                uploadMultipartSingle(fileUri);

//                updateImage(token, image);

            } catch (IOException e) {

            }


        }
    }

    private void uploadMultipartSingle(Uri image) {

        parent_of_loading.setVisibility(View.VISIBLE);

        MultipartUploadRequest multipartUploadRequest = null;
        try {
            multipartUploadRequest = new MultipartUploadRequest(this, EndApi.VEHICLE_OWNER_KYC)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .addHeader("Authorization", PreferenceUtil.getData(ProfileActivity.this,"token"))
                    .setUtf8Charset()
                    .setMethod("POST")
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {


                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                            parent_of_loading.setVisibility(View.GONE);

                            Log.i("TAG", "onError: " + serverResponse.getBodyAsString());

                            Toast.makeText(context, "Error" + serverResponse.getBodyAsString(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCompleted(final Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                            new Handler(getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    parent_of_loading.setVisibility(View.GONE);

                                }
                            }, 2000);

                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            parent_of_loading.setVisibility(View.GONE);


                        }


                    });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {

            String listing_logo = UUID.randomUUID().toString().replaceAll("-", "");


            multipartUploadRequest.addFileToUpload(image.getPath(), "profile", listing_logo, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        multipartUploadRequest.startUpload();

    }


    private void updateImage(String token, String image) {
        ResponseProfile profile = new ResponseProfile();
        profile.setProfile(image);
        api.updateProfile(token, profile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("minal", "" + response.code() + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}