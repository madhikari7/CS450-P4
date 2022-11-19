package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
        String cid = intent.getStringExtra("cidExtra");
        TextView displayTransInfo = findViewById(R.id.display_trans_tv);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity3.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] allTransInfo = s.split("#");
                StringBuilder transBuffer = new StringBuilder();
                for (String transInfo : allTransInfo){
                    String[] splitInfo = transInfo.split(",");
                    transBuffer.append(splitInfo[0]).append("       ");
                    transBuffer.append(splitInfo[1]).append("       ");
                    transBuffer.append(splitInfo[2]).append("       ");
                    transBuffer.append("$").append(splitInfo[3]);
                    transBuffer.append("\n");
                }
                displayTransInfo.setText(transBuffer.toString());
            }
        }, null);
        requestQueue.add(request);
    }
}