/* shady.h */

#ifndef SHADY_H_1727_INCLUDED
#define SHADY_H_1727_INCLUDED

/* Number of devices to create (default: shady0 and shady1) */
#ifndef SHADY_NDEVICES
#define SHADY_NDEVICES 1
#endif

/* The structure to represent 'shady' devices. 
 *  data - data buffer;
 *  buffer_size - size of the data buffer;
 *  block_size - maximum number of bytes that can be read or written 
 *    in one call;
 *  shady_mutex - a mutex to protect the fields of this structure;
 *  cdev - ñharacter device structure.
 */
struct shady_dev {
  unsigned char *data;
  struct mutex shady_mutex; 
  struct cdev cdev;
};
#endif /* SHADY_H_1727_INCLUDED */
