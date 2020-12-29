package com.truck.transfly.Activty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityBankDetailsBinding;
import com.truck.transfly.databinding.ActivityKycEditBinding;
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
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BankDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 99;
    private ActivityBankDetailsBinding activity;
    private ArrayList<String> storageList=new ArrayList<>();
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayAdapter<String> adapter;
    ArrayList<String> pendingList  =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_bank_details);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        PreferenceUtil.putData(BankDetailsActivity.this,"token","transporter:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiIxMzMzNjY2NjY2NjEiLCJpYXQiOjE2MDkyMjczMDIsImV4cCI6MTYxMTgxOTMwMn0.zkmUjQE8OiLeY3nE46v6lZrQszxa6D17pgJAR_2Vfog");

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

        activity.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                askPermissions();

            }
        });

        activity.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=activity.registerCategory.getSelectedItemPosition();

                if (activity.registerCategory.getSelectedItem().toString().equals("tds")) {

                    if(!TextUtils.isEmpty(activity.enterTdsNumber.getText().toString())){

                        uploadMultipartSingle(activity.enterTdsNumber.getText().toString(),"tds","tdsimage");

                    } else {

                        Toast.makeText(BankDetailsActivity.this, "Enter Tds Number", Toast.LENGTH_SHORT).show();

                    }


                } else if (activity.registerCategory.getSelectedItem().toString().equals("pan")) {

                    if(!TextUtils.isEmpty(activity.panEdittext.getText().toString())){

                        uploadMultipartSingle(activity.panEdittext.getText().toString(),"pan","panimage");

                    } else {

                        Toast.makeText(BankDetailsActivity.this, "Enter Pan Number", Toast.LENGTH_SHORT).show();

                    }

                } else if (activity.registerCategory.getSelectedItem().toString().equals("bank")) {

                    if(TextUtils.isEmpty(activity.bankName.getText().toString())){

                        Toast.makeText(BankDetailsActivity.this, "Enter bank Name", Toast.LENGTH_SHORT).show();

                    } else if(TextUtils.isEmpty(activity.bankNumber.getText().toString())){

                        Toast.makeText(BankDetailsActivity.this, "Enter Bank Number", Toast.LENGTH_SHORT).show();

                    } else if(TextUtils.isEmpty(activity.bankIfc.getText().toString())){

                        Toast.makeText(BankDetailsActivity.this, "Enter Bank IFSC", Toast.LENGTH_SHORT).show();

                    } else {

                        uploadBankMultipart();

                    }

                }

            }
        });

        activity.registerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activity.panEditLayout.setVisibility(View.GONE);
                activity.bankParent.setVisibility(View.GONE);
                activity.tdsEditLayout.setVisibility(View.GONE);

                if (activity.registerCategory.getSelectedItem().toString().equals("tds")) {

                    activity.tdsEditLayout.setVisibility(View.VISIBLE);

                } else if (activity.registerCategory.getSelectedItem().toString().equals("pan")) {

                    activity.panEditLayout.setVisibility(View.VISIBLE);

                } else if (activity.registerCategory.getSelectedItem().toString().equals("bank")) {

                    activity.bankParent.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getPendingList(String token)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getPendingList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {

                    Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
                    try {
                        pendingList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(pendingList.isEmpty())
                    {

                        Log.d("minal","kyc completed");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        adapter.notifyDataSetChanged();
                        Log.d("minal",pendingList.toString());
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

            }
        });
    }

    private void uploadBankMultipart() {

        parent_of_loading.setVisibility(View.VISIBLE);

        MultipartUploadRequest multipartUploadRequest = null;
        try {
            multipartUploadRequest = new MultipartUploadRequest(this, EndApi.VEHICLE_OWNER_KYC)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .addHeader("Authorization", PreferenceUtil.getData(BankDetailsActivity.this,"token"))
                    .addParameter("bankname",activity.bankName.getText().toString())
                    .addParameter("accountno",activity.bankNumber.getText().toString())
                    .addParameter("ifsc",activity.bankIfc.getText().toString())
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

                            Toast.makeText(context, "Error" + serverResponse.getHttpCode(), Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onCompleted(final Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                            new Handler(getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    parent_of_loading.setVisibility(View.GONE);

//                                    Intent intent = new Intent(StepThreeActivity.this, StepFourActivity.class);
//                                    intent.putExtra("listing_id", listing_id);
//                                    startActivity(intent);

                                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();

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

            multipartUploadRequest.addFileToUpload(storageList.get(0), "bankimage", listing_logo, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        multipartUploadRequest.startUpload();

    }

    private void uploadMultipartSingle(String number_us, String numberName, String imageName) {

        parent_of_loading.setVisibility(View.VISIBLE);

        Log.i("TAG", "uploadMultipartSingle: "+storageList.get(0));

        MultipartUploadRequest multipartUploadRequest = null;
        try {
            multipartUploadRequest = new MultipartUploadRequest(this, EndApi.VEHICLE_OWNER_KYC)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .addHeader("Authorization", PreferenceUtil.getData(BankDetailsActivity.this,"token"))
                    .addParameter(numberName, number_us)
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

                                    activity.panEdittext.setText("");
                                    activity.enterTdsNumber.setText("");
                                    activity.bankNumber.setText("");
                                    activity.bankName.setText("");
                                    activity.bankIfc.setText("");

                                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();

                                    finish();


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

            multipartUploadRequest.addFileToUpload(storageList.get(0), imageName, listing_logo, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        multipartUploadRequest.startUpload();

    }

    private void askPermissions() {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        String rationale = "Please provide Storage And Camera permission so that you can Upload R.c Image";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {

                openStorage();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
            }
        });
    }

    private void openStorage() {

        Matisse.from(BankDetailsActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(1)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.truck.transfly.provider"))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen._100ssp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener((uriList, pathList) -> {
                })
                .showSingleMediaType(true)
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(isChecked -> {
                })
                .forResult(REQUEST_CODE_CHOOSE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE_CHOOSE && data != null) {

                storageList.clear();
                storageList.addAll(Matisse.obtainPathResult(data));

                Glide.with(BankDetailsActivity.this).load(storageList.get(0)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.chooseImageView);

                activity.chooseTextView.setText("1 Image Selected");

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}