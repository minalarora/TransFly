package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.ResponseVehicle;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class SmallIconsAdapter extends RecyclerView.Adapter<SmallIconsAdapter.viewholder> {

    private final Context context;
    private List<ResponseVehicle> vehicleList;
    private int row_index;
    private onClickListener onClickListener;

    public SmallIconsAdapter(Context context, ArrayList<ResponseVehicle> vehicleList){

        this.context=context;

        this.vehicleList=vehicleList;

    }

    public interface onClickListener{

        void onClick(ResponseVehicle responseVehicle);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.small_icon_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseVehicle responseVehicle = vehicleList.get(position);

//        holder.vehicle_name.setText(responseVehicle.getVehiclename());
        holder.vehicle_number.setText(responseVehicle.getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index=holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if(row_index==position){
            holder.small_parent.setBackgroundResource(R.drawable.round_blue_tick);
            onClickListener.onClick(responseVehicle);
//            circleImageView.setBorderWidth(3);
//            circleImageView.setBorderColor(Color.parseColor("#000000"));
        } else {
            holder.small_parent.setBackgroundResource(R.drawable.round_light_pink);
//            circleImageView.setBorderWidth(3);
//            circleImageView.setBorderColor(Color.parseColor("#828282"));
        }

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private RelativeLayout small_parent;
        private TextView vehicle_name,vehicle_number;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            small_parent=itemView.findViewById(R.id.parent_of_small_icons);
            vehicle_name=itemView.findViewById(R.id.vehicle_name);
            vehicle_number=itemView.findViewById(R.id.vehicle_number);

        }
    }
}
