package com.ysd.visitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.GetModelListBean;

import java.util.List;

public class SttingsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GetDoorDevsBean.DataBean> list;
    private String model;

    public SttingsListAdapter(FragmentActivity activity, List<GetDoorDevsBean.DataBean> data1, String model) {
        this.context = activity;
        this.list = data1;
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.check_layout, parent, false);
        return new mySttingsListAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof mySttingsListAdapter){
                String name = list.get(position).getDevName();
                ((mySttingsListAdapter) holder).name.setText(name);
                if (list.get(position).isCheck()){
                    ((mySttingsListAdapter) holder).check.setBackgroundResource(R.mipmap.check_select);
                }else {
                    ((mySttingsListAdapter) holder).check.setBackgroundResource(R.mipmap.cart_icon_default);
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
    private class mySttingsListAdapter extends RecyclerView.ViewHolder {

        private final TextView name;
        private final ImageView check;

        public mySttingsListAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.modellist_adapter_name);
            check = itemView.findViewById(R.id.modellist_adapter_check);
        }
    }
    private onCheckLisenter onCheckLisenter;
    public void mySttingsListAdapter(onCheckLisenter onCheckLisenter){
        this.onCheckLisenter = onCheckLisenter;
    }
    public interface onCheckLisenter{
        void onCliick(int i);
    }
}
