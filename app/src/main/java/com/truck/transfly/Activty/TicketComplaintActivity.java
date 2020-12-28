package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.truck.transfly.R;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

public class TicketComplaintActivity extends AppCompatActivity {

    private EditText ticket_complaint;
    private Spinner ticket_spinner;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_complaint);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        ticket_spinner =findViewById(R.id.ticket_spinner);
        ticket_complaint=findViewById(R.id.ticket_complaint);
        RelativeLayout submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(ticket_complaint.getText().toString()) && ticket_complaint.getText().toString().length()>=10){

                    setDataOnServer();

                } else {

                    Toast.makeText(TicketComplaintActivity.this, "Ticket Greater then 10 characters", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void setDataOnServer() {

        parent_of_loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, EndApi.TICKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(TicketComplaintActivity.this, "Ticket Add Successful", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(TicketComplaintActivity.this, "No Internet Connection!Try Again", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                map.put("AUTHORIZATION", PreferenceUtil.getData(TicketComplaintActivity.this,"token"));

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                map.put("category",ticket_spinner.getSelectedItem().toString());
                map.put("message",ticket_complaint.getText().toString());

                return map;
            }
        };

        Volley.newRequestQueue(TicketComplaintActivity.this).add(stringRequest);

    }
}