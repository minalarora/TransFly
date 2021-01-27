package com.truck.transfly.Activty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.FieldStafAdapter;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseFirebase;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FieldStafActivity extends AppCompatActivity {

    private RecyclerView fieldStafRecylcer;
    private List<String> stringList = new ArrayList<>();
    private ImageView viewById;
    private DrawerLayout drawerLayout;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private ArrayList<ResponseBooking> responseBookingList = new ArrayList<>();
    private FrameLayout parent_of_loading;
    private FieldStafAdapter fieldStafAdapter;
    private TextView noDataFound;
    private RelativeLayout no_internet_connection;
    private String token;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        View headerLayout = navigationView.getHeaderView(0);
        TextView customerName = headerLayout.findViewById(R.id.customer_name);
        TextView number = headerLayout.findViewById(R.id.number);


        image= headerLayout.findViewById(R.id.profile_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FieldStafActivity.this, ProfileActivity.class));

                Toast.makeText(FieldStafActivity.this, "You can change your profile in My Profile section", Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.search_booking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        headerLayout.findViewById(R.id.appSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);

            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        ResponseFirebase responseFirebase = new ResponseFirebase();
                        responseFirebase.setFirebase(token);

                        updateFirebase(PreferenceUtil.getData(FieldStafActivity.this, "token"), responseFirebase);

                    }
                });

        ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();

        Menu menu = navigationView.getMenu();
        MenuItem kyc_drawer = menu.findItem(R.id.kyc_drawer);
        MenuItem bookingHistory = menu.findItem(R.id.booking_history);

        bookingHistory.setVisible(true);

        customerName.setText(responseFieldStaff.getName());
        number.setText(responseFieldStaff.getMobile());

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseBookingList.clear();
                no_internet_connection.setVisibility(View.GONE);
                fieldStafAdapter.notifyDataSetChanged();
                getAllBookingFieldStaff(PreferenceUtil.getData(FieldStafActivity.this, "token"));

            }
        });

        navigationViewListener(navigationView);
        noDataFound = findViewById(R.id.no_data_found);
        noDataFound.setVisibility(View.GONE);

        fieldStafRecylcer = findViewById(R.id.fieldStafRecylcer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FieldStafActivity.this, LinearLayoutManager.VERTICAL, false);
        fieldStafRecylcer.setLayoutManager(linearLayoutManager);
        fieldStafAdapter = new FieldStafAdapter(this, responseBookingList);
        fieldStafRecylcer.setAdapter(fieldStafAdapter);

        this.viewById = findViewById(R.id.drawer_icon);

        drawerLayout = findViewById(R.id.drawer_layout);

        this.viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

                    drawerLayout.closeDrawer(GravityCompat.START);

                } else {

                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                responseBookingList.clear();
                getAllBookingFieldStaff(PreferenceUtil.getData(FieldStafActivity.this, "token"));
                noDataFound.setVisibility(View.GONE);

            }
        });


    }
    
    private void updateFirebase(String token, ResponseFirebase firebase) {
        api.updateFirebase(token, firebase).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ResponseFieldStaff responseFieldStaff = ((TransflyApplication) getApplication()).getResponseFieldStaff();
        Glide.with(FieldStafActivity.this).load(responseFieldStaff.getProfile()).placeholder(R.drawable.dummy_user).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(image);

        getAllBookingFieldStaff(PreferenceUtil.getData(FieldStafActivity.this, "token"));

    }

    private void navigationViewListener(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.profile_drawer:

                        Intent intent = new Intent(FieldStafActivity.this, ProfileActivity.class);
                        intent.putExtra("stringText", "fieldStaff");
                        startActivity(intent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(FieldStafActivity.this, EmergencyContactActivity.class));

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent = new Intent(FieldStafActivity.this, AreaFieldStafActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.bank_details:

                        Intent bankDetails = new Intent(FieldStafActivity.this, BankDetailsActivity.class);
                        startActivity(bankDetails);

                        break;

                    case R.id.ticket_drawer:

                        Intent ticket_complain = new Intent(FieldStafActivity.this, TicketComplaintActivity.class);
                        startActivity(ticket_complain);

                        break;

                    case R.id.feedback_drawer:

                        Intent feedback_intent = new Intent(FieldStafActivity.this, FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.booking_history:

                        Intent current_invoices = new Intent(FieldStafActivity.this, FieldStaffInvoicesActivity.class);
                        startActivity(current_invoices);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent = new Intent(FieldStafActivity.this, ReferActivity.class);
                        refer_intent.putExtra("keyword", "refer");
                        startActivity(refer_intent);

                        break;

                    case R.id.reward_program:

                        Intent rewardIntent = new Intent(FieldStafActivity.this, ReferActivity.class);
                        rewardIntent.putExtra("keyword", "reward");
                        startActivity(rewardIntent);

                        break;

                    case R.id.rate_etl:

                        startActivity(new Intent(FieldStafActivity.this, SearchBarActivity.class));

                        break;

                    case R.id.contact_us:

                        String phone = "7847072064";
                        Intent phoneCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(phoneCall);

                        break;

                    case R.id.logout:

                        ResponseFirebase responseFirebase = new ResponseFirebase();
                        responseFirebase.setFirebase(token);

                        deleteFirebase(PreferenceUtil.getData(FieldStafActivity.this, "token"),responseFirebase);

                        PreferenceUtil.putData(FieldStafActivity.this, "token", "");

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });


    }

    private void deleteFirebase(String token, ResponseFirebase firebase) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.deleteFirebase(token, firebase).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Intent logoutIntent = new Intent(FieldStafActivity.this, LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(FieldStafActivity.this, "No Internet Connection! Try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAllBookingFieldStaff(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);

        responseBookingList.clear();
        fieldStafAdapter.notifyDataSetChanged();

        no_internet_connection.setVisibility(View.GONE);

        api.getBookingFieldStaff(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Type collectionType = new TypeToken<ArrayList<ResponseBooking>>() {
                    }.getType();
                    try {
                        responseBookingList.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                    } catch (IOException e) {

                    }
                    if (responseBookingList.isEmpty()) {

                        noDataFound.setVisibility(View.VISIBLE);
                        Log.d("minal", "no vehicle");
                    } else {
                        //['pan','aadhaar','bank']

                        noDataFound.setVisibility(View.GONE);
                        fieldStafAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                no_internet_connection.setVisibility(View.VISIBLE);

                Toast.makeText(FieldStafActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}