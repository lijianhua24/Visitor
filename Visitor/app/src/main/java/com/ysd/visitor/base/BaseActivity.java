package com.ysd.visitor.base;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.ysd.visitor.utlis.ActivityCollectorUtil;
import com.ysd.visitor.utlis.DataCleanManager;
import com.ysd.visitor.utlis.NetUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseActivity声明规范：
 * 1、封装泛型 P extents {@link BasePresenter}
 * 2、implements {@link IBaseView}  ps:方便基类封装 P 层绑定和解绑 view 的操作
 * 3、使用模板方法设计模式，规范子类执行流程
 * 4、封装 {@link ButterKnife} bind 和 unBind
 * 5、封装 P 层 attach 和 dettach
 * 6、{@link #initView()} ()} 方法空实现，必要的时候子类可以重写，进行find特殊控件，和设置特殊监听
 * 7、{@link #initData()} 方法空实现，必要的时候子类可以重写，进行初始化数据操作
 * <p>
 * <p>
 * 子类使用规范：
 * {
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected P mPresenter;
    private Unbinder mUnbinder;
    public static Context context;
    public static String totalCacheSize;
    private boolean isNeedCheck = true;
    private int permissionRequestCode = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ActivityCollectorUtil.addActivity(this);
        this.setContentView(this.provideLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mUnbinder = ButterKnife.bind(this);
        mPresenter = providePresenter();
        context = this;
        //StatusBarUtil.setTranslucentForImageView(this, 0, null);
        //StatusBarUtil.setTransparent(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }

        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initData();





    }

    protected abstract P providePresenter();

    /**
     * 空实现，子类需要用的时候，可以选择重写
     */
    protected abstract void initData();

    /**
     * 空实现，子类需要用的时候，可以选择重写
     */
    protected abstract void initView();


    protected abstract int provideLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        SharedPreferences usersss = getSharedPreferences("usersss", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = usersss.edit();
        edit.clear();
        edit.commit();
    }

    /**
     * 判断是否有网络
     */
    public boolean hasNetwork() {
        return NetUtil.hasNetwork(this);
    }

    /**
     * 无网提醒
     */
    public void showNoNetTip() {
        //ToastUtil.setToast("无网,请检查网络");
    }

    /**
     * 为presenter层提供上下文环境，ps： 非必须
     */
    @Override
    public Context context() {
        return this;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (isNeedCheck) {

            String[] permissions = {
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,

            };
            if (permissions != null) {
                checkPermissions(permissions);
            }
        }
    }

    private void checkPermissions(String[] permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]), permissionRequestCode);
        } else {
            allPermissionsOk(Arrays.asList(permissions));
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRequestCode) {
            if (grantResults.length > 0) {
                if (!verifyPermissions(grantResults)) {
                    permissionsDenied(deniedPermissions(permissions, grantResults));
                    isNeedCheck = false;
                } else {
                    allPermissionsOk(Arrays.asList(permissions));
                }
            }
        }
    }

    private List deniedPermissions(String[] permissions, int[] grantResults) {
        List<String> deniedPermissionList = new ArrayList<>();
        for (int index = 0; index < grantResults.length; index++) {
            if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permissions[index]);
            }

        }
        return deniedPermissionList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 当部分权限被拒绝时会调用该函数，子类Activity可以重写该方法取得回调
     *
     * @param deniedPermissions 被拒绝的权限
     */
    protected void permissionsDenied(List<String> deniedPermissions) {

    }

    /**
     * 当申请完所有权限时会调用该函数，子类Activity可以重写该方法以取得回调
     *
     * @param permissions 所有已经获取到的权限
     */
    protected void allPermissionsOk(List<String> permissions) {

    }

}
