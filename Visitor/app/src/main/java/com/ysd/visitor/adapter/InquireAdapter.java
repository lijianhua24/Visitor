package com.ysd.visitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetVisitorListBean;
import com.ysd.visitor.bean.VisitorInOutRecordBean;
import com.ysd.visitor.ui.activity.AuthorityActivity;
import com.ysd.visitor.ui.activity.VisitorDetailsActivity;
import com.ysd.visitor.utlis.TimeUtil;

import java.util.ArrayList;

public class InquireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<VisitorInOutRecordBean.DataBean> list;

    public InquireAdapter(Context authorityActivity, ArrayList<VisitorInOutRecordBean.DataBean> list) {
        this.context = authorityActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.authority_adapter, parent, false);
        return new myInquireAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof myInquireAdapter) {
            //头像
            String imgUrl = list.get(position).getInOutImgUrl();
            if (imgUrl != null) {
                Uri parse = Uri.parse(imgUrl);
                Log.d("TAG", "onBindViewHolder: " + parse);
                //((myAuthorityAdapter) holder).image.setImageURI(parse);
                Glide.with(context).load(imgUrl).into(((myInquireAdapter) holder).image);

            }

            //姓名
            String name = list.get(position).getPerName();
            if (name != null) {
                ((myInquireAdapter) holder).name.setText("姓名：" + name);
            }
           /* //身份证号
            String cardid = list.get(position).getCardid();
            ((myInquireAdapter) holder).idcad.setText("身份证号：" + cardid);*/
            //((myInquireAdapter) holder).idcad.setVisibility(View.VISIBLE);

            //
            String devname = list.get(position).getDevname();
           // + devname
            ((myInquireAdapter) holder).leavetime.setText(devname);
            ((myInquireAdapter) holder).equipment.setText("设备名称：");

            String devaddress = list.get(position).getDevaddress();
            ((myInquireAdapter) holder).idcad.setText("设备位置：" + devaddress);
            //来访时间
            String createtime = list.get(position).getOccTime();
            if (createtime != null) {
                Long aLong = Long.valueOf(createtime);
                String s = TimeUtil.timeYMDChinese(aLong);
                ((myInquireAdapter) holder).creantime.setText(s);
            }
            ((myInquireAdapter) holder).relative.setVisibility(View.GONE);
          /*  //结束时间
            String leave_time = list.get(position).getLeave_time();
            if (leave_time != null) {
                Long aLong = Long.valueOf(leave_time);
                String s = TimeUtil.timeYMDChinese(aLong);
                ((myInquireAdapter) holder).leavetime.setText("结束时间：" + s);
            }*/
           /* holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visitorid = list.get(position).getVisitorid();
                    Intent intent = new Intent(context, VisitorDetailsActivity.class);
                    intent.putExtra("visitorid",visitorid+"");
                    context.startActivity(intent);
                }
            });*/

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class myInquireAdapter extends RecyclerView.ViewHolder {

        private final TextView name, idcad, creantime, leavetime,equipment,purpose;
        private final ImageView image;
        private final RelativeLayout relative;

        public myInquireAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.aurhortiy_adapter_name);
            idcad = itemView.findViewById(R.id.aurhortiy_adapter_idcad);
            creantime = itemView.findViewById(R.id.aurhortiy_adapter_creantime);
            equipment = itemView.findViewById(R.id.aurhortiy_adapter_equipment);
            leavetime = itemView.findViewById(R.id.aurhortiy_adapter_leavetime);
            image = itemView.findViewById(R.id.aurhortiy_adapter_simple);
            purpose = itemView.findViewById(R.id.aurhortiy_adapter_purpose);
            relative = itemView.findViewById(R.id.aurhortiy_adapter_relative);
        }
    }
}
