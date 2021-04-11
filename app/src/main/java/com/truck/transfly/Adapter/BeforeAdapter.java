package com.truck.transfly.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseLoading;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class BeforeAdapter extends RecyclerView.Adapter<BeforeAdapter.Viewholder> {

    private final Context context;
    private final FragmentActivity fragmentActivity;
    private final String loading;
    private List<ResponseMine> responseBookingList;
    private onClickListener onClickListener;

    public BeforeAdapter(Context context, ArrayList<ResponseMine> responseBookingList,String loading){

        this.context=context;
        this.responseBookingList=responseBookingList;
        this.loading = loading;
        fragmentActivity= (FragmentActivity) context;
    }

    public interface onClickListener{

        void createBooking(ResponseMine responseBooking);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.single_beforebooking, parent, false);

        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        ResponseMine responseBooking = responseBookingList.get(position);

        holder.to_from.setText(responseBooking.getName()+" - "+loading);
        ResponseLoading dummyloading  = null;

        ArrayList<ResponseLoading> list = (ArrayList<ResponseLoading>) responseBooking.getLoading();
        for (ResponseLoading l : list) {
            if (l.getLoadingname().equalsIgnoreCase(loading)) {
               dummyloading = l;
               break;
            }
        }
          holder.created_date.setText(dummyloading.getRate().toString() + " PMT");
          holder.vehicle_number.setText(dummyloading.getEtl().toString() + " HRS");
          holder.tyres.setText("Tyres:-" + responseBooking.getTyres() + "   " + "Trailer:-" + (responseBooking.getTrailer()?"ALLOWED":"NOT ALLOWED"));

          holder.parent.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onClickListener.createBooking(responseBooking);
              }
          });

        holder.showInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.createBooking(responseBooking);
            }
        });

//        holder.directions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onClickListener.onClick(responseBooking,position,true);
//
//            }
//        });
//
//        holder.remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onClickListener.onClick(responseBooking,position, false);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return responseBookingList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView to_from,created_date,vehicle_number,tyres;
        private RelativeLayout remove;
        private RelativeLayout directions;
        private CardView parent;
        private final LinearLayout showInvoice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


            parent = itemView.findViewById(R.id.parent);
            showInvoice = itemView.findViewById(R.id.show_invoices);

            to_from=itemView.findViewById(R.id.to_from);
            created_date=itemView.findViewById(R.id.date_name);
            vehicle_number=itemView.findViewById(R.id.time2);
            tyres = itemView.findViewById(R.id.tyres);

//            etl=itemView.findViewById(R.id.etl);
//            remove=itemView.findViewById(R.id.remove);
//            directions=itemView.findViewById(R.id.directions);

        }
    }

}

