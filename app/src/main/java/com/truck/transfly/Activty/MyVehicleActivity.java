package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.MyVehicleAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class MyVehicleActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        MyVehicleAdapter myVehicleAdapter=new MyVehicleAdapter(MyVehicleActivity.this,stringList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyVehicleActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myVehicleAdapter);

    }
}