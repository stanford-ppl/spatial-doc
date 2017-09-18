
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

.. _Foreach:

Foreach
=======

@alias Foreach

The *Foreach* controller is similar to a *for* loop. Significantly, however, unless explicitly told otherwise, the compiler
will assume each iteration of *Foreach* is independent, and will attempt to parallelize and pipeline the body.
*Foreach* has no usable return value.


--------------

**Static methods**

@table-start
object Foreach

  /** Foreach over a one dimensional space. **/
  @api def apply(ctr: Counter)(func: Int => Unit): Unit

  /** Foreach over a two dimensional space. **/
  @api def apply(ctr1: Counter, ctr2: Counter)(func: (Int, Int) => Unit): Unit

  /** Foreach over a three dimensional space. **/
  @api def apply(ctr1: Counter, ctr2: Counter, ctr3: Counter)(func: (Int, Int, Int) => Unit): Unit

  /** 
    * Foreach over a 4+ dimensional space. 
    * Note that `func` is on a `List` of iterators.
    * The number of iterators will be the same as the number of counters supplied.
    **/
  @api def apply(ctr1: Counter, ctr2: Counter, ctr3: Counter, ctr4: Counter, ctr5: Counter*)(func: List[Int] => Unit): Unit

@table-end
