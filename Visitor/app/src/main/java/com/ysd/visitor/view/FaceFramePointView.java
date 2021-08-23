package com.ysd.visitor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.megvii.facepp.multi.sdk.FaceDetectApi;
import com.ysd.visitor.camera.CameraFactory;

public class FaceFramePointView extends View {
    private int width;
    private int height;

    private int mCameraHeight;
    private int mCameraWidth;
    private Paint paint;
    private Paint rectPaint;
    private boolean isBackCamera;

    public FaceFramePointView(Context context) {
        super(context);
        init();
    }

    public FaceFramePointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceFramePointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCameraHeight = CameraFactory.mHeight;
        mCameraWidth = CameraFactory.mWidth;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#7EB9FF"));

        rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#7EB9FF"));
        rectPaint.setStrokeWidth(5);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width != 0 && height != 0) {
            setMeasuredDimension(width, height);
        }
    }

    public void refreshView(int width, int height) {
        this.width = width;
        this.height = height;
        requestLayout();
    }

    private FaceDetectApi.Face[] faces = null;

    public void setResult(FaceDetectApi.Face[] faces, boolean isBackCamera) {
        this.faces = faces;
        this.isBackCamera = isBackCamera;
//        invalidate();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (faces != null) {
            for (FaceDetectApi.Face face : faces) {
//                PointF[] points = face.points;
//                for (PointF point : points) {
//                    float x = point.x;
//                    float y = point.y;
//                    float relativeX = (y * 1.0f / mCameraHeight) * width;
//                    float relativeY = ((mCameraWidth - x) / mCameraWidth) * height;
//                    relativeX = width - relativeX;
//                    if (isBackCamera) {
//                        relativeY = height - relativeY;
//                    }
//                    canvas.drawCircle(relativeX, relativeY, 5f, paint);
//                }

                Rect rect = face.rect;
//                int top = (int) ((rect.top * 1.0f / mCameraHeight) * width);
//                int bottom = (int) ((rect.bottom * 1.0f / mCameraHeight) * width);
//                int left = (int) ((rect.left * 1.0f / mCameraHeight) * height);
//                int right = (int) ((rect.right * 1.0f / mCameraHeight) * height);


//                int top = (int) (((mCameraWidth - rect.right) * 1.0f / mCameraWidth) * width);
//                int bottom = (int) (((mCameraWidth - rect.left) * 1.0f / mCameraWidth) * width);
//                if (isBackCamera) {
//                    top = (int) ((rect.left * 1.0f / mCameraWidth) * width);
//                    bottom = (int) ((rect.right * 1.0f / mCameraWidth) * width);


//                }

                float scale = width / mCameraWidth;
                int top = (int) (rect.top * scale);
                int bottom = (int) (rect.bottom * scale);
                int left = (int) (rect.left * scale);
                int right = (int) (rect.right * scale);

                //镜像
                canvas.drawRect(new Rect(left, bottom, right, top), rectPaint);
            }
        }


//        if (bitmap != null) {
//            Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//            Canvas bitmapCanvas = new Canvas(resultBitmap);
//            bitmapCanvas.drawBitmap(bitmap, new Matrix(), new Paint());
//
//            if (faces != null) {
//                for (FaceDetectApi.Face face : faces) {
//                    PointF[] points = face.points;
//                    for (PointF point : points) {
//                        bitmapCanvas.drawCircle(point.x, point.y, 2.5f, paint);
//                    }
//
//                    Rect rect = face.rect;
//                    bitmapCanvas.drawRect(rect, rectPaint);
//                }
//            }
//            canvas.drawBitmap(resultBitmap, new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight()), new Rect(0, 0, width, height), new Paint());
//        }
    }
}





