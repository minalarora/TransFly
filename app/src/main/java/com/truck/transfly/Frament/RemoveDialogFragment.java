package com.truck.transfly.Frament;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.truck.transfly.R;

public class RemoveDialogFragment extends DialogFragment {


    private onClickListener onClickListener;

    public RemoveDialogFragment() {
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

        View inflate = inflater.inflate(R.layout.fragment_remove_dialog, container, false);

        RelativeLayout cancel =inflate.findViewById(R.id.cancel);
        RelativeLayout remove =inflate.findViewById(R.id.remove);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
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