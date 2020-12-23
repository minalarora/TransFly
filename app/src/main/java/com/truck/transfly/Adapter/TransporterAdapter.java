package com.truck.transfly.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.truck.transfly.Activty.ShowInvoiceActivity;
import com.truck.transfly.R;

import java.util.List;

public class TransporterAdapter extends RecyclerView.Adapter<TransporterAdapter.viewholder> {

    private final Context context;
    private List<String> stringList;

    public TransporterAdapter(Context context, List<String> stringList){

        this.context=context;
        this.stringList=stringList;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.transporter_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, ShowInvoiceActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        public viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
