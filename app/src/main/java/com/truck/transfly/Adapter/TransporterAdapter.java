package com.truck.transfly.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.truck.transfly.Activty.ShowInvoiceActivity;
import com.truck.transfly.Model.ResponseInvoice;
import com.truck.transfly.R;

import java.util.List;

public class TransporterAdapter extends RecyclerView.Adapter<TransporterAdapter.viewholder> {

    private final Context context;
    private List<ResponseInvoice> responseInvoiceList;
    private int decideKeywords;

    public TransporterAdapter(Context context, List<ResponseInvoice> responseInvoiceList){

        this.context=context;
        this.responseInvoiceList=responseInvoiceList;

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

        holder.itemView.setVisibility(View.GONE);
        ResponseInvoice responseInvoice = responseInvoiceList.get(position);

        if(decideKeywords==1 && responseInvoice.getStatus().equals("PENDING")){

            holder.itemView.setVisibility(View.VISIBLE);

        } else if(decideKeywords==2 && responseInvoice.getStatus().equals("COMPLETED")){

            holder.itemView.setVisibility(View.VISIBLE);

        } else if (decideKeywords==0 && (responseInvoice.getStatus().equals("PENDING") || responseInvoice.getStatus().equals("COMPLETED"))){

            holder.itemView.setVisibility(View.VISIBLE);

        }

        Log.i("TAG", "onBindViewHolder: "+responseInvoice.getStatus());

        holder.price_rate.setText(""+responseInvoice.getRate());
        holder.vehicle_number.setText(responseInvoice.getVehiclenumber());
        holder.mobileNumber.setText(responseInvoice.getVehicleownermobile());
        holder.vehile_owner.setText(responseInvoice.getVehicleOwnerName());
        holder.date_created.setText(responseInvoice.getDate());
        holder.to_from_dest.setText(responseInvoice.getLoading() +" - "+responseInvoice.getMinename());

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowInvoiceActivity.class);
                intent.putExtra("responseInvoice",responseInvoice);
                intent.putExtra("shareBill",true);
                context.startActivity(intent);

            }
        });

        holder.show_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowInvoiceActivity.class);
                intent.putExtra("responseInvoice",responseInvoice);
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowInvoiceActivity.class);
                intent.putExtra("responseInvoice",responseInvoice);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return responseInvoiceList.size();
    }

    public void setDecideKeywords(int i) {

        this.decideKeywords=i;

    }

    public class viewholder extends RecyclerView.ViewHolder {

        private TextView to_from_dest,date_created,vehile_owner,mobileNumber,vehicle_number,price_rate;

        private RelativeLayout show_invoice,share;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            to_from_dest=itemView.findViewById(R.id.to_from_dest);
            date_created=itemView.findViewById(R.id.date_created);
            vehile_owner=itemView.findViewById(R.id.vehile_owner);
            mobileNumber=itemView.findViewById(R.id.mobileNumber);
            vehicle_number=itemView.findViewById(R.id.vehicle_number);
            price_rate=itemView.findViewById(R.id.price_rate);
            show_invoice=itemView.findViewById(R.id.show_invoice);
            share=itemView.findViewById(R.id.share);

        }
    }

}
