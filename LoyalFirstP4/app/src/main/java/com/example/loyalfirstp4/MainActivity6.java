package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {

    private String transId;
    private String cid;
    private String famId;

    private int transPoint;
    private int famPercent;

    private Spinner trefSpinner;

    private TextView transPointTv;
    private TextView famIDTv;
    private TextView famPercentTV;

    private Button addPointBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        transId = "";
        cid = "";
        famId = "";

        transPoint = 0;
        famPercent = 0;

        trefSpinner = findViewById(R.id.trefSpinner);
        transPointTv = findViewById(R.id.trans_point_tv);
        famIDTv = findViewById(R.id.fam_id_tv);
        famPercentTV = findViewById(R.id.fam_percent_tv);
        addPointBtn = findViewById(R.id.add_fam_point_btn);

        Intent intent = getIntent();
        cid = intent.getStringExtra("cidExtra");
        loadTransIdIntoSpinner();
        trefSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                transId = (String) adapterView.getSelectedItem();
                displayInfoRegardingCus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                transId = (String) adapterView.getItemAtPosition(0);
                displayInfoRegardingCus();
            }
        });

        addPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFamAccPoints();
            }
        });


    }

    private void loadTransIdIntoSpinner(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity6.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> myArrayList = new ArrayList<>();
                String[] result = s.trim().split("#");
                for(int i = 0; i < result.length; i++){
                    String[] trefInfo = result[i].split(",");
                    myArrayList.add(trefInfo[0]);
                }

                ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,myArrayList);
                newArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                trefSpinner.setAdapter(newArrayAdapter);
            }
        }, null);
        requestQueue.add(request);
    }

    private void displayInfoRegardingCus(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity6.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid="+cid+"&tref="+transId;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> myArrayList = new ArrayList<>();
                String[] result = s.trim().split("#");
                for (String info : result){
                    String[] infoArr = info.split(",");
                    transPoint = Integer.parseInt(infoArr[0]);
                    transPointTv.setText(transPointTv.getText()+" "+infoArr[0]);
                    famId = infoArr[1];
                    famIDTv.setText(famIDTv.getText()+" "+famId);
                    famPercent = Integer.parseInt(infoArr[2]);
                    famPercentTV.setText(famPercentTV.getText()+" "+famPercent);


                }
            }
        }, null);
        requestQueue.add(request);
    }

    private void updateFamAccPoints(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity6.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid="+famId+"&cid="+cid+"&npoints="+(transPoint * famPercent / 100);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity6.this,s,Toast.LENGTH_LONG).show();
            }
        }, null);
        requestQueue.add(request);
    }
}