package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.ReferAdapter;
import com.truck.transfly.Model.ReferModel;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class ReferActivity extends AppCompatActivity {

    private List<ReferModel> referModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        for (int i = 0; i < 10; i++) {

            ReferModel referModel=new ReferModel();

            if(i%2==0)
                referModel.setTextImages(true);
            else if(i%3==0)
                referModel.setText(true);
            else
                referModel.setImages(true);

            referModelList.add(referModel);

        }

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ReferActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ReferAdapter referAdapter=new ReferAdapter(ReferActivity.this,referModelList);
        recyclerView.setAdapter(referAdapter);

    }
}