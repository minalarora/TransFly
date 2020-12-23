package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.SmallIconsAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class SelectYourVehicleActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_vehicle);

        initSmallIconAdapter();

    }

    private void initSmallIconAdapter() {

        for (int i = 0; i < 9; i++) {

            stringList.add("s");

        }

        RecyclerView recyclerView=findViewById(R.id.show_small_icons);
        GridLayoutManager linearLayoutManager=new GridLayoutManager(SelectYourVehicleActivity.this,1, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        SmallIconsAdapter smallIconsAdapter=new SmallIconsAdapter(SelectYourVehicleActivity.this,stringList);
        recyclerView.setAdapter(smallIconsAdapter);

    }
}