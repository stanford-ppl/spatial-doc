
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

.. _MemFold:

MemFold
=======

@alias MemFold

**MemFold** describes the reduction *across* multiple local memories. It functions essentially the same way as a
:doc:`memreduce`, except that it uses the existing value of the accumulator at the start of the reduction as the
initial value of the accumulator, rather than resetting.
Like MemReduce, unless otherwise disabled, the compiler will try to parallelize both the creation of multiple memories and the reduction
of each of these memories into a single accumulator.

--------------

**Static methods**

@table-start
object MemFold

  /** 
    * On-chip memory fold over a one dimensional space.
    * Returns the accumulator `accum`. 
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr: Counter)(map: Int => C[T])(reduce: (T,T) => T): C[T]

  /** 
    * On-chip memory fold over a two dimensional space.
    * Returns the accumulator `accum`.
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr1: Counter, ctr2: Counter)(map: (Int,Int) => C[T])(reduce: (T,T) => T): C[T]

  /** 
    * On-chip memory fold over a three dimensional space.
    * Returns the accumulator `accum`.
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr1: Counter, ctr2: Counter, ctr3: Counter)(map: (Int,Int,Int) => C[T])(reduce: (T,T) => T): C[T]

  /** 
    * On-chip memory fold over a 4+ dimensional space.
    * Returns the accumulator `accum`.
    * Note that the `map` function is on a `List` of iterators.
    * The number of iterators will be the same as the number of counters supplied.
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr1: Counter, ctr2: Counter, ctr3: Counter, ctr4: Counter, ctr5: Counter*)(map: List[Int] => C[T])(reduce: (T,T) => T): C[T]

@table-end
