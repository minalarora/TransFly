package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchFieldStafAdapter extends RecyclerView.Adapter<SearchFieldStafAdapter.viewHolder> implements Filterable {
    private final List<ResponseFieldStaff> cityModelList;
    private final Context context;
    private List<ResponseFieldStaff> listFilter;
    private String state;
    private onClickListerner onClickListerner;

    public interface onClickListerner{

        void onClick(ResponseFieldStaff city_id);

    }

    public void setOnClickListener(onClickListerner onClickListener){

        this.onClickListerner=onClickListener;

    }

    public SearchFieldStafAdapter(Context context, List<ResponseFieldStaff> cityModelList) {

        this.context=context;
        this.cityModelList=cityModelList;
        this.listFilter=cityModelList;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.search_fieldstaff_adapter_layout, parent, false);

        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final ResponseFieldStaff cityModel = listFilter.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onClickListerner.onClick(cityModel);


            }
        });

        holder.city_name.setText(cityModel.getName());

    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    public void setActivity(String state) {

        this.state=state;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFilter = cityModelList;
                } else {
                    List<ResponseFieldStaff> filteredList = new ArrayList<>();
                    for (ResponseFieldStaff row : cityModelList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
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

                listFilter = (ArrayList<ResponseFieldStaff>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView city_name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            city_name =itemView.findViewById(R.id.city_name);

        }
    }
}
