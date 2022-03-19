//
// Created by walker on 2021/10/22.
//
#include <jni.h>
#include <string>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <android/log.h>
#include "com_walker_safecopy_SafeCopy.h"

int8_t *m_ptr;
int32_t m_size;
int CONTENT_SIZE = 512;
int m_fd = -1;

void unInit();

void myInit(JNIEnv *env, jstring path);

extern "C"
JNIEXPORT void JNICALL
Java_com_walker_safecopy_SafeCopy_putString2memory(JNIEnv
                                                   *env,
                                                   jobject thiz, jstring
                                                   path,
                                                   jstring content
) {
    myInit(env, path);
    memset(m_ptr, 0, CONTENT_SIZE);
    std::string data(env->GetStringUTFChars(content, NULL));
    //将 data 的 data.size() 个数据 拷贝到 m_ptr
    memcpy(m_ptr, data.data(), data.size());

    __android_log_print(ANDROID_LOG_ERROR, "SafeCopy", "fd:%d   写入数据:%s", m_fd, data.c_str());

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_walker_safecopy_SafeCopy_getString4memory(JNIEnv *env, jobject
thiz,
                                                   jstring path
) {
    myInit(env, path);
    //申请内存
    char *buf = static_cast<char *>(malloc(CONTENT_SIZE));
    memcpy(buf, m_ptr, CONTENT_SIZE);
    std::string result(buf);
    __android_log_print(ANDROID_LOG_ERROR, "SafeCopy", "fd:%d   读取数据:%s", m_fd, result.c_str());
    unInit();

    return env->NewStringUTF(result.c_str());
}

void myInit(JNIEnv *env, jstring path) {
    if (m_fd == -1) {
        std::string file = env->GetStringUTFChars(path, NULL);
        //打开文件
        m_fd = open(file.c_str(), O_RDWR | O_CREAT, S_IRWXU);
        //获得一页内存大小
        //Linux采用了分页来管理内存，即内存的管理中，内存是以页为单位,一般的32位系统一页为 4096个字节
        m_size = getpagesize();
        //将文件设置为 m_size这么大
        ftruncate(m_fd, m_size); // 100  1000 10000
        // m_size:映射区的长度。 需要是整数页个字节    byte[]
        m_ptr = (int8_t *) mmap(0, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd,
                                0);
    }
}

void unInit() {
    if (m_fd != -1) {
        //取消映射
        munmap(m_ptr, m_size);
        //关闭文件
        close(m_fd);
        m_fd = -1;
    }
}