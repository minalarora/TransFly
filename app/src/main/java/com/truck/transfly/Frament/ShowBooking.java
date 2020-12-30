package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truck.transfly.Activty.FieldStafActivity;
import com.truck.transfly.Adapter.FieldStafAdapter;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class ShowBooking extends Fragment {

    private List<String> stringList=new ArrayList<>();
    private View inflate;
    private ArrayList<ResponseBooking> responseBookingList=new ArrayList<>();
    private RecyclerView fieldStafRecylcer;
    private FragmentActivity fragmentActivity;

    public ShowBooking() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        inflate = inflater.inflate(R.layout.fragment_show_booking, container, false);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        fieldStafRecylcer =inflate.findViewById(R.id.fieldStafRecylcer);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        fieldStafRecylcer.setLayoutManager(linearLayoutManager);
        FieldStafAdapter fieldStafAdapter=new FieldStafAdapter(fragmentActivity,responseBookingList);
        fieldStafRecylcer.setAdapter(fieldStafAdapter);

        return inflate;
    }
}