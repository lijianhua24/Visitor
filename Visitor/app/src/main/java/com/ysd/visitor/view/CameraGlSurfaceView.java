package com.ysd.visitor.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class CameraGlSurfaceView extends GLSurfaceView {
    private int width;
    private int height;
    public CameraGlSurfaceView(Context context) {
        super(context);
    }

    public CameraGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
}

