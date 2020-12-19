package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.truck.transfly.Model.ReferModel;
import com.truck.transfly.R;

public class ReferAdapter extends RecyclerView.Adapter<ReferAdapter.ViewHolderSubclass> {
    private final List<ReferModel> referModelList;
    private final Context context;

    public ReferAdapter(Context context, List<ReferModel> referModelList){

        this.context=context;
        this.referModelList=referModelList;

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

        ReferModel referModel = referModelList.get(position);

        holder.only_text_image.setVisibility(View.GONE);
        holder.only_text.setVisibility(View.GONE);
        holder.only_images.setVisibility(View.GONE);

        if(referModel.isImages())
            holder.only_images.setVisibility(View.VISIBLE);
        else if(referModel.isText())
            holder.only_text.setVisibility(View.VISIBLE);
        else
            holder.only_text_image.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return referModelList.size();
    }

    public static class ViewHolderSubclass extends RecyclerView.ViewHolder {

        private CardView only_text,only_images,only_text_image;

        public ViewHolderSubclass(@NonNull View itemView) {
            super(itemView);

            only_text=itemView.findViewById(R.id.only_text);
            only_images=itemView.findViewById(R.id.only_images);
            only_text_image=itemView.findViewById(R.id.only_text_image);

        }
    }
}
