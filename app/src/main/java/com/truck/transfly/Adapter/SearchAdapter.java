package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewholder> {

    private final Context context;
    private ArrayList<ResponseMine> responseMineList;
    private onClickListener onClickListener;

    public interface onClickListener{

        void onClick(ResponseMine responseMine);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    public SearchAdapter(Context context, ArrayList<ResponseMine> responseMineArrayList){

        this.context=context;
        this.responseMineList=responseMineArrayList;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.search_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseMine responseMine = responseMineList.get(position);
        holder.from_dest.setText(responseMine.getName());
        holder.rate.setText("Max Tyres: "+String.valueOf(responseMine.getTyres()));

        if(responseMine.getTrailer()){

            holder.etl.setText("Trailer: "+"YES");

        } else {

            holder.etl.setText("Trailer: "+"NO");

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(responseMine.getActive()!=null && responseMine.getActive()) {

                    onClickListener.onClick(responseMine);

                } else {

                    Toast.makeText(context, "This Mine is INACTIVE for the day, please try another Mine for your Loading.", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return responseMineList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private TextView from_dest,rate,etl;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            from_dest=itemView.findViewById(R.id.from_dest);
            rate=itemView.findViewById(R.id.rate);
            etl=itemView.findViewById(R.id.etl);

        }
    }

}
