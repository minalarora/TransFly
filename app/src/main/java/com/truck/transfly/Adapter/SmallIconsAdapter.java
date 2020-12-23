package com.truck.transfly.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.R;

import java.util.List;

public class SmallIconsAdapter extends RecyclerView.Adapter<SmallIconsAdapter.viewholder> {

    private final Context context;
    private List<String> stringList;
    private int row_index;

    public SmallIconsAdapter(Context context, List<String> stringList){

        this.context=context;
        this.stringList=stringList;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index=holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if(row_index==position){
            holder.small_parent.setBackgroundResource(R.drawable.round_blue_tick);
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
        return stringList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private RelativeLayout small_parent;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            small_parent=itemView.findViewById(R.id.parent_of_small_icons);

        }
    }
}
