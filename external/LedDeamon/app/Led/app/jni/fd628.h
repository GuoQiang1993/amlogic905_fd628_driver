#ifndef ANDROID_LED_INTERFACE_H
#define ANDROID_LED_INTERFACE_H

#ifdef __cplusplus
extern "C" {
#endif

#define FPM_HW_MODULE_ID	"fd628"
#define DEVICE_NAME 		"/dev/fd628_dev"
#define MODULE_NAME			"led-jni"

#include <fcntl.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <sys/file.h>
#include <pthread.h>
#include <android/log.h>

#define  LOG_NDEBUG     1
#define  LOG_TAG    "fd628-ndk"

#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  ALOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  ALOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#ifndef LOGI
#define LOGI    ALOGI
#endif
#ifndef LOGE
#define LOGE    ALOGE
#endif

#ifndef  TRUE
#define  TRUE  1
#endif

#ifndef  FALSE
#define  FALSE 0
#endif

enum{
	STATUS_SUCCESS = 0,
	STATUS_FAILED = -1
};

typedef struct _DotLedBitMap{
	uint8_t on;
	uint8_t mask;
}LedBitMap;

#define LED_MASK_VOID	0x00
enum {
	LED_ALARM,
	LED_USB,
	LED_PLAY,
	LED_PAUSE,
	LED_SEC,
	LED_ETH,
	LED_WIFI,
	LED_MAX
};


struct led_device_t{
	int fd;
    int (*open)(struct led_device_t* device);
    int (*close)(struct led_device_t* device);
   	int (*set)(struct led_device_t* device, int id, int value);
};


 /* 打开设备和关闭设备 */
int led_dev_init(struct led_device_t** device);
int led_dev_close(struct led_device_t* device);


#ifdef __cplusplus
}
#endif

/* ANDROID_LED_INTERFACE_H */
#endif
