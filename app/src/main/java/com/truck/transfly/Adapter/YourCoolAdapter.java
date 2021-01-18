package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;


import com.bumptech.glide.Glide;
import com.truck.transfly.Activty.HomeActivity;
import com.truck.transfly.Activty.OlxPageActivity;
import com.truck.transfly.Activty.SearchBarActivity;
import com.truck.transfly.Model.ResponseBanner;
import com.truck.transfly.Model.SliderModel;
import com.truck.transfly.MuUtils.MetalRecyclerViewPager;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class YourCoolAdapter extends MetalRecyclerViewPager.MetalAdapter<YourCoolAdapter.YourCoolViewHolder> {

    private final List<ResponseBanner> yourDataSource;
    private OnClickListener onClickListener;
    private Context context;

    public interface OnClickListener {

        void onClick();

    }

    public void setOnClickListener(OnClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    public YourCoolAdapter(@NonNull DisplayMetrics metrics, @NonNull ArrayList<ResponseBanner> yourDataSource, Context context) {
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
        ResponseBanner sliderModel = yourDataSource.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sliderModel.getBannerType().equals("booking")){

                    Intent searchBarAcivity = new Intent(context, SearchBarActivity.class);

                    searchBarAcivity.putExtra("vehicle",true);

                    context.startActivity(searchBarAcivity);

                } else if(sliderModel.getBannerType().equals("resale")){

                    context.startActivity(new Intent(context, OlxPageActivity.class));

                }

            }
        });

        Glide.with(context).load("https://transfly-ftr2t.ondigitalocean.app/bannerimage/" + sliderModel.getId()).into(holder.image);

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
