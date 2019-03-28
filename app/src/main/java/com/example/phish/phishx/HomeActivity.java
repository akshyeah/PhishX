package com.example.phish.phishx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private TextView email, name, link_fake;
    private Button btn_logout;

    private SessionManager sessionManager;
  //  private   FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // firebaseAuth=FirebaseAuth.getInstance();
       // FirebaseUser user=firebaseAuth.getCurrentUser();

        sessionManager = new SessionManager(this);
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
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.loggout();
            }
        });
    }
}
