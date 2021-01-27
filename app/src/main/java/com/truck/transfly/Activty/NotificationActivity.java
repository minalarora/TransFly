package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truck.transfly.Adapter.NotificationAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initNotifications();

    }

    private void initNotifications() {

        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager gridLayoutManage = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, stringList);
        recyclerView.setLayoutManager(gridLayoutManage);
        recyclerView.setAdapter(notificationAdapter);


    }

}