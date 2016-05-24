package com.nu3.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.nu3.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://www.pagosvirtualesavvillas.com.co/personal/pagos/2543");
        // webView.getSettings().setUseWideViewPort(true);
        // webView.setInitialScale(1);

        setTitle(R.string.donate_pse);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
