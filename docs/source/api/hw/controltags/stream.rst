
.. role:: black
.. role:: gray
.. role:: silver
.. role:: white
.. role:: maroon
.. role:: red
.. role:: fuchsia
.. role:: pink
.. role:: orange
.. role:: yellow
.. role:: lime
.. role:: green
.. role:: olive
.. role:: teal
.. role:: cyan
.. role:: aqua
.. role:: blue
.. role:: navy
.. role:: purple

.. _Stream:

Stream
======

**Stream** is a scheduling directive which tells the compiler to overlap inner computation in a fine-grained, streaming fashion.
In controllers which contain multiple control stages, this implies that communication is being done through @FIFO memories.
at an element-wise level.

Communication across stages within Stream controllers through any memory except FIFOs is currently disallowed.
Note that this may change as the language evolves.


-----------------

**Static methods**

@table-start
object Stream

  /** Creates a streaming Unit Pipe, akin to a Foreach with one iteration. **/
  @api def apply(func: => Unit): Unit


  /** References the @Fold object with streaming specified as the scheduling directive. **/
  @api def Fold: Fold

  /** References the @Foreach object with streaming specified as the scheduling directive. **/
  @api def Foreach: Foreach

  /** References the @MemFold object with streaming specified as the scheduling directive. **/
  @api def MemFold: MemFold

  /** References the @MemReduce object with streaming specified as the scheduling directive. **/
  @api def MemReduce: MemReduce

  /** References the @Reduce object with streaming specified as the scheduling directive. **/
  @api def Reduce: Reduce

@table-end
