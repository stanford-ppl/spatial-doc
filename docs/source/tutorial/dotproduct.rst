
1. Vector Inner Product
=======================


Catalog of Features
-------------------

In this section, you will learn about the following components in Spatial:

 - Tiling

 - Reduce and Fold

 - Sequential execution and Coarse-grain pipelining
 
 - Parallelization
 
 - Basic buffering and banking


Application Overview
--------------------

Inner product (also called dot product) is an extremely simple linear algebra kernel, defined as the
sum of the element-wise products between two vectors of data. For this example, we'll assume that the
data in this case are 32-bit signed fixed point numbers with 8 fractional bits. You could, however, 
also do the same operations with custom struct types.  


Data Setup and Validation
-------------------------

Let's start by creating the data structures above the Accel that we will compute the dot product on. We will expose
the length of these vectors as a command-line argument. We will also write the code below the Accel to ensure we have
the correct result::
	
    import spatial.dsl._
    import org.virtualized._

    object DotProduct extends SpatialApp {

      @virtualize
      def main() {

		type T = FixPt[TRUE,_24,_8]
		
		val N = args(0).to[Int]
		val length = ArgIn[Int]
		setArg(length, N)
		val result = ArgOut[T]
		
		val vector1_data = Array.tabulate(N){i => random[T](5)}
		val vector2_data = Array.tabulate(N){i => random[T](5)}

		val vector1 = DRAM[T](length) // DRAMs can be sized by ArgIns
		val vector2 = DRAM[T](length)

		setMem(vector1, vector1_data)
		setMem(vector2, vector2_data)

		Accel {}
		
		val result_dot = getArg(result)
		val gold_dot = vector1_data.zip(vector2_data){_*_}.reduce{_+_}
		val cksum = gold_dot == result_dot
		println("Received " + result_dot + ", wanted " + gold_dot)
		println("Pass? " + cksum)

Tiling and Reduce/Fold
-----------------

Now we will focus our attention on writing the accelerator code.  We must first figure out how to process a variable-sized
vector on a fixed hardware design.  To do this, we use tiling.  Let's create a val for the tileSize just inside the ``main()`` 
method::

	val tileSize = 64

Now we can break the vectors into 64-element chunks, and then process these chunks locally on the FPGA using the ``Reduce`` 
construct::
	
	result := Sequential.Reduce(Reg[T](0))(length by tileSize){tile => // Returns Reg[T], writes to ArgOut
		val tile1 = SRAM[T](tileSize)
		val tile2 = SRAM[T](tileSize)

		tile1 load vector1(tile :: tile + tileSize)
		tile2 load vector2(tile :: tile + tileSize)

        val local_accum = Reg[T](0)
		Reduce(local_accum)(tileSize by 1){i => tile1(i) * tile2(i)}{_+_} // Accumulates directly into local_accum
        local_accum
	}{_+_}




It might seem a bit odd at first that we have the line `{_+_}` twice in this app. This is because the inner reduce accumulates over a tile, and the outer reduce
accumulates each result we get from each tile.  The ``Reduce`` construct assumes that the register that is doing the accumulation will
effectively reset on each iteration of its parent controller.  This means that on the first iteration of the innermost reduce, the hardware
will write the first product directly to the register. On the second iteration of this innermost reduce, it will write the current value of local_accum
PLUS the next product.  This means that if local_accum were declared to have an initial value of 5, reducing on top of it will start at 0, not 5.

Also, notice the ``Sequential`` modifier on the outer reduce loop.  This ensures that everything inside of this loop will happen sequentially and without
coarse-grained pipelining.  We will play more with this parameter in the next section, `Pipelining and Parallelization`_.

The ``Reduce`` construct takes four pieces of information: **1)** an existing Reg or a new Reg in which to accumulate, 
**2)** a range and step for its counter to scan, **3)** a map function, and **4)** a reduce function.  If a new Reg is declared
inside the ``Reduce``, then the structure returns this register.  Importantly, note that a declaration of ``val local_accum = Reg[T](0)`` does not
mean that local_accum is reset on every iteration.  This is a declaration of hardware and is always present.  It is the contract
implicit with the ``Reduce`` construct that effectively resets the register.  You can manually reset the register in the code with
``local_accum.reset``.

Alternatively, you can express the Accel for dot product using a ``Fold``.  This is similar to a ``Reduce``, except the Reg
is persistent and not reset unless explicitly reset by the user.  In the case where a Reg was declared to have an initial value of
5, the Fold on top of this Reg would start at 5 and not 0.  The code would look like this::

    val accum = Reg[T](0)
    Sequential.Foreach(length by tileSize){tile =>
        val tile1 = SRAM[T](tileSize)
        val tile2 = SRAM[T](tileSize)

        tile1 load vector1(tile :: tile + tileSize)
        tile2 load vector2(tile :: tile + tileSize)

        Fold(accum)(tileSize by 1){i => tile1(i) * tile2(i)}{_+_} 
    }
    result := accum

Let's take a look at the hardware we have generated.  The animation below demonstrates how this code
will synthesize and execute.

.. image:: dotseq.gif

While the above code appears to be correct, there is a problem when handling edge-cases.  If
the user inputs a vector size that is not a multiple of our tileSize, then we will have an issue
with the above code on the final iteration.  

To fix this, we need to keep track of how many elements we `actually` want to reduce over each time
we execute the inner pipe::

    val accum = Reg[T](0)
    Foreach(length by tileSize){tile =>
        val numel = min(tileSize, length - tile)
        val tile1 = SRAM[T](tileSize)
        val tile2 = SRAM[T](tileSize)

        tile1 load vector1(tile :: tile + numel)
        tile2 load vector2(tile :: tile + numel)

        Fold(accum)(numel by 1){i => tile1(i) * tile2(i)}{_+_} 
    }
    result := accum


Pipelining and Parallelization
------------------------------


.. Next example: :doc:`outerproduct`