package com.example.phish.phishx;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password;
    private Button btn_regist;
    private ProgressBar loading;
    private TextView link_login;
    private static String URL_REGIST="http://192.168.43.209:10080/htdocs/android_register_login/register.php";
    //Firebase object declared
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("PhishX | Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Creating FireBase Instance onCreate
        firebaseAuth=FirebaseAuth.getInstance();

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);
        link_login = findViewById(R.id.link_login);


        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = name.getText().toString().trim();
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                String mC_password = c_password.getText().toString().trim();

                if(!(mName.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty() || mC_password.isEmpty() )) {
                    Regist();
                }
                else{
                    name.setError("Please Enter your Name");
                    email.setError("Please Enter your Email-ID");
                    password.setError("Please Enter your Password");
                    c_password.setError("Please Confirm your Password");
                }
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this,"Registered Succesfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Could not Register",Toast.LENGTH_SHORT).show();
                }
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_regist.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( this);
        requestQueue.add(stringRequest);
    }
}
