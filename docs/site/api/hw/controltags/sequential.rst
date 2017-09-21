
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

.. _Sequential:

Sequential
==========

**Sequential** is a scheduling directive which tells the compiler not to attempt to parallelize or to pipeline inner computation.
In this scheduling mode, the controller's counter will only increment when it's last stage is complete.
This directive is needed primarily when the algorithm contains long loop-carry dependencies that cannot be optimized away.

-----------------

**Static methods**


@table-start
object Sequential

  /** Creates a Unit Pipe, akin to a Foreach with one iteration. **/
  @api def apply(func: => Unit): Unit


  /** References the @Fold object with sequential execution specified as the scheduling directive. **/
  @api def Fold: Fold

  /** References the @Foreach object with sequential execution specified as the scheduling directive. **/
  @api def Foreach: Foreach

  /** References the @MemFold object with sequential execution specified as the scheduling directive. **/
  @api def MemFold: MemFold

  /** References the @MemReduce object with sequential execution specified as the scheduling directive. **/
  @api def MemReduce: MemReduce

  /** References the @Reduce object with sequential execution specified as the scheduling directive. **/
  @api def Reduce: Reduce

@table-end
