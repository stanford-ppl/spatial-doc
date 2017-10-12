3. General Matrix Multiply (GEMM)
=================================

Catalog of Features
-------------------

In this section, you will learn about the following components in Spatial:

 - MemReduce and MemFold

 - Instrumentation Hooks

 - Advanced Banking

 - Advanced Buffering
 

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

	    val M = ArgIn[Int]
	    val N = ArgIn[Int]
	    val K = ArgIn[Int]
	    setArg(M,args(0).to[Int])
	    setArg(N,args(1).to[Int])
	    setArg(K,args(2).to[Int])

	    val a_data = (0::args(0).to[Int], 0::args(2).to[Int]){(i,j) => random[T](3)}
	    val b_data = (0::args(2).to[Int], 0::args(1).to[Int]){(i,j) => random[T](3)}
	    val c_init = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 0.to[T]}
	    val a = DRAM[T](M, K)
	    val b = DRAM[T](K, N)
	    val c = DRAM[T](M, N)

	    setMem(a, a_data)
	    setMem(b, b_data)
	    setMem(c, c_init)

		Accel {}

		val accel_matrix = getMatrix(c)

		val gold_matrix = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 
			Array.tabulate(args(2).to[Int]){k => a_data(i,k) * b_data(k,j)}.reduce{_+_}
		}

		printMatrix(accel_matrix, "Received: ")
		printMatrix(gold_matrix, "Wanted: ")
		val cksum = accel_matrix.zip(gold_matrix){_==_}.reduce{_&&_}
		println("Pass? " + cksum)
      }
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


.. image:: gemmtile.gif

Note that this is not necessarily the most efficient implementation of this algorithm.  It is 
simply meant to be an implementation that demonstrates features of Spatial. 

Now let's write the code to implement this computation.  The large arrows and boxes represent
matrix multiplies on the highlighted tiles using outer products.  There will be six nested loops:
one for each dimension of tiling and one for each dimension within the tile.  

Considering the tiling loops first, this particular animation shows that we are treating the N dimension
as the innermost loop, followed by the M dimension, and finally the K dimension. Below shows the nested 
loops along with the data structures and their tile transfers required within each scope.  
Remember that you may add parallelization wherever you please::

	Accel {
		Foreach(K by tileK){kk => 
			val numel_k = min(tileK.to[Int], K - kk)
			Foreach(M by tileM){mm =>
				val numel_m = min(tileM.to[Int], M - mm)
				val tileA_sram = SRAM[T](tileM, tileK)
				tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
				Foreach(N by tileN){nn =>
					val numel_n = min(tileN.to[Int], N - nn)
					val tileB_sram = SRAM[T](tileK, tileN)
					val tileC_sram = SRAM.buffer[T](tileM, tileN)
					tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n)
					tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n)


					c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram
				}
			}
		}
	}


Note that we must compute the ``numel_*`` values to handle the edge cases correct, when the tile dimensions
do not evenly divide the full matrices.

Also note that we declare ``tileC_sram`` as a `.buffer` SRAM.  If you do not declare it this way,
then the compiler will throw an error about this and explain the issue.  You will learn more about
this in the `Advanced Buffering`_ section below.

Next, we will implement the full outer product of the tiles that we have brought into the chip::

	Accel {
		Foreach(K by tileK){kk => 
			val numel_k = min(tileK.to[Int], K - kk)
			Foreach(M by tileM){mm =>
				val numel_m = min(tileM.to[Int], M - mm)
				val tileA_sram = SRAM[T](tileM, tileK)
				tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
				Foreach(N by tileN){nn =>
					val numel_n = min(tileN.to[Int], N - nn)
					val tileB_sram = SRAM[T](tileK, tileN)
					val tileC_sram = SRAM.buffer[T](tileM, tileN)
					tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n)
					tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n)

					MemFold(tileC_sram)(numel_k by 1){k => 
						val tileK_local = SRAM[T](tileM, tileN)
						Foreach(numel_m by 1, numel_n by 1){(i,j) => 
							tileK_local(i,j) = tileA_sram(i,k) * tileB_sram(k,j)
						}
						tileK_local
					}{_+_}

					c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram
				}
			}
		}
	}

Notice that the code added in the above snippet uses a ``MemFold`` and creates a new memory called
``tileK_local`` inside of it.  The ``MemFold`` is similar to the ``Fold`` used in the previous :doc:`dotproduct`
example, except it operates on SRAMs and RegFiles rather than Regs.  The SRAM returned in the body of the map function
of the ``MemFold`` must match the dimensions of the accumulating SRAM given to the controller.  

There is also a ``MemReduce`` node, which is analogous to the ``Reduce`` node for Regs, but this particular node
will not work in this design because we need to accumulate a new partial sum on top of the partial sum that was
previously stored for a particular tile in DRAM.  The ``MemReduce`` controller will directly write the result of the
map function on the first iteration of the controller (i.e.- when k == 0), and then respect the lambda function (i.e.- addition)
for every iteration after that. 

Advanced Buffering
------------------

This Accel above already implements coarse-grain pipelining at various levels.  For example, the controller whose counter is ``nn`` has 
three stages in it.  The first stage loads ``tileB_sram`` and tileC_sram`` in parallel, the second stage performs the ``MemFold`` 
into ``tileC_sram``, and the third stage writes the resulting ``tileC_sram`` back into the appropriate region of DRAM.  This is an
example where the compiler will create a triple-buffer for ``tileC_sram`` in order to ensure that the correct values are being worked with
when this coarse-grain pipeline fills up and executes.  

If you had not declared ``tileC_sram`` as a `.buffer` SRAM, then the compiler is suspicious of your code.  This is because it is generally
very easy when specifying pipelined hardware to accidentally create loop-carry dependency issues.  Specifically, in this code, it sees that 
you write to the SRAM in the first stage, and then write to it again in the second stage.  It is very easy, even for advanced users, to
write this kind of structure without realizing it and then receive an incorrect result when using a cycle-accurate simulator of the hardware
because of values "rotating" through the buffer inadvertently.

The animation below specifically demonstrates the triple buffer ``tileC_sram`` in this algorithm.

.. image:: triplebuf.gif

Note that at the beginning and end of each row, there are a few iterations where parts of the buffer are not being used.
This is because of the way the loops are written, such that we step through each tile in the N dimension before we
increment the tile for M.  If you want to write the app such that there are no wasteful fill and drain iterations,
you must combine loops appropriately.

Advanced Banking
----------------

Let's now add in more optimizations to improve the performance of this application.  Specifically, we will parallelize two of the
loops in such a way to expose hierarchical banking.  The following code shows the loops for ``k`` and ``j`` parallelized by 2 and 4
respectively.::

	Accel {
		Foreach(K by tileK){kk => 
			val numel_k = min(tileK.to[Int], K - kk)
			Foreach(M by tileM){mm =>
				val numel_m = min(tileM.to[Int], M - mm)
				val tileA_sram = SRAM[T](tileM, tileK)
				tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
				Foreach(N by tileN){nn =>
					val numel_n = min(tileN.to[Int], N - nn)
					val tileB_sram = SRAM[T](tileK, tileN)
					val tileC_sram = SRAM.buffer[T](tileM, tileN)
					tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n)
					tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n)

					MemFold(tileC_sram)(numel_k by 1 par 2){k => 
						val tileK_local = SRAM[T](tileM, tileN)
						Foreach(numel_m by 1, numel_n by 1 par 4){(i,j) => 
							tileK_local(i,j) = tileA_sram(i,k) * tileB_sram(k,j)
						}
						tileK_local
					}{_+_}

					c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram
				}
			}
		}
	}

Now let's look at what happens to ``tileB_sram``.  It's first and second indices are both parallelized.
Index ``j`` is vectorized by 4, while index ``k`` is duplicated for two different values of k when the 
loop is unrolled by 2.  This means we must bank ``tileB_sram`` in both the horizontal and vertical dimensions
in order to guarantee that all 8 of these accesses will be able to touch unique banks every time we read from this memory.
The animation below demonstrates how we hierarchically bank this SRAM.

.. image:: hierbank.gif

Let's consider the situation if we instead decided to parallelize a different way.  Below is the code for the application
if we chose to parallelize the loading of tileB_sram by 8 while also parallelizing the ``k`` loop by 2.::

	Accel {
		Foreach(K by tileK){kk => 
			val numel_k = min(tileK.to[Int], K - kk)
			Foreach(M by tileM){mm =>
				val numel_m = min(tileM.to[Int], M - mm)
				val tileA_sram = SRAM[T](tileM, tileK)
				tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
				Foreach(N by tileN){nn =>
					val numel_n = min(tileN.to[Int], N - nn)
					val tileB_sram = SRAM[T](tileK, tileN)
					val tileC_sram = SRAM.buffer[T](tileM, tileN)
					tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n par 8)
					tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n)

					MemFold(tileC_sram)(numel_k by 1 par 2){k => 
						val tileK_local = SRAM[T](tileM, tileN)
						Foreach(numel_m by 1, numel_n by 1){(i,j) => 
							tileK_local(i,j) = tileA_sram(i,k) * tileB_sram(k,j)
						}
						tileK_local
					}{_+_}

					c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram
				}
			}
		}
	}

While the hierarchical banking scheme shown above will still work for this case, where we have 2 banks along the rows
and 8 banks along the columns, the Spatial compiler will perform a memory-saving optimization called Diagonal Banking.
In this example, we need to be able to access 8 elements along the column simultaneously, and later in the app we need to
access 2 elements from different rows simultaneously.  However, these accesses do not occur at the same time, so we do
not need 16 unique banks (as is implied by the previous example) and can get away with 8 banks.

.. image:: diagbank.gif

If the parallelizations of the various accesses are not multiples of each other, the compiler will figure out the most
minimalistic banking scheme that guarantees correctness.


Final Code
----------

Below is the complete GEMM app.  See the @HelloWorld page for a refresher on how to compile and test an app::

    import spatial.dsl._
    import org.virtualized._

    object GEMM extends SpatialApp {

      @virtualize
      def main() {

		type T = FixPt[TRUE,_24,_8]
		val tileM = 16
		val tileN = 16
		val tileK = 16

	    val M = ArgIn[Int]
	    val N = ArgIn[Int]
	    val K = ArgIn[Int]
	    setArg(M,args(0).to[Int])
	    setArg(N,args(1).to[Int])
	    setArg(K,args(2).to[Int])

	    val a_data = (0::args(0).to[Int], 0::args(2).to[Int]){(i,j) => random[T](3)}
	    val b_data = (0::args(2).to[Int], 0::args(1).to[Int]){(i,j) => random[T](3)}
	    val c_init = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 0.to[T]}
	    val a = DRAM[T](M, K)
	    val b = DRAM[T](K, N)
	    val c = DRAM[T](M, N)

	    setMem(a, a_data)
	    setMem(b, b_data)
	    setMem(c, c_init)

		Accel {
			Foreach(K by tileK){kk => 
				val numel_k = min(tileK.to[Int], K - kk)
				Foreach(M by tileM){mm =>
					val numel_m = min(tileM.to[Int], M - mm)
					val tileA_sram = SRAM[T](tileM, tileK)
					tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
					Foreach(N by tileN){nn =>
						val numel_n = min(tileN.to[Int], N - nn)
						val tileB_sram = SRAM[T](tileK, tileN)
						val tileC_sram = SRAM.buffer[T](tileM, tileN)
						tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n par 8)
						tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n)

						MemFold(tileC_sram)(numel_k by 1 par 2){k => 
							val tileK_local = SRAM[T](tileM, tileN)
							Foreach(numel_m by 1, numel_n by 1){(i,j) => 
								tileK_local(i,j) = tileA_sram(i,k) * tileB_sram(k,j)
							}
							tileK_local
						}{_+_}

						c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram
					}
				}
			}
		}

		val accel_matrix = getMatrix(c)

		val gold_matrix = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 
			Array.tabulate(args(2).to[Int]){k => a_data(i,k) * b_data(k,j)}.reduce{_+_}
		}

		printMatrix(accel_matrix, "Received: ")
		printMatrix(gold_matrix, "Wanted: ")
		val cksum = accel_matrix.zip(gold_matrix){_==_}.reduce{_&&_}
		println("Pass? " + cksum)
		
      }
    }



Instrumentation Hooks
---------------------

Now that you have finished writing an algorithm, you will want to try to get the best performance possible.  In order to
get optimal performance, it is important to balance the stages in your pipelines.  While you could get a good estimate
by eyeballing your code, there is a way to get actual execution cycles on a controller-by-controller basis using
a Spatial/special feature called "instrumentation."

To turn on instrumentation hooks, use the ``bin/spatial <app name> --synth --instrument`` flag when compiling the app.  This flag
injects performance counters that count the number of cycles each controller is enabled, as well as the number of times a particular
controller is done.  Note that performance counters will only be injected in the --synth backend.

Once you compile your app, you should run it normally with the run.sh script.  You may notice that there are some extra lines
that are spitting out information about the app.  Running the run.sh script created a file in your current directory called
`instrumentation.txt`, which will be used to populate a visualization of your app.  Let's start by opening up the controller tree::

	google-chrome controller_tree.html # Or whatever your favorite browser is (firefox, etc.)

You will get a screen that looks like this.

.. raw:: html

    <embed>
	<!DOCTYPE html>
	<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	</head><body>

	  <div data-role="main" class="ui-content" style="overflow-x:scroll;">
	    <h2>Controller Diagram for MatMult_inner</h2>
	<TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">
	  <!--Begin x7564 -->
	  <TD><font size = "6">Hwblock<br><font size = "2">MatMults.scala:139:11</font><br><b>x7564</b></font><br><font size = "1">Counter: </font> 
	  <div data-role="collapsible">
	  <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	    <!--Begin x7563 -->
	    <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:140:39</font><br><b>x7563</b></font><br><font size = "1">Counter: x6631</font> 
	    <div data-role="collapsible">
	    <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	      <!--Begin x7478 -->
	      <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7478</b></font><br><font size = "1">Counter: </font> 
	      <div data-role="collapsible">
	      <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	        <!--Begin x7055 -->
	        <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:143:32</font><br><b>x7055</b></font><br><font size = "1">Counter: x6636</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x6710 -->
	          <TD><font size = "6">Parallel<br><font size = "2">MatMults.scala:146:20</font><br><b>x6710</b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x6641 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x6641</b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x6641 -->


	            <!--Begin x6674 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x6674</b></font><br><font size = "1">Counter: x6643</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x6662 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:147:19</font><br><b>x6662</b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x6644-----></div>
	              </TD>
	              <!-- Close x6662 -->


	              <!--Begin x6673 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x6673</b></font><br><font size = "1">Counter: x6665</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x6645</div>
	              </TD>
	              <!-- Close x6673 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x6674 -->


	            <!--Begin x6676 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x6676</b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x6676 -->


	            <!--Begin x6709 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x6709</b></font><br><font size = "1">Counter: x6678</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x6697 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:148:19</font><br><b>x6697</b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x6679-----></div>
	              </TD>
	              <!-- Close x6697 -->


	              <!--Begin x6708 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x6708</b></font><br><font size = "1">Counter: x6700</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x6680</div>
	              </TD>
	              <!-- Close x6708 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x6709 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x6710 -->


	          <!--Begin x7054 -->
	          <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:150:43</font><br><b>x7054</b></font><br><font size = "1">Counter: x6713</font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7030 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7030</b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x6872 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x6872</b></font><br><font size = "1">Counter: x6717</font> 
	              </TD>
	              <!-- Close x6872 -->


	              <!--Begin x7029 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x7029</b></font><br><font size = "1">Counter: x6874</font> 
	              </TD>
	              <!-- Close x7029 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7030 -->


	            <!--Begin x7053 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7053</b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7041 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7041</b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7041 -->


	              <!--Begin x7052 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7052</b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7052 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7053 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7054 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7055 -->


	        <!--Begin x7477 -->
	        <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:143:32</font><br><b>x7477</b></font><br><font size = "1">Counter: x7058</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x7132 -->
	          <TD><font size = "6">Parallel<br><font size = "2">MatMults.scala:146:20</font><br><b>x7132</b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7063 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x7063</b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x7063 -->


	            <!--Begin x7096 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x7096</b></font><br><font size = "1">Counter: x7065</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7084 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:147:19</font><br><b>x7084</b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x7066-----></div>
	              </TD>
	              <!-- Close x7084 -->


	              <!--Begin x7095 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x7095</b></font><br><font size = "1">Counter: x7087</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x7067</div>
	              </TD>
	              <!-- Close x7095 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7096 -->


	            <!--Begin x7098 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x7098</b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x7098 -->


	            <!--Begin x7131 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x7131</b></font><br><font size = "1">Counter: x7100</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7119 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:148:19</font><br><b>x7119</b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x7101-----></div>
	              </TD>
	              <!-- Close x7119 -->


	              <!--Begin x7130 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x7130</b></font><br><font size = "1">Counter: x7122</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x7102</div>
	              </TD>
	              <!-- Close x7130 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7131 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7132 -->


	          <!--Begin x7476 -->
	          <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:150:43</font><br><b>x7476</b></font><br><font size = "1">Counter: x7135</font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7452 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7452</b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7294 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x7294</b></font><br><font size = "1">Counter: x7139</font> 
	              </TD>
	              <!-- Close x7294 -->


	              <!--Begin x7451 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x7451</b></font><br><font size = "1">Counter: x7296</font> 
	              </TD>
	              <!-- Close x7451 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7452 -->


	            <!--Begin x7475 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7475</b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7463 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7463</b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7463 -->


	              <!--Begin x7474 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7474</b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7474 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7475 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7476 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7477 -->

	      </TABLE></div>
	      </TD>
	      <!-- Close x7478 -->


	      <!--Begin x7485 -->
	      <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7485</b></font><br><font size = "1">Counter: </font> 
	      <div data-role="collapsible">
	      <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	        <!--Begin x7481 -->
	        <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:140:39</font><br><b>x7481</b></font><br><font size = "1">Counter: </font> 
	        </TD>
	        <!-- Close x7481 -->


	        <!--Begin x7484 -->
	        <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:140:39</font><br><b>x7484</b></font><br><font size = "1">Counter: </font> 
	        </TD>
	        <!-- Close x7484 -->

	      </TABLE></div>
	      </TD>
	      <!-- Close x7485 -->


	      <!--Begin x7562 -->
	      <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7562</b></font><br><font size = "1">Counter: </font> 
	      <div data-role="collapsible">
	      <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	        <!--Begin x7523 -->
	        <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7523</b></font><br><font size = "1">Counter: x7487</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x7517 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7517</b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7506 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7506</b></font><br><font size = "1">Counter: </font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7488-----></div>
	            </TD>
	            <!-- Close x7506 -->


	            <!--Begin x7516 -->
	            <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7516</b></font><br><font size = "1">Counter: x7508</font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7489-----></div>
	            </TD>
	            <!-- Close x7516 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7517 -->


	          <!--Begin x7522 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7522</b></font><br><font size = "1">Counter: </font> 
	          <div style="border:1px solid black">Stream Info<br><p align="left">----->x7490</div>
	          </TD>
	          <!-- Close x7522 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7523 -->


	        <!--Begin x7561 -->
	        <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7561</b></font><br><font size = "1">Counter: x7525</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x7555 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7555</b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7544 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7544</b></font><br><font size = "1">Counter: </font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7526-----></div>
	            </TD>
	            <!-- Close x7544 -->


	            <!--Begin x7554 -->
	            <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7554</b></font><br><font size = "1">Counter: x7546</font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7527-----></div>
	            </TD>
	            <!-- Close x7554 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7555 -->


	          <!--Begin x7560 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7560</b></font><br><font size = "1">Counter: </font> 
	          <div style="border:1px solid black">Stream Info<br><p align="left">----->x7528</div>
	          </TD>
	          <!-- Close x7560 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7561 -->

	      </TABLE></div>
	      </TD>
	      <!-- Close x7562 -->

	    </TABLE></div>
	    </TD>
	    <!-- Close x7563 -->

	  </TABLE></div>
	  </TD>
	  <!-- Close x7564 -->

	  </TABLE>
	</body>
	</html>
	</embed>


If you play around with this screen, you will see that this shows you the control hierarchy in your app, and points each box
back to the original source code.  To make this a more useful tool, we will now inject the instrumentation results into this
page.  Run the script::

	bash instrument.sh

Now refresh the controller tree page.  There should be a lot of red text, similar to the image shown below:


.. raw:: html

    <embed>
	<!DOCTYPE html>
	<html>
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	</head><body>

	  <div data-role="main" class="ui-content" style="overflow-x:scroll;">
	    <h2>Controller Diagram for MatMult_inner</h2><h2><font color="red">Instrumentation Annotiations </font></h2>
	<TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">
	  <!--Begin x7564 -->
	  <TD><font size = "6">Hwblock<br><font size = "2">MatMults.scala:139:11</font><br><b>x7564 - <font color="red"> 45928 cycles/iter<br><font size="2">(45928 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	  <div data-role="collapsible">
	  <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	    <!--Begin x7563 -->
	    <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:140:39</font><br><b>x7563 - <font color="red"> 45901 cycles/iter<br><font size="2">(45901 total cycles, 1 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6631</font> 
	    <div data-role="collapsible">
	    <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	      <!--Begin x7478 -->
	      <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7478 - <font color="red"> 10799 cycles/iter<br><font size="2">(43197 total cycles, 4 total iters)<br>[4 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	      <div data-role="collapsible">
	      <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	        <!--Begin x7055 -->
	        <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:143:32</font><br><b>x7055 - <font color="red"> 10796 cycles/iter<br><font size="2">(43185 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6636</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x6710 -->
	          <TD><font size = "6">Parallel<br><font size = "2">MatMults.scala:146:20</font><br><b>x6710 - <font color="red"> 5128 cycles/iter<br><font size="2">(20513 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x6641 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x6641 - <font color="red"> 3 cycles/iter<br><font size="2">(12 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x6641 -->


	            <!--Begin x6674 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x6674 - <font color="red"> 1654 cycles/iter<br><font size="2">(6619 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6643</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x6662 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:147:19</font><br><b>x6662 - <font color="red"> 3 cycles/iter<br><font size="2">(192 total cycles, 64 total iters)<br>[16 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x6644-----></div>
	              </TD>
	              <!-- Close x6662 -->


	              <!--Begin x6673 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x6673 - <font color="red"> 64 cycles/iter<br><font size="2">(4153 total cycles, 64 total iters)<br>[16 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6665</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x6645</div>
	              </TD>
	              <!-- Close x6673 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x6674 -->


	            <!--Begin x6676 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x6676 - <font color="red"> 3 cycles/iter<br><font size="2">(12 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x6676 -->


	            <!--Begin x6709 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x6709 - <font color="red"> 5125 cycles/iter<br><font size="2">(20501 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6678</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x6697 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:148:19</font><br><b>x6697 - <font color="red"> 3 cycles/iter<br><font size="2">(768 total cycles, 256 total iters)<br>[64 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x6679-----></div>
	              </TD>
	              <!-- Close x6697 -->


	              <!--Begin x6708 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x6708 - <font color="red"> 64 cycles/iter<br><font size="2">(16632 total cycles, 256 total iters)<br>[64 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6700</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x6680</div>
	              </TD>
	              <!-- Close x6708 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x6709 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x6710 -->


	          <!--Begin x7054 -->
	          <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:150:43</font><br><b>x7054 - <font color="red"> 5653 cycles/iter<br><font size="2">(22613 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6713</font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7030 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7030 - <font color="red"> 9 cycles/iter<br><font size="2">(18432 total cycles, 2048 total iters)<br>[512 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x6872 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x6872 - <font color="red"> 6 cycles/iter<br><font size="2">(12288 total cycles, 2048 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6717</font> 
	              </TD>
	              <!-- Close x6872 -->


	              <!--Begin x7029 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x7029 - <font color="red"> 6 cycles/iter<br><font size="2">(12288 total cycles, 2048 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x6874</font> 
	              </TD>
	              <!-- Close x7029 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7030 -->


	            <!--Begin x7053 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7053 - <font color="red"> 6 cycles/iter<br><font size="2">(12288 total cycles, 2048 total iters)<br>[512 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7041 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7041 - <font color="red"> 3 cycles/iter<br><font size="2">(6144 total cycles, 2048 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7041 -->


	              <!--Begin x7052 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7052 - <font color="red"> 3 cycles/iter<br><font size="2">(6144 total cycles, 2048 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7052 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7053 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7054 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7055 -->


	        <!--Begin x7477 -->
	        <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:143:32</font><br><b>x7477 - <font color="red"> 4 cycles/iter<br><font size="2">(4 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7058</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x7132 -->
	          <TD><font size = "6">Parallel<br><font size = "2">MatMults.scala:146:20</font><br><b>x7132 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7063 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x7063 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x7063 -->


	            <!--Begin x7096 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x7096 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7065</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7084 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:147:19</font><br><b>x7084 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x7066-----></div>
	              </TD>
	              <!-- Close x7084 -->


	              <!--Begin x7095 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:147:19</font><br><b>x7095 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7087</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x7067</div>
	              </TD>
	              <!-- Close x7095 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7096 -->


	            <!--Begin x7098 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:146:20</font><br><b>x7098 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            </TD>
	            <!-- Close x7098 -->


	            <!--Begin x7131 -->
	            <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x7131 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7100</font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7119 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:148:19</font><br><b>x7119 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              <div style="border:1px solid black">Stream Info<br><p align="right">x7101-----></div>
	              </TD>
	              <!-- Close x7119 -->


	              <!--Begin x7130 -->
	              <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:148:19</font><br><b>x7130 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7122</font> 
	              <div style="border:1px solid black">Stream Info<br><p align="left">----->x7102</div>
	              </TD>
	              <!-- Close x7130 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7131 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7132 -->


	          <!--Begin x7476 -->
	          <TD><font size = "6">Meta.UnrolledForeach<br><font size = "2">MatMults.scala:150:43</font><br><b>x7476 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7135</font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7452 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7452 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7294 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x7294 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7139</font> 
	              </TD>
	              <!-- Close x7294 -->


	              <!--Begin x7451 -->
	              <TD><font size = "6">Inner.UnrolledReduce<br><font size = "2">MatMults.scala:151:92</font><br><b>x7451 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7296</font> 
	              </TD>
	              <!-- Close x7451 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7452 -->


	            <!--Begin x7475 -->
	            <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7475 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            <div data-role="collapsible">
	            <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	              <!--Begin x7463 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7463 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7463 -->


	              <!--Begin x7474 -->
	              <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:150:43</font><br><b>x7474 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	              </TD>
	              <!-- Close x7474 -->

	            </TABLE></div>
	            </TD>
	            <!-- Close x7475 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7476 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7477 -->

	      </TABLE></div>
	      </TD>
	      <!-- Close x7478 -->


	      <!--Begin x7485 -->
	      <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7485 - <font color="red"> 6 cycles/iter<br><font size="2">(24 total cycles, 4 total iters)<br>[4 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	      <div data-role="collapsible">
	      <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	        <!--Begin x7481 -->
	        <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:140:39</font><br><b>x7481 - <font color="red"> 3 cycles/iter<br><font size="2">(12 total cycles, 4 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	        </TD>
	        <!-- Close x7481 -->


	        <!--Begin x7484 -->
	        <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:140:39</font><br><b>x7484 - <font color="red"> 4 cycles/iter<br><font size="2">(4 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	        </TD>
	        <!-- Close x7484 -->

	      </TABLE></div>
	      </TD>
	      <!-- Close x7485 -->


	      <!--Begin x7562 -->
	      <TD><font size = "6">Parallel<br><font size = "2">UnrollingTransformer.scala:428:43</font><br><b>x7562 - <font color="red"> 2517 cycles/iter<br><font size="2">(10068 total cycles, 4 total iters)<br>[4 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	      <div data-role="collapsible">
	      <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	        <!--Begin x7523 -->
	        <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7523 - <font color="red"> 1343 cycles/iter<br><font size="2">(1343 total cycles, 1 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7487</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x7517 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7517 - <font color="red"> 74 cycles/iter<br><font size="2">(1193 total cycles, 16 total iters)<br>[16 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7506 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7506 - <font color="red"> 3 cycles/iter<br><font size="2">(48 total cycles, 16 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7488-----></div>
	            </TD>
	            <!-- Close x7506 -->


	            <!--Begin x7516 -->
	            <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7516 - <font color="red"> 66 cycles/iter<br><font size="2">(1056 total cycles, 16 total iters)<br>[1 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7508</font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7489-----></div>
	            </TD>
	            <!-- Close x7516 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7517 -->


	          <!--Begin x7522 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7522 - <font color="red"> 1 cycles/iter<br><font size="2">(16 total cycles, 16 total iters)<br>[16 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	          <div style="border:1px solid black">Stream Info<br><p align="left">----->x7490</div>
	          </TD>
	          <!-- Close x7522 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7523 -->


	        <!--Begin x7561 -->
	        <TD><font size = "6">Stream.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7561 - <font color="red"> 1 cycles/iter<br><font size="2">(1 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7525</font> 
	        <div data-role="collapsible">
	        <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	          <!--Begin x7555 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7555 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	          <div data-role="collapsible">
	          <h4> </h4><TABLE BORDER="3" CELLPADDING="10" CELLSPACING="10">

	            <!--Begin x7544 -->
	            <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7544 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7526-----></div>
	            </TD>
	            <!-- Close x7544 -->


	            <!--Begin x7554 -->
	            <TD><font size = "6">Inner.UnrolledForeach<br><font size = "2">MatMults.scala:156:29</font><br><b>x7554 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: x7546</font> 
	            <div style="border:1px solid black">Stream Info<br><p align="right">x7527-----></div>
	            </TD>
	            <!-- Close x7554 -->

	          </TABLE></div>
	          </TD>
	          <!-- Close x7555 -->


	          <!--Begin x7560 -->
	          <TD><font size = "6">Seq.Unitpipe<br><font size = "2">MatMults.scala:156:29</font><br><b>x7560 - <font color="red"> 0 cycles/iter<br><font size="2">(0 total cycles, 0 total iters)<br>[0 iters/parent execution]</font></font></b></font><br><font size = "1">Counter: </font> 
	          <div style="border:1px solid black">Stream Info<br><p align="left">----->x7528</div>
	          </TD>
	          <!-- Close x7560 -->

	        </TABLE></div>
	        </TD>
	        <!-- Close x7561 -->

	      </TABLE></div>
	      </TD>
	      <!-- Close x7562 -->

	    </TABLE></div>
	    </TD>
	    <!-- Close x7563 -->

	  </TABLE></div>
	  </TD>
	  <!-- Close x7564 -->

	  </TABLE>
	</body>
	</html>
	</embed>


You can now play around with this page and look at how the various stages in your pipelines are performing.  We leave it up
to the user to figure out how to use parallelizations and rewrite portions of the app to figure out how to balance the pipelines
and get better performance.


When you understand the concepts introduced in this page, you may move on to the next example, :doc:`convolution`, where you
will learn to perform reductions on memories, include instrumentation hooks to help balance your pipeline,
and see more complicated examples of banking.
