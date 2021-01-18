package com.truck.transfly.Frament;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truck.transfly.R;

public class MineChooseFragment extends DialogFragment {

    private onClickListener onClickListener;

    public MineChooseFragment() {
        // Required empty public constructor
    }

    public interface onClickListener{

        void onClick();

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_mine_choose, container, false);

        Bundle arguments = getArguments();

        String minename = arguments.getString("minename");
        String loading=arguments.getString("loading");

        TextView keyword =inflate.findViewById(R.id.keyword);

        keyword.setText(Html.fromHtml("Are you sure you want to confirm the booking from <a href='google.com'>"+minename +"</a> to <a href='google.com'>"+loading+"</a>"));

        inflate.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        inflate.findViewById(R.id.createBooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickListener.onClick();

                dismiss();

            }
        });

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_min);
        }

        return inflate;
    }
}