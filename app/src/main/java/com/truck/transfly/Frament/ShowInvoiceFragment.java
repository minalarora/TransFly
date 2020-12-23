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

import com.truck.transfly.Activty.TransporterActivity;
import com.truck.transfly.Adapter.TransporterAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class ShowInvoiceFragment extends Fragment {

    private List<String> stringList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private RecyclerView areaManagerRecycler;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;
        
    }

    public ShowInvoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_show_invoice, container, false);

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        areaManagerRecycler =inflate.findViewById(R.id.show_invoice_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(fragmentActivity,LinearLayoutManager.VERTICAL,false);
        areaManagerRecycler.setLayoutManager(linearLayoutManager);
        TransporterAdapter fieldStafAdapter=new TransporterAdapter(fragmentActivity,stringList);
        areaManagerRecycler.setAdapter(fieldStafAdapter);

        return inflate;
        
    }
}