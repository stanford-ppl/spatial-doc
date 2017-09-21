
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

.. _Pipe:

Pipe
====

**Pipe** is the default scheduling directive in Spatial, and doesn't usually need to be specified.
This directive tells the compiler that the stages of the controller can be overlapped in a pipelined fashion.
If the controller contains other controllers within it, this means that these inner controllers will be executed using
coarse-grained pipeline scheduling.


-----------------

**Static methods**

@table-start
object Pipe

  /** Creates a Unit Pipe, akin to a Foreach with one iteration. **/
  @api def apply(func: => Unit): Unit


  /** References the @Fold object with pipelining specified as the scheduling directive. **/
  @api def Fold: Fold

  /** References the @Foreach object with pipelining specified as the scheduling directive. **/
  @api def Foreach: Foreach

  /** References the @MemFold object with pipelining specified as the scheduling directive. **/
  @api def MemFold: MemFold

  /** References the @MemReduce object with pipelining specified as the scheduling directive. **/
  @api def MemReduce: MemReduce

  /** References the @Reduce object with pipelining specified as the scheduling directive. **/
  @api def Reduce: Reduce

@table-end
