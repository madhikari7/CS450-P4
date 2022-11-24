package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {

    private String cid;
    private Spinner spinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        cid = getIntent().getStringExtra("cidExtra");

        spinner = findViewById(R.id.prize_spinner);
        loadPrizeIdsIntoSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadPrizeIdsIntoSpinner(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity5.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid="+cid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> myArrayList = new ArrayList<>();
                String[] result = s.trim().split("#");
                for(int i = 0; i < result.length; i++){
                    myArrayList.add(result[i]);
                }

                ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,myArrayList);
                newArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(newArrayAdapter);
            }
        }, null);
        requestQueue.add(request);
    }
}