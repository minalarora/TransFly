package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.truck.transfly.R;

import java.io.File;

public class Screenshot extends AppCompatActivity {

    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        init();
    }

    private void init() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        String completePath = getExternalFilesDir(null) + "/" + File.separator +"transFly.jpg";
        Glide.with(Screenshot.this).load(completePath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_image);

    }


}