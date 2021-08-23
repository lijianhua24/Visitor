package com.ysd.visitor.camera;

import android.os.Build;
import android.util.Log;

public class CameraFactory {
    private final static String TAG = "CameraFactory";

    public final static boolean IS_USE_CAMERA2 = false;
    public static int mWidth = 640;
    public static int mHeight = 480;
    public static int mAngle = 90;

    public static CameraWrapper getCamera() {

        CameraWrapper cameraWrapper;

        // From Lollipop, android implements new Camera architecture.
        // So import Camera2.
        if (IS_USE_CAMERA2 &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "use Camera2");
            cameraWrapper = new Camera2WrapperImpl();
        } else {
            Log.d(TAG, "use Camera");
            cameraWrapper = new CameraWrapperImpl();
        }

        return cameraWrapper;
    }

    ;
}
