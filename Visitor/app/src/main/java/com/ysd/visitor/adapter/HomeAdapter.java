package com.ysd.visitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.HomeBean;
import com.ysd.visitor.ui.activity.AuthorityActivity;
import com.ysd.visitor.ui.activity.CouponActivity;
import com.ysd.visitor.ui.activity.InquireActivity;
import com.ysd.visitor.ui.activity.MainActivity;
import com.ysd.visitor.ui.activity.OpenDoorActivity;
import com.ysd.visitor.ui.activity.RegisterActivity;
import com.ysd.visitor.ui.activity.SettingsActivity;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<HomeBean> homeList;

    public HomeAdapter(MainActivity mainActivity, ArrayList<HomeBean> homeList) {
        this.context = mainActivity;
        this.homeList = homeList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.home_layout, parent, false);
        return new myHomeAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof myHomeAdapter) {
            String name = homeList.get(position).getName();
            ((myHomeAdapter) holder).home_name.setText(name);
            int icon = homeList.get(position).getIcon();
            ((myHomeAdapter) holder).home_image.setBackgroundResource(icon);
            ((myHomeAdapter) holder).home_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            context.startActivity(new Intent(context, RegisterActivity.class));
                            break;
                        case 1:
                            context.startActivity(new Intent(context, AuthorityActivity.class));
                            break;
                        case 2:
                            context.startActivity(new Intent(context, InquireActivity.class));
                            break;
                        case 3:
                            context.startActivity(new Intent(context, SettingsActivity.class));
                            break;
                        case 4:
                            context.startActivity(new Intent(context, OpenDoorActivity.class));
                            break;
                        case 5:
                            context.startActivity(new Intent(context, CouponActivity.class));
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    private class myHomeAdapter extends RecyclerView.ViewHolder {

        private final LinearLayout home_linear;
        private final ImageView home_image;
        private final TextView home_name;

        public myHomeAdapter(@NonNull View itemView) {
            super(itemView);
            home_linear = itemView.findViewById(R.id.home_linear);
            home_image = itemView.findViewById(R.id.home_image);
            home_name = itemView.findViewById(R.id.home_name);
        }
    }
}
