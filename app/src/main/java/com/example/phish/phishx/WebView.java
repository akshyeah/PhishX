package com.example.phish.phishx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

public class WebView extends AppCompatActivity implements View.OnClickListener {

    /**
     * A default URL to open if the URL is empty.
     */
    private static final String DEFAULT_URL = "http://www.google.com";
    private static final String MODE_HTTP = "http://";
    private static final String MODE_HTTPS = "https://";
    /**
     * The URL for performing a google search
     */
    private static final String GOOGLE_SEARCH = "https://www.google.com/search?q=";

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
        search_btn.setOnClickListener(this);

        superProgressBar = findViewById(R.id.myProgresBar);
        superProgressBar.setMax(100);

        initWebView();
        openWebsite(getURLPassedToActivity());

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == search_btn) {
            if (TextUtils.isEmpty(search_url.getText().toString()))
                Toast.makeText(this, "Please Enter URL or Text to Search", Toast.LENGTH_LONG).show();
            else
                openWebsite(search_url.getText().toString());
        }
    }

    /**
     * A method to handle initialisation of the WebView and its settings.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = findViewById(R.id.webview);
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
                if (getSupportActionBar() == null)
                    return;
                getSupportActionBar().setTitle(title);
            }
        });
    }

    /**
     * This method handles the logic of fetching the URL passed to this activity.
     * This is required to be handled in order to get the URL that the user clicked and opened the app.
     * @return the URL passed or null
     */
    @Nullable
    private String getURLPassedToActivity() {

        Intent intent = getIntent();

        Uri data = intent.getData();
        URL url = null;

        try {
            url = new URL(data.getScheme(),
                    data.getHost(),
                    data.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* if (getIntent() == null || getIntent().getData() == null)
            return null;

        return getIntent().getData().toString();*/
       if(url==null)
           return null;
       else
           return url.toString();
    }

    /**
     * This method handles the logic of loading the website.
     * If the url is a string then It loads the google search and If it is a valid URL then loads it.
     * @param url a URL or the search string.
     */
    private void openWebsite(@Nullable final String url) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));

        startActivity(intent);

        Log.d("WebView", "Opening URL: " + url);
        if (TextUtils.isEmpty(url)) {
            webView.loadUrl(DEFAULT_URL);
        } else {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                if (url.startsWith(MODE_HTTP) || url.startsWith(MODE_HTTPS))
                    webView.loadUrl(url);
                else
                    webView.loadUrl(MODE_HTTP + url);
            } else
                webView.loadUrl(GOOGLE_SEARCH + url.trim());
        }
    }
}
