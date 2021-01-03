package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.OlxRecyclerAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class OlxPageActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olx_page);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        OlxRecyclerAdapter olxRecyclerAdapter=new OlxRecyclerAdapter(this,stringList);
        GridLayoutManager linearLayoutManager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(olxRecyclerAdapter);
    }
}