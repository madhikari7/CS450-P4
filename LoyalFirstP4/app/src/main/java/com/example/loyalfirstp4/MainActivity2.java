package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity {

    private String cid;

    private TextView userNameText;
    private TextView rewardPointText;
    private Button allTransBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        userNameText = findViewById(R.id.userName_text);
        rewardPointText = findViewById(R.id.reward_points);
        allTransBtn = findViewById(R.id.all_trans_btn);

        Intent intent = getIntent();
        cid = intent.getStringExtra("cidExtra");
        allTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAllTransPage();
            }
        });
        getUserInfo();

    }

    private void getUserInfo(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] cusInfo = s.split(",");
                String name = cusInfo[1];
                String rewardPoints = cusInfo[0];
                userNameText.setText(name);
                rewardPointText.setText(rewardPoints);
            }
        }, null);
        requestQueue.add(request);
    }

    private void navigateToAllTransPage(){
        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
        intent.putExtra("cidExtra",cid);
        startActivity(intent);
    }
}