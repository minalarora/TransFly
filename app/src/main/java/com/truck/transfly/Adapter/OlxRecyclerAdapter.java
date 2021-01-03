package com.truck.transfly.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truck.transfly.Activty.ShowOlxProductActivity;
import com.truck.transfly.Model.ResponseResale;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;

public class OlxRecyclerAdapter extends RecyclerView.Adapter<OlxRecyclerAdapter.viewholder> {

    private final Context context;
    private List<ResponseResale> responseResaleList;

    public OlxRecyclerAdapter(Context context, ArrayList<ResponseResale> responseResaleList) {

        this.context = context;
        this.responseResaleList = responseResaleList;

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

        ResponseResale responseResale = responseResaleList.get(position);

        if (responseResale.getImageList()!=null && responseResale.getImageList().size() > 0)
            Glide.with(context).load(responseResale.getImageList().get(0)).into(holder.imageProduct);

        holder.title.setText(responseResale.getCompany());
        holder.price.setText(responseResale.getVehicleName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShowOlxProductActivity.class);
                intent.putExtra("vehicleStore",responseResale);
                intent.putExtra("stringImage",responseResale.getImageList());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return responseResaleList.size();
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
