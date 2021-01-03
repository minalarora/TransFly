package com.truck.transfly.Adapter;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentBookingAdapter extends RecyclerView.Adapter<CurrentBookingAdapter.viewholder> {

    private final Context context;
    private final String lat;
    private final String aLongitude;
    private final FragmentActivity fragmentActivity;
    private List<ResponseBooking> responseBookingList;
    private onClickListener onClickListener;

    public CurrentBookingAdapter(Context context, ArrayList<ResponseBooking> responseBookingList){

        this.context=context;
        this.responseBookingList=responseBookingList;

        fragmentActivity= (FragmentActivity) context;

        SharedPreferences sharedPreferences=context.getSharedPreferences("currentLocation",Context.MODE_PRIVATE);
        lat = sharedPreferences.getString("lat", "");
        aLongitude = sharedPreferences.getString("long", "");

    }

    public interface onClickListener{

        void onClick(ResponseBooking responseBooking, int position);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.currentbooking_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseBooking responseBooking = responseBookingList.get(position);

        holder.to_from.setText(responseBooking.getMinename()+" - "+responseBooking.getLoading());
        holder.created_date.setText(responseBooking.getDate());
        holder.vehicle_number.setText(responseBooking.getVehiclename());

        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);

                String uri = "http://maps.google.com/maps?saddr=" + lat + "," + aLongitude + "&daddr=" + 0 + "," + 0;

                intent.setData(Uri.parse(uri));
                if (intent.resolveActivity(fragmentActivity.getPackageManager()) != null) {
                    context.startActivity(intent);
                }

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickListener.onClick(responseBooking,position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return responseBookingList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private TextView to_from,created_date,vehicle_number,etl;
        private RelativeLayout remove;
        private RelativeLayout directions;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            to_from=itemView.findViewById(R.id.to_from);
            created_date=itemView.findViewById(R.id.created_date);
            vehicle_number=itemView.findViewById(R.id.vehicle_number);
            etl=itemView.findViewById(R.id.etl);
            remove=itemView.findViewById(R.id.remove);
            directions=itemView.findViewById(R.id.directions);

        }
    }

}
