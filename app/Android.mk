

LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE:=crash_handler

LOCAL_LDLIBS:= -llog

LOCAL_SRC_FILES:= \
    src/main/cpp/native_crash_handler.cc

include $(BUILD_SHARED_LIBRARY)