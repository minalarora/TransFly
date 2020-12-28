package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        if(position==1){

            holder.cityName.setText("Bhopal");
            holder.circularImageText.setText("Bh");

        } else if(position==2){

            holder.cityName.setText("Hydrabad");
            holder.circularImageText.setText("Hy");

        }else if(position==3){

            holder.cityName.setText("Nepal");
            holder.circularImageText.setText("Ne");

        } else if(position==4){

            holder.cityName.setText("Kolkatta");
            holder.circularImageText.setText("Ko");

        } else if(position==5){

            holder.cityName.setText("Agra");
            holder.circularImageText.setText("Ag");

        } else if(position==6){

            holder.cityName.setText("Raybrali");
            holder.circularImageText.setText("Ra");

        } else if(position==7){

            holder.cityName.setText("Lucknow");
            holder.circularImageText.setText("Lu");

        }

        holder.position_any.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return positionModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private LinearLayout position_any,position_zero;
        private TextView circularImageText,cityName;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            position_zero=itemView.findViewById(R.id.position_zero);
            position_any=itemView.findViewById(R.id.postion_any);
            circularImageText=itemView.findViewById(R.id.circularImageText);
            cityName=itemView.findViewById(R.id.cityName);

        }
    }

}
