package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.SearchAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class SearchBarActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        SearchAdapter searchAdapter=new SearchAdapter(SearchBarActivity.this,stringList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SearchBarActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter);

    }
}