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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.truck.transfly.Model.RequestTicket;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketComplaintActivity extends AppCompatActivity {

    private EditText ticket_complaint;
    private Spinner ticket_spinner;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private FrameLayout parent_of_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_complaint);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

        ticket_spinner =findViewById(R.id.ticket_spinner);
        ticket_complaint=findViewById(R.id.ticket_complaint);
        RelativeLayout submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(ticket_complaint.getText().toString()) && ticket_complaint.getText().toString().length()>=10){

                    RequestTicket requestTicket=new RequestTicket();
                    requestTicket.setCategory(ticket_spinner.getSelectedItem().toString());
                    requestTicket.setMessage(ticket_complaint.getText().toString());

                    createBooking(PreferenceUtil.getData(TicketComplaintActivity.this,"token"),requestTicket);

                } else {

                    Toast.makeText(TicketComplaintActivity.this, "Ticket Greater then 10 characters", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void createBooking(String token, RequestTicket ticket)
    {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.createTicket(token, ticket).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    Toast.makeText(TicketComplaintActivity.this, "Ticket Update Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Toast.makeText(TicketComplaintActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(TicketComplaintActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }
}