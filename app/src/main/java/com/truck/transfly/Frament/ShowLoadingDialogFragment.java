package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.truck.transfly.R;

import java.util.ArrayList;

public class ShowLoadingDialogFragment extends DialogFragment {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> loadingList=new ArrayList<>();
    private FragmentActivity fragmentActivity;
    private onClickListener onClickListener;

    public ShowLoadingDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;

    }

    public interface onClickListener{

        void onClick(String s);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle arguments = getArguments();

        if(arguments!=null){

            loadingList = arguments.getStringArrayList("loadingList");

        }

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_dialog);
        }

        View inflate = inflater.inflate(R.layout.fragment_show_loading_dialog, container, false);

        ListView listViewLoading =inflate.findViewById(R.id.list_item_loading);
        adapter = new ArrayAdapter<String>(fragmentActivity,
                android.R.layout.simple_list_item_1, android.R.id.text1, loadingList);

        listViewLoading.setAdapter(adapter);

        listViewLoading.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 onClickListener.onClick(loadingList.get(position));

                 dismiss();

            }
        });

        return inflate;
    }
}