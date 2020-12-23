package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Activty.FieldStafBookingConfirmationActivity;
import com.truck.transfly.R;

import java.util.List;

public class FieldStafAdapter extends RecyclerView.Adapter<FieldStafAdapter.viewholder> {

    private final Context context;
    private List<String> stringList;

    public FieldStafAdapter(Context context,List<String> stringList){

        this.context=context;
        this.stringList=stringList;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, FieldStafBookingConfirmationActivity.class));

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
        return stringList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private RelativeLayout confirm_booking;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            confirm_booking=itemView.findViewById(R.id.confirm_booking);

        }
    }

}
