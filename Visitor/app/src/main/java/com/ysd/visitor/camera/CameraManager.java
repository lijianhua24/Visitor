package com.ysd.visitor.camera;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import com.ysd.visitor.camera.CameraFactory;
import com.ysd.visitor.camera.CameraWrapper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CameraManager {
    public CameraWrapper mCameraWrapper;
    private int mCamraId = 0;  //1是前置摄像头   0是后置摄像头
    public int cameraWidth = 640;//1280;//
    public int cameraHeight = 480;//720;//
    public int Angle = 0;
    private WeakReference<Activity> mActivity;
    private CameraWrapper.CameraOpenCallback mCameraOpenCallback;


    public CameraManager(Activity activity) {
        mActivity = new WeakReference<>(activity);
        cameraWidth = CameraFactory.mWidth;
        cameraHeight = CameraFactory.mHeight;
        CameraFactory.mAngle = getAngleGoogle();
        mCameraWrapper = CameraFactory.getCamera();

    }

    public void setmCameraOpenCallback(CameraWrapper.CameraOpenCallback mCameraOpenCallback) {
        this.mCameraOpenCallback = mCameraOpenCallback;
    }

    public synchronized void switchCamera() {
        mCameraWrapper.closeCamera();
//        mCameraWrapper = CameraFactory.getCamera();
        mCamraId = (mCamraId + 1) % 2;
        if (mCamraId == 0) {
            CameraFactory.mAngle = Angle = 90;
        } else {
            CameraFactory.mAngle = Angle = 270;
        }
        openCamera();
    }

    public synchronized void switchCamera(int width, int height) {
        CameraFactory.mWidth = width;
        CameraFactory.mHeight = height;
        cameraWidth = CameraFactory.mWidth;
        cameraHeight = CameraFactory.mHeight;
        mCameraWrapper = CameraFactory.getCamera();
    }

    public boolean isFrontCam() {
        return mCamraId == 1;
    }

    // 获取拍照照片的宽度
    public int getPictureWidth() {
        return mCameraWrapper.getPictureWidth();
    }

    // 获取拍照照片的高度
    public int getPictureHeight() {
        return mCameraWrapper.getPictureHeight();
    }

    public void openCamera() {
        mCameraWrapper.openCamera(!isFrontCam(), mActivity.get(), mCameraOpenCallback);
    }

    public void startPreview(SurfaceTexture surfaceTexturer) {
        mCameraWrapper.startPreview(surfaceTexturer);
    }

    public void setDisplayOrientation() {
        mCameraWrapper.setDisplayOrientation(getAngleGoogle());
    }

    public void closeCamera() {
        mCameraWrapper.closeCamera();
    }

    public void takePicture() {
        mCameraWrapper.takePicture();
    }

    /**
     * 通过传入的宽高算出最接近于宽高值的相机大小
     */
    private Camera.Size calBestPreviewSize(Camera.Parameters camPara, final int width, final int height) {
        List<Camera.Size> allSupportedSize = camPara.getSupportedPreviewSizes();
        ArrayList<Camera.Size> widthLargerSize = new ArrayList<Camera.Size>();
        for (Camera.Size tmpSize : allSupportedSize) {
            if (tmpSize.width > tmpSize.height) {
                widthLargerSize.add(tmpSize);
            }
            Log.d("CameraManager", "calBestPreviewSize " + tmpSize.width + ", " + tmpSize.height);
        }

        Collections.sort(widthLargerSize, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                int off_one = Math.abs(lhs.width * lhs.height - width * height);
                int off_two = Math.abs(rhs.width * rhs.height - width * height);
                return off_one - off_two;
            }
        });
        Log.d("CameraManager", "calBestPreviewSize  final" + widthLargerSize.get(0).width + ", " + widthLargerSize.get(0).height);

        return widthLargerSize.get(0);
    }

    /**
     * 开始检测脸
     */
    public boolean actionDetect(CameraWrapper.ICameraCallback cameraCallback) {
        mCameraWrapper.startDetect(cameraCallback);
        return true;
    }

    /**
     * 对下面官方的计算一般是计算display 放在这里并不合适
     * 前置270 后置90
     * <p>
     * <p>
     * 前置摄像头
     * <p>
     * 1.camera onPreviewFrame     和 。    surfacetexture呈现的camera 是镜像的关系
     * 2.onPreviewFrame逆时针旋转270度和真人呈镜像关系。
     * 3.surfacetexture 。   旋转90度和真人一样，所以setDisplayOrientation应该是操作的这个图像
     * https://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation(int)
     * 这是orient的链接项目也用到了 前置摄像头的确Camera.CameraInfo()是270   但是算出来使用的结果是90
     * 4.项目是先横着镜像，然后处理 （全部传入270度---角度是参照正常物体需要逆时针多少度和图像对应起来）。 然后 旋转270+镜像
     * <p>
     * <p>
     * 后置摄像头
     * <p>
     * 1.camera onPreviewFrame     和 。    surfacetexture呈现的camera 是一样的
     * 2.onPreviewFrame逆时针旋转90度和真人一样。
     * 3.surfacetexture 。   旋转90度和真人一样，所以setDisplayOrientation应该是操作的这个图像
     * https://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation(int)
     * 这是orient的链接项目也用到了 后置摄像头的确Camera.CameraInfo()是90   算出来使用的结果也是90
     * 4.项目是不旋转操作，然后处理 （全部传入90度）。 然后 旋转90
     */

    private int getAngle() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCamraId, info);
        return info.orientation;
    }

    public int getAngleGoogle() {
        int rotateAngle = 90;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCamraId, info);
        if (mActivity.get() == null) {
            return rotateAngle;
        }
        int rotation = mActivity.get().getWindowManager().getDefaultDisplay().getRotation();
        Log.e("xie", "rotation = " + rotation);
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        Log.e("xie", "xie getAngle: origin onPreviewFrame" + degrees + "orient" + info.orientation);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotateAngle = (info.orientation + degrees) % 360;
            rotateAngle = (360 - rotateAngle) % 360; // compensate the mirror
        } else { // back-facing
            rotateAngle = (info.orientation - degrees + 360) % 360;
        }
        Log.e("xie", "xie getAngle: process" + rotateAngle);
        return rotateAngle;
    }


}
