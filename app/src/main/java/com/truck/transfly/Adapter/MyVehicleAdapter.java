package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.ResponseVehicle;
import com.truck.transfly.R;

import java.util.List;

public class MyVehicleAdapter extends RecyclerView.Adapter<MyVehicleAdapter.viewholder> {

    private final Context context;
    private List<ResponseVehicle> responseVehicleList;
    private onClickListener onClickListener;

    public MyVehicleAdapter(Context context,List<ResponseVehicle> responseVehicleList){

        this.context=context;
        this.responseVehicleList=responseVehicleList;

    }

    public interface onClickListener{

        void onClick(ResponseVehicle responseVehicle, int position);
        void onEdit(ResponseVehicle responseVehicle, int position);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.my_vehicle_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseVehicle responseVehicle = responseVehicleList.get(position);

        holder.vehicle_number_series.setText("Vehicle "+String.valueOf(position+1));

        holder.vehicle_number.setText(responseVehicle.getNumber());
        holder.vehicle_name.setText(responseVehicle.getVehiclename());

        holder.date_created.setText(responseVehicle.getDate().substring(0,15));

        holder.contact_text.setText(responseVehicle.getContact());

        holder.edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickListener.onEdit(responseVehicle,position);

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickListener.onClick(responseVehicle,position);

            }
        });

        if(responseVehicle.getStatus()!=null && responseVehicle.getStatus()==0){

            holder.status.setText("Pending");

        } else {

            holder.status.setText("Active");

        }

    }

    @Override
    public int getItemCount() {
        return responseVehicleList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private TextView vehicle_name,vehicle_number,status,date_created,vehicle_number_series,contact_text;
        private ImageView btn_delete,edit_contact;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            vehicle_name=itemView.findViewById(R.id.vehicle_name);
            vehicle_number=itemView.findViewById(R.id.vehicle_number);
            status=itemView.findViewById(R.id.status);
            date_created=itemView.findViewById(R.id.date_created);
            contact_text=itemView.findViewById(R.id.contact_text);
            vehicle_number_series=itemView.findViewById(R.id.vehicle_number_series);
            btn_delete=itemView.findViewById(R.id.btn_delete);
            edit_contact=itemView.findViewById(R.id.edit_contact);

        }
    }

}
