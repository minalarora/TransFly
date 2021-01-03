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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityTransporterKycBinding;
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

public class TransporterKycActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHOOSE = 99;
    private TextView mTextView;

    private ActivityTransporterKycBinding activity;
    private ArrayList<String> storageList=new ArrayList<>();
    private FrameLayout parent_of_loading;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> pendingList  =  new ArrayList<>();
    private RelativeLayout no_internet_connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_transporter_kyc);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pendingList.clear();
                no_internet_connection.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                getPendingList(PreferenceUtil.getData(TransporterKycActivity.this,"token"));

            }
        });

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

                int position = activity.registerCategory.getSelectedItemPosition();

                if(activity.registerCategory.getSelectedItem().toString().equals("aadhaar")){

                    callValidationAndMultipart(activity.enterTdsNumber.getText().toString(),"aadhaar","aadhaarimage","Enter Aadhar Number");

                } else if(activity.registerCategory.getSelectedItem().toString().equals("pan")){

                    callValidationAndMultipart(activity.enterTdsNumber.getText().toString(),"pan","panimage","Enter Pan Number");

                } else if(activity.registerCategory.getSelectedItem().toString().equals("gst")) {

                    callValidationAndMultipart(activity.enterTdsNumber.getText().toString(),"gst","gstimage","Enter Gst Number");

                }else if(activity.registerCategory.getSelectedItem().toString().equals("sta")) {

                    callValidationAndMultipart(activity.enterTdsNumber.getText().toString(),"sta","staimage","Enter Sta Number");

                }else if(activity.registerCategory.getSelectedItem().toString().equals("mininglicense")) {

                    callValidationAndMultipart(activity.enterTdsNumber.getText().toString(),"mininglicense","mininglicenseimage","Enter Mining License Number");

                }

            }
        });

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, pendingList);

        activity.registerCategory.setAdapter(adapter);

        getPendingList(PreferenceUtil.getData(TransporterKycActivity.this,"token"));

        activity.registerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(activity.registerCategory.getSelectedItem().toString().equals("aadhaar")){

                    activity.tdsEditLayout.setHint("Enter Aadhar Number");

                } else if(activity.registerCategory.getSelectedItem().toString().equals("pan")){

                    activity.tdsEditLayout.setHint("Enter Pan Number");

                } else if(activity.registerCategory.getSelectedItem().toString().equals("gst")) {

                    activity.tdsEditLayout.setHint("Enter Gst Number");

                }else if(activity.registerCategory.getSelectedItem().toString().equals("sta")) {

                    activity.tdsEditLayout.setHint("Enter STA Number");

                }else if(activity.registerCategory.getSelectedItem().toString().equals("mininglicense")) {

                    activity.tdsEditLayout.setHint("Enter Mining License Number");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void callValidationAndMultipart(String number_us, String numberName, String imageName,String validation) {

        if(!TextUtils.isEmpty(activity.enterTdsNumber.getText().toString())){

            uploadMultipartSingle(number_us,numberName,imageName);

        } else {

            Toast.makeText(TransporterKycActivity.this, validation, Toast.LENGTH_SHORT).show();

        }

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

                        Toast.makeText(TransporterKycActivity.this, "No Kyc Need, Everything is clear", Toast.LENGTH_SHORT).show();

                        finish();

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

    private void uploadMultipartSingle(String number_us, String numberName, String imageName) {

        parent_of_loading.setVisibility(View.VISIBLE);

        MultipartUploadRequest multipartUploadRequest = null;
        try {
            multipartUploadRequest = new MultipartUploadRequest(this, EndApi.TRANSPORTER_KYC)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .addHeader("Authorization", PreferenceUtil.getData(TransporterKycActivity.this,"token"))
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

        Matisse.from(TransporterKycActivity.this)
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

                Glide.with(TransporterKycActivity.this).load(storageList.get(0)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(activity.chooseImageView);

                activity.chooseTextView.setText("1 Image Selected");

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}