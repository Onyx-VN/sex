#include <jni.h>
#include <sys/uio.h>

extern "C" {
    JNIEXPORT jfloat JNICALL Java_com_ff_cheat_MemReader_getAngle(JNIEnv*, jclass) {
        pid_t pid = 12345; uintptr_t addr = 0x7A3F5C10; float ang = 0;
        struct iovec local{&ang, sizeof(float)}, remote{(void*)addr, sizeof(float)};
        process_vm_readv(pid, &local, 1, &remote, 1, 0); return ang;
    }

    JNIEXPORT jobjectArray JNICALL Java_com_ff_cheat_MemReader_getList(JNIEnv* env, jclass) {
        jclass cls = env->FindClass("com/ff/cheat/AimbotEngine$Target");
        jmethodID ctor = env->GetMethodID(cls, "<init>", "(FFFFI)V");
        jobjectArray arr = env->NewObjectArray(10, cls, nullptr);
        for(int i=0;i<10;i++){ jobject obj = env->NewObject(cls, ctor, 100.f+i*50, 200.f, 100.f+i*50, 180.f, 100); env->SetObjectArrayElement(arr, i, obj); }
        return arr;
    }

    JNIEXPORT void JNICALL Java_com_ff_cheat_MemWriter_setAngle(JNIEnv*, jclass, jfloat ang) {
        pid_t pid = 12345; uintptr_t addr = 0x7A3F5C10;
        struct iovec local{(void*)&ang, sizeof(float)}, remote{(void*)addr, sizeof(float)};
        process_vm_writev(pid, &local, 1, &remote, 1, 0);
    }
}
