package com.truck.transfly.Activty;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.Model.RequestVehicle;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityAddVehicleBinding;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddVehicleActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 1009;
    private List<String> storageList = new ArrayList<>();
    private ActivityAddVehicleBinding activity;
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = DataBindingUtil.setContentView(this, R.layout.activity_add_vehicle);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);
        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

//        activity.addRcImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                askPermissions();
//
//            }
//        });

        activity.addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(false){

                    Toast.makeText(AddVehicleActivity.this, "Vehicle Rc Image Required", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (false) {

                    Toast.makeText(AddVehicleActivity.this, "Not Empty Item", Toast.LENGTH_SHORT).show();

                } else if(false) {

                    Toast.makeText(AddVehicleActivity.this, "Select Vehicle Tyres", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(activity.vehicleNumber.getText().toString())) {

                    activity.vehicleNumber.setError("Vehicle Number is Required");
                    activity.vehicleNumber.requestFocus();

                } else if (false) {

//                    activity.rcnumber.setError("R.c Number is Required");
//                    activity.rcnumber.requestFocus();

                } else if(TextUtils.isEmpty(activity.contactNumber.getText().toString())){

                    activity.contactNumber.setError("Contact Number is Required");
                    activity.contactNumber.requestFocus();

                } else if (false) {

                    Toast.makeText(AddVehicleActivity.this, "Please Provide R.c Image", Toast.LENGTH_SHORT).show();

                } else {

                    uploadImageInMultipart();

                }

            }
        });

    }

//    private void openStorage() {
//
//        Matisse.from(AddVehicleActivity.this)
//                .choose(MimeType.ofImage(), false)
//                .countable(true)
//                .maxSelectable(1)
//                .capture(true)
//                .captureStrategy(new CaptureStrategy(true, "com.truck.transfly.provider"))
//                .gridExpectedSize(
//                        getResources().getDimensionPixelSize(R.dimen._100ssp))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                .thumbnailScale(0.85f)
//                .imageEngine(new GlideEngine())
//                .setOnSelectedListener((uriList, pathList) -> {
//                })
//                .showSingleMediaType(true)
//                .originalEnable(true)
//                .maxOriginalSize(10)
//                .autoHideToolbarOnSingleTap(true)
//                .setOnCheckedListener(isChecked -> {
//                })
//                .forResult(REQUEST_CODE_CHOOSE);
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if (resultCode == RESULT_OK) {
//
//            if (requestCode == REQUEST_CODE_CHOOSE && data != null) {
//
//                storageList.clear();
//
//                storageList.addAll(Matisse.obtainPathResult(data));
//                Glide.with(AddVehicleActivity.this).load(storageList.get(0)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.imageLink);
//                activity.chooseTextView.setText("1 Image Selected");
//
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    private void askPermissions() {
//
//        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
//        String rationale = "Please provide Storage And Camera permission so that you can Upload R.c Image";
//        Permissions.Options options = new Permissions.Options()
//                .setRationaleDialogTitle("Info")
//                .setSettingsDialogTitle("Warning");
//
//        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
//            @Override
//            public void onGranted() {
//
//                openStorage();
//
//            }
//
//            @Override
//            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
//                // permission denied, block the feature.
//            }
//        });
//    }

    private void uploadImageInMultipart() {

        parent_of_loading.setVisibility(View.VISIBLE);
        RequestVehicle vehicle = new RequestVehicle();
        vehicle.setNumber(activity.vehicleNumber.getText().toString().replaceAll("\\s", ""));
        vehicle.setContact(activity.contactNumber.getText().toString());

        api.createVehicle(PreferenceUtil.getData(this,"token"),vehicle).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                parent_of_loading.setVisibility(View.GONE);
                if(response.code() == 200)
                {
                    Toast.makeText(AddVehicleActivity.this, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddVehicleActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            parent_of_loading.setVisibility(View.GONE);
                Toast.makeText(AddVehicleActivity.this, "Something Went Wrong! Try Again", Toast.LENGTH_SHORT).show();
                finish();




            }
        });

//        MultipartUploadRequest multipartUploadRequest = null;
//        try {
//            multipartUploadRequest = new MultipartUploadRequest(this, EndApi.ADD_VEHICLE)
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(5)
//                    .addHeader("Authorization", PreferenceUtil.getData(AddVehicleActivity.this,"token"))
//                    .addParameter("number", activity.vehicleNumber.getText().toString())
//                  //  .addParameter("rc", activity.rcnumber.getText().toString())
//                    .addParameter("contact",activity.contactNumber.getText().toString())
//                    //.addParameter("vehiclename", activity.vehicleNameSpinner.getSelectedItem().toString())
//                    //.addParameter("tyres",activity.vehicleOwnTyres.getSelectedItem().toString())
//                    .setUtf8Charset()
//                    .setMethod("POST")
//                    .setDelegate(new UploadStatusDelegate() {
//                        @Override
//                        public void onProgress(Context context, UploadInfo uploadInfo) {
//
//
//                        }
//
//                        @Override
//                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
//
//                            parent_of_loading.setVisibility(View.GONE);
//
//                            Log.i("TAG", "onError: " + serverResponse.getBodyAsString());
//
//                            Toast.makeText(context, "Error" + serverResponse.getBody(), Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onCompleted(final Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
//
//                            new Handler(getMainLooper()).postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    parent_of_loading.setVisibility(View.GONE);
//
//                                    finish();
//
////                                    Intent intent = new Intent(StepThreeActivity.this, StepFourActivity.class);
////                                    intent.putExtra("listing_id", listing_id);
////                                    startActivity(intent);
//
//                                    Toast.makeText(context, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }, 2000);
//
//                        }
//
//                        @Override
//                        public void onCancelled(Context context, UploadInfo uploadInfo) {
//
//                            parent_of_loading.setVisibility(View.GONE);
//
//
//                        }
//
//
//                    });
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

//        try {
//
//            String listing_logo = UUID.randomUUID().toString().replaceAll("-", "");
//
//            multipartUploadRequest.addFileToUpload(storageList.get(0), "rcimage", listing_logo, "UTF-8");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        multipartUploadRequest.startUpload();

    }
}