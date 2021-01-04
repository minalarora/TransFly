package com.truck.transfly.Activty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.TransporterAdapter;
import com.truck.transfly.Frament.ShowInvoiceFragment;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseInvoice;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yalantis.phoenix.PullToRefreshView;

import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransporterActivity extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private RecyclerView areaManagerRecycler;
    private ImageView viewById;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseInvoice> invoicesList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private TransporterAdapter fieldStafAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private TextView no_booking_data;
    private PullToRefreshView pullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }


        areaManagerRecycler =findViewById(R.id.areaManagerRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TransporterActivity.this,LinearLayoutManager.VERTICAL,false);
        areaManagerRecycler.setLayoutManager(linearLayoutManager);
        fieldStafAdapter=new TransporterAdapter(this,invoicesList);
        areaManagerRecycler.setAdapter(fieldStafAdapter);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invoicesList.clear();
                no_internet_connection.setVisibility(View.GONE);
                fieldStafAdapter.notifyDataSetChanged();
                getInvoiceTransporter(PreferenceUtil.getData(TransporterActivity.this,"token"),String.valueOf(new Date().getTime()));

            }
        });

        pullToRefreshView=findViewById(R.id.pullToRefresh);

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                invoicesList.clear();
                getInvoiceTransporter(PreferenceUtil.getData(TransporterActivity.this,"token"),String.valueOf(new Date().getTime()));

                fieldStafAdapter.notifyDataSetChanged();

                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        pullToRefreshView.setRefreshing(false);

                    }
                },2500);

            }
        });

        no_booking_data = findViewById(R.id.no_booking_data);
        no_booking_data.setVisibility(View.GONE);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        View headerLayout = navigationView.getHeaderView(0);
        TextView customerName = headerLayout.findViewById(R.id.customer_name);
        TextView number=headerLayout.findViewById(R.id.number);

        ResponseTransporter responseFieldStaff = ((TransflyApplication) getApplication()).getResponseTransporterOwner();

        customerName.setText(responseFieldStaff.getName());
        number.setText(responseFieldStaff.getMobile());

        navigationViewListener(navigationView);

        navigationView.setItemIconTintList(null);

        viewById = findViewById(R.id.drawer_icon);

        drawerLayout = findViewById(R.id.drawer_layout);

        CardView calenderSelected = findViewById(R.id.calender_selected);

        calenderSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        TransporterActivity.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_1);

                dpd.setAccentColor(ContextCompat.getColor(TransporterActivity.this, R.color.quantum_pink));

                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

                    drawerLayout.closeDrawer(GravityCompat.START);

                } else {

                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

        getInvoiceTransporter(PreferenceUtil.getData(TransporterActivity.this,"token"),String.valueOf(new Date().getTime()));

    }

    private void navigationViewListener(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile_drawer:

                        Intent intent = new Intent(TransporterActivity.this, ProfileActivity.class);
                        intent.putExtra("stringText","transporter");
                        startActivity(intent);

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent=new Intent(TransporterActivity.this, TransporterKycActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.ticket_drawer:

                        Intent ticket_complain=new Intent(TransporterActivity.this,TicketComplaintActivity.class);
                        startActivity(ticket_complain);

                        break;

                    case R.id.bank_details:

                        Intent bankDetails=new Intent(TransporterActivity.this,BankDetailsActivity.class);
                        startActivity(bankDetails);

                        break;

                    case R.id.feedback_drawer:

                        Intent feedback_intent=new Intent(TransporterActivity.this,FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent = new Intent(TransporterActivity.this, ReferActivity.class);
                        refer_intent.putExtra("keyword","refer");
                        startActivity(refer_intent);

                        break;

                    case R.id.reward_program:

                        Intent rewardIntent = new Intent(TransporterActivity.this, ReferActivity.class);
                        rewardIntent.putExtra("keyword","reward");
                        startActivity(rewardIntent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(TransporterActivity.this,EmergencyContactActivity.class));

                        break;

                    case R.id.logout:

                        Intent logoutIntent = new Intent(TransporterActivity.this, LoginActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        finish();

                        PreferenceUtil.putData(TransporterActivity.this,"token","");

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });


    }

    private void getInvoiceTransporter(String token,String timestamp)
    {
        no_internet_connection.setVisibility(View.GONE);
        no_booking_data.setVisibility(View.GONE);
        parent_of_loading.setVisibility(View.VISIBLE);

        api.getInvoiceTransporter(token,timestamp).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                pullToRefreshView.setRefreshing(false);

                if(response.code() == 200)
                {
                    Type collectionType = new TypeToken<ArrayList<ResponseInvoice>>(){}.getType();
                    try {
                        invoicesList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(invoicesList.isEmpty())
                    {

                        fieldStafAdapter.notifyDataSetChanged();
                        Log.d("minal","no vehicle");

                        no_booking_data.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        fieldStafAdapter.notifyDataSetChanged();
                        Log.d("minal", invoicesList.toString());

                    }



                } else {

                    Toast.makeText(TransporterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(TransporterActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DateTime dateTime=new DateTime(year,monthOfYear+1,dayOfMonth,new DateTime().getHourOfDay(),new DateTime().getMinuteOfHour());

        invoicesList.clear();
        fieldStafAdapter.notifyDataSetChanged();
        getInvoiceTransporter(PreferenceUtil.getData(TransporterActivity.this, "token"), String.valueOf(dateTime.getMillis()));
    }
}