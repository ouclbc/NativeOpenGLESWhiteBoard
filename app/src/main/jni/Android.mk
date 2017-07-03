LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := NativeOpenGLES

LOCAL_SRC_FILES := NativeOpenGLESJni.cpp Renderer.cpp GLUtil.cpp Shape.cpp ./glm/detail/*.* ./glm/gtc/*.* ./glm/gtx/*.* ./glm/simd/*.* ./glm/*.*

LOCAL_LDLIBS := -lGLESv2 -llog -lEGL -landroid

include $(BUILD_SHARED_LIBRARY)
