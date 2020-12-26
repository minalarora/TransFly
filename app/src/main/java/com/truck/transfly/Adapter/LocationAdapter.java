package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.PositionModel;
import com.truck.transfly.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.viewholder> {

    private final Context context;
    private List<PositionModel> positionModelList;

    public LocationAdapter(Context context,List<PositionModel> positionModels){

        this.context=context;
        this.positionModelList=positionModels;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.location_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.position_any.setVisibility(View.GONE);
        holder.position_zero.setVisibility(View.GONE);

        if(position==0) {

            holder.position_zero.setVisibility(View.VISIBLE);

            return;
        }

        holder.position_any.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return positionModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private LinearLayout position_any,position_zero;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            position_zero=itemView.findViewById(R.id.position_zero);
            position_any=itemView.findViewById(R.id.postion_any);

        }
    }

}