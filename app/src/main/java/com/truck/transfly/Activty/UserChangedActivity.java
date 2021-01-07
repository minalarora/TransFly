package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Adapter.ChangeUserAdapter;
import com.truck.transfly.Model.WhoModel;
import com.truck.transfly.R;
import com.truck.transfly.databinding.ActivityUserChangedBinding;
import com.truck.transfly.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class UserChangedActivity extends AppCompatActivity {

    private ActivityUserChangedBinding activity;
    private List<WhoModel> whoModelList=new ArrayList<>();
    private WhoModel whoModelGlobal;
    private String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = DataBindingUtil.setContentView(this,R.layout.activity_user_changed);

        mobileNo = getIntent().getStringExtra("mobileNo");

        whoModelList.add(MyUtils.returnModel(R.drawable.vehicleowner,"Vehicle Owner","Kyc Needed","PAN - TDS","vehicleowner"));
        whoModelList.add(MyUtils.returnModel(R.drawable.vehicleowner,"Transporter","Kyc Needed","GST - STA - PAN - AADHAAR","transporter"));
        whoModelList.add(MyUtils.returnModel(R.drawable.vehicleowner,"Field Staff","Kyc Needed","PAN - AADHAAR","fieldstaff"));
        whoModelList.add(MyUtils.returnModel(R.drawable.vehicleowner,"Area Manager","Kyc Needed","PAN - AADHAAR","areamanager"));

        activity.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        RecyclerView recyclerViewUserType = activity.recyclerViewUserType;
        ChangeUserAdapter changeUserAdapter=new ChangeUserAdapter(UserChangedActivity.this,whoModelList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(UserChangedActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerViewUserType.setLayoutManager(linearLayoutManager);
        recyclerViewUserType.setAdapter(changeUserAdapter);

        changeUserAdapter.setOnClickListener(new ChangeUserAdapter.onClickListener() {

            @Override
            public void onClick(WhoModel whoModel) {

                whoModelGlobal=whoModel;

            }
        });

        activity.checkedRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(whoModelGlobal!=null){

                    Intent intent=new Intent(UserChangedActivity.this,SignUpActivity.class);
                    intent.putExtra("mobileNo",mobileNo);
                    intent.putExtra("type",whoModelGlobal.getKeyword());
                    startActivity(intent);

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(UserChangedActivity.this,LoginActivity.class);
        startActivity(intent);

        finish();

    }
}