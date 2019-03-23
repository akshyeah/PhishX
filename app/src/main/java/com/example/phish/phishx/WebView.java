package com.example.phish.phishx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebView extends AppCompatActivity implements View.OnClickListener{

    private EditText search_url;
    private ImageButton search_btn;
    private ProgressBar superProgressBar;
    private android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        search_url = findViewById(R.id.search_url);
        search_btn = findViewById(R.id.search_btn);
        superProgressBar = findViewById(R.id.myProgresBar);
        webView = (android.webkit.WebView) findViewById(R.id.webview);

        superProgressBar.setMax(100);

        webView.loadUrl("http://www.google.co.in");
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                superProgressBar.setProgress(newProgress);
            }

            @Override
              public void onReceivedTitle(android.webkit.WebView view, String title) {
              super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }
        });

        search_btn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed()
    {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
            {
            finish();
        }

    }

    @Override
    public void onClick(View view)
    {
        if(view == search_btn)
        {
            openWebsite();
        }
    }

    private void openWebsite()
    {
        String url_address = search_url.getText().toString();
        //String google_search = "https://www.google.com/search?q=";
        if(TextUtils.isEmpty(url_address) )
        {
            Toast.makeText(this, "Please Enter URL or Web Address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //String url_without_http = url_address.replaceAll("https://www.","");
            //String url = google_search+url_address;
            webView.loadUrl(url_address);
        }
    }
}
