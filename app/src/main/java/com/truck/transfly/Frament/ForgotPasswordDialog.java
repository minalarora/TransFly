package com.truck.transfly.Frament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truck.transfly.Activty.OtpValidation;
import com.truck.transfly.R;

public class ForgotPasswordDialog extends DialogFragment {

    private FragmentActivity fragmentActivity;

    public ForgotPasswordDialog() {
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

        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_min);
        }

        inflate.findViewById(R.id.send_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(fragmentActivity, OtpValidation.class);
                intent.putExtra("fromForgot",true);
                startActivity(intent);

            }
        });

        inflate.findViewById(R.id.clear_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        return inflate;
    }
}