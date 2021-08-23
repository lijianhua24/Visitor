package com.ysd.visitor.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@TargetApi(21)
public class Camera2WrapperImpl extends CameraWrapper {
    private static final String TAG = "Camera2WrapperImpl";

    // event for Camera
    private final static int EVENT_OPEN_CAMERA = 1;
    private final static int EVENT_START_PREVIEW = 2;

    // worker thread for camera
    private Context mContext;
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    // UI handler for callback
    private Handler mUIHandler;

    // camera info
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private String mCameraId;
    private boolean mIsBackCamera;
    private CameraOpenCallback mCameraOpenCallback;


    // preview info
    private ImageReader mPreviewImageReader;
    private ICameraCallback mCameraCallback;
    private SurfaceTexture mSurfaceTexture;
    private ImageReader mCaptureImageReader;

    /**
     * 预览请求的Builder
     */
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    // Listener for frame data of preview
    private ImageReader.OnImageAvailableListener mPreviewListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            if (mCameraCaptureSession == null) {
                Log.i(TAG, "mCameraCaptureSession = null.");
                return;
            }

            Image image = reader.acquireNextImage();
            if (mCameraCaptureSession == null) {
                Log.i(TAG, "mCameraCaptureSession = null.");
                image.close();
                return;
            }
            if (checkNotNull(mCameraCallback)) {
                byte[] yuvData = new byte[image.getHeight() * image.getWidth() * 3 / 2];
                image2NV21(image, yuvData);
                mCameraCallback.onPreviewFrame(yuvData, null);
            }

            // close
            image.close();
        }
    };

    // Listener for frame data of capture photo
    private ImageReader.OnImageAvailableListener mCapturePhotoListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            if (mCameraCaptureSession == null) {
                Log.i(TAG, "mCameraCaptureSession = null.");
                return;
            }

            Image image = reader.acquireNextImage();
            if (checkNotNull(mCameraCallback)) {
                byte[] yuvData = new byte[image.getHeight() * image.getWidth() * 3 / 2];
                image2NV21(image, yuvData);
                mCameraCallback.onCapturePicture(yuvData);
            }

            // close
            image.close();
        }
    };

    public Camera2WrapperImpl() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            throw new RuntimeException("can't use this class");
        }


    }

    /**
     * state of opening Camera
     */
    private final CameraDevice.StateCallback mCameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Log.d(TAG, "onOpened()...");
            mCameraDevice = cameraDevice;
            callbackToCaller(Result.RESULT_SUCCESS);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.d(TAG, "onDisconnected()...");
            mCameraDevice.close();
            mCameraDevice = null;
            callbackToCaller(Result.RESULT_DISCONNECTED);
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int errorCode) {
            Log.d(TAG, "onError()...code:" + errorCode);
            mCameraDevice.close();
            mCameraDevice = null;
            callbackToCaller(Result.RESULT_FAILED);
        }
    };

    /**
     * Camera state: Showing camera preview.
     * 相机预览状态
     */
    private static final int STATE_PREVIEW = 0;

    /**
     * Camera state: Waiting for the focus to be locked.
     * <p>
     * 相机拍照，被锁住，等待焦点状态
     */
    private static final int STATE_WAITING_LOCK = 1;

    /**
     * Camera state: Waiting for the exposure to be precapture state.
     */
    private static final int STATE_WAITING_PRECAPTURE = 2;

    /**
     * Camera state: Waiting for the exposure state to be something other than precapture.
     */
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;

    /**
     * Camera state: Picture was taken.
     * 图片已经获取
     */
    private static final int STATE_PICTURE_TAKEN = 4;

    private static final int STATE_CLOSE_PREVIEW = 5;

    /**
     * The current state of camera state for taking pictures.
     *
     * @see #mCaptureCallback
     */
    private volatile int mState = STATE_PREVIEW;
    /**
     * CameraCaptureSession.CaptureCallback : 处理捕获到的NV21事件。
     */
    private CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        private void process(CaptureResult result) {
            Log.i(TAG, "CaptureCallback... mState = " + mState);
            switch (mState) {
                //正常预览状态
                case STATE_PREVIEW: {
                    break;
                }
                //刚开始拍照，锁住，等待状态
                case STATE_WAITING_LOCK: {
                    //当前自动对焦的状态
                    Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == null) {
                        capturePicture();
                    } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState || CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {
                        // CONTROL_AE_STATE can be null on some devices
                        Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                        if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                            mState = STATE_PICTURE_TAKEN;
                            capturePicture();
                        } else {
                            runPrecaptureSequence();
                        }
                    } else {
                        mState = STATE_PICTURE_TAKEN;
                        capturePicture();
                    }
                    break;
                }
                //等待，预捕获
                case STATE_WAITING_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE || aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        mState = STATE_WAITING_NON_PRECAPTURE;
                    }
                    break;
                }
                //已经完成预捕获，直接拍照。
                case STATE_WAITING_NON_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        mState = STATE_PICTURE_TAKEN;
                        capturePicture();
                    }
                    break;

                }
                default:
                    break;
            }
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            process(result);
        }
    };

    /**
     * Open Camera asynchronously
     */
    @RequiresPermission(Manifest.permission.CAMERA)
    @Override
    public void openCamera(boolean isBackCamera, Context context, CameraOpenCallback callback) {
        Log.d(TAG, "openCamera()...back camera:" + isBackCamera);

        // init worker thread
        initEventLooper(context);

        // save params
        mContext = context;
        mIsBackCamera = isBackCamera;
        mCameraOpenCallback = callback;

        // obtain message
        Message msg = Message.obtain(mHandler, EVENT_OPEN_CAMERA);
        msg.sendToTarget();
    }

    /**
     * Start Preview
     *
     * @param surfaceTexturer
     */
    @Override
    public void startPreview(SurfaceTexture surfaceTexturer) {
        Log.d(TAG, "startPreview()...");
        mState = STATE_PREVIEW;
        // save surfaceTexture
        mSurfaceTexture = surfaceTexturer;

        // Init ImageReader
        initImageReader();

        // obtain message
        mHandler.sendEmptyMessageDelayed(EVENT_START_PREVIEW, 200);
    }

    /**
     * Begin to detect and receive frame data of preview
     *
     * @param callback receive frame data of preview
     */
    @Override
    public void startDetect(ICameraCallback callback) {
        mCameraCallback = callback;
    }

    /**
     * Stop detecting，
     */
    @Override
    public void stopDetect() {
        mCameraCallback = null;
    }

    /**
     * Stop preview
     */
    @Override
    public void stopPreview() {
//        mCameraCallback = null;
        Log.d(TAG, "stopPreview()...");
        if (checkNotNull(mCameraCaptureSession)) {
            try {
                mCameraCaptureSession.stopRepeating();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "exception when stop repeating");
            }
            mCameraCaptureSession = null;
        }

        // set Image Reader
        if (checkNotNull(mPreviewImageReader)) {
            mPreviewImageReader.close();
            mPreviewImageReader = null;
        }
    }

    /**
     * Close camera
     */
    @Override
    public void closeCamera() {
        Log.d(TAG, "closeCamera()...");
        mState = STATE_CLOSE_PREVIEW;
        stopPreview();

        if (checkNotNull(mCameraDevice)) {
            mCameraDevice.close();
            mCameraDevice = null;
        }

        if (checkNotNull(mCaptureImageReader)) {
            mCaptureImageReader.close();
            mCaptureImageReader = null;
        }

        // reset camera info
        resetPreviewInfo();

        // exit event looper
        exitEventLooper();
    }

    @Override
    public void takePicture() {
        try {
            //告诉相机，这里已经锁住焦点
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
            // 标识，正在进行拍照动作
            mState = STATE_WAITING_LOCK;
            //进行拍照处理
            mCameraCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * 运行预捕获的序列，为捕获一个静态图片
     */
    private void runPrecaptureSequence() {
        try {
            // 告诉相机，这里触发.
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            //设置成预捕获状态，将需等待。
            mState = STATE_WAITING_PRECAPTURE;
            mCameraCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void capturePicture() {
        try {
            // 创建一个拍照的CaptureRequest.Builder
            final CaptureRequest.Builder captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);

            captureBuilder.addTarget(mCaptureImageReader.getSurface());

            // 使用相同的AE和AF模式作为预览.
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

            CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session
                        , @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    //拍照完成，进行释放焦点操作。
                    unlockFocus();
                }
            };

            //先停止以前的预览状态
            mCameraCaptureSession.stopRepeating();
            mCameraCaptureSession.abortCaptures();
            //执行拍照状态
            mCameraCaptureSession.capture(captureBuilder.build(), captureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 完成一些列拍照后，释放焦点。
     */
    private void unlockFocus() {
        try {
            // 重置一系列的对焦
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            mCameraCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback, mHandler);

            // 恢复正常状态
            mState = STATE_PREVIEW;
            mCameraCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), null, mHandler);
            Log.i(TAG, TAG + " 拍照完成，释放焦点  unlockFocus() ");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set Orientation of Display
     *
     * @param angle 0~360
     */
    @Override
    public void setDisplayOrientation(int angle) {
    }

    // init Image Reader
    private void initImageReader() {
        Log.d(TAG, "initImageReader()...width * height = " + mWidth + " * " + mHeight);
        mPreviewImageReader = ImageReader.newInstance(mWidth, mHeight, ImageFormat.YUV_420_888, 3);
        mPreviewImageReader.setOnImageAvailableListener(mPreviewListener, mHandler);

        //对于静态图片，使用可用的最大值来拍摄。
        //设置ImageReader,将大小，图片格式
        Log.d(TAG, "initImageReader()...pitcurewidth * pitcureheight = " + mPictureWidth + " * " + mPictureHeight);
        mCaptureImageReader = ImageReader.newInstance(mPictureWidth, mPictureHeight, ImageFormat.YUV_420_888, 3);
        mCaptureImageReader.setOnImageAvailableListener(mCapturePhotoListener, mHandler);
    }

    // init event thread
    private void initEventLooper(Context context) {
        Log.d(TAG, "initEventLooper()...");
        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage()..." + msg);
                switch (msg.what) {
                    case EVENT_OPEN_CAMERA:
                        handleOpenCamera();
                        break;
                    case EVENT_START_PREVIEW:
                        handleStartPreview();
                        break;
                    default:
                        Log.d(TAG, "Unsupport event type");
                        break;
                }
                return false;
            }
        });

        mUIHandler = new Handler(context.getMainLooper());
    }

    private void exitEventLooper() {
        Log.d(TAG, "exitEventLooper()...");
        if (checkNotNull(mUIHandler)) {
            mUIHandler.removeCallbacksAndMessages(null);
        }
        if (checkNotNull(mHandler)) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (checkNotNull(mHandlerThread)) {
            mHandlerThread.quit();
        }
        mHandlerThread = null;
        mHandler = null;
        mUIHandler = null;
    }

    /**
     * Open Camera
     */
    // handle for opening Camera
    private void handleOpenCamera() {
        Log.d(TAG, "handleOpenCamera()...");

        mCameraManager = (CameraManager) mContext.getSystemService(Activity.CAMERA_SERVICE);
        if (!checkNotNull(mCameraManager)) {
            callbackToCaller(Result.RESULT_FAILED);
            return;
        }

        // select back or front camera
        mCameraId = selectBackOrFrontCamera();
        if (!checkNotNull(mCameraId)) {
            Log.e(TAG, "Camera id is invalid, return!");
            callbackToCaller(Result.RESULT_FAILED);
            return;
        }

        // Open Camera
        try {
            mCameraManager.openCamera(mCameraId, mCameraStateCallback, mHandler);
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to open Camera");
            e.printStackTrace();
            callbackToCaller(Result.RESULT_FAILED);
        } catch (SecurityException e) {
            Log.e(TAG, "Failed to open Camera");
            e.printStackTrace();
            callbackToCaller(Result.RESULT_FAILED);
        }
    }

    /**
     * Start Preview
     */
    private void handleStartPreview() {
        Log.d(TAG, "handleStartPreview()...");

        // check if Camera already opened
        if (!checkNotNull(mCameraDevice)) {
            Log.e(TAG, "Invalid Camera device");
            return;
        }

        // 创建一个预览界面需要用到的Surface对象
        mSurfaceTexture.setDefaultBufferSize(CameraFactory.mWidth,CameraFactory.mHeight);
        Surface surface = new Surface(mSurfaceTexture);

        // Camera already opened and go to preview
        List<Surface> surfaces = new ArrayList<>();
        surfaces.add(mPreviewImageReader.getSurface());
//        surfaces.add(mCaptureImageReader.getSurface());
        surfaces.add(surface);

        try {
            // create Capture request
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(mPreviewImageReader.getSurface());
            mPreviewRequestBuilder.addTarget(surface);

            // create Capture Session
            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (!checkNotNull(mCameraDevice)) {
                        return;
                    }
                    try {
                        // auto focus
                        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO);

                        mCameraCaptureSession = session;

                        // send request
                        session.setRepeatingRequest(mPreviewRequestBuilder.build(), null, mHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Log.e(TAG, "on configure failed");
                }
            }, mHandler);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to start preview!");
        }
    }

    private void callbackToCaller(final int result) {
        Log.d(TAG, "callbackToCaller...");
        if (checkNotNull(mUIHandler)) {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (checkNotNull(mCameraOpenCallback)) {
                        switch (result) {
                            case Result.RESULT_SUCCESS:
                                mCameraOpenCallback.onOpenSuccess();
                                break;
                            case Result.RESULT_DISCONNECTED:
                                mCameraOpenCallback.onDisconnected();
                                break;
                            case Result.RESULT_FAILED:
                                mCameraOpenCallback.onOpenFailed();
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }
    }

    // check if Camera is null
    private boolean checkNotNull(Object object) {
        return (null != object);
    }

    // select back or front Camera
    private String selectBackOrFrontCamera() {
        Log.d(TAG, "selectBackOrFrontCamera()...");
        String cameraId = null;

        try {
            // get id list
            String[] idList = mCameraManager.getCameraIdList();
            for (String id : idList) {
                Log.d(TAG, "selectBackOrFrontCamera()...id = " + id);
                //获取到每个相机的参数对象，包含前后摄像头，分辨率等
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);

                //获取到每个相机的参数对象，包含前后摄像头，分辨率等
                //存储流配置类
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }

//                Size[] support = map.getOutputSizes(ImageFormat.JPEG);
//                for (Size xx : support) {
//                    Log.d(TAG, "width * height = " + xx.getWidth() + " * " + xx.getHeight());
//                }

                Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new Comparator<Size>() {
                    @Override
                    public int compare(Size lhs, Size rhs) {
                        return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth());
                    }
                });

                mPictureWidth = largest.getWidth();
                mPictureHeight = largest.getHeight();
                Log.d(TAG, "mPictureWidth * mPictureHeight = " + mPictureWidth + " * " + mPictureHeight);

                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                Log.d(TAG, "selectBackOrFrontCamera()...facing = " + facing);
                if (facing != null) {
                    if (mIsBackCamera && // back camera
                            CameraCharacteristics.LENS_FACING_BACK == facing) {
                        cameraId = id;
                        break;
                    } else if (CameraCharacteristics.LENS_FACING_FRONT == facing) {
                        cameraId = id;
                        break;
                    }
                }
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Invalid Camera Id");
        }

        Log.d(TAG, "Camera Id:" + cameraId);
        return cameraId;
    }

    // reset preview info
    private void resetPreviewInfo() {
        mCameraId = null;
        mSurfaceTexture = null;
        mCameraOpenCallback = null;

        mCameraCallback = null;
        mIsBackCamera = false;
    }

    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<Size>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }

        // Pick the smallest of those, assuming we found any
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    /**
     * 根据ImageReader得到YUV的图像，此时的排列为YYYYYYYYUUVV这种格式，
     * 稍后要转换成 YYYYUVUV 的 NV21格式
     *
     * @param image Image对象，可以从png/yuv等格式创建
     * @param data  返回NV21格式数据
     */
    @TargetApi(21)
    private int image2NV21(Image image, byte[] data) {
        int result = readImageIntoBuffer(image, data);
        if (result == -1)
            return -1;
        revertHalf(data);
        return result;
    }

    /**
     * 将Image读取到data中
     *
     * @param image
     * @param data
     */
    @TargetApi(21)
    private int readImageIntoBuffer(Image image, byte[] data) {
        if (image == null) {
            Log.e("NULL Image", "image is null");
            return -1;
        }
        final int imageWidth = image.getWidth();
        final int imageHeight = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        int offset = 0;
        for (int plane = 0; plane < planes.length; ++plane) {
            if (STATE_CLOSE_PREVIEW == mState) {
                Log.i(TAG, "middle mState = " + mState);
                return -1;
            }

            final ByteBuffer buffer = planes[plane].getBuffer();
            final int rowStride = planes[plane].getRowStride();
            // Experimentally, U and V planes have |pixelStride| = 2, which
            // essentially means they are packed. That's silly, because we are
            // forced to unpack here.
            final int pixelStride = planes[plane].getPixelStride();
            final int planeWidth = (plane == 0) ? imageWidth : imageWidth / 2;
            final int planeHeight = (plane == 0) ? imageHeight : imageHeight / 2;
            if (pixelStride == 1 && rowStride == planeWidth) {
                // Copy whole plane from buffer into |data| at once.
                buffer.get(data, offset, planeWidth * planeHeight);
                offset += planeWidth * planeHeight;
            } else {
                // Copy pixels one by one respecting pixelStride and rowStride.
                byte[] rowData = new byte[rowStride];
                for (int row = 0; row < planeHeight - 1; ++row) {
                    if (STATE_CLOSE_PREVIEW == mState) {
                        Log.i(TAG, "2222 mState = " + mState);
                        return -1;
                    }
                    buffer.get(rowData, 0, rowStride);
                    for (int col = 0; col < planeWidth; ++col) {
                        data[offset++] = rowData[col * pixelStride];
                    }
                }
                // Last row is special in some devices and may not contain the full
                // |rowStride| bytes of data. See  http://crbug.com/458701  and
                // http://developer.android.com/reference/android/media/Image.Plane.html#getBuffer()
                buffer.get(rowData, 0, Math.min(rowStride, buffer.remaining()));
                for (int col = 0; col < planeWidth; ++col) {
                    data[offset++] = rowData[col * pixelStride];
                }
            }
        }
        return 0;
    }

    /**
     * 将 YYYYUUVV 转换为 YYYYUVUV
     *
     * @param yuvData
     */
    private void revertHalf(byte[] yuvData) {
        int SIZE = yuvData.length;
        byte[] uv = new byte[SIZE / 3];
        int u = SIZE / 6 * 4;
        int v = SIZE / 6 * 5;
        for (int i = 0; i < uv.length - 1; i += 2) {
            uv[i] = yuvData[v++];
            uv[i + 1] = yuvData[u++];
        }
        for (int i = SIZE / 3 * 2; i < SIZE; i++) {
            yuvData[i] = uv[i - SIZE / 3 * 2];
        }

    }

    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }
}
