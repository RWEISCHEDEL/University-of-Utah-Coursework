/*
 *    hello.c - Simple example Loadable Kernel Module that prints
 *              output to the syslog
 *
 *    By Mark Loiseau (http://markloiseau.com)
 *
 *    Background: http://tldp.org/HOWTO/Module-HOWTO/x73.html
 */

// Defining __KERNEL__ and MODULE allows us to access kernel-level code not usually available to userspace programs.
#undef __KERNEL__
#define __KERNEL__

#undef MODULE
#define MODULE

// Linux Kernel/LKM headers: module.h is needed by all modules and kernel.h is needed for KERN_INFO.
#include <linux/module.h>	// included for all kernel modules
#include <linux/kernel.h>	// included for KERN_INFO
#include <linux/init.h>		// included for __init and __exit macros


static int __init hello_init(void)
{
    printk(KERN_INFO "Loading the hello module.\n");
    return 0;	// Non-zero return means that the module couldn't be loaded.
}

static void __exit hello_cleanup(void)
{
    printk(KERN_INFO "Unloading the hello module.\n");
}

module_init(hello_init);
module_exit(hello_cleanup);
