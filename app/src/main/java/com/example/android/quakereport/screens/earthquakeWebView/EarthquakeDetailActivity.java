package com.example.android.quakereport.screens.earthquakeWebView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.quakereport.R;

public class EarthquakeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_detail);
        WebView webView = findViewById(R.id.earthquakeDetailsWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        String earthquakeURL;
        Intent intent = getIntent();
        earthquakeURL = intent.getStringExtra("EarthquakeURL");

        webView.loadUrl(earthquakeURL);
    }
}
