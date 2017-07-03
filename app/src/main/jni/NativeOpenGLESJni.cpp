#include <jni.h>
#include "Renderer.h"
#include "GLUtil.h"
#include <android/native_window_jni.h>
#define LOG_TAG "HSJNI"


ANativeWindow * mWindow;
Renderer * mRenderer;
#ifdef __cpluscplus
extern "C" {
#endif
JNIEXPORT void JNICALL
nativeStartRender(JNIEnv *env,jclass type) {
    mRenderer = new Renderer();
    mRenderer->start();

}

JNIEXPORT void JNICALL
nativeSurfaceChanged(JNIEnv *env,jclass type,jobject surface) {
    mWindow = ANativeWindow_fromSurface(env, surface);
    // surfacechange时 发送SurfaceChanged消息，此时创建egl环境的消息
    mRenderer->requestInitEGL(mWindow);

}

JNIEXPORT void JNICALL
nativeSurfaceDestroyed(JNIEnv *env,jclass type) {
    mRenderer->requestDestroy();
    ANativeWindow_release(mWindow);
    delete mRenderer;
}

JNIEXPORT void JNICALL
nativeRequestRender(JNIEnv *env,jclass type,jfloatArray points) {

    float * pointarr;
    int length;
    pointarr = env->GetFloatArrayElements(points, NULL);
    length = env->GetArrayLength(points);
    LOGI(1,"nativeRequestRender pointarr: %p", pointarr);
    LOGI(1,"nativeRequestRender length: %d", length);
    mRenderer->requestRenderFrame(pointarr,length);

}
#define JNIREG_CLASS "com/ouclbc/nativeopengleswhiteboard/Libjni"//指定要注册的类

/**
* Table of methods associated with a single class.
*/
static JNINativeMethod sMethods[] = {
        { "nativeStartRender", "()V", (void*)nativeStartRender },
        { "nativeSurfaceChanged", "(Landroid/view/Surface;)V", (void*)nativeSurfaceChanged },
        { "nativeRequestRender", "([F)V", (void*)nativeRequestRender },
        { "nativeSurfaceDestroyed", "()V", (void*)nativeSurfaceDestroyed },
};

/*
* Register several native methods for one class.
*/
static int registerNativeMethods(JNIEnv* env, const char* className,
                                 JNINativeMethod* sMethods, int numMethods)
{
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, sMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}


/*
* Register native methods for all classes we know about.
*/
static int registerNatives(JNIEnv* env)
{
    if (!registerNativeMethods(env, JNIREG_CLASS, sMethods,
                               sizeof(sMethods) / sizeof(sMethods[0])))
        return JNI_FALSE;

    return JNI_TRUE;
}

jint JNI_OnLoad(JavaVM* vm, void *reserved){
    JNIEnv *e;
    int status;
    if (vm->GetEnv((void**)&e, JNI_VERSION_1_6)) {
        LOGI(1,"JNI version mismatch error");
        return JNI_ERR;
    }

    if ((status = registerNatives(e)) < 0) {
        LOGI(1,"jni registration failure, status: %d", status);
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}
#ifdef __cpluscplus
}
#endif