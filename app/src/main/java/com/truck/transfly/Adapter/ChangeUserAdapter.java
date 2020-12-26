package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.truck.transfly.Model.WhoModel;
import com.truck.transfly.R;

import java.util.List;


public class ChangeUserAdapter extends RecyclerView.Adapter<ChangeUserAdapter.viewholder> {

    private final Context context;
    private List<WhoModel> whoModelList;
    private int row_index;
    private onClickListener onClickListener;

    public ChangeUserAdapter(Context context,List<WhoModel> whoModelList){

        this.context=context;
        this.whoModelList=whoModelList;

    }

    public interface onClickListener{

        void onClick(WhoModel whoModel);

    }

    public void setOnClickListener(onClickListener onClickListener){

        this.onClickListener=onClickListener;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.change_user_adapter_layout, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        WhoModel whoModel = whoModelList.get(position);
        holder.image_link.setImageResource(whoModel.getImage_url());
        holder.belowOwner.setText(whoModel.getBelowOwner());
        holder.belowOwner2.setText(whoModel.getBelow2Owner());
        holder.ownerName.setText(whoModel.getOwnerType());
        holder.checkView.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index=holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if(row_index==position){
            holder.parent_of_card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red_900));
            holder.ownerName.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.belowOwner.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.belowOwner2.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.checkView.setVisibility(View.VISIBLE);
            onClickListener.onClick(whoModel);
        } else {
            holder.parent_of_card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.ownerName.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.belowOwner.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.belowOwner2.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.checkView.setVisibility(View.GONE);
            onClickListener.onClick(whoModel);
        }

    }

    @Override
    public int getItemCount() {
        return whoModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        private ImageView image_link;
        private TextView belowOwner,belowOwner2,ownerName;
        private CardView parent_of_card;
        private ImageView checkView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            belowOwner=itemView.findViewById(R.id.belowOwner);
            belowOwner2=itemView.findViewById(R.id.belowOwner2);
            image_link=itemView.findViewById(R.id.image_link);
            ownerName=itemView.findViewById(R.id.ownerName);
            parent_of_card=itemView.findViewById(R.id.parent_of_card);
            checkView=itemView.findViewById(R.id.check1);

        }
    }

}
