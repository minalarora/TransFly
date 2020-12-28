package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityFeedbackBinding;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private ActivityFeedbackBinding activity;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_feedback);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        if(activity.ratingBar.getRating()>0){

            Toast.makeText(this, "Select an rating", Toast.LENGTH_SHORT).show();

        } else if(TextUtils.isEmpty(activity.feedbackText.getText().toString())){

            Toast.makeText(this, "Write A Feedback", Toast.LENGTH_SHORT).show();

        } else {

            uploadAFeedback();

        }

    }

    private void uploadAFeedback() {

        parent_of_loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, EndApi.RATING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parent_of_loading.setVisibility(View.GONE);

                activity.ratingBar.setRating(0f);
                activity.feedbackText.setText("");

                Toast.makeText(FeedbackActivity.this, "Add rating successful", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(FeedbackActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("AUTHORIZATION", PreferenceUtil.getData(FeedbackActivity.this,"token"));

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("rating", String.valueOf(activity.ratingBar.getRating()));
                map.put("message",activity.feedbackText.getText().toString());

                return map;
            }
        };

        Volley.newRequestQueue(FeedbackActivity.this).add(stringRequest);

    }
}