#include <string.h>
#include <jni.h>
#include <stdio.h>

#include "fd628.h"

// jni调用上下文结构
static struct led_device_t* fd628_device = NULL;

#ifdef __cplusplus
extern "C" {
#endif

// init 接口，创建调用上下文结构
JNIEXPORT jint Java_com_gaoee_led_LedService_ledInit(JNIEnv* env, jclass clazz)
{
	ALOGI("Led FD628 jni: initializing....");
	if(led_dev_init(&fd628_device) == 0)
	{
		ALOGI("Led FD628 jni: open.");
		return 0;
	}else{
		ALOGE("Led FD628 jni: failed to open device");
		return 1;
	}

	LOGE("Led FD628 jni: failed to get led stub module");
	return 2;
}

// open 接口，打开设备文件
JNIEXPORT jint Java_com_gaoee_led_LedService_ledOpen(JNIEnv* env, jobject clazz)
{
	if( !fd628_device ){
		ALOGI("Led FD628 jni: device is not open.");
		return 1;
	}

	fd628_device->open(fd628_device);
	ALOGI("Led FD628 jni: device opened.");
	return 0;
}

// close 接口，关闭设备文件
JNIEXPORT jint Java_com_gaoee_led_LedService_ledClose(JNIEnv* env, jobject clazz)
{
	if( !fd628_device ){
		ALOGI("Led FD628 jni: device is not open.");
		return 1;
	}

	fd628_device->close(fd628_device);
	ALOGI("Led FD628 jni: device closed.");
	return 0;
}

// set接口，设置Led值
JNIEXPORT jint Java_com_gaoee_led_LedService_ledSet(JNIEnv* env, jobject clazz, int id, int value)
{
	int stat = 0;
	if( !fd628_device ){
		ALOGI("Led FD628 jni: device is not open.");
		return 1;
	}
	stat = fd628_device->set(fd628_device,id, value);
	ALOGI("Led FD628 jni: device set id %d value %d.", id , value);
	return stat;
}

#ifdef __cplusplus
}
#endif
