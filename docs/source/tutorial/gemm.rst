3. General Matrix Multiply (GEMM)
=================================

Catalog of Features
-------------------

In this section, you will learn about the following components in Spatial:

 - MemReduce and MemFold

 - Instrumentation Hooks

 - Advanced Banking
 

Application Overview
--------------------

General Matrix Multiply (GEMM) is a common algorithm in linear algebra, machine learning,
statistics, and many other domains.  It provides a more interesting trade-off space than
the previous tutorial, as there are many ways to break up the computation.  This includes
using blocking, inner products, outer products, and systolic array techniques.  In this tutorial,
we will demonstrate how to build a blocked GEMM app that uses outer products, and leave it to the
user to try and build a GEMM version that uses inner products.  Later tutorials will show how
to use shift registers and systolic arrays in other applications, but the same techniques can
be retroactively applied to this tutorial on GEMM as well.


Data Setup and Validation
-------------------------

Let's start by creating the data structures above the Accel that we will set up the matrices and compute the 
gold check. We will expose the dimensions of the matrices as command-line arguments. ::
	
    import spatial.dsl._
    import org.virtualized._

    object GEMM extends SpatialApp {

      @virtualize
      def main() {

		type T = FixPt[TRUE,_24,_8]

		// MxP * PxN = MxN 
	    val M = ArgIn[Int]
	    val N = ArgIn[Int]
	    val P = ArgIn[Int]
	    setArg(M,args(0).to[Int])
	    setArg(N,args(1).to[Int])
	    setArg(P,args(2).to[Int])

	    val a_data = (0::args(0), 0::args(2)){(i,j) => random[T](3)}
	    val b_data = (0::args(2), 0::args(1)){(i,j) => random[T](3)}
	    val c_init = (0::args(0), 0::args(1)){(i,j) => 0.to[T]}
	    val a = DRAM[T](M, P)
	    val b = DRAM[T](P, N)
	    val c = DRAM[T](M, N)

	    setMem(a, a_data)
	    setMem(b, b_data)
	    setMem(c, c_init)

		Accel {}

		val accel_matrix = getMatrix(c)

		// Check is computed with inner products since it is easier to write this in parallel patterns
		val gold_matrix = (0::args(0), 0::args(1)){(i,j) => 
			Array.tabulate(args(2)){k => a_data(i,k) * b_data(k,j)}.reduce{_+_}
		}

		// Print results and cksum
		printMatrix(accel_matrix, "Received: ")
		printMatrix(gold_matrix, "Wanted: ")
		val cksum = accel_matrix.zip(gold_matrix){_==_}.reduce{_&&_}
		println("Pass? " + cksum)

		
      }

Notice that we create an initial matrix for the result and set all values to 0.  This is necessary
because GEMM using outer products computes part of a tile of the result and accumulates this on top 
of what was previously in that tile.  This means we will need to fetch a tile from off-chip DRAM
and accumulate a new result on top of that, then write this new tile back.


MemReduce and MemFold
---------------------

The animation below shows how to compute GEMM without tiling, using outer products.

.. image:: gemmfull.gif

Because we cannot create hardware to handle variable-sized matrices, we must tile the problem.
The animation below shows one valid scheme for doing so.  We will set our tile sizes in the
M, N, and K dimensions above the Accel as follows::
	
	val tileM = 16
	val tileN = 16
	val tileK = 16


.. image:: gemmtiled.gif

