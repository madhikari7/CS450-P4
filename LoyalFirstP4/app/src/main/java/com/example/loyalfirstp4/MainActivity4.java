package com.example.loyalfirstp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class MainActivity4 extends AppCompatActivity {

    private Spinner trefSpinner;

    private TextView displayDateTv;
    private TextView pointDisplayTv;
    private TextView displayOtherInfoTv;
    private String selectedTref;

    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        cid = getIntent().getStringExtra("cidExtra");
        selectedTref = "";

        trefSpinner = findViewById(R.id.spinner);
        displayDateTv = findViewById(R.id.date_display_tv);
        pointDisplayTv = findViewById(R.id.point_display_tv);
        displayOtherInfoTv = findViewById(R.id.display_otherinfo_tv);


        loadTrefIntoSpinner();
        trefSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTref = (String) adapterView.getSelectedItem();
                loadInfoOfSelectedTref();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedTref = (String) adapterView.getItemAtPosition(0);
                loadInfoOfSelectedTref();
            }
        });
    }

    private void loadTrefIntoSpinner(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
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

                ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,myArrayList);
                newArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                trefSpinner.setAdapter(newArrayAdapter);
            }
        }, null);
        requestQueue.add(request);
    }

    private void loadInfoOfSelectedTref(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref="+selectedTref;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String transDate = "";
                String transPoint = "";
                String otherInfo = "";
                String[] result = s.trim().split("#");
                for(int i = 0; i < result.length; i++){
                    String[] trefInfo = result[i].split(",");
                    for (int j = 0; j<trefInfo.length;j++){
                        if (j == 0){
                            transDate = trefInfo[j];
                        }
                        else if (j==1){
                            transPoint = trefInfo[j] + " points ";
                        }
                        else{
                            otherInfo+=trefInfo[j]+" ";
                        }
                    }
                    otherInfo+="\n";
                }

                displayDateTv.setText(transDate);
                pointDisplayTv.setText(transPoint);
                displayOtherInfoTv.setText(otherInfo);
            }
        }, null);
        requestQueue.add(request);
    }
}