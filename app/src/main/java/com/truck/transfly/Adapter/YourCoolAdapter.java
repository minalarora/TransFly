package com.truck.transfly.Adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;


import com.truck.transfly.Model.SliderModel;
import com.truck.transfly.MuUtils.MetalRecyclerViewPager;
import com.truck.transfly.R;

import java.util.List;

public class YourCoolAdapter extends MetalRecyclerViewPager.MetalAdapter<YourCoolAdapter.YourCoolViewHolder> {

    private final List<SliderModel> yourDataSource;
    private OnClickListener onClickListener;
    private Context context;

    public interface OnClickListener {

        void onClick();

    }

    public void setOnClickListener(OnClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    public YourCoolAdapter(@NonNull DisplayMetrics metrics, @NonNull List<SliderModel> yourDataSource, Context context) {
        super(metrics);

        this.yourDataSource =yourDataSource;
        this.context=context;

    }

    @NonNull
    @Override
    public YourCoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.root_layout, parent, false);

        return new YourCoolViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull YourCoolViewHolder holder, int position) {
        // don't forget to call supper.onBindViewHolder!
        super.onBindViewHolder(holder, position);

//        SliderModel sliderModel = yourDataSource.get(position);
//
//        Glide.with(context).load(sliderModel.getSliderName()).into(holder.image);
//
//        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                onClickListener.onClick();
//
//            }
//        });

        // ...
    }

    @Override
    public int getItemCount() {
        return yourDataSource.size();
    }

    static class YourCoolViewHolder extends MetalRecyclerViewPager.MetalViewHolder {

        private CardView cardLayout;
        private ImageView image;

        public YourCoolViewHolder(View itemView) {
            super(itemView);

            cardLayout =itemView.findViewById(R.id.card_layout);
            image =itemView.findViewById(R.id.image);

            // ...
        }
    }

    // ...
}
