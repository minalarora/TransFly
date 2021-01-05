package com.truck.transfly.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.truck.transfly.Model.ReferModel;
import com.truck.transfly.Model.ResponseReward;
import com.truck.transfly.R;

public class ReferAdapter extends RecyclerView.Adapter<ReferAdapter.ViewHolderSubclass> {
    private final List<ResponseReward> responseRewardList;
    private final Context context;

    public ReferAdapter(Context context, List<ResponseReward> responseRewardList){

        this.context=context;
        this.responseRewardList=responseRewardList;

    }

    @NonNull
    @Override
    public ViewHolderSubclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.refer_adapter_layout, parent, false);

        return new ViewHolderSubclass(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSubclass holder, int position) {

        ResponseReward responseReward = responseRewardList.get(position);

        holder.only_text_image.setVisibility(View.GONE);
        holder.only_text.setVisibility(View.GONE);
        holder.only_images.setVisibility(View.GONE);

        if(responseReward.getStatus()==1) {
            holder.only_images.setVisibility(View.VISIBLE);
            Glide.with(context).load(responseReward.getIm()).into(holder.image3);
        }else if(responseReward.getStatus()==2) {
            holder.only_text.setVisibility(View.VISIBLE);
            holder.text2.setText(responseReward.getText());
        }else {
            holder.only_text_image.setVisibility(View.VISIBLE);
            holder.text1.setText(responseReward.getText());
            Glide.with(context).load(responseReward.getIm()).into(holder.image1);

        }

    }

    @Override
    public int getItemCount() {
        return responseRewardList.size();
    }

    public static class ViewHolderSubclass extends RecyclerView.ViewHolder {

        private CardView only_text,only_images,only_text_image;
        private TextView text1,text2;
        private ImageView image1,image3;

        public ViewHolderSubclass(@NonNull View itemView) {
            super(itemView);

            only_text=itemView.findViewById(R.id.only_text);
            only_images=itemView.findViewById(R.id.only_images);
            only_text_image=itemView.findViewById(R.id.only_text_image);
            text1=itemView.findViewById(R.id.text1);
            text2=itemView.findViewById(R.id.text2);
            image1=itemView.findViewById(R.id.image1);
            image3=itemView.findViewById(R.id.image3);

        }
    }
}
