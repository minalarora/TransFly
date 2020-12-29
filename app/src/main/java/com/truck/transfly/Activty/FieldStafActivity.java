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
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class FieldStafActivity extends AppCompatActivity {

    private RecyclerView fieldStafRecylcer;
    private List<String> stringList=new ArrayList<>();
    private ImageView viewById;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(map);

        navigationViewListener(navigationView);

        navigationView.setItemIconTintList(null);
        
        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        fieldStafRecylcer =findViewById(R.id.fieldStafRecylcer);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FieldStafActivity.this,LinearLayoutManager.VERTICAL,false);
        fieldStafRecylcer.setLayoutManager(linearLayoutManager);
        FieldStafAdapter fieldStafAdapter=new FieldStafAdapter(this,stringList);
        fieldStafRecylcer.setAdapter(fieldStafAdapter);

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

                        Intent intent=new Intent(FieldStafActivity.this,ProfileActivity.class);
                        startActivity(intent);

                        break;

                    case R.id.emergency_details:

                        startActivity(new Intent(FieldStafActivity.this,EmergencyContactActivity.class));

                        break;

                    case R.id.kyc_drawer:

                        Intent kyc_intent=new Intent(FieldStafActivity.this, AreaFieldStafActivity.class);
                        startActivity(kyc_intent);

                        break;

                    case R.id.bank_details:

                        Intent bankDetails=new Intent(FieldStafActivity.this,BankDetailsActivity.class);
                        startActivity(bankDetails);

                        break;

                    case R.id.ticket_drawer:

                        Intent ticket_complain=new Intent(FieldStafActivity.this,TicketComplaintActivity.class);
                        startActivity(ticket_complain);

                        break;

                    case R.id.feedback_drawer:

                        Intent feedback_intent=new Intent(FieldStafActivity.this,FeedbackActivity.class);
                        startActivity(feedback_intent);

                        break;

                    case R.id.refer_drawer:

                        Intent refer_intent=new Intent(FieldStafActivity.this,ReferActivity.class);
                        startActivity(refer_intent);

                        break;


                    case R.id.logout:

                        Intent logout_intent=new Intent(FieldStafActivity.this,LoginActivity.class);
                        startActivity(logout_intent);

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
        

    }
}