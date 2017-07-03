package com.ouclbc.nativeopengleswhiteboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ouclbc.nativeopengleswhiteboard.util.Lines;
import com.ouclbc.nativeopengleswhiteboard.util.PointArray;

/**
 * Created by ouclbc on 2017/6/27.
 */

public class MyGLSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    public MyGLSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Lines.getInstance().touchProcess(event);
        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                addPoints(event.getX(),event.getY());
                for (PointArray pointArray:Lines.getInstance().getLinePointLists()){
                    if (pointArray.size()>2){
                        Libjni.nativeRequestRender(pointArray.getDatas());
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                addPoints(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        return true;
    }

    private void addPoints(float x,float y){
        float realx = (x/ (float) MainActivity.width*2f)-1;
        float realy = (y/ (float) MainActivity.height*2f)-1;
        Lines.getInstance().getLine().add(realx,realy);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Libjni.nativeStartRender();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Libjni.nativeSurfaceChanged(getHolder().getSurface());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Libjni.nativeSurfaceDestroyed();
    }
}
