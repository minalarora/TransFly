package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.truck.transfly.R;

import im.delight.android.webview.AdvancedWebView;

public class WebViewActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;
    private int progress;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        long from_time = intent.getLongExtra("from_time",0);
        long to_time = intent.getLongExtra("to_time",0);
        String mobile = intent.getStringExtra("mobile");
        String keywords = intent.getStringExtra("keywords");

        progressBar=findViewById(R.id.progress);

        mWebView =  findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(false);
        mWebView.setCookiesEnabled(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if(newProgress>0){

                    progressBar.setVisibility(View.VISIBLE);

                }

                progressBar.setProgress(newProgress);

                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(newProgress * 100); //Make the bar disappear after URL is loaded


                // Return the app name after finish loading
                if(newProgress == 100) {
                    setTitle(R.string.app_name);

                    progressBar.setVisibility(View.GONE);

                }

            }
        });

        Log.i("TAG", "https://transfly-ftr2t.ondigitalocean.app/"+keywords+"?mobile="+mobile+"&from="+from_time+"&"+"to="+to_time);

        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.loadUrl("https://transfly-ftr2t.ondigitalocean.app/"+keywords+"?mobile="+mobile+"&from="+from_time+"&"+"to="+to_time);

    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

}