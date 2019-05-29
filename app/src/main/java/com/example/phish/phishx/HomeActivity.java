package com.example.phish.phishx;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Random;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";
    private TextView email,fake_email, fake_password,name, link_fake;
    private Button btn_generate;

    private SessionManager sessionManager;
    private   FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("PhishX | Home");
        //btn_logout=findViewById(R.id.btn_logout);
        btn_generate = findViewById(R.id.btn_generate);
        fake_email = findViewById(R.id.fake_email);
        fake_password = findViewById(R.id.fake_password);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser fuser=firebaseAuth.getCurrentUser();
        email= findViewById(R.id.email);
       try {
           email.setText(fuser.getEmail());
       }catch(Exception e)
       {
           e.printStackTrace();
       }

        /*sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        link_fake = findViewById(R.id.link_fake);
        btn_logout = findViewById(R.id.btn_logout);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(SessionManager.NAME);
        String mEmail = user.get(SessionManager.EMAIL);

        name.setText(mName);
        email.setText(mEmail);

        link_fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, FakeLoginActivity.class));
            }
        });*/

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fake_email.setText(getRandomEmail(13));
                fake_password.setText(getRandomPassword(13));
            }
        });

    }

    private String getRandomEmail(int length) {
        char[] characters = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<length ; i++)
        {
            char c = characters[random.nextInt(characters.length)];
            stringBuilder.append(c);
        }
        String result = stringBuilder.toString()+"@gmail.com";
        return result;
    }

    @NonNull
    private String getRandomPassword(int length) {
        char[] characters = "QWERTYUIOPASDFGHJKLZXCVBNMmnbvcxzlkjhgfdsapoiuytrewq0123456789!@#$*".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<length ; i++)
        {
            char c = characters[random.nextInt(characters.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.btn_logout :
                        //sessionManager.loggout();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                return true;

            case R.id.btn_about : startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                return true;

            case R.id.btn_browser :
                String url = "https://www.google.com";
                Intent intent = new Intent(this, WebView.class);
                intent.putExtra(EXTRA_MESSAGE, url);
                startActivity(intent);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}
