package com.truck.transfly.Activty;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.truck.transfly.Model.ResponseInvoice;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityShowInvoiceBinding;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;

public class ShowInvoiceActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 99;
    private boolean boolean_save;
    private boolean boolean_permission;
    private LinearLayout linearLayout;
    private ActivityShowInvoiceBinding activity;
    private boolean shareBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this, R.layout.activity_show_invoice);

        linearLayout = findViewById(R.id.ll_linear);

        getIntent();

        Intent intent = getIntent();
        shareBill = intent.getBooleanExtra("shareBill", false);
        boolean vehicle_owner = intent.getBooleanExtra("vehicle_owner", false);
        ResponseInvoice responseInvoice = intent.getParcelableExtra("responseInvoice");

        if(PreferenceUtil.getData(ShowInvoiceActivity.this,"token").split(":")[0].equals("vehicleowner")){

            activity.challanTransporterParent.setVisibility(View.GONE);
            activity.transNameParent.setVisibility(View.GONE);

        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        activity.toFromDest.setText(responseInvoice.getMinename() +" - "+responseInvoice.getLoading());
        activity.dateCreated.setText(responseInvoice.getDate());
        activity.ownerName.setText(responseInvoice.getVehicleOwnerName());
        activity.ownerMobile.setText(responseInvoice.getVehicleownermobile());
        activity.vehicleNumber.setText(responseInvoice.getVehiclenumber());
        activity.tonnege.setText(String.valueOf(responseInvoice.getTonnage()));
        activity.rate.setText(String.valueOf(responseInvoice.getRate()));
        activity.amount.setText(String.valueOf(responseInvoice.getAmount()));
        activity.hsd.setText(String.valueOf(responseInvoice.getHsd()));
        activity.cash.setText(String.valueOf(responseInvoice.getCash()));
        activity.tds.setText(String.valueOf(responseInvoice.getTds()));
        activity.officeCharge.setText(String.valueOf(responseInvoice.getOfficecharge()));
        activity.shortage.setText(String.valueOf(responseInvoice.getShortage()));
        activity.balanceAmount.setText(String.valueOf(responseInvoice.getBalanceamount()));
        activity.challanTransporter.setText(responseInvoice.getChallanToTransporter());
        activity.balanceAmountCleared.setText(responseInvoice.getBalanceAmountCleared());

        activity.modeOfPayment.setText(responseInvoice.getModeofpayment());
        activity.transName.setText(responseInvoice.getTransportername());
        activity.status.setText(responseInvoice.getStatus());

        if(responseInvoice.getStatus().toLowerCase().equals("pending")){

             activity.iconOfGod.setImageResource(R.drawable.pending_icon);

        } else {

            activity.iconOfGod.setImageResource(R.drawable.completed_icon);

        }

        findViewById(R.id.share_screenshots).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartShareImage();

            }
        });

       new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
           @Override
           public void run() {

               if(shareBill){

                   StartShareImage();

               }

           }
       },300);

    }

    private void StartShareImage() {

        if (permissionIsGranted()) {
            Bitmap bitmap1 = loadBitmapFromView(linearLayout, linearLayout.getWidth(), linearLayout.getHeight());
            saveBitmap(bitmap1);
        } else {

            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE};
            String rationale = "Please provide Storage permission so that you can Share the Bills";
            Permissions.Options options = new Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning");

            Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
                @Override
                public void onGranted() {

                    Bitmap bitmap1 = loadBitmapFromView(linearLayout, linearLayout.getWidth(), linearLayout.getHeight());
                    saveBitmap(bitmap1);

                }

                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                    StartShareImage();
                }
            });

        }

    }

    public Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);

        return b;
    }

    public void saveBitmap(Bitmap bitmap) {

        File imagePath = new File(getExternalFilesDir(null) + "/" + File.separator +"transFly.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(), imagePath.getAbsolutePath() + "", Toast.LENGTH_SHORT).show();

            shareFile(imagePath);

            Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareFile(File file) {

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);

        Uri imageUri = FileProvider.getUriForFile(
                ShowInvoiceActivity.this,
                "com.truck.transfly.provider", //(use your app signature + ".provider" )
                file);

        intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
        intentShareFile.putExtra(Intent.EXTRA_STREAM,
                imageUri);
        intentShareFile.putExtra(Intent.EXTRA_TEXT,"");

        //if you need
        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject);
        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");

        startActivity(Intent.createChooser(intentShareFile, "Share File"));

    }

    public boolean permissionIsGranted() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(ShowInvoiceActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(ShowInvoiceActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(ShowInvoiceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(ShowInvoiceActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {

            StartShareImage();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                StartShareImage();


            } else {

                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }
}
