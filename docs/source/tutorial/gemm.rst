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

		// TODO: 
		
		Accel {}
		
      }

