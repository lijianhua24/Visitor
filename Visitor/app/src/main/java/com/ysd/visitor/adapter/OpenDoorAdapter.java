package com.ysd.visitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysd.visitor.R;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.GetVisitorRightsBean;
import com.ysd.visitor.ui.activity.OpenDoorActivity;
import com.ysd.visitor.ui.activity.VisitorDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class OpenDoorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GetDoorDevsBean.DataBean> list;
    public OpenDoorAdapter(OpenDoorActivity openDoorActivity, List<GetDoorDevsBean.DataBean> data1) {
        this.context = openDoorActivity;
        this.list = data1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.open_door_adapter_layout, parent, false);
        return new myOpenDoorAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof myOpenDoorAdapter){
                String devState = list.get(position).getDevState();
                if (devState.contains("1")){
                    String devName = list.get(position).getDevName();
                    ((myOpenDoorAdapter) holder).name.setText(devName);
                }
                ((myOpenDoorAdapter) holder).loadstatus.setOnClickListener(new View.OnClickListener() {
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
    private class myOpenDoorAdapter extends RecyclerView.ViewHolder {

        private final TextView name,loadstatus;

        public myOpenDoorAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.open_adapter_name);
            loadstatus = itemView.findViewById(R.id.open_adapter_loadstatus);
        }
    }
    private myClick myClick;
    public void myListenter(myClick myClick){
        this.myClick = myClick;
    }
    public interface myClick{
        void myLoadstatus(int i);
    }
}
