package com.ysd.visitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetUnionCodeListBean;
import com.ysd.visitor.ui.activity.CouponActivity;
import com.ysd.visitor.ui.activity.UseCouponActivity;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GetUnionCodeListBean.DataBean> list;
    public CouponAdapter(CouponActivity couponActivity, List<GetUnionCodeListBean.DataBean> data1) {
        this.context = couponActivity;
        this.list = data1;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.coupon_adapter_layout, parent, false);
        return new myCouponAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof myCouponAdapter){
            String type = list.get(position).getType();
            int limit = list.get(position).getLimit();
            if (type.contains("3")){
                ((myCouponAdapter) holder).name.setText("减免券（小时）");
                ((myCouponAdapter) holder).type.setText("消费既使用减免"+limit+"时");
            }else if (type.contains("5")){
                ((myCouponAdapter) holder).name.setText("减免券（金额）");
                ((myCouponAdapter) holder).type.setText("消费既使用减免"+limit+"元");
            }else {
                ((myCouponAdapter) holder).name.setText("全免券");
                ((myCouponAdapter) holder).type.setText("消费既使用全部减免");
            }

            String expiretime = list.get(position).getExpiretime();
            String[] ts = expiretime.split("T");
            ((myCouponAdapter) holder).time.setText(ts[0]+" "+ts[1]);
            int sheets = list.get(position).getSheets();
            ((myCouponAdapter) holder).sum.setText("总张数："+sheets+"");
            //3 小时减免券  5 金额减免券   其他全免券
            ((myCouponAdapter) holder).coupon_adapter_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String unionCode = list.get(position).getUnionCode();
                    Intent intent = new Intent(context, UseCouponActivity.class);
                    intent.putExtra("unionCode",unionCode);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class myCouponAdapter extends RecyclerView.ViewHolder {

        private final Button coupon_adapter_bt;
        private final TextView name,type,time,sum;

        public myCouponAdapter(@NonNull View itemView) {
            super(itemView);
            coupon_adapter_bt = itemView.findViewById(R.id.coupon_adapter_bt);
            name = itemView.findViewById(R.id.coupon_adapter_name);
            type = itemView.findViewById(R.id.coupon_adapter_type);
            time = itemView.findViewById(R.id.coupon_adapter_time);
            sum = itemView.findViewById(R.id.coupon_adapter_sum);
        }
    }
}
