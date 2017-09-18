3. Differentiator & Sobel Filter
================================


Catalog of Features
-------------------

In this section, you will learn about the following components in Spatial:

 - LineBuffer 
 
 - ShiftRegister
 
 - LUT

 - Spatial Functions and Multifile Projects

Application Overview
--------------------

Convolution is a common algorithm in linear algebra, machine learning,
statistics, and many other domains.  The tutorials in this section will
demonstrate how to use the building blocks that Spatial provides to do
convolutions.

Specifically, we will build a basic differentiator for a time-series
using sliding window averaging for an example 1D convolution.  The animation below
demonstrates this convolution.

.. image:: conv1d.gif

We will then build a Sobel Filter to detect edges on an image for an example of 2D convolution.
While this section will not explore convolutions in more than 2 dimensions,
it is possible to combine the Spatial primitives demonstrated in this section and previous
sections to build up a higher-dimensional convolution.  The animation below demonstrates
the 2D convolution with the padding that we will use (credit [vdumoulin](https://github.com/vdumoulin/conv_arithmetic)).

.. image:: conv2d.gif

Data Setup and Validation
-------------------------

Let's start by creating the data structures above the Accel that we will set up the image and
filters and compute the gold check. We will expose the rows of the images as command-line arguments.::
	
    import spatial.dsl._
    import org.virtualized._

    object Convolution extends SpatialApp {

      @virtualize
      def main() {

		type T = FixPt[TRUE,_16,_16]

		val R = args(0).to[Int]
		val C = args(1).to[Int]
	    val ROWS = ArgIn[Int]
	    val COLS = ArgIn[Int]
	    setArg(ROWS,R)
	    setArg(COLS,C)

	    val window = 16
	    val x_t = Array.tabulate(C){i => 
	    	val x = i.to[T] * (4.to[T] / args(1).to[T]) - 2
	    	-0.18.to[T] * pow(x, 4) + 0.5.to[T] * pow(x, 2) + 0.8.to[T]
	    }
	    val h_t = Array.tabulate(16){i => if (i < window/2) -1.to[T] else 1.to[T]}

	    val X_1D = DRAM[T](COLS)
	    val H_1D = DRAM[T](window)
	    val Y_1D = DRAM[T](COLS)
	    setMem(X_1D, x_t)
	    setMem(H_1D, h_t)

	    val border = 3
	    val image = (0::R, 0::C){(i,j) => if (j > border && j < C-border && i > border && i < C - border) (i*16).to[T] else 0.to[T]}
	    val X_2D = DRAM[T](ROWS, COLS)
	    val Y_2D = DRAM[T](ROWS, COLS)
	    setMem(X_2D, image)

	    Accel{}

	    val Y_1D_result = getMem(Y_1D)
	    val Y_2D_result = getMatrix(Y_2D)






