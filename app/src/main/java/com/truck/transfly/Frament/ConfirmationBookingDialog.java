package com.truck.transfly.Frament;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;

public class ConfirmationBookingDialog extends DialogFragment {


    private onClickListener onClickListener;
    private ResponseBooking responseBooking;
    private String hsd,rate_string,cash;

    public ConfirmationBookingDialog() {
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

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_min);
        }

        View inflate = inflater.inflate(R.layout.fragment_confirmation_booking_dialog, container, false);

        Bundle arguments = getArguments();

        if(arguments!=null){

            responseBooking = arguments.getParcelable("responseBooking");
            hsd = arguments.getString("hsd");
            rate_string = arguments.getString("rate");
            cash = arguments.getString("cash");

        }

        TextView vehicleNo =inflate.findViewById(R.id.vehicle_no);
        TextView hsd_value=inflate.findViewById(R.id.hsd_value);
        TextView cashValue=inflate.findViewById(R.id.cash_value);
        TextView rate=inflate.findViewById(R.id.rate);
        RelativeLayout yes=inflate.findViewById(R.id.yes);
        RelativeLayout no=inflate.findViewById(R.id.no);

        vehicleNo.setText(responseBooking.getVehicle());
        hsd_value.setText(hsd);
        cashValue.setText(cash);
        rate.setText(rate_string);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickListener.onClick();

                dismiss();

            }
        });

        return inflate;
    }
}