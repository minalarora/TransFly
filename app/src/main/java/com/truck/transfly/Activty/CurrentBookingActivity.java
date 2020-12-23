package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.CurrentBookingAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentBookingActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_booking);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        RecyclerView current_booking_recycler =findViewById(R.id.current_booking_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        current_booking_recycler.setLayoutManager(linearLayoutManager);
        CurrentBookingAdapter currentBookingAdapter=new CurrentBookingAdapter(this,stringList);
        current_booking_recycler.setAdapter(currentBookingAdapter);

    }
}