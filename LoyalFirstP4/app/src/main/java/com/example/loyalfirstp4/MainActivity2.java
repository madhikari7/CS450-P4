package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    private String cid;

    private TextView userNameText;
    private TextView rewardPointText;
    private Button allTransBtn;
    private Button transDetailBtn;
    private Button redemptionDetailBtn;
    private Button addPointBtn;
    private Button exitBtn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        userNameText = findViewById(R.id.userName_text);
        rewardPointText = findViewById(R.id.reward_points);
        allTransBtn = findViewById(R.id.all_trans_btn);
        transDetailBtn = findViewById(R.id.trans_detail_btn);
        redemptionDetailBtn = findViewById(R.id.redemption_detail_btn);
        addPointBtn = findViewById(R.id.add_percent_btn);
        exitBtn = findViewById(R.id.exit_btn);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        cid = intent.getStringExtra("cidExtra");
        allTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAllTransPage();
            }
        });
        getUserInfo();

        transDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToTransDetailPage();
            }
        });

        redemptionDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRedemptionDetailPage();
            }
        });

        addPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddPointPage();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToLogin();
            }
        });

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
        showImage();
    }

    private void showImage(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        int cidInt = Integer.parseInt(cid);
        if (cidInt>=1 && cidInt <=5){
            String url = "http://10.0.2.2:8080/loyaltyfirst/images/"+""+cid+".jpeg";
            ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }, 0,0,null,null);
            requestQueue.add(request);
        }
    }

    private void navigateToAllTransPage(){
        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
        intent.putExtra("cidExtra",cid);
        startActivity(intent);
    }

    private void navigateToTransDetailPage(){
        Intent intent = new Intent(MainActivity2.this,MainActivity4.class);
        intent.putExtra("cidExtra",cid);
        startActivity(intent);
    }

    private void navigateToRedemptionDetailPage(){
        Intent intent = new Intent(MainActivity2.this,MainActivity5.class);
        intent.putExtra("cidExtra",cid);
        startActivity(intent);
    }

    private void navigateToAddPointPage(){
        Intent intent = new Intent(MainActivity2.this,MainActivity6.class);
        intent.putExtra("cidExtra",cid);
        startActivity(intent);
    }

    private void goBackToLogin(){
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);
    }
}