package com.ysd.visitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetModelListBean;
import com.ysd.visitor.bean.VisitorMachineBean;

import java.util.List;

public class ModelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GetModelListBean.DataBean> list;
    private String model;

    public ModelListAdapter(FragmentActivity activity, List<GetModelListBean.DataBean> data1, String model) {
        this.context = activity;
        this.list = data1;
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.check_layout, parent, false);
        return new myModelListAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof myModelListAdapter){
                String name = list.get(position).getName();
                ((myModelListAdapter) holder).name.setText(name);
                if (list.get(position).isCheck()){
                    ((myModelListAdapter) holder).check.setBackgroundResource(R.mipmap.check_select);
                }else {
                    ((myModelListAdapter) holder).check.setBackgroundResource(R.mipmap.cart_icon_default);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.get(position).setCheck(!list.get(position).isCheck());
                        if (onCheckLisenter!=null){
                            onCheckLisenter.onCliick(position);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class myModelListAdapter extends RecyclerView.ViewHolder {

        private final TextView name;
        private final ImageView check;

        public myModelListAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.modellist_adapter_name);
            check = itemView.findViewById(R.id.modellist_adapter_check);
        }
    }
    private onCheckLisenter onCheckLisenter;
    public void myCheckLisenter(onCheckLisenter onCheckLisenter){
        this.onCheckLisenter = onCheckLisenter;
    }
    public interface onCheckLisenter{
        void onCliick(int i);
    }
}
