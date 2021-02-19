package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truck.transfly.Model.RequestArea;
import com.truck.transfly.Model.ResponseLoading;
import com.truck.transfly.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.viewholder> {

    private final Context context;
    private final List<ResponseLoading> responseLoadingList;
    private List<RequestArea> positionModelList;
    private onClickListener onClickListener;
    private boolean loading;

    public LocationAdapter(Context context, List<RequestArea> positionModels, List<ResponseLoading> responseLoadingList) {

        this.context = context;
        this.positionModelList = positionModels;
        this.responseLoadingList = responseLoadingList;

    }

    public void setLoading(boolean b) {

        this.loading = b;

    }

    public interface onClickListener {

        void onClick(RequestArea requestArea);

        void onClickLoading(ResponseLoading requestArea);

    }

    public void setOnClickListener(onClickListener onClickListener) {

        this.onClickListener = onClickListener;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.location_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.position_any.setVisibility(View.GONE);
        holder.position_zero.setVisibility(View.GONE);

        if (position == 0) {

            holder.position_zero.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickListener.onClick(null);

                }
            });

            return;
        }
        else if( position > 0 && position < (positionModelList.size()+1)) {
            RequestArea requestArea = positionModelList.get(position - 1);
            holder.position_any.setVisibility(View.VISIBLE);

            holder.cityName.setText(requestArea.getName());
            Glide.with(context).load(requestArea.getAreaimage()).into(holder.circleImageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickListener.onClick(requestArea);

                }
            });
        }
        else {

            ResponseLoading loading = responseLoadingList.get(position-(positionModelList.size()+1));
            holder.position_any.setVisibility(View.VISIBLE);

            holder.cityName.setText(loading.getLoadingName()+"\n Loading");
            Glide.with(context).load("https://transflyhome.club/loading/"+loading.getLoadingName()).into(holder.circleImageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickListener.onClickLoading(loading);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return positionModelList.size() + responseLoadingList.size()+1;
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private LinearLayout position_any, position_zero;
        private TextView cityName;
        private CircleImageView circleImageView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            position_zero = itemView.findViewById(R.id.position_zero);
            position_any = itemView.findViewById(R.id.postion_any);
            cityName = itemView.findViewById(R.id.cityName);
            circleImageView = itemView.findViewById(R.id.circularImage);

        }
    }

}
