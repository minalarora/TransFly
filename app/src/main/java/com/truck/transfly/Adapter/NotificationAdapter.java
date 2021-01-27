package com.truck.transfly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.truck.transfly.Model.ResponseNotification;
import com.truck.transfly.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {

    private final Context context;
    private List<ResponseNotification> responseNotificationList;

    public NotificationAdapter(Context context, List<ResponseNotification> responseNotificationList) {

        this.context = context;
        this.responseNotificationList = responseNotificationList;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.notification_adapter_layout, parent, false);

        return new viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        ResponseNotification responseNotification = responseNotificationList.get(position);

        holder.title.setText(responseNotification.getText());

        if (responseNotification.getDate() != null)
            holder.timestamp.setReferenceTime(Long.parseLong(responseNotification.getDate()));

    }

    @Override
    public int getItemCount() {
        return responseNotificationList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {

        private TextView title;
        private RelativeTimeTextView timestamp;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }

}
