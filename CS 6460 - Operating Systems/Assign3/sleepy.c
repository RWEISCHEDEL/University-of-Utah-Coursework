/* cfake.c - implementation of a simple module for a character device 
 * can be used for testing, demonstrations, etc.
 */

/* ========================================================================
 * Copyright (C) 2010-2011, Institute for System Programming 
 *                          of the Russian Academy of Sciences (ISPRAS)
 * Authors: 
 *      Eugene A. Shatokhin <spectre@ispras.ru>
 *      Andrey V. Tsyvarev  <tsyvarev@ispras.ru>
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 ======================================================================== */

#include <linux/version.h>
#include <linux/module.h>
#include <linux/moduleparam.h>
#include <linux/init.h>

#include <linux/kernel.h>
#include <linux/slab.h>
#include <linux/fs.h>
#include <linux/errno.h>
#include <linux/err.h>
#include <linux/cdev.h>
#include <linux/device.h>
#include <linux/mutex.h>

#include <asm/uaccess.h>
#include <linux/param.h>
#include <linux/sched.h>

#include "sleepy.h"

MODULE_AUTHOR("Eugene A. Shatokhin, John Regehr");
MODULE_LICENSE("GPL");

#define SLEEPY_DEVICE_NAME "sleepy"

/* parameters */
static int sleepy_ndevices = SLEEPY_NDEVICES;

module_param(sleepy_ndevices, int, S_IRUGO);
/* ================================================================ */

static unsigned int sleepy_major = 0;
static struct sleepy_dev *sleepy_devices = NULL;
static struct class *sleepy_class = NULL;
/* ================================================================ */

int 
sleepy_open(struct inode *inode, struct file *filp)
{
  unsigned int mj = imajor(inode);
  unsigned int mn = iminor(inode);
	
  struct sleepy_dev *dev = NULL;
	
  if (mj != sleepy_major || mn < 0 || mn >= sleepy_ndevices)
    {
      printk(KERN_WARNING "[target] "
	     "No device found with minor=%d and major=%d\n", 
	     mj, mn);
      return -ENODEV; /* No such device */
    }
	
  /* store a pointer to struct sleepy_dev here for other methods */
  dev = &sleepy_devices[mn];
  filp->private_data = dev; 

  if (inode->i_cdev != &dev->cdev)
    {
      printk(KERN_WARNING "[target] open: internal error\n");
      return -ENODEV; /* No such device */
    }
	
  return 0;
}

int 
sleepy_release(struct inode *inode, struct file *filp)
{
  return 0;
}

ssize_t 
sleepy_read(struct file *filp, char __user *buf, size_t count, 
	    loff_t *f_pos)
{
  struct sleepy_dev *dev = (struct sleepy_dev *)filp->private_data;
  ssize_t retval = 0;
	
  if (mutex_lock_killable(&dev->sleepy_mutex))
    return -EINTR;
	
  /* YOUR CODE HERE */

  if(waitqueue_active(&dev->wait_queue) == 0){
    mutex_unlock(&dev->sleepy_mutex);
    return 0;
  }

  dev->flag = 1;

  wake_up_interruptible(&dev->wait_queue);

  int minor;

  minor = (int)iminor(filp->f_path.dentry->dinode);

  printk("SLEEPY_READ DEVICE (%d): Process is waking everyone up. \n", minor);

  if(copy_to_user(buf, dev->data, count) != 0){
    mutex_unlock(&dev->sleepy_mutex);
    return -EFAULT;
  }


  /* END YOUR CODE */
	
  mutex_unlock(&dev->sleepy_mutex);
  return retval;
}
                
ssize_t 
sleepy_write(struct file *filp, const char __user *buf, size_t count, 
	     loff_t *f_pos)
{
  struct sleepy_dev *dev = (struct sleepy_dev *)filp->private_data;
  ssize_t retval = 0;
	
  if (mutex_lock_killable(&dev->sleepy_mutex))
    return -EINTR;
	
  /* YOUR CODE HERE */

  unsigned long sleepVal;
  unsigned long wokeVal;
  unsigned ing differenceVal;
  int writeVal;
  int hasWoke = 0;

  if(count != 4){
    return -EINVAL;
  }

  if(copy_from_user(dev->data, buf, count) != 0){
    mutex_unlock(&dev->sleepy_mutex);
    return -EFAULT;
  }

  writeVal = *(int*)dev->data;

  if(writeVal < 0){
    mutex_unlock(&dev->sleepy_mutex);
    return 0;
  }

  sleepVal = jiffies;
  mutex_lock(&dev->sleepy_mutex);

  if(hasWoke == 1){
    differenceVal = (wokeVal - sleepVal) / HZ;
    retval = writeVal - differenceVal;

    int minor;
    minor = (int)iminor(filp->f_path.dentry->d_inode);
    printk("SLEEPY_WRITE DEVICE (%d): remaining = %zd \n", minor, retval);

    mutex_unlock(&dev->sleepy_mutex);
    return retval;

  }
  else{
    mutex_unlock(&dev->sleepy_mutex);
    return 0;
  }



/* END YOUR CODE */
	
  //mutex_unlock(&dev->sleepy_mutex);
  //return retval;
}

loff_t 
sleepy_llseek(struct file *filp, loff_t off, int whence)
{
  return 0;
}

struct file_operations sleepy_fops = {
  .owner =    THIS_MODULE,
  .read =     sleepy_read,
  .write =    sleepy_write,
  .open =     sleepy_open,
  .release =  sleepy_release,
  .llseek =   sleepy_llseek,
};

/* ================================================================ */
/* Setup and register the device with specific index (the index is also
 * the minor number of the device).
 * Device class should be created beforehand.
 */
static int
sleepy_construct_device(struct sleepy_dev *dev, int minor, 
			struct class *class)
{
  int err = 0;
  dev_t devno = MKDEV(sleepy_major, minor);
  struct device *device = NULL;
    
  BUG_ON(dev == NULL || class == NULL);

  /* Memory is to be allocated when the device is opened the first time */
  dev->data = NULL;     
  mutex_init(&dev->sleepy_mutex);
    
  cdev_init(&dev->cdev, &sleepy_fops);
  dev->cdev.owner = THIS_MODULE;
    
  err = cdev_add(&dev->cdev, devno, 1);
  if (err)
    {
      printk(KERN_WARNING "[target] Error %d while trying to add %s%d",
	     err, SLEEPY_DEVICE_NAME, minor);
      return err;
    }

  device = device_create(class, NULL, /* no parent device */ 
			 devno, NULL, /* no additional data */
			 SLEEPY_DEVICE_NAME "%d", minor);

  if (IS_ERR(device)) {
    err = PTR_ERR(device);
    printk(KERN_WARNING "[target] Error %d while trying to create %s%d",
	   err, SLEEPY_DEVICE_NAME, minor);
    cdev_del(&dev->cdev);
    return err;
  }
  return 0;
}

/* Destroy the device and free its buffer */
static void
sleepy_destroy_device(struct sleepy_dev *dev, int minor,
		      struct class *class)
{
  BUG_ON(dev == NULL || class == NULL);
  device_destroy(class, MKDEV(sleepy_major, minor));
  cdev_del(&dev->cdev);
  kfree(dev->data);
  return;
}

/* ================================================================ */
static void
sleepy_cleanup_module(int devices_to_destroy)
{
  int i;
	
  /* Get rid of character devices (if any exist) */
  if (sleepy_devices) {
    for (i = 0; i < devices_to_destroy; ++i) {
      sleepy_destroy_device(&sleepy_devices[i], i, sleepy_class);
    }
    kfree(sleepy_devices);
  }
    
  if (sleepy_class)
    class_destroy(sleepy_class);

  /* [NB] sleepy_cleanup_module is never called if alloc_chrdev_region()
   * has failed. */
  unregister_chrdev_region(MKDEV(sleepy_major, 0), sleepy_ndevices);
  return;
}

static int __init
sleepy_init_module(void)
{
  int err = 0;
  int i = 0;
  int devices_to_destroy = 0;
  dev_t dev = 0;
	
  if (sleepy_ndevices <= 0)
    {
      printk(KERN_WARNING "[target] Invalid value of sleepy_ndevices: %d\n", 
	     sleepy_ndevices);
      err = -EINVAL;
      return err;
    }
	
  /* Get a range of minor numbers (starting with 0) to work with */
  err = alloc_chrdev_region(&dev, 0, sleepy_ndevices, SLEEPY_DEVICE_NAME);
  if (err < 0) {
    printk(KERN_WARNING "[target] alloc_chrdev_region() failed\n");
    return err;
  }
  sleepy_major = MAJOR(dev);

  /* Create device class (before allocation of the array of devices) */
  sleepy_class = class_create(THIS_MODULE, SLEEPY_DEVICE_NAME);
  if (IS_ERR(sleepy_class)) {
    err = PTR_ERR(sleepy_class);
    goto fail;
  }
	
  /* Allocate the array of devices */
  sleepy_devices = (struct sleepy_dev *)kzalloc(
						sleepy_ndevices * sizeof(struct sleepy_dev), 
						GFP_KERNEL);
  if (sleepy_devices == NULL) {
    err = -ENOMEM;
    goto fail;
  }
	
  /* Construct devices */
  for (i = 0; i < sleepy_ndevices; ++i) {
    err = sleepy_construct_device(&sleepy_devices[i], i, sleepy_class);
    if (err) {
      devices_to_destroy = i;
      goto fail;
    }
  }
  
  printk ("sleepy module loaded\n");

  return 0; /* success */

 fail:
  sleepy_cleanup_module(devices_to_destroy);
  printk ("sleepy module failed to load\n");
  return err;
}

static void __exit
sleepy_exit_module(void)
{
  sleepy_cleanup_module(sleepy_ndevices);
  printk ("sleepy module unloaded\n");
  return;
}

module_init(sleepy_init_module);
module_exit(sleepy_exit_module);
/* ================================================================ */
