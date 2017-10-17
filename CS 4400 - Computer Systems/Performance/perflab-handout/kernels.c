/*******************************************
 * Solutions for the CS:APP Performance Lab
 ********************************************/

#include <stdio.h>
#include <stdlib.h>
#include "defs.h"

/* 
 * Please fill in the following student struct 
 */
student_t student = {
  "Robert Weischedel;",     /* Full name */
  "u0887905@utah.edu",  /* Email address */

};

/***************
 * ROTATE KERNEL
 ***************/

/******************************************************
 * Your different versions of the rotate kernel go here
 ******************************************************/

/* 
 * naive_pinwheel - The naive baseline version of pinwheel 
 */
char naive_pinwheel_descr[] = "naive_pinwheel: Naive baseline implementation";
void naive_pinwheel(int dim, pixel *src, pixel *dest)
{
  int i, j;

  for (i = 0; i < dim/2; i++)
    for (j = 0; j < dim/2; j++)
      dest[RIDX(dim/2 - 1 - j, i, dim)] = src[RIDX(i, j, dim)];

  for (i = 0; i < dim/2; i++)
    for (j = 0; j < dim/2; j++)
      dest[RIDX(dim/2 - 1 - j, dim/2 + i, dim)] = src[RIDX(i, dim/2 + j, dim)];

  for (i = 0; i < dim/2; i++)
    for (j = 0; j < dim/2; j++)
      dest[RIDX(dim - 1 - j, i, dim)] = src[RIDX(dim/2 + i, j, dim)];

  for (i = 0; i < dim/2; i++)
    for (j = 0; j < dim/2; j++)
      dest[RIDX(dim - 1 - j, dim/2 + i, dim)] = src[RIDX(dim/2 + i, dim/2 + j, dim)];
}

char pinwheel_a_descr[] = "Pinwheel a: Combine for loops.";
void pinwheel_a(int dim, pixel *src, pixel *dest) {

  int i, j;

  for (i = 0; i < dim/2; i++){
    for (j = 0; j < dim/2; j++){
      dest[RIDX(dim/2 - 1 - j, i, dim)] = src[RIDX(i, j, dim)];
      dest[RIDX(dim/2 - 1 - j, dim/2 + i, dim)] = src[RIDX(i, dim/2 + j, dim)];
      dest[RIDX(dim - 1 - j, i, dim)] = src[RIDX(dim/2 + i, j, dim)];
      dest[RIDX(dim - 1 - j, dim/2 + i, dim)] = src[RIDX(dim/2 + i, dim/2 + j, dim)];
    }
  }

}

char pinwheel_b_descr[] = "Pinwheel b: Combine for loops and remove incrementing values";
void pinwheel_b(int dim, pixel *src, pixel *dest) {

  int i, j;
  int dimi = dim/2;
  int dimj = dim/2;

  for (i = 0; i < dimi; i++){
    for (j = 0; j < dimj; j++){
      dest[RIDX(dim/2 - 1 - j, i, dim)] = src[RIDX(i, j, dim)];
      dest[RIDX(dim/2 - 1 - j, dim/2 + i, dim)] = src[RIDX(i, dim/2 + j, dim)];
      dest[RIDX(dim - 1 - j, i, dim)] = src[RIDX(dim/2 + i, j, dim)];
      dest[RIDX(dim - 1 - j, dim/2 + i, dim)] = src[RIDX(dim/2 + i, dim/2 + j, dim)];
    }
  }

}

char pinwheel_c_descr[] = "Pinwheel c: Unroll loop 4 times using first strcuture";
void pinwheel_c(int dim, pixel *src, pixel *dest) {

  int i, j;

  for (i = 0; i < dim/2; i+=4){
    for (j = 0; j < dim/2; j++){
      dest[RIDX(dim/2 - 1 - j, i, dim)] = src[RIDX(i, j, dim)];
      dest[RIDX(dim/2 - 1 - j, i+1, dim)] = src[RIDX(i+1, j, dim)];
      dest[RIDX(dim/2 - 1 - j, i+2, dim)] = src[RIDX(i+2, j, dim)];
      dest[RIDX(dim/2 - 1 - j, i+3, dim)] = src[RIDX(i+3, j, dim)];
    }
  }


  for (i = 0; i < dim/2; i+=4){
    for (j = 0; j < dim/2; j++){
      dest[RIDX(dim/2 - 1 - j, dim/2 + i, dim)] = src[RIDX(i, dim/2 + j, dim)];
      dest[RIDX(dim/2 - 1 - j, dim/2 + i+1, dim)] = src[RIDX(i+1, dim/2 + j, dim)];
      dest[RIDX(dim/2 - 1 - j, dim/2 + i+2, dim)] = src[RIDX(i+2, dim/2 + j, dim)];
      dest[RIDX(dim/2 - 1 - j, dim/2 + i+3, dim)] = src[RIDX(i+3, dim/2 + j, dim)];
    }
  }

  for (i = 0; i < dim/2; i+=4){
    for (j = 0; j < dim/2; j++){
      dest[RIDX(dim - 1 - j, i, dim)] = src[RIDX(dim/2 + i, j, dim)];
      dest[RIDX(dim - 1 - j, i+1, dim)] = src[RIDX(dim/2 + i+1, j, dim)];
      dest[RIDX(dim - 1 - j, i+2, dim)] = src[RIDX(dim/2 + i+2, j, dim)];
      dest[RIDX(dim - 1 - j, i+3, dim)] = src[RIDX(dim/2 + i+3, j, dim)];
    }
  }

  for (i = 0; i < dim/2; i+=4){
    for (j = 0; j < dim/2; j++){
      dest[RIDX(dim - 1 - j, dim/2 + i, dim)] = src[RIDX(dim/2 + i, dim/2 + j, dim)];
      dest[RIDX(dim - 1 - j, dim/2 + i+1, dim)] = src[RIDX(dim/2 + i+1, dim/2 + j, dim)];
      dest[RIDX(dim - 1 - j, dim/2 + i+2, dim)] = src[RIDX(dim/2 + i+2, dim/2 + j, dim)];
      dest[RIDX(dim - 1 - j, dim/2 + i+3, dim)] = src[RIDX(dim/2 + i+3, dim/2 + j, dim)];
    }
  }

}

/*
 * rotate - Your current working version of pinwheel
 * IMPORTANT: This is the version you will be graded on
 */
char pinwheel_descr[] = "pinwheel: Current working version";
void pinwheel(int dim, pixel *src, pixel *dest)
{
  pinwheel_c(dim, src, dest);
}

/*********************************************************************
 * register_pinwheel_functions - Register all of your different versions
 *     of the pinwheel kernel with the driver by calling the
 *     add_pinwheel_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_pinwheel_functions() {
  add_pinwheel_function(&pinwheel, pinwheel_descr);
  add_pinwheel_function(&naive_pinwheel, naive_pinwheel_descr);
  add_pinwheel_function(&pinwheel_a, pinwheel_a_descr);
  add_pinwheel_function(&pinwheel_b, pinwheel_b_descr);
  add_pinwheel_function(&pinwheel_c, pinwheel_c_descr);
}


/***************
 * SMOOTH KERNEL
 **************/

/***************************************************************
 * Various typedefs and helper functions for the smooth function
 * You may modify these any way you like.
 **************************************************************/

/* A struct used to compute averaged pixel value */
typedef struct {
  int red;
  int green;
  int blue;
} pixel_sum;

/*
 * initialize_pixel_sum - Initializes all fields of sum to 0
 */
static void initialize_pixel_sum(pixel_sum *sum)
{
  sum->red = sum->green = sum->blue = 0;
  return;
}

/*
 * accumulate_sum - Accumulates field values of p in corresponding
 * fields of sum
 */
static void accumulate_weighted_sum(pixel_sum *sum, pixel p, double weight)
{
  sum->red += (int) p.red * weight;
  sum->green += (int) p.green * weight;
  sum->blue += (int) p.blue * weight;
  return;
}

/*
 * assign_sum_to_pixel - Computes averaged pixel value in current_pixel
 */
static void assign_sum_to_pixel(pixel *current_pixel, pixel_sum sum)
{
  current_pixel->red = (unsigned short)sum.red;
  current_pixel->green = (unsigned short)sum.green;
  current_pixel->blue = (unsigned short)sum.blue;
  return;
}

/*
 * weighted_combo - Returns new pixel value at (i,j)
 */
static pixel weighted_combo(int dim, int i, int j, pixel *src)
{
  int ii, jj;
  pixel_sum sum;
  pixel current_pixel;
  double weights[3][3] = { { 0.50, 0.03125, 0.00 },
                           { 0.03125, 0.25, 0.03125 },
                           { 0.00, 0.03125, 0.125 } };

  initialize_pixel_sum(&sum);
  for(ii=0; ii < 3; ii++)
    for(jj=0; jj < 3; jj++)
      if ((i + ii < dim) && (j + jj < dim))
        accumulate_weighted_sum(&sum,
                                src[RIDX(i+ii,j+jj,dim)],
                                weights[ii][jj]);

  assign_sum_to_pixel(&current_pixel, sum);

  return current_pixel;
}

/*
 * Improved upon the original weighted combo idea, in order to make it much more efficent. Uses bitshifting and eliminates the nested for loops.
 */
static pixel improved_weighted_combo(int dim, int i, int j, pixel *src)
{
  pixel_sum sum;
  pixel current_pixel;

  initialize_pixel_sum(&sum);
  if ((i + 0 < dim) && (j + 0 < dim)) {
    pixel p = src[RIDX(i+0,j+0,dim)];
    sum.red += (int)p.red >> 1;
    sum.green += (int)p.green >> 1;
    sum.blue += (int)p.blue >> 1;
  }
  if ((i + 0 < dim) && (j + 1 < dim)) {
    pixel p = src[RIDX(i+0,j+1,dim)];
    sum.red += (int)p.red >> 5;
    sum.green += (int)p.green >> 5;
    sum.blue += (int)p.blue >> 5;
  }
  if ((i + 0 < dim) && (j + 2 < dim)) {
    sum.red += 0;
    sum.green += 0;
    sum.blue += 0;
  }
  if ((i + 1 < dim) && (j + 0 < dim)) {
    pixel p = src[RIDX(i+1,j+0,dim)];
    sum.red += (int)p.red >> 5;
    sum.green += (int)p.green >> 5;
    sum.blue += (int)p.blue >> 5;
  }
  if ((i + 1 < dim) && (j + 1 < dim)) {
    pixel p = src[RIDX(i+1,j+1,dim)];
    sum.red += (int)p.red >> 2;
    sum.green += (int)p.green >> 2;
    sum.blue += (int)p.blue >> 2;
  }
  if ((i + 1 < dim) && (j + 2 < dim)) {
    pixel p = src[RIDX(i+1,j+2,dim)];
    sum.red += (int)p.red >> 5;
    sum.green += (int)p.green >> 5;
    sum.blue += (int)p.blue >> 5;
  }
  if ((i + 2 < dim) && (j + 0 < dim)) {
    sum.red += 0;
    sum.green += 0;
    sum.blue += 0;
  }
  if ((i + 2 < dim) && (j + 1 < dim)) {
    pixel p = src[RIDX(i+2,j+1,dim)];
    sum.red += (int) p.red >> 5;
    sum.green += (int) p.green >> 5;
    sum.blue += (int) p.blue >> 5;
  }
  if ((i + 2 < dim) && (j + 2 < dim)) {
    pixel p = src[RIDX(i+2,j+2,dim)];
    sum.red += (int)p.red >> 3;
    sum.green += (int)p.green >> 3;
    sum.blue += (int)p.blue >> 3;
  }

  assign_sum_to_pixel(&current_pixel, sum);

  return current_pixel;
}

/******************************************************
 * Your different versions of the motion kernel go here
 ******************************************************/

/*
 * naive_motion - The naive baseline version of motion
 */
char naive_motion_descr[] = "naive_motion: Naive baseline implementation";
void naive_motion(int dim, pixel *src, pixel *dst)
{
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++)
      dst[RIDX(i, j, dim)] = weighted_combo(dim, i, j, src);
}

/*
 * Attempt 1 - not good 1.0
 */
char motion_a_descr[] = "Motion a: Unroll both loops by 2";
void motion_a(int dim, pixel *src, pixel *dst)
{
    int i, j;
    int olimit = dim - 1;
    int ilimit = dim - 1;

    for (i = 0; i < olimit; i += 2) {
        for (j = 0; j < ilimit; j += 2) {
          dst[RIDX(i, j,   dim)] = weighted_combo(dim, i, j,   src);
          dst[RIDX(i, j+1, dim)] = weighted_combo(dim, i, j+1, src);

          dst[RIDX(i+1, j,   dim)] = weighted_combo(dim, i+1, j,   src);
          dst[RIDX(i+1, j+1, dim)] = weighted_combo(dim, i+1, j+1, src);
        }
    }
}

/*
 * Attempt 2 - not any better 1.0
 */
char motion_b_descr[] = "Motion b: Unroll both loops by 4";
void motion_b(int dim, pixel *src, pixel *dst)
{
    int i, j;
    int olimit = dim - 3;
    int ilimit = dim - 3;

    for (i = 0; i < olimit; i += 4) {
        for (j = 0; j < ilimit; j += 4) {
          dst[RIDX(i, j,   dim)] = weighted_combo(dim, i, j,   src);
          dst[RIDX(i, j+1, dim)] = weighted_combo(dim, i, j+1, src);
          dst[RIDX(i, j+2, dim)] = weighted_combo(dim, i, j+2, src);
          dst[RIDX(i, j+3, dim)] = weighted_combo(dim, i, j+3, src);

          dst[RIDX(i+1, j,   dim)] = weighted_combo(dim, i+1, j,   src);
          dst[RIDX(i+1, j+1, dim)] = weighted_combo(dim, i+1, j+1, src);
          dst[RIDX(i+1, j+2, dim)] = weighted_combo(dim, i+1, j+2, src);
          dst[RIDX(i+1, j+3, dim)] = weighted_combo(dim, i+1, j+3, src);

          dst[RIDX(i+2, j,   dim)] = weighted_combo(dim, i+2, j,   src);
          dst[RIDX(i+2, j+1, dim)] = weighted_combo(dim, i+2, j+1, src);
          dst[RIDX(i+2, j+2, dim)] = weighted_combo(dim, i+2, j+2, src);
          dst[RIDX(i+2, j+3, dim)] = weighted_combo(dim, i+2, j+3, src);

          dst[RIDX(i+3, j,   dim)] = weighted_combo(dim, i+3, j,   src);
          dst[RIDX(i+3, j+1, dim)] = weighted_combo(dim, i+3, j+1, src);
          dst[RIDX(i+3, j+2, dim)] = weighted_combo(dim, i+3, j+2, src);
          dst[RIDX(i+3, j+3, dim)] = weighted_combo(dim, i+3, j+3, src);
        }
    }
}

/*
 * Attempt 3 - Huge unsuccessful mess, only 1.2
 */
char motion_c_descr[] = "Motion C: Loop unrolling to the max! And also combine with weighted combo to improve performance. ";
void motion_c(int dim, pixel *src, pixel *dst)
{
  int i, j;
  pixel_sum sum;
  pixel current_pixel;
  double weights[9] = {0.50, 0.03125, 0.00, 0.03125, 0.25, 0.03125, 0.00, 0.03125, 0.125};

  for (i = 0; i < dim; i+=4){
    for (j = 0; j < dim; j+=2){

      sum.red = sum.green = sum.blue = 0;

      if((i + 0 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX(i + 0, j + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX(i + 0, j + 0, dim)].green * weights[0];
        sum.blue += (int)  src[RIDX(i + 0, j + 0, dim)].blue * weights[0];
      }

      if((i + 0 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
        sum.red += (int)src[RIDX(i + 0, j + 1, dim)].red * weights[1];
        sum.green += (int)src[RIDX(i + 0, j + 1, dim)].green * weights[1];
        sum.blue += (int)src[RIDX(i + 0, j + 1, dim)].blue * weights[1];
      }

      if((i + 0 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX(i + 0, j + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX(i + 0, j + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX(i + 0, j + 2, dim)].blue * weights[2];
      }

      if((i + 1 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX(i + 1, j + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX(i + 1, j + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX(i + 1, j + 0, dim)].blue * weights[3];

      }

      if((i + 1 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX(i + 1, j + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX(i + 1, j + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX(i + 1, j + 1, dim)].blue * weights[4];
      }

      if((i + 1 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX(i + 1, j + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX(i + 1, j + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX(i + 1, j + 2, dim)].blue * weights[5];
      }

      if((i + 2 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX(i + 2, j + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX(i + 2, j + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX(i + 2, j + 0, dim)].blue * weights[6];
      }

      if((i + 2 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX(i + 2, j + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX(i + 2, j + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX(i + 2, j + 1, dim)].blue * weights[7];
      }

      if((i + 2 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX(i + 2, j + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX(i + 2, j + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX(i + 2, j + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX(i, j, dim)] = current_pixel;

      sum.red = sum.green = sum.blue = 0;

      if((i + 0 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX(i + 0, (j+1) + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX(i + 0, (j+1) + 0, dim)].green * weights[0];
        sum.blue += (int)src[RIDX(i + 0, (j+1) + 0, dim)].blue * weights[0];
      }

      if((i + 0 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
        sum.red += (int)src[RIDX(i + 0, (j+1) + 1, dim)].red * weights[1];
        sum.green += (int)src[RIDX(i + 0, (j+1) + 1, dim)].green * weights[1];
        sum.blue += (int)src[RIDX(i + 0, (j+1) + 1, dim)].blue * weights[1];
      }

      if((i + 0 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX(i + 0, (j+1) + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX(i + 0, (j+1) + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX(i + 0, (j+1) + 2, dim)].blue * weights[2];
      }

      if((i + 1 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX(i + 1, (j+1) + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX(i + 1, (j+1) + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX(i + 1, (j+1) + 0, dim)].blue * weights[3];

      }

      if((i + 1 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX(i + 1, (j+1) + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX(i + 1, (j+1) + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX(i + 1, (j+1) + 1, dim)].blue * weights[4];
      }

      if((i + 1 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX(i + 1, (j+1) + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX(i + 1, (j+1) + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX(i + 1, (j+1) + 2, dim)].blue * weights[5];
      }

      if((i + 2 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX(i + 2, (j+1) + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX(i + 2, (j+1) + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX(i + 2, (j+1) + 0, dim)].blue * weights[6];
      }

      if((i + 2 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX(i + 2, (j+1) + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX(i + 2, (j+1) + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX(i + 2, (j+1) + 1, dim)].blue * weights[7];
      }

      if((i + 2 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX(i + 2, (j+1) + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX(i + 2, (j+1) + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX(i + 2, (j+1) + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX(i, (j+1), dim)] = current_pixel;

      // part 2

       sum.red = sum.green = sum.blue = 0;

      if(((i+1) + 0 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX((i+1) + 0, j + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX((i+1) + 0, j + 0, dim)].green * weights[0];
        sum.blue += (int)  src[RIDX((i+1) + 0, j + 0, dim)].blue * weights[0];
      }

      if(((i+1) + 0 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
      sum.red += (int)src[RIDX((i+1) + 0, j + 1, dim)].red * weights[1];
      sum.green += (int)src[RIDX((i+1) + 0, j + 1, dim)].green * weights[1];
      sum.blue += (int)src[RIDX((i+1) + 0, j + 1, dim)].blue * weights[1];
      }

      if(((i+1) + 0 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX((i+1) + 0, j + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX((i+1) + 0, j + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX((i+1) + 0, j + 2, dim)].blue * weights[2];
      }

      if(((i+1) + 1 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX((i+1) + 1, j + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX((i+1) + 1, j + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX((i+1) + 1, j + 0, dim)].blue * weights[3];

      }

      if(((i+1) + 1 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX((i+1) + 1, j + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX((i+1) + 1, j + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX((i+1) + 1, j + 1, dim)].blue * weights[4];
      }

      if(((i+1) + 1 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX((i+1) + 1, j + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX((i+1) + 1, j + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX((i+1) + 1, j + 2, dim)].blue * weights[5];
      }

      if(((i+1) + 2 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX((i+1) + 2, j + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX((i+1) + 2, j + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX((i+1) + 2, j + 0, dim)].blue * weights[6];
      }

      if(((i+1) + 2 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX((i+1) + 2, j + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX((i+1) + 2, j + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX((i+1) + 2, j + 1, dim)].blue * weights[7];
      }

      if(((i+1) + 2 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX((i+1) + 2, j + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX((i+1) + 2, j + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX((i+1) + 2, j + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX((i+1), j, dim)] = current_pixel;

      sum.red = sum.green = sum.blue = 0;

      if(((i+1) + 0 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX((i+1) + 0, (j+1) + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX((i+1) + 0, (j+1) + 0, dim)].green * weights[0];
        sum.blue += (int)src[RIDX((i+1) + 0, (j+1) + 0, dim)].blue * weights[0];
      }

      if(((i+1) + 0 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
        sum.red += (int)src[RIDX((i+1) + 0, (j+1) + 1, dim)].red * weights[1];
        sum.green += (int)src[RIDX((i+1) + 0, (j+1) + 1, dim)].green * weights[1];
        sum.blue += (int)src[RIDX((i+1) + 0, (j+1) + 1, dim)].blue * weights[1];
      }

      if(((i+1) + 0 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX((i+1) + 0, (j+1) + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX((i+1) + 0, (j+1) + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX((i+1) + 0, (j+1) + 2, dim)].blue * weights[2];
      }

      if(((i+1) + 1 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX((i+1) + 1, (j+1) + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX((i+1) + 1, (j+1) + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX((i+1) + 1, (j+1) + 0, dim)].blue * weights[3];

      }

      if(((i+1) + 1 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX((i+1) + 1, (j+1) + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX((i+1) + 1, (j+1) + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX((i+1) + 1, (j+1) + 1, dim)].blue * weights[4];
      }

      if(((i+1) + 1 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX((i+1) + 1, (j+1) + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX((i+1) + 1, (j+1) + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX((i+1) + 1, (j+1) + 2, dim)].blue * weights[5];
      }

      if(((i+1) + 2 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX((i+1) + 2, (j+1) + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX((i+1) + 2, (j+1) + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX((i+1) + 2, (j+1) + 0, dim)].blue * weights[6];
      }

      if(((i+1) + 2 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX((i+1) + 2, (j+1) + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX((i+1) + 2, (j+1) + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX((i+1) + 2, (j+1) + 1, dim)].blue * weights[7];
      }

      if(((i+1) + 2 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX((i+1) + 2, (j+1) + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX((i+1) + 2, (j+1) + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX((i+1) + 2, (j+1) + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX((i+1), (j+1), dim)] = current_pixel;

      //part 3

      sum.red = sum.green = sum.blue = 0;

      if(((i+2) + 0 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX((i+2) + 0, j + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX((i+2) + 0, j + 0, dim)].green * weights[0];
        sum.blue += (int)  src[RIDX((i+2) + 0, j + 0, dim)].blue * weights[0];
      }

      if(((i+2) + 0 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
      sum.red += (int)src[RIDX((i+2) + 0, j + 1, dim)].red * weights[1];
      sum.green += (int)src[RIDX((i+2) + 0, j + 1, dim)].green * weights[1];
      sum.blue += (int)src[RIDX((i+2) + 0, j + 1, dim)].blue * weights[1];
      }

      if(((i+2) + 0 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX((i+2) + 0, j + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX((i+2) + 0, j + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX((i+2) + 0, j + 2, dim)].blue * weights[2];
      }

      if(((i+2) + 1 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX((i+2) + 1, j + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX((i+2) + 1, j + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX((i+2) + 1, j + 0, dim)].blue * weights[3];

      }

      if(((i+2) + 1 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX((i+2) + 1, j + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX((i+2) + 1, j + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX((i+2) + 1, j + 1, dim)].blue * weights[4];
      }

      if(((i+2) + 1 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX((i+2) + 1, j + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX((i+2) + 1, j + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX((i+2) + 1, j + 2, dim)].blue * weights[5];
      }

      if(((i+2) + 2 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX((i+2) + 2, j + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX((i+2) + 2, j + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX((i+2) + 2, j + 0, dim)].blue * weights[6];
      }

      if(((i+2) + 2 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX((i+2) + 2, j + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX((i+2) + 2, j + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX((i+2) + 2, j + 1, dim)].blue * weights[7];
      }

      if(((i+2) + 2 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX((i+2) + 2, j + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX((i+2) + 2, j + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX((i+2) + 2, j + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX((i+2), j, dim)] = current_pixel;

       sum.red = sum.green = sum.blue = 0;

       if(((i+2) + 0 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
         sum.red += (int)src[RIDX((i+2) + 0, (j+1) + 0, dim)].red * weights[0];
         sum.green += (int)src[RIDX((i+2) + 0, (j+1) + 0, dim)].green * weights[0];
         sum.blue += (int)src[RIDX((i+2) + 0, (j+1) + 0, dim)].blue * weights[0];
      }

       if(((i+2) + 0 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
         sum.red += (int)src[RIDX((i+2) + 0, (j+1) + 1, dim)].red * weights[1];
         sum.green += (int)src[RIDX((i+2) + 0, (j+1) + 1, dim)].green * weights[1];
         sum.blue += (int)src[RIDX((i+2) + 0, (j+1) + 1, dim)].blue * weights[1];
      }

       if(((i+2) + 0 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
         sum.red += (int)src[RIDX((i+2) + 0, (j+1) + 2, dim)].red * weights[2];
         sum.green += (int)src[RIDX((i+2) + 0, (j+1) + 2, dim)].green * weights[2];
         sum.blue += (int)src[RIDX((i+2) + 0, (j+1) + 2, dim)].blue * weights[2];
      }

       if(((i+2) + 1 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

         sum.red += (int)src[RIDX((i+2) + 1, (j+1) + 0, dim)].red * weights[3];
         sum.green += (int)src[RIDX((i+2) + 1, (j+1) + 0, dim)].green * weights[3];
         sum.blue += (int)src[RIDX((i+2) + 1, (j+1) + 0, dim)].blue * weights[3];

      }

       if(((i+2) + 1 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

         sum.red += (int)src[RIDX((i+2) + 1, (j+1) + 1, dim)].red * weights[4];
         sum.green += (int)src[RIDX((i+2) + 1, (j+1) + 1, dim)].green * weights[4];
         sum.blue += (int)src[RIDX((i+2) + 1, (j+1) + 1, dim)].blue * weights[4];
      }

       if(((i+2) + 1 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

         sum.red += (int)src[RIDX((i+2) + 1, (j+1) + 2, dim)].red * weights[5];
         sum.green += (int)src[RIDX((i+2) + 1, (j+1) + 2, dim)].green * weights[5];
         sum.blue += (int)src[RIDX((i+2) + 1, (j+1) + 2, dim)].blue * weights[5];
      }

       if(((i+2) + 2 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

         sum.red += (int)src[RIDX((i+2) + 2, (j+1) + 0, dim)].red * weights[6];
         sum.green += (int)src[RIDX((i+2) + 2, (j+1) + 0, dim)].green * weights[6];
         sum.blue += (int)src[RIDX((i+2) + 2, (j+1) + 0, dim)].blue * weights[6];
      }

       if(((i+2) + 2 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
         sum.red += (int)src[RIDX((i+2) + 2, (j+1) + 1, dim)].red * weights[7];
         sum.green += (int)src[RIDX((i+2) + 2, (j+1) + 1, dim)].green * weights[7];
         sum.blue += (int)src[RIDX((i+2) + 2, (j+1) + 1, dim)].blue * weights[7];
      }

       if(((i+2) + 2 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

         sum.red += (int)src[RIDX((i+2) + 2, (j+1) + 2, dim)].red * weights[8];
         sum.green += (int)src[RIDX((i+2) + 2, (j+1) + 2, dim)].green * weights[8];
         sum.blue += (int)src[RIDX((i+2) + 2, (j+1) + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX((i+2), (j+1), dim)] = current_pixel;

      //part 4

      sum.red = sum.green = sum.blue = 0;

      if(((i+3) + 0 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX((i+3) + 0, j + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX((i+3) + 0, j + 0, dim)].green * weights[0];
        sum.blue += (int)  src[RIDX((i+3) + 0, j + 0, dim)].blue * weights[0];
      }

      if(((i+3) + 0 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
      sum.red += (int)src[RIDX((i+3) + 0, j + 1, dim)].red * weights[1];
      sum.green += (int)src[RIDX((i+3) + 0, j + 1, dim)].green * weights[1];
      sum.blue += (int)src[RIDX((i+3) + 0, j + 1, dim)].blue * weights[1];
      }

      if(((i+3) + 0 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX((i+3) + 0, j + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX((i+3) + 0, j + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX((i+3) + 0, j + 2, dim)].blue * weights[2];
      }

      if(((i+3) + 1 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX((i+3) + 1, j + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX((i+3) + 1, j + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX((i+3) + 1, j + 0, dim)].blue * weights[3];

      }

      if(((i+3) + 1 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX((i+3) + 1, j + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX((i+3) + 1, j + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX((i+3) + 1, j + 1, dim)].blue * weights[4];
      }

      if(((i+3) + 1 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX((i+3) + 1, j + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX((i+3) + 1, j + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX((i+3) + 1, j + 2, dim)].blue * weights[5];
      }

      if(((i+3) + 2 < dim) && (j + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX((i+3) + 2, j + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX((i+3) + 2, j + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX((i+3) + 2, j + 0, dim)].blue * weights[6];
      }

      if(((i+3) + 2 < dim) && (j + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX((i+3) + 2, j + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX((i+3) + 2, j + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX((i+3) + 2, j + 1, dim)].blue * weights[7];
      }

      if(((i+3) + 2 < dim) && (j + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX((i+3) + 2, j + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX((i+3) + 2, j + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX((i+3) + 2, j + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX((i+3), j, dim)] = current_pixel;

      sum.red = sum.green = sum.blue = 0;

      if(((i+3) + 0 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 0, dim)], weights[0]);
        sum.red += (int)src[RIDX((i+3) + 0, (j+1) + 0, dim)].red * weights[0];
        sum.green += (int)src[RIDX((i+3) + 0, (j+1) + 0, dim)].green * weights[0];
        sum.blue += (int)src[RIDX((i+3) + 0, (j+1) + 0, dim)].blue * weights[0];
      }

      if(((i+3) + 0 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 1, dim)], weights[1]);
        sum.red += (int)src[RIDX((i+3) + 0, (j+1) + 1, dim)].red * weights[1];
        sum.green += (int)src[RIDX((i+3) + 0, (j+1) + 1, dim)].green * weights[1];
        sum.blue += (int)src[RIDX((i+3) + 0, (j+1) + 1, dim)].blue * weights[1];
      }

      if(((i+3) + 0 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 0, j + 2, dim)], weights[2]);
        sum.red += (int)src[RIDX((i+3) + 0, (j+1) + 2, dim)].red * weights[2];
        sum.green += (int)src[RIDX((i+3) + 0, (j+1) + 2, dim)].green * weights[2];
        sum.blue += (int)src[RIDX((i+3) + 0, (j+1) + 2, dim)].blue * weights[2];
      }

      if(((i+3) + 1 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 0, dim)], weights[3]);

        sum.red += (int)src[RIDX((i+3) + 1, (j+1) + 0, dim)].red * weights[3];
        sum.green += (int)src[RIDX((i+3) + 1, (j+1) + 0, dim)].green * weights[3];
        sum.blue += (int)src[RIDX((i+3) + 1, (j+1) + 0, dim)].blue * weights[3];

      }

      if(((i+3) + 1 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 1, dim)], weights[4]);

        sum.red += (int)src[RIDX((i+3) + 1, (j+1) + 1, dim)].red * weights[4];
        sum.green += (int)src[RIDX((i+3) + 1, (j+1) + 1, dim)].green * weights[4];
        sum.blue += (int)src[RIDX((i+3) + 1, (j+1) + 1, dim)].blue * weights[4];
      }

      if(((i+3) + 1 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 1, j + 2, dim)], weights[5]);

        sum.red += (int)src[RIDX((i+3) + 1, (j+1) + 2, dim)].red * weights[5];
        sum.green += (int)src[RIDX((i+3) + 1, (j+1) + 2, dim)].green * weights[5];
        sum.blue += (int)src[RIDX((i+3) + 1, (j+1) + 2, dim)].blue * weights[5];
      }

      if(((i+3) + 2 < dim) && ((j+1) + 0 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 0, dim)], weights[6]);

        sum.red += (int)src[RIDX((i+3) + 2, (j+1) + 0, dim)].red * weights[6];
        sum.green += (int)src[RIDX((i+3) + 2, (j+1) + 0, dim)].green * weights[6];
        sum.blue += (int)src[RIDX((i+3) + 2, (j+1) + 0, dim)].blue * weights[6];
      }

      if(((i+3) + 2 < dim) && ((j+1) + 1 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 1, dim)], weights[7]);
        sum.red += (int)src[RIDX((i+3) + 2, (j+1) + 1, dim)].red * weights[7];
        sum.green += (int)src[RIDX((i+3) + 2, (j+1) + 1, dim)].green * weights[7];
        sum.blue += (int)src[RIDX((i+3) + 2, (j+1) + 1, dim)].blue * weights[7];
      }

      if(((i+3) + 2 < dim) && ((j+1) + 2 < dim)){
        //accumulate_weighted_sum(&sum, src[RIDX(i + 2, j + 2, dim)], weights[8]);

        sum.red += (int)src[RIDX((i+3) + 2, (j+1) + 2, dim)].red * weights[8];
        sum.green += (int)src[RIDX((i+3) + 2, (j+1) + 2, dim)].green * weights[8];
        sum.blue += (int)src[RIDX((i+3) + 2, (j+1) + 2, dim)].blue * weights[8];

      }

      //assign_sum_to_pixel(&current_pixel, sum);

      current_pixel.red = (unsigned short)sum.red;
      current_pixel.green = (unsigned short)sum.green;
      current_pixel.blue = (unsigned short)sum.blue;

      dst[RIDX((i+3), (j+1), dim)] = current_pixel;
    }
  }
}

/*
 * Attempt 4 - Much better! 8.5
 */
char motion_d_descr[] = "Same as original naive method, but now using an improved weighted combo method.";
void motion_d(int dim, pixel *src, pixel *dst)
{
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++)
      dst[RIDX(i, j, dim)] = improved_weighted_combo(dim, i, j, src);
}


/*
 * motion - Your current working version of motion.
 * IMPORTANT: This is the version you will be graded on
 */
char motion_descr[] = "motion: Current working version";
void motion(int dim, pixel *src, pixel *dst)
{
  motion_d(dim, src, dst);
}

/*********************************************************************
 * register_motion_functions - Register all of your different versions
 *     of the motion kernel with the driver by calling the
 *     add_motion_function() for each test function.  When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_motion_functions() {
  add_motion_function(&motion, motion_descr);
  add_motion_function(&naive_motion, naive_motion_descr);
  add_motion_function(&motion_a, motion_a_descr);
  add_motion_function(&motion_b, motion_b_descr);
  add_motion_function(&motion_c, motion_c_descr);
  add_motion_function(&motion_d, motion_d_descr);
}
