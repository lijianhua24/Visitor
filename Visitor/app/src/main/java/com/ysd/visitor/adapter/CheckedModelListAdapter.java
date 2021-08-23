package com.ysd.visitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.CheckedModelListBean;
import com.ysd.visitor.ui.activity.RegisterActivity;

import java.util.List;

public class CheckedModelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<CheckedModelListBean.DataBean> list;
    public CheckedModelListAdapter(RegisterActivity registerActivity, List<CheckedModelListBean.DataBean> data1) {
        this.context = registerActivity;
        this.list = data1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.checked_list_layout, parent, false);
        return new myCheckedModelListAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof myCheckedModelListAdapter){
            String name = list.get(position).getName();
            ((myCheckedModelListAdapter) holder).name.setText(name);
            ((myCheckedModelListAdapter) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setListnter!=null){
                        setListnter.getListenter(position);
                    }
                }
            });
            if (position == getmPosition()) {
                //((MyRechargecoldeAdapter) holder).linear.setBackgroundResource(R.drawable.biankuang_xuanzhong);
                // ((MyRechargecoldeAdapter) holder).linear.setBackgroundColor(context.getResources().getColor(R.color.qianlan));
                ((myCheckedModelListAdapter) holder).name.setBackgroundResource(R.drawable.bg_shape);
                ((myCheckedModelListAdapter) holder).name.setTextColor(context.getResources().getColor(R.color.colorBai));

            }else{
//            否则的话就全白色初始化背景
                //((MyRechargecoldeAdapter) holder).linear.setBackgroundColor(context.getResources().getColor(R.color.bai));
                ((myCheckedModelListAdapter) holder).name.setBackgroundResource(R.color.colorBai);
                ((myCheckedModelListAdapter) holder).name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class myCheckedModelListAdapter extends RecyclerView.ViewHolder {

        private final TextView name;

        public myCheckedModelListAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cheecked_name);
        }
    }
    private  int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
    private setListnter setListnter;
    public void getChange(setListnter setListnter){
        this.setListnter = setListnter;
    }
    public interface setListnter{
        void getListenter(int i);
    }
}
