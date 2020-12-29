package com.truck.transfly.Activty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.truck.transfly.Adapter.FieldStafAdapter;
import com.truck.transfly.Adapter.TransporterAdapter;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class TransporterActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();
    private RecyclerView areaManagerRecycler;
    private ImageView viewById;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        areaManagerRecycler =findViewById(R.id.areaManagerRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TransporterActivity.this,LinearLayoutManager.VERTICAL,false);
        areaManagerRecycler.setLayoutManager(linearLayoutManager);
        TransporterAdapter fieldStafAdapter=new TransporterAdapter(this,stringList);
        areaManagerRecycler.setAdapter(fieldStafAdapter);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        navigationViewListener(navigationView);

        navigationView.setItemIconTintList(null);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        viewById = findViewById(R.id.drawer_icon);

        drawerLayout = findViewById(R.id.drawer_layout);

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

    }

    private void navigationViewListener(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile_drawer:

                        Intent intent=new Intent(TransporterActivity.this,ProfileActivity.class);
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

                    case R.id.feedback_drawer:

                        Intent feedback_intent=new Intent(TransporterActivity.this,FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent=new Intent(TransporterActivity.this,ReferActivity.class);
                        startActivity(refer_intent);

                        break;


                    case R.id.logout:

                        Intent logout_intent=new Intent(TransporterActivity.this,LoginActivity.class);
                        startActivity(logout_intent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(TransporterActivity.this,EmergencyContactActivity.class));

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });


    }

}