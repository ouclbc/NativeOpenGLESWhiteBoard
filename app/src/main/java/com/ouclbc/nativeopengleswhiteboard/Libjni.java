package com.ouclbc.nativeopengleswhiteboard;

import android.view.Surface;

/**
 * Created by ouclbc on 2017/6/27.
 */

public class Libjni {
    static {
        System.loadLibrary("NativeOpenGLES");
    }
    public static native void nativeSurfaceChanged(Surface surface);

    public static native void nativeSurfaceDestroyed();

    public static native void nativeStartRender();

    public static native void nativeRequestRender(float[] points);
}
