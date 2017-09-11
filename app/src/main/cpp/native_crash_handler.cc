//
// Created by LiuQijun on 11/25/16.
//

#include <signal.h>
#include <jni.h>
#include <android/log.h>
#include <stdlib.h>

void signal_handler(int signal, siginfo_t* info, void* reserved) {
    __android_log_print(ANDROID_LOG_ERROR, "NativeCrashHandler", "Deal with native crash!");
    exit(0);
}


const int handledSignals[] = {
        SIGSEGV, SIGABRT, SIGFPE, SIGILL, SIGBUS
};

const int handledSignalNum = sizeof(handledSignals) / sizeof(handledSignals[0]);
struct sigaction old_handlers[handledSignalNum];

extern "C" jint JNIEXPORT JNICALL JNI_OnLoad(JavaVM* jvm, void *reserved) {

    struct sigaction handler;
    memset(&handler, 0, sizeof(handler));
    handler.sa_sigaction = signal_handler;
    handler.sa_flags = SA_SIGINFO;

    for(int i = 0; i < handledSignalNum; ++i) {
        sigaction(handledSignals[i], &handler, &old_handlers[i]);
    }

    return JNI_VERSION_1_6;
}


extern "C" void JNIEXPORT JNICALL Java_com_netease_study_exercise_utils_CrashHandler_nativeCrash(JNIEnv* jni, jobject) {
    int *ptr = NULL;
    *ptr = 0;
}