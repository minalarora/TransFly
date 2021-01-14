package com.truck.transfly.Activty;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.truck.transfly.Model.RequestTicket;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketComplaintActivity extends AppCompatActivity {

    private EditText ticket_complaint;
    private RadioGroup ticket_spinner;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private FrameLayout parent_of_loading;
    private String StringRadio;

    private EditText driver_name,driver_phone,location_name,vehicle_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_complaint);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        ticket_spinner = findViewById(R.id.select_complaint);
        ticket_complaint = findViewById(R.id.ticket_complaint);
        driver_name = findViewById(R.id.driver_name);
        location_name = findViewById(R.id.location_name);
        driver_phone = findViewById(R.id.driver_phone);
        vehicle_number = findViewById(R.id.vehicle_number);
        RelativeLayout submit = findViewById(R.id.submit);

        StringRadio = "Vehicle Break Down";

        ticket_spinner.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.break_down:

                        StringRadio = "Vehicle Break Down";

                        break;

                    case R.id.accident:

                        StringRadio = "Accidental";

                        break;

                    case R.id.loading_problem:

                        StringRadio = "Loading Problem";

                        break;

                    case R.id.other_support:

                        StringRadio = "Other Support";

                        break;

                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(ticket_complaint.getText().toString()) && ticket_complaint.getText().toString().length() >= 10) {

                    RequestTicket requestTicket = new RequestTicket();
                    requestTicket.setCategory(StringRadio);
                    requestTicket.setMessage("Driver name : "+driver_name.getText().toString()+"\n"+"Driver phone Number : "+driver_phone.getText().toString()+"\n"+" vehicle Number : "+vehicle_number.getText().toString()+"\nLocation : "+location_name.getText().toString()+"\nTicket : "+ticket_complaint.getText().toString());

                    createBooking(PreferenceUtil.getData(TicketComplaintActivity.this, "token"), requestTicket);

                } else {

                    Toast.makeText(TicketComplaintActivity.this, "Ticket Greater then 10 characters", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void createBooking(String token, RequestTicket ticket) {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.createTicket(token, ticket).enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Toast.makeText(TicketComplaintActivity.this, "Ticket Update Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TicketComplaintActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();


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