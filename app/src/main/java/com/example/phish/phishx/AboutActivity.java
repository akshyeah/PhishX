package com.example.phish.phishx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("PhishX | About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
