package com.truck.transfly.Frament;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.truck.transfly.R;

public class ContactUsDialogFragment extends DialogFragment {

    private onClickListener onClickListener;

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

        View inflate = inflater.inflate(R.layout.fragment_contact_us_dialog, container, false);

        RelativeLayout phone =inflate.findViewById(R.id.phone);
        RelativeLayout telegram =inflate.findViewById(R.id.telegram);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = "18002702356";
                Intent phoneCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(phoneCall);

                dismiss();

            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/transflybot"));
                startActivity(telegram);

                dismiss();

            }
        });

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_min);
        }

        return inflate;
    }
}