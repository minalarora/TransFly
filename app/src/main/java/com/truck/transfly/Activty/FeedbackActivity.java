package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.truck.transfly.Model.RequestRating;
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
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FeedbackActivity extends AppCompatActivity {

    private ActivityFeedbackBinding activity;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_feedback);

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        activity.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activity.ratingBar.getRating()==0){

                    Toast.makeText(FeedbackActivity.this, "Select an rating", Toast.LENGTH_SHORT).show();

                } else if(TextUtils.isEmpty(activity.feedbackText.getText().toString())){

                    Toast.makeText(FeedbackActivity.this, "Write A Feedback", Toast.LENGTH_SHORT).show();

                } else {

                    RequestRating requestRating=new RequestRating();
                    requestRating.setMessage(activity.feedbackText.getText().toString());
                    requestRating.setRating((int) activity.ratingBar.getRating());

                    createRating(PreferenceUtil.getData(FeedbackActivity.this,"token"),requestRating);

                }

            }
        });

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

                Toast.makeText(FeedbackActivity.this, "No Internet Connection"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("Authorization", PreferenceUtil.getData(FeedbackActivity.this,"token"));

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

    private void createRating(String token, RequestRating rating)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.createRating(token, rating).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    Toast.makeText(FeedbackActivity.this, "Feedback Send", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(FeedbackActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });
    }

}