package com.ysd.visitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.ListBean;
import com.ysd.visitor.ui.activity.RegisterActivity;

import java.util.List;

public class RegisterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<ListBean.DataBean> data1;
    public RegisterAdapter(RegisterActivity registerActivity, List<ListBean.DataBean> data1) {
        this.context = registerActivity;
        this.data1 = data1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.access_adapterlayout, parent, false);
        return new myRegisterAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof myRegisterAdapter){
                String devName = data1.get(position).getDevName();
                ((myRegisterAdapter) holder).access_name.setText(devName);
                ((myRegisterAdapter) holder).access_name.setOnClickListener(new View.OnClickListener() {
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
                    ((myRegisterAdapter) holder).access_name.setBackgroundResource(R.drawable.bg_shape);
                    ((myRegisterAdapter) holder).access_name.setTextColor(context.getResources().getColor(R.color.colorBai));

                }else{
//            否则的话就全白色初始化背景
                    //((MyRechargecoldeAdapter) holder).linear.setBackgroundColor(context.getResources().getColor(R.color.bai));
                    ((myRegisterAdapter) holder).access_name.setBackgroundResource(R.color.colorBai);
                    ((myRegisterAdapter) holder).access_name.setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
            }
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    private class myRegisterAdapter extends RecyclerView.ViewHolder {

        private final TextView access_name;

        public myRegisterAdapter(@NonNull View itemView) {
            super(itemView);
            access_name = itemView.findViewById(R.id.access_name);
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
