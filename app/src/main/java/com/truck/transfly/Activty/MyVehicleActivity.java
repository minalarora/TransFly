package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.truck.transfly.Adapter.MyVehicleAdapter;
import com.truck.transfly.Model.ResponseVehicle;
import com.truck.transfly.R;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyVehicleActivity extends AppCompatActivity {

    private List<ResponseVehicle> responseVehicleList=new ArrayList<>();
    private MyVehicleAdapter myVehicleAdapter;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        myVehicleAdapter=new MyVehicleAdapter(MyVehicleActivity.this,responseVehicleList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyVehicleActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myVehicleAdapter);

        getAllVehicles();

    }

    private void getAllVehicles() {

        parent_of_loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, EndApi.MY_VEHICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parent_of_loading.setVisibility(View.GONE);

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    if(jsonArray.length()>0){

                        for (int i = 0; i < jsonArray.length(); i++) {

                            ResponseVehicle responseVehicle=new ResponseVehicle();

                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            responseVehicle.setNumber(jsonObject.getString("number"));
                            responseVehicle.setRc(jsonObject.getString("rc"));
                            responseVehicle.setVehiclename(jsonObject.getString("vehiclename"));
                            responseVehicle.setStatus(jsonObject.getInt("status"));
                            responseVehicle.setActive(jsonObject.getBoolean("active"));
                            responseVehicle.setDate(jsonObject.getString("date"));

                            responseVehicleList.add(responseVehicle);
                        }

                        myVehicleAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(MyVehicleActivity.this, "No Internet Connection ! Try Again", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                map.put("AUTHORIZATION", PreferenceUtil.getData(MyVehicleActivity.this,"token"));

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();

                return map;
            }
        };

        Volley.newRequestQueue(MyVehicleActivity.this).add(stringRequest);

    }
}