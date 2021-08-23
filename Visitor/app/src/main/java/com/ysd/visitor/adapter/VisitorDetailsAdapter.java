package com.ysd.visitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetVisitorRightsBean;
import com.ysd.visitor.ui.activity.VisitorDetailsActivity;

import java.util.ArrayList;

public class VisitorDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<GetVisitorRightsBean.DataBean> list;
    public VisitorDetailsAdapter(VisitorDetailsActivity visitorDetailsActivity, ArrayList<GetVisitorRightsBean.DataBean> list) {
        this.context = visitorDetailsActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.visitor_details_adapter_layout, parent, false);
        return new myVisitorDetailsAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof myVisitorDetailsAdapter){
                String devname = list.get(position).getDevname();
                if (devname!=null){
                    ((myVisitorDetailsAdapter) holder).name.setText(devname);
                }
                String devcode = list.get(position).getDevcode();
                if (devcode!=null){
                    ((myVisitorDetailsAdapter) holder).code.setText(devcode);
                }
                String loadstatus = list.get(position).getLoadstatus();
                if (loadstatus!=null){
                    if (loadstatus.contains("未下发")){
                        ((myVisitorDetailsAdapter) holder).loadstatus.setText("下发权限");
                    }else {
                        ((myVisitorDetailsAdapter) holder).loadstatus.setText("取消权限");
                    }
                }
                String blackstatus = list.get(position).getBlackstatus();
                if (blackstatus!=null){
                    if (blackstatus.contains("未拉黑")){
                        ((myVisitorDetailsAdapter) holder).blackstatus.setText("拉黑");
                    }else {
                        ((myVisitorDetailsAdapter) holder).blackstatus  .setText("取消拉黑");
                    }
                }
                //点击拉黑
                ((myVisitorDetailsAdapter) holder).blackstatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myClick!=null){
                            myClick.myBlackstatus(position);
                        }
                    }
                });
                ((myVisitorDetailsAdapter) holder).loadstatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myClick!=null){
                            myClick.myLoadstatus(position);
                        }
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class myVisitorDetailsAdapter extends RecyclerView.ViewHolder {

        private final TextView name,code,loadstatus,blackstatus;

        public myVisitorDetailsAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.detalis_adapter_name);
            code = itemView.findViewById(R.id.detalis_adapter_code);
            loadstatus = itemView.findViewById(R.id.detalis_adapter_loadstatus);
            blackstatus = itemView.findViewById(R.id.detalis_adapter_blackstatus);
        }
    }
    private myClick myClick;
    public void myListenter(myClick myClick){
        this.myClick = myClick;
    }
    public interface myClick{
        void myLoadstatus(int i);
        void myBlackstatus(int i);
    }
}
