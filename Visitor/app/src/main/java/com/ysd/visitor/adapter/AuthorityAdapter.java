package com.ysd.visitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetVisitorListBean;
import com.ysd.visitor.ui.activity.VisitorDetailsActivity;
import com.ysd.visitor.ui.activity.AuthorityActivity;
import com.ysd.visitor.utlis.TimeUtil;

import java.util.ArrayList;

public class AuthorityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<GetVisitorListBean.DataBean> list;

    public AuthorityAdapter(AuthorityActivity authorityActivity, ArrayList<GetVisitorListBean.DataBean> list) {
        this.context = authorityActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.authority_adapter, parent, false);
        return new myAuthorityAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof myAuthorityAdapter) {
            //头像
            String imgUrl = list.get(position).getImgUrl();
            if (imgUrl != null) {
                Uri parse = Uri.parse(imgUrl);
                Log.d("TAG", "onBindViewHolder: " + parse);
                //((myAuthorityAdapter) holder).image.setImageURI(parse);
                Glide.with(context).load(imgUrl).placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei).into(((myAuthorityAdapter) holder).image);

            }

            //姓名
            String name = list.get(position).getName();
            if (name != null) {
                ((myAuthorityAdapter) holder).name.setText(name);
            }
            //身份证号
            String cardid = list.get(position).getCardid();
            ((myAuthorityAdapter) holder).idcad.setText(cardid);
            //来访时间
            String createtime = list.get(position).getVisitor_time();
            if (createtime != null) {
                Long aLong = Long.valueOf(createtime);
                String s = TimeUtil.timeYMDChinese(aLong);
                ((myAuthorityAdapter) holder).creantime.setText(s);
            }
            //结束时间
            String leave_time = list.get(position).getLeave_time();
            if (leave_time != null) {
                Long aLong = Long.valueOf(leave_time);
                String s = TimeUtil.timeYMDChinese(aLong);
                ((myAuthorityAdapter) holder).leavetime.setText(s);
            }
            //来访目的
            String purpose = list.get(position).getPurpose();
            ((myAuthorityAdapter) holder).purpose.setText(purpose);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visitorid = list.get(position).getVisitorid();
                    Intent intent = new Intent(context, VisitorDetailsActivity.class);
                    intent.putExtra("visitorid",visitorid+"");
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class myAuthorityAdapter extends RecyclerView.ViewHolder {

        private final TextView name, idcad, creantime, leavetime,purpose;
        private final ImageView image;

        public myAuthorityAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.aurhortiy_adapter_name);
            idcad = itemView.findViewById(R.id.aurhortiy_adapter_idcad);
            creantime = itemView.findViewById(R.id.aurhortiy_adapter_creantime);
            leavetime = itemView.findViewById(R.id.aurhortiy_adapter_leavetime);
            image = itemView.findViewById(R.id.aurhortiy_adapter_simple);
            purpose = itemView.findViewById(R.id.aurhortiy_adapter_purpose);
        }
    }
}
