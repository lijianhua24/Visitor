package com.ysd.visitor.ui.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.display.DisplayManager;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.huashi.otg.sdk.HsOtgApi;
import com.huashi.otg.sdk.HSIDCardInfo;
import com.huashi.otg.sdk.HandlerMsg;
import com.king.zxing.util.CodeUtils;
import com.megvii.facepp.multi.sdk.FaceDetectApi;
import com.megvii.facepp.multi.sdk.FacePPImage;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.CheckedModelListAdapter;
import com.ysd.visitor.adapter.ListProAdapter;
import com.ysd.visitor.adapter.RegisterAdapter;
import com.ysd.visitor.adapter.TimeAdapter;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.bean.CheckedModelListBean;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.ListBean;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.RegisterPresenter;
import com.ysd.visitor.utlis.SecondScreen;
import com.ysd.visitor.utlis.TimeUtil;
import com.ysd.visitor.utlis.camera.Camera3Listener;
import com.ysd.visitor.utlis.camera.CameraHelper;
import com.ysd.visitor.utlis.camera.CameraListener;
import com.ysd.visitor.utlis.util.ImageTransformUtils;
import com.ysd.visitor.widget.RoundTextureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.facebook.imagepipeline.nativecode.NativeJpegTranscoder.TAG;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements ViewTreeObserver.OnGlobalLayoutListener, CameraListener, SeekBar.OnSeekBarChangeListener, HomeContract.getSubmitVisitorInfo.IView {

    private RoundTextureView register_round;
    private ImageView register_image;
    private Button register_bt, register_bb, register_read, register_finsh;
    private CameraHelper cameraHelper;
    private Camera.Size previewSize;
    String filepath = "";
    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
    private String name;
    private String idcard;
    private String sex;
    private ArrayList<String> list_pro = new ArrayList<>();
    private ArrayList<String> list_time = new ArrayList<>();
    DisplayManager mDisplayManager;//屏幕管理类
    Display[] displays;//屏幕数组
    Handler h = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == HandlerMsg.READ_SUCCESS) {
                Toast.makeText(RegisterActivity.this, "读卡成功", Toast.LENGTH_SHORT).show();
                HSIDCardInfo ic = (HSIDCardInfo) msg.obj;
                byte[] fp = new byte[1024];
                fp = ic.getFpDate();
                String m_FristPFInfo = "";
                String m_SecondPFInfo = "";

                if (fp[4] == (byte) 0x01) {
                    m_FristPFInfo = String.format("指纹  信息：第一枚指纹注册成功。指位：%s。指纹质量：%d \n", GetFPcode(fp[5]), fp[6]);
                } else {
                    m_FristPFInfo = "身份证无指纹 \n";
                }
                if (fp[512 + 4] == (byte) 0x01) {
                    m_SecondPFInfo = String.format("指纹  信息：第二枚指纹注册成功。指位：%s。指纹质量：%d \n", GetFPcode(fp[512 + 5]),
                            fp[512 + 6]);
                } else {
                    m_SecondPFInfo = "身份证无指纹 \n";
                }
                if (ic.getcertType() == " ") {
                    name = ic.getPeopleName();
                    idcard = ic.getIDCard();
                    sex = ic.getSex();
                    register_name.setText(ic.getPeopleName());
                    register_carid.setText(ic.getIDCard());
                    Log.d("TAG", "handleMessage: " + "证件类型：身份证\n" + "姓名："
                            + ic.getPeopleName() + "\n" + "性别：" + ic.getSex()
                            + "\n" + "民族：" + ic.getPeople() + "\n" + "出生日期："
                            + df.format(ic.getBirthDay()) + "\n" + "地址："
                            + ic.getAddr() + "\n" + "身份号码：" + ic.getIDCard()
                            + "\n" + "签发机关：" + ic.getDepartment() + "\n"
                            + "有效期限：" + ic.getStrartDate() + "-"
                            + ic.getEndDate() + "\n" + m_FristPFInfo + "\n"
                            + m_SecondPFInfo);
                } else {
                    if (ic.getcertType() == "J") {

                        name = ic.getPeopleName();
                        idcard = ic.getIDCard();
                        sex = ic.getSex();
                        register_name.setText(ic.getPeopleName());
                        register_carid.setText(ic.getIDCard());
                        Log.d("TAG", "handleMessage: " + "证件类型：港澳台居住证（J）\n"
                                + "姓名：" + ic.getPeopleName() + "\n" + "性别："
                                + ic.getSex() + "\n"
                                + "签发次数：" + ic.getissuesNum() + "\n"
                                + "通行证号码：" + ic.getPassCheckID() + "\n"
                                + "出生日期：" + df.format(ic.getBirthDay())
                                + "\n" + "地址：" + ic.getAddr() + "\n" + "身份号码："
                                + ic.getIDCard() + "\n" + "签发机关："
                                + ic.getDepartment() + "\n" + "有效期限："
                                + ic.getStrartDate() + "-" + ic.getEndDate() + "\n"
                                + m_FristPFInfo + "\n" + m_SecondPFInfo);
                    } else {
                        if (ic.getcertType() == "I") {
                            name = ic.getPeopleName();
                            idcard = ic.getIDCard();
                            sex = ic.getSex();
                            register_name.setText(ic.getPeopleName());
                            register_carid.setText(ic.getIDCard());
                            Log.d("TAG", "handleMessage: " + "证件类型：外国人永久居留证（I）\n"
                                    + "英文名称：" + ic.getPeopleName() + "\n"
                                    + "中文名称：" + ic.getstrChineseName() + "\n"
                                    + "性别：" + ic.getSex() + "\n"
                                    + "永久居留证号：" + ic.getIDCard() + "\n"
                                    + "国籍：" + ic.getstrNationCode() + "\n"
                                    + "出生日期：" + df.format(ic.getBirthDay())
                                    + "\n" + "证件版本号：" + ic.getstrCertVer() + "\n"
                                    + "申请受理机关：" + ic.getDepartment() + "\n"
                                    + "有效期限：" + ic.getStrartDate() + "-" + ic.getEndDate() + "\n"
                                    + m_FristPFInfo + "\n" + m_SecondPFInfo);
                        }
                    }

                }
                // Test.test("/mnt/sdcard/test.txt4", ic.toString());
                try {
                    byte[] getwltdata = ic.getwltdata();
                    Log.d("TAG", "handleMessage: " + ic.getwltdata());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(getwltdata, 0, getwltdata.length);
                    register_image.setImageBitmap(bitmap);
                    // 照片解码
                    int ret = api.Unpack(filepath, ic.getwltdata());
                    //Test.test("/mnt/sdcard/test3.txt", "解码中");
                    if (ret != 0) {// 读卡失败
                        return;
                    }
                    FileInputStream fis = new FileInputStream(filepath + "/zp.bmp");
                    bmp = BitmapFactory.decodeStream(fis);
                    doDetect(bmp, 0);
                    register_image.setImageBitmap(bmp);
                    fis.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "头像不存在！", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    Toast.makeText(getApplicationContext(), "头像读取错误", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "头像解码失败", Toast.LENGTH_SHORT).show();
                }

            } else if (msg.what == 2021) {
                register_image.setImageBitmap(bitmap1);
            }
        }
    };
    private HsOtgApi api;
    private Bitmap bmp;
    FaceDetectApi.Face face1 = null;
    FaceDetectApi.Face face2 = null;
    private long startTimes = 0;
    private int imageOneResult = -1; // 1 ok 2 无人脸 3 多人脸
    private int imageTwoResult = -1;
    private long detectCostTimes1 = 0;
    private long detectCostTimes2 = 0;
    private long getFeatureCostTimes1 = 0;
    private long getFeatureCostTimes2 = 0;
    private long compareCostTimes = 0;
    private TextView register_text;
    private DecimalFormat decimalFormat;
    private boolean isCheck = true;
    private Bitmap bitmap1;
    private TextView register_time, register_efficient_tv, register_purpose_text;
    private RelativeLayout register_purpose, register_efficient;
    private EditText register_efficient_edit, register_efficient_phone, register_name, register_carid;
    private String itemType;
    private String itemTime;
    private String token;
    private boolean check = false;
    private RecyclerView register_recy;
    private CheckedModelListAdapter checkedModelListAdapter;
    private LinearLayout register_efficient_linear, register_contrast;
    private String value;
    private String data_name;
    private String devCode;
    private Dialog dialog;
    private int ret;
    private RecyclerView register_efficient_recy, register_purpose_recy;
    private boolean mArtificial = false;

    @Override
    protected RegisterPresenter providePresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initData() {
        initCamera();
      /*  mDisplayManager = (DisplayManager) RegisterActivity.this.getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays(); //得到显示器数组
        SecondScreen mPresentation = new SecondScreen(getApplicationContext(), displays[1], R.layout.camera_layout);//displays[1]是副屏
        mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mPresentation.show();*/
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devcode", token);
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getCheckedModelListPresenter(requestBody);
        register_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    if (api.Authenticate(200, 200) != 1) {
                        Toast.makeText(RegisterActivity.this, "卡认证失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    HSIDCardInfo ici = new HSIDCardInfo();
                    if (api.ReadCard(ici, 200, 1300) == 1) {
                        Message msg = Message.obtain();
                        msg.obj = ici;
                        msg.what = HandlerMsg.READ_SUCCESS;
                        h.sendMessage(msg);
                    }
                } else {
                    inID();
                }
            }
        });
        /*register_purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onConstellationPicker(v);
                myPurpose();
            }
        });*/
        myPurpose();
        myTime();

        register_bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemTime != null) {
                    String[] split = itemTime.split("分钟");
                    itemTime = split[0];
                }
                if (check) {
                    String phone = register_efficient_phone.getText().toString();
                    if (sex.contains("name")) {
                        sex = "1";
                    } else {
                        sex = "2";
                    }
                    if (data_name.contains("二维码")) {
                        if (itemType != null) {
                            if (itemTime != null) {
                                if (!phone.isEmpty()) {
                                    String s1 = bitmapToBase64(bitmap1);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("devcode", token);
                                    hashMap.put("name", name);
                                    hashMap.put("idcard", idcard);
                                    hashMap.put("sex", sex);
                                    hashMap.put("phone", phone);
                                    hashMap.put("type", value);
                                    hashMap.put("purpose", itemType);
                                    hashMap.put("limit", itemTime);
                                    hashMap.put("photo", "data:image/jpeg;base64," + s1);
                                    String s = new Gson().toJson(hashMap);
                                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                                    mPresenter.getSubmitVisitorInfoPresenter(requestBody);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "请选择权限有效期", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "请选择来访目的", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (itemType != null) {
                            if (itemTime != null) {
                                String s1 = bitmapToBase64(bitmap1);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("devcode", token);
                                hashMap.put("name", name);
                                hashMap.put("idcard", idcard);
                                hashMap.put("sex", sex);
                                //hashMap.put("phone", phone);
                                hashMap.put("type", value);
                                hashMap.put("purpose", itemType);
                                hashMap.put("limit", itemTime);
                                hashMap.put("photo", "data:image/jpeg;base64," + s1);
                                String s = new Gson().toJson(hashMap);
                                RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                                mPresenter.getSubmitVisitorInfoPresenter(requestBody);
                            } else {
                                Toast.makeText(RegisterActivity.this, "请选择权限有效期", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "请选择来访目的", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else if (data_name.contains("人工模式")) {
                    String name = register_name.getText().toString();
                    String phone = register_efficient_phone.getText().toString();
                    String carid = register_carid.getText().toString();
                    if (itemType != null) {
                        if (itemTime != null) {
                            if (bitmap1 != null) {
                                if (name != null) {
                                    if (phone.length() == 11) {
                                        if (carid != null) {
                                            Toast.makeText(RegisterActivity.this, "12", Toast.LENGTH_SHORT).show();
                                            String s1 = bitmapToBase64(bitmap1);
                                            HashMap<String, String> hashMap = new HashMap<>();
                                            hashMap.put("devcode", token);
                                            hashMap.put("name", name);
                                            hashMap.put("idcard", carid);
                                            hashMap.put("phone", phone);
                                            hashMap.put("type", value);
                                            hashMap.put("purpose", itemType);
                                            hashMap.put("limit", itemTime);
                                            hashMap.put("photo", "data:image/jpeg;base64," + s1);
                                            String s = new Gson().toJson(hashMap);
                                            Log.d(TAG, "itemType: " + itemType);
                                            Log.d(TAG, "itemTime: " + itemTime);
                                            Log.d(TAG, "idcard: " + carid);
                                            Log.d(TAG, "phone: " + phone);
                                            Log.d(TAG, "json: " + s);
                                            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                                            mPresenter.getSubmitVisitorInfosPresenter(requestBody);
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "请输入身份证号", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "手机号不正确，请重新输入 ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "请输入名字", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "请采集访客留存", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "请选择权限有效期", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "请选择来访目的", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "请完成人脸采集", Toast.LENGTH_SHORT).show();
                }


            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        register_recy.setLayoutManager(linearLayoutManager);

        list_pro.add("外卖");
        list_pro.add("面试");
        list_pro.add("访客");
        list_pro.add("找人");
        list_pro.add("保洁");
        list_pro.add("客户");
        list_time.add("10分钟");
        list_time.add("20分钟");
        list_time.add("30分钟");
        list_time.add("60分钟");
        list_time.add("80分钟");
        list_time.add("100分钟");
        list_time.add("120分钟");
    }

    @Override
    protected void initView() {
        token = App.sharedPreferences.getString("token", null);
        decimalFormat = new DecimalFormat("0.00");
        filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wltlib";// 授权目录
        register_image = findViewById(R.id.register_image);
        register_bt = findViewById(R.id.register_bt);
        register_bb = findViewById(R.id.register_bb);
        register_name = findViewById(R.id.register_name);
        register_carid = findViewById(R.id.register_carid);
        register_purpose = findViewById(R.id.register_purpose);
        register_time = findViewById(R.id.register_time);
        register_efficient_tv = findViewById(R.id.register_efficient_tv);
        register_purpose_text = findViewById(R.id.register_purpose_text);
        register_efficient_edit = findViewById(R.id.register_efficient_edit);
        register_recy = findViewById(R.id.register_recy);
        register_efficient = findViewById(R.id.register_efficient);
        register_round = findViewById(R.id.register_round);
        register_efficient_linear = findViewById(R.id.register_efficient_linear);
        register_efficient_phone = findViewById(R.id.register_efficient_phone);
        register_contrast = findViewById(R.id.register_contrast);
        register_read = findViewById(R.id.register_read);
        register_efficient_recy = findViewById(R.id.register_efficient_recy);
        register_purpose_recy = findViewById(R.id.register_purpose_recy);
        register_finsh = findViewById(R.id.register_finsh);

        register_finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inID();
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraHelper.captureImage();
                if (cameraHelper != null) {
                    cameraHelper.setCameraListener(new Camera3Listener() {
                        @Override
                        public void onCaptured(Bitmap bitmap) {
                            String time = TimeUtil.getTime();
                            register_time.setText(time);
                            bitmap1 = bitmap;
                            if (mArtificial) {

                            } else {
                                doDetect(bitmap, 1);
                            }

                            //register_image.setImageBitmap(bitmap1);

                            cameraHelper.start();
                            if (cameraHelper != null) {
                                cameraHelper.release();
                            }
                            initCamera();
                        }
                    });
                }

            }
        });
        register_round.setRadius(130);
        register_round.turnRound();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_register;

    }

    private void initCamera() {
        cameraHelper = new CameraHelper.Builder()
                .cameraListener(this)
                .specificCameraId(1)
                .previewOn(register_round)
                .previewViewSize(new Point(register_round.getLayoutParams().width, register_round.getLayoutParams().height))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .build();
        cameraHelper.start();
    }

    private void inID() {

        copy(RegisterActivity.this, "base.dat", "base.dat", filepath);
        copy(RegisterActivity.this, "license.lic", "license.lic", filepath);
        api = new HsOtgApi(h, RegisterActivity.this);

        for (int i = 0; i < 20; i++) {
            ret = api.init();

            // 因为第一次需要点击授权，所以第一次点击时候的返回是-1所以我利用了广播接受到授权后用handler发送消息

            if (ret == 1) {
                Toast.makeText(this, "连接成功", Toast.LENGTH_SHORT).show();
                // new Thread(new CPUThread()).start();
                isCheck = true;
                break;
            } else {
                //Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
                ret = api.init();
                isCheck = false;
            }
        }

    }


    @Override
    public void onSubmitVisitorInfoSuccess(Object data) {
        bmp = null;
        bitmap1 = null;
        if (data instanceof SubmitVisitorInfoBean) {
            if (((SubmitVisitorInfoBean) data).getCode() == 0) {
                Toast.makeText(this, "登记成功", Toast.LENGTH_SHORT).show();
                if (data_name.contains("二维码")) {
                    Object data1 = ((SubmitVisitorInfoBean) data).getData();
                    String code = String.valueOf(data1);
                    mDisplayManager = (DisplayManager) RegisterActivity.this.getSystemService(Context.DISPLAY_SERVICE);
                    displays = mDisplayManager.getDisplays(); //得到显示器数组
                    SecondScreen mPresentations = new SecondScreen(getApplicationContext(), displays[1], R.layout.qrcode_layout);//displays[1]是副屏
                    mPresentations.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    mPresentations.show();
                    ImageView qrcode_image = mPresentations.findViewById(R.id.qrcode_image);
                    Bitmap qrCode = CodeUtils.createQRCode(code, 300, null);
                    qrcode_image.setImageBitmap(qrCode);
                }
            } else {
                Toast.makeText(this, "" + ((SubmitVisitorInfoBean) data).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSubmitVisitorInfoFailure(Throwable e) {

    }

    @Override
    public void onSubmitVisitorInfosSuccess(Object data) {
        bmp = null;
        bitmap1 = null;
        check = false;
        if (data_name.contains("人工模式")) {
            ListBean listBean = (ListBean) data;
            List<ListBean.DataBean> list = listBean.getData();
            dialog = new Dialog(RegisterActivity.this, R.style.DialogTheme);
            View inflate = View.inflate(RegisterActivity.this, R.layout.access_layout, null);
            RecyclerView recyclerView = inflate.findViewById(R.id.access_recy);
            Button button = inflate.findViewById(R.id.access_bt);
            dialog.setContentView(inflate);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setLayout(1500, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            devCode = list.get(0).getDevCode();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(RegisterActivity.this, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            RegisterAdapter registerAdapter = new RegisterAdapter(RegisterActivity.this, list);
            recyclerView.setAdapter(registerAdapter);
            registerAdapter.getChange(new RegisterAdapter.setListnter() {
                @Override
                public void getListenter(int i) {
                    registerAdapter.setmPosition(i);
                    devCode = list.get(i).getDevCode();
                    registerAdapter.notifyDataSetChanged();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = register_efficient_phone.getText().toString();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("devcode", devCode);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getOpenDoorPresenter(requestBody);
                }
            });
        }
    }

    @Override
    public void onSubmitVisitorInfosFailure(Throwable e) {

    }

    @Override
    public void onCheckedModelListSuccess(CheckedModelListBean data) {
        if (data.getCode() == 0) {
            List<CheckedModelListBean.DataBean> data1 = data.getData();
            data_name = data1.get(0).getName();
            checkedModelListAdapter = new CheckedModelListAdapter(this, data1);
            register_recy.setAdapter(checkedModelListAdapter);
            checkedModelListAdapter.getChange(new CheckedModelListAdapter.setListnter() {
                @Override
                public void getListenter(int i) {
                    checkedModelListAdapter.setmPosition(i);
                    checkedModelListAdapter.notifyDataSetChanged();
                    data_name = data1.get(i).getName();
                    value = data1.get(i).getValue();
                    if (data_name.contains("短信模式")) {
                        register_contrast.setVisibility(View.VISIBLE);
                        register_efficient_linear.setVisibility(View.VISIBLE);
                        register_name.setEnabled(false);
                        register_carid.setEnabled(false);
                        register_carid.setHint("请读取身份证");
                        register_name.setHint("请读取身份证");
                        mArtificial = false;
                    } else if (data_name.contains("人工模式")) {
                        register_efficient_linear.setVisibility(View.VISIBLE);
                        register_name.setEnabled(true);
                        register_carid.setEnabled(true);
                        register_carid.setHint("请输入身份证号");
                        register_name.setHint("请输入姓名");
                        register_contrast.setVisibility(View.GONE);
                        mArtificial = true;
                    } else {
                        register_name.setEnabled(false);
                        register_carid.setEnabled(false);
                        register_carid.setHint("请读取身份证");
                        register_name.setHint("请读取身份证");
                        register_contrast.setVisibility(View.VISIBLE);
                        register_efficient_linear.setVisibility(View.GONE);
                        mArtificial = false;
                    }
                }
            });
        }
    }

    @Override
    public void onCheckedModelListFailure(Throwable e) {

    }

    @Override
    public void onOpenDoorSuccess(VisitorBanedBean data) {
        Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            dialog.dismiss();
        }
    }

    @Override
    public void onOpenDoorFailure(Throwable e) {

    }

    @Override
    public void onGlobalLayout() {
        register_round.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        ViewGroup.LayoutParams layoutParams = register_round.getLayoutParams();
        int sideLength = Math.min(register_round.getWidth(), register_round.getHeight()) * 3 / 4;
        layoutParams.width = sideLength;
        layoutParams.height = sideLength;
        register_round.setLayoutParams(layoutParams);
        register_round.turnRound();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {

        previewSize = camera.getParameters().getPreviewSize();
        //在相机打开时，添加右上角的view用于显示原始数据和预览数据
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //将预览控件和预览尺寸比例保持一致，避免拉伸
                {
                    ViewGroup.LayoutParams layoutParams = register_round.getLayoutParams();
                    //横屏
                    if (displayOrientation % 180 == 0) {
                        layoutParams.height = layoutParams.width * previewSize.height / previewSize.width;
                    }
                    //竖屏
                    else {
                        layoutParams.height = layoutParams.width * previewSize.width / previewSize.height;
                    }
                    register_round.setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Override
    public void onPreview(byte[] data, Camera camera) {

    }

    @Override
    public void onCameraClosed() {

    }

    @Override
    public void onCameraError(Exception e) {

    }

    @Override
    public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {

    }

    /*public class CPUThread extends Thread {
        public CPUThread() {
            super();
        }

        @Override
        public void run() {
            super.run();
            HSIDCardInfo ici;
            Message msg;
            while (false) {
                if (api != null) {
                    if (api == null) {
                        api = new HsOtgApi(h, RegisterActivity.this);
                    }
                    int authenticate = api.Authenticate(200, 200);
                    if (authenticate != 1) {
                        msg = Message.obtain();
                        msg.what = HandlerMsg.READ_ERROR;
                        h.sendMessage(msg);
                    } else {
                        ici = new HSIDCardInfo();
                        int i = api.ReadCard(ici, 200, 1300);
                        if (i == 1) {
                            msg = Message.obtain();
                            msg.obj = ici;
                            msg.what = HandlerMsg.READ_SUCCESS;
                            h.sendMessage(msg);
                        }
                    }
                }

            }

        }
    }*/


    private void copy(Context context, String fileName, String saveName,
                      String savePath) {
        File path = new File(savePath);
        if (!path.exists()) {
            path.mkdir();
        }

        try {
            File e = new File(savePath + "/" + saveName);
            if (e.exists() && e.length() > 0L) {
                Log.i("LU", saveName + "存在了");
                return;
            }

            FileOutputStream fos = new FileOutputStream(e);
            InputStream inputStream = context.getResources().getAssets()
                    .open(fileName);
            byte[] buf = new byte[1024];
            boolean len = false;

            int len1;
            while ((len1 = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, len1);
            }

            fos.close();
            inputStream.close();
        } catch (Exception var11) {
            Log.i("LU", "IO异常");
        }

    }

    /**
     * 指纹 指位代码
     *
     * @param FPcode
     * @return
     */
    String GetFPcode(int FPcode) {
        switch (FPcode) {
            case 11:
                return "右手拇指";
            case 12:
                return "右手食指";
            case 13:
                return "右手中指";
            case 14:
                return "右手环指";
            case 15:
                return "右手小指";
            case 16:
                return "左手拇指";
            case 17:
                return "左手食指";
            case 18:
                return "左手中指";
            case 19:
                return "左手环指";
            case 20:
                return "左手小指";
            case 97:
                return "右手不确定指位";
            case 98:
                return "左手不确定指位";
            case 99:
                return "其他不确定指位";
            default:
                return "未知";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (api == null) {
            return;
        }

        api.unInit();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private void doDetect(Bitmap bitmap, int index) {
        if (index == 0) {
            face1 = null;
        } else {
            face2 = null;
        }
        byte[] imageBgr = ImageTransformUtils.bitmap2BGR(bitmap);
        FacePPImage facePPImage = new FacePPImage.Builder()
                .setData(imageBgr)
                .setWidth(bitmap.getWidth())
                .setHeight(bitmap.getHeight())
                .setMode(FacePPImage.IMAGE_MODE_BGR)
                .setRotation(FacePPImage.FACE_UP).build();
        startTimes = System.currentTimeMillis();
        FaceDetectApi.Face[] faces = FaceDetectApi.getInstance().detectFace(facePPImage);
        Toast.makeText(this, "" + faces.length, Toast.LENGTH_SHORT).show();
        if (faces.length == 0) {
            if (index == 0) {
                imageOneResult = 2;
            } else {
                imageTwoResult = 2;
            }
            updateFailureInfo();
        } else if (faces.length > 1) {
            if (index == 0) {
                imageOneResult = 3;
            } else {
                imageTwoResult = 3;
            }
            updateFailureInfo();
        } else {
            if (index == 0) {
                detectCostTimes1 = System.currentTimeMillis() - startTimes;
                face1 = faces[0];
                startTimes = System.currentTimeMillis();
                FaceDetectApi.getInstance().getExtractFeature(face1);
                getFeatureCostTimes1 = System.currentTimeMillis() - startTimes;
                imageOneResult = 1;
            } else {
                detectCostTimes2 = System.currentTimeMillis() - startTimes;
                face2 = faces[0];
                startTimes = System.currentTimeMillis();
                FaceDetectApi.getInstance().getExtractFeature(face2);
                getFeatureCostTimes2 = System.currentTimeMillis() - startTimes;
                imageTwoResult = 1;
            }
            doCompare();
        }

    }

    private void updateFailureInfo() {
        StringBuilder sb = new StringBuilder();
        if (imageOneResult == 2) {
            sb.append("图片一中未检测到人脸");
        } else if (imageOneResult == 3) {
            sb.append("图片一中检测到多张人脸");
        }

        if (imageTwoResult == 2) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("图片二中未检测到人脸");
        } else if (imageTwoResult == 3) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("图片二中检测到多张人脸");
        }

    }

    private void doCompare() {
        if (face1 != null && face2 != null) {
            startTimes = System.currentTimeMillis();
            double score = FaceDetectApi.getInstance().faceCompare(face1, face2);
            Log.d("TAG", "doCompare: " + score);
            compareCostTimes = System.currentTimeMillis() - startTimes;
            if (score > 80.00) {
                check = true;
                Toast.makeText(this, "核验通过", Toast.LENGTH_SHORT).show();
            } else {
                check = false;
                Toast.makeText(this, "核验失败", Toast.LENGTH_SHORT).show();
            }

            //tvComapareScore.setText(df.format(score));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("图片一检测耗时(ms)：").append(detectCostTimes1).append("\n");
            stringBuilder.append("图片一抽取特征耗时(ms)：").append(getFeatureCostTimes1).append("\n");
            stringBuilder.append("图片二检测耗时(ms)：").append(detectCostTimes2).append("\n");
            stringBuilder.append("图片二抽取特征耗时(ms)：").append(getFeatureCostTimes2).append("\n");
            stringBuilder.append("比对总耗时(ms)：").append(compareCostTimes + detectCostTimes1 + detectCostTimes2 + getFeatureCostTimes1 + getFeatureCostTimes2);
            //tvDetectTips.setText(stringBuilder.toString());
        } else {
            updateFailureInfo();
        }
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void myPurpose() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RegisterActivity.this, RecyclerView.HORIZONTAL, false);
        register_purpose_recy.setLayoutManager(linearLayoutManager);
        TimeAdapter timeAdapter = new TimeAdapter(RegisterActivity.this, list_pro);
        if (itemType != null) {
            for (int i = 0; i < list_pro.size(); i++) {
                if (list_pro.get(i).contains(itemType)) {
                    timeAdapter.setmPosition(i);
                    timeAdapter.notifyDataSetChanged();
                }
            }
        }
        register_purpose_recy.setAdapter(timeAdapter);
        timeAdapter.getChange(new TimeAdapter.setListnter() {
            @Override
            public void getListenter(int i) {
                timeAdapter.setmPosition(i);
                timeAdapter.notifyDataSetChanged();
                itemType = list_pro.get(i);
                register_purpose_text.setText(itemType);
                register_purpose_text.setTextColor(RegisterActivity.this.getResources().getColor(R.color.colorBlack));

            }
        });

    }

    private void myTime() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RegisterActivity.this, RecyclerView.HORIZONTAL, false);
        register_efficient_recy.setLayoutManager(linearLayoutManager);
        ListProAdapter timeAdapter = new ListProAdapter(RegisterActivity.this, list_time);
        if (itemTime != null) {
            for (int i = 0; i < list_time.size(); i++) {
                if (list_time.get(i).contains(itemTime)) {
                    timeAdapter.setmPosition(i);
                    timeAdapter.notifyDataSetChanged();
                }
            }
        }
        register_efficient_recy.setAdapter(timeAdapter);
        timeAdapter.getChange(new ListProAdapter.setListnter() {
            @Override
            public void getListenter(int i) {
                timeAdapter.setmPosition(i);
                timeAdapter.notifyDataSetChanged();
                itemTime = list_time.get(i);
                register_efficient_tv.setText(itemTime);
                register_efficient_tv.setTextColor(RegisterActivity.this.getResources().getColor(R.color.colorBlack));

            }
        });
    }
}
