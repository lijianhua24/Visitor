package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysd.visitor.R;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.ui.fragment.ModelFragment;
import com.ysd.visitor.ui.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    private TextView settings_model,settings_bind;

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected void initData() {
        settings_model.setEnabled(false);
        settings_bind.setEnabled(true);
        settings_bind.setBackgroundColor(Color.parseColor("#F6F7FB"));
        settings_bind.setTextColor(SettingsActivity.this.getResources().getColor(R.color.colorBlack));
        settings_model.setBackgroundResource(R.drawable.bg_shape);
        settings_model.setTextColor(SettingsActivity.this.getResources().getColor(R.color.colorBai));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment, new ModelFragment())        //.addToBackStack("fname")
                .commit();
        settings_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_model.setEnabled(false);
                settings_bind.setEnabled(true);
                settings_bind.setBackgroundColor(Color.parseColor("#F6F7FB"));
                settings_bind.setTextColor(SettingsActivity.this.getResources().getColor(R.color.colorBlack));
                settings_model.setBackgroundResource(R.drawable.bg_shape);
                settings_model.setTextColor(SettingsActivity.this.getResources().getColor(R.color.colorBai));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.settings_fragment, new ModelFragment())        //.addToBackStack("fname")
                        .commit();
            }
        });
        settings_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_model.setEnabled(true);
                settings_bind.setEnabled(false);
                settings_model.setBackgroundColor(Color.parseColor("#F6F7FB"));
                settings_model.setTextColor(SettingsActivity.this.getResources().getColor(R.color.colorBlack));
                settings_bind.setBackgroundResource(R.drawable.bg_shape);
                settings_bind.setTextColor(SettingsActivity.this.getResources().getColor(R.color.colorBai));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.settings_fragment, new SettingsFragment())        //.addToBackStack("fname")
                        .commit();
            }
        });
    }

    @Override
    protected void initView() {
        settings_model = findViewById(R.id.settings_model);
        settings_bind = findViewById(R.id.settings_bind);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("系统设置");
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_settings;
    }
}
