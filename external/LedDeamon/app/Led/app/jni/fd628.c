#include "fd628.h"

#include <asm/ioctl.h>

/* Use 'K' as magic number */
#define FD628_IOC_MAGIC  'M'
#define FD628_IOC_STATUS_LED _IOW(FD628_IOC_MAGIC, 7, int)


int led_dev_init (struct led_device_t** device);
int led_dev_close(struct led_device_t* device);

static LedBitMap ledStatus[LED_MAX] = {
	{0, 0x01},
	{0, 0x02},
	{0, 0x04},
	{0, 0x08},
	{0, 0x10},
	{0, 0x20},
	{0, 0x40},
};

/*************************************************************************************/
/* 驱动方法实现
/*************************************************************************************/
static int led_open(struct led_device_t* dev)
{
  	//TODO:打开设备
  	dev->fd = open(DEVICE_NAME, O_RDWR | O_NDELAY);
	if(dev->fd == -1){
		LOGE("led-jni stub: open %s failed", DEVICE_NAME);
		return STATUS_FAILED;
	}
    LOGI("led-jni stub: open %s succeed", DEVICE_NAME);
    return STATUS_SUCCESS;
}

static int led_close(struct led_device_t* dev)
{
	if(dev){
		close(dev->fd);
	}
    LOGI("led-jni stub: close %s succeed", DEVICE_NAME);
    return STATUS_SUCCESS;
}

static int led_set(struct led_device_t* dev, int id, int value)
{
	int tmp = 0;
	int ledVal;

    if(dev){
        if(dev->fd >= 0){
			if(id >= 0 && id < LED_MAX){
				ledStatus[id].on = value;
			}else{
				return STATUS_FAILED;
			}
			// 更新灯
			for(tmp = 0; tmp < LED_MAX; tmp++){
			   if(ledStatus[tmp].on){
				   ledVal |= ledStatus[tmp].mask;
			   }else{
				   ledVal &= (~ledStatus[tmp].mask);
			   }
			}

            ioctl(dev->fd, FD628_IOC_STATUS_LED, &ledVal);
            LOGI("led-jni stub: set %d value %02x succeed", id, ledVal);
            return STATUS_SUCCESS;
        }
    }
	return STATUS_FAILED;
}



/*************************************************************************************/
/* JNI设备调用实现
/*************************************************************************************/
int led_dev_init(struct led_device_t** device)
{
	struct led_device_t* dev;
	dev = (struct led_device_t*) malloc(sizeof(struct led_device_t));

	if(!dev){
		LOGE("led-jni stub: failed to alloc space");
		return -EFAULT;
	}

	memset(dev, 0x00, sizeof(struct led_device_t));

	dev->open = led_open;
	dev->close = led_close;
	dev->set = led_set;

	*device = dev;

	LOGI("led-jni stub: open led device successfully");
	return STATUS_SUCCESS;
}

int led_dev_close(struct led_device_t* device)
{
	struct led_device_t* fpm_device = device;
	if(fpm_device)
	{
		LOGI("led-jni stub: %s closed",DEVICE_NAME);
		close(fpm_device->fd);
		free(fpm_device);
	}
	return 0;
}
