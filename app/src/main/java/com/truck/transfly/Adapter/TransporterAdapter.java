package com.truck.transfly.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.truck.transfly.Activty.ShowInvoiceActivity;
import com.truck.transfly.Activty.TransporterShowInvoiceActivity;
import com.truck.transfly.Model.ResponseInvoice;
import com.truck.transfly.R;
import com.truck.transfly.utils.PreferenceUtil;

import java.util.List;

public class TransporterAdapter extends RecyclerView.Adapter<TransporterAdapter.viewholder> {

    private final Context context;
    private final boolean transporter;
    private List<ResponseInvoice> responseInvoiceList;
    private int decideKeywords;
    private boolean b;

    public TransporterAdapter(Context context, List<ResponseInvoice> responseInvoiceList){

        this.context=context;
        this.responseInvoiceList=responseInvoiceList;

        String token = PreferenceUtil.getData(context, "token");
        transporter = token.split(":")[0].equals("transporter");


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

        ResponseInvoice responseInvoice = responseInvoiceList.get(position);

        holder.price_rate.setText(""+responseInvoice.getRate());
        holder.vehicle_number.setText(responseInvoice.getVehiclenumber());
        holder.mobileNumber.setText(responseInvoice.getContact());
        holder.vehile_owner.setText(responseInvoice.getVehicleOwnerName());
        holder.date_created.setText(responseInvoice.getDate());

        if(!TextUtils.isEmpty(responseInvoice.getDate())) {

            holder.date_name.setText(responseInvoice.getDate().split(" ")[0]);
            holder.time.setText(responseInvoice.getDate().split(" ")[1]);

        }

        holder.to_from_dest.setText(responseInvoice.getMinename() +" - "+responseInvoice.getLoading());
        holder.vehicleNamepParent.setVisibility(View.GONE);

        if(transporter){

            holder.vehicleNamepParent.setVisibility(View.GONE);
            holder.mobileNumberParent.setVisibility(View.GONE);

        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(transporter){

                    Intent intent = new Intent(context, TransporterShowInvoiceActivity.class);
                    intent.putExtra("responseInvoice",responseInvoice);
                    intent.putExtra("shareBill",true);
                    context.startActivity(intent);

                    return;

                }

                Intent intent = new Intent(context, ShowInvoiceActivity.class);
                intent.putExtra("responseInvoice",responseInvoice);
                intent.putExtra("shareBill",true);
                context.startActivity(intent);

            }
        });

        holder.show_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(transporter){

                    Intent intent = new Intent(context, TransporterShowInvoiceActivity.class);
                    intent.putExtra("vehicle_owner",b);
                    intent.putExtra("responseInvoice",responseInvoice);
                    context.startActivity(intent);

                    return;

                }

                Intent intent = new Intent(context, ShowInvoiceActivity.class);
                intent.putExtra("vehicle_owner",b);
                intent.putExtra("responseInvoice",responseInvoice);
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(transporter){

                    Intent intent = new Intent(context, TransporterShowInvoiceActivity.class);
                    intent.putExtra("responseInvoice",responseInvoice);
                    context.startActivity(intent);

                    return;

                }

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

    public void setVehicleOwnwer(boolean vehicleOwner) {

        this.b=vehicleOwner;

    }

    public class viewholder extends RecyclerView.ViewHolder {

        private TextView to_from_dest,date_created,vehile_owner,mobileNumber,vehicle_number,price_rate,date_name,time;

        private LinearLayout mobileNumberParent,vehicleNamepParent;

        private RelativeLayout show_invoice,share;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            to_from_dest=itemView.findViewById(R.id.to_from_dest);
            mobileNumberParent=itemView.findViewById(R.id.mobileNumberParent);
            vehicleNamepParent=itemView.findViewById(R.id.vehicleNamepParent);
            date_created=itemView.findViewById(R.id.date_created);
            vehile_owner=itemView.findViewById(R.id.vehile_owner);
            mobileNumber=itemView.findViewById(R.id.mobileNumber);
            vehicle_number=itemView.findViewById(R.id.vehicle_number);
            price_rate=itemView.findViewById(R.id.price_rate);
            show_invoice=itemView.findViewById(R.id.show_invoice);
            share=itemView.findViewById(R.id.share);
            date_name=itemView.findViewById(R.id.date_name);
            time=itemView.findViewById(R.id.time);

        }
    }

}
