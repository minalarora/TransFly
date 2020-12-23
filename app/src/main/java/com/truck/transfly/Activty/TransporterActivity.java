package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.TransporterAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class TransporterActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();
    private RecyclerView areaManagerRecycler;

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

    }
}