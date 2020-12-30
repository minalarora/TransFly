package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Activty.FieldStafBookingConfirmationActivity;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class FieldStafAdapter extends RecyclerView.Adapter<FieldStafAdapter.viewholder> {

    private final Context context;
    private List<ResponseBooking> responseBookingList;

    public FieldStafAdapter(Context context, ArrayList<ResponseBooking> responseBookingList){

        this.context=context;
        this.responseBookingList=responseBookingList;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.field_staf_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseBooking responseBooking = responseBookingList.get(position);

        holder.to_from_dest.setText(responseBooking.getLoading()+" - "+responseBooking.getMinename());
        holder.number.setText(responseBooking.getVehicleownermobile());
        holder.name_of_owner.setText(responseBooking.getVehicleowner());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FieldStafBookingConfirmationActivity.class);
                intent.putExtra("responseBooking",responseBooking);
                context.startActivity(intent);

            }
        });

        holder.confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, FieldStafBookingConfirmationActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return responseBookingList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private RelativeLayout confirm_booking;
        private TextView to_from_dest,name_of_owner,number;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            confirm_booking=itemView.findViewById(R.id.confirm_booking);
            to_from_dest=itemView.findViewById(R.id.to_from_dest);
            name_of_owner=itemView.findViewById(R.id.name_of_owner);
            number=itemView.findViewById(R.id.number);

        }
    }

}
