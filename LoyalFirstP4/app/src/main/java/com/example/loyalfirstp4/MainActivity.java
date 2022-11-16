package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userEdt = findViewById(R.id.userNameEdit);
        EditText passwordEdt = findViewById(R.id.passwordEdt);
        Button loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://10.0.2.2:8080/loyaltyfirst/login?user="+userEdt.getText().toString()+"&pass="+passwordEdt.getText().toString();
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        s = s.trim();
                        if (!s.equals("No")) {

                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            String[] idString = s.split(":");
                            intent.putExtra("cidExtra",idString[1]);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, null);
                requestQueue.add(request);
            }
        });
    }
}