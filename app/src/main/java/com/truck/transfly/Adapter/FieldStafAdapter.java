package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Activty.FieldStafBookingConfirmationActivity;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FieldStafAdapter extends RecyclerView.Adapter<FieldStafAdapter.viewholder>  implements Filterable {

    private final Context context;
    private List<ResponseBooking> responseBookingList;
    private List<ResponseBooking> listFilter;

    public FieldStafAdapter(Context context, ArrayList<ResponseBooking> responseBookingList){

        this.context=context;
        this.responseBookingList=responseBookingList;
        this.listFilter=responseBookingList;

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

        ResponseBooking responseBooking = listFilter.get(position);

        holder.to_from_dest.setText(responseBooking.getMinename()+" - "+responseBooking.getLoading());
        holder.number.setText(responseBooking.getContact());
        holder.name_of_owner.setText(responseBooking.getVehiclename());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FieldStafBookingConfirmationActivity.class);
                intent.putExtra("responseBooking",responseBooking);
                context.startActivity(intent);

            }
        });

        holder.call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent phoneCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", responseBooking.getContact(), null));
                context.startActivity(phoneCall);

            }
        });

        holder.confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FieldStafBookingConfirmationActivity.class);
                intent.putExtra("responseBooking",responseBooking);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFilter = responseBookingList;
                } else {
                    List<ResponseBooking> filteredList = new ArrayList<>();
                    for (ResponseBooking row : responseBookingList) {
                        if (row.getVehiclename().toLowerCase().contains(charString.toLowerCase()) || row.getContact().toLowerCase().contains(charString.toLowerCase())) {
                            Log.d(TAG, "performFiltering: " + charString + " == " + row);
                            filteredList.add(row);
                        }
                    }

                    listFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                listFilter = (ArrayList<ResponseBooking>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private RelativeLayout confirm_booking,call_now;
        private TextView to_from_dest,name_of_owner,number;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            confirm_booking=itemView.findViewById(R.id.confirm_booking);
            to_from_dest=itemView.findViewById(R.id.to_from_dest);
            name_of_owner=itemView.findViewById(R.id.name_of_owner);
            number=itemView.findViewById(R.id.number);
            call_now=itemView.findViewById(R.id.call_now);

        }
    }

}
