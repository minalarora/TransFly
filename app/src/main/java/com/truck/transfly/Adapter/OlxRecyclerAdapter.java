package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truck.transfly.Activty.ShowOlxProductActivity;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseResale;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class OlxRecyclerAdapter extends RecyclerView.Adapter<OlxRecyclerAdapter.viewholder> implements Filterable {

    private final Context context;
    private List<ResponseResale> responseResaleList;
    private List<ResponseResale> listFilter;
    private boolean lease;

    public OlxRecyclerAdapter(Context context, ArrayList<ResponseResale> responseResaleList) {

        this.context = context;
        this.responseResaleList = responseResaleList;
        this.listFilter=responseResaleList;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.olx_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseResale responseResale = listFilter.get(position);

        if (responseResale.getImageList()!=null && responseResale.getImageList().size() > 0)
            Glide.with(context).load(responseResale.getImageList().get(0)).into(holder.imageProduct);

        holder.title.setText(responseResale.getCompany());
        holder.price.setText(responseResale.getVehiclename());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowOlxProductActivity.class);
                intent.putExtra("vehicleStore",responseResale);
                intent.putExtra("stringImage",responseResale.getImageList());
                intent.putExtra("lease",lease);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    public void setLeaseValue(boolean b) {

        this.lease=b;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFilter = responseResaleList;
                } else {
                    List<ResponseResale> filteredList = new ArrayList<>();
                    for (ResponseResale row : responseResaleList) {
                        if (row.getVehiclename().toLowerCase().contains(charString.toLowerCase())) {
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

                listFilter = (ArrayList<ResponseResale>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private ImageView imageProduct;
        private TextView price, title;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            imageProduct = itemView.findViewById(R.id.image_product);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);

        }
    }

}
