package com.example.phish.phishx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView phishx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        phishx = findViewById(R.id.phishx);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        phishx.startAnimation(animation);

        Thread thread = new Thread()
        {
            public void run()
            {
                try{
                    sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                finally {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                }
            }
        };
        thread.start();
    }
}
