
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

.. _MemReduce:

MemReduce
=========

@alias MemReduce

**MemReduce** describes the reduction *across* multiple local memories.
Like @Reduce, MemReduce requires both a *map* and a *reduction* function. However, in MemReduce, the *map*
describes the creation and population of a local memory (typically an @SRAM).
The *reduction* function still operates on scalars, and is used to combine local memories together element-wise.
Unlike Reduce, MemReduce always requires an explicit accumulator.
Unless otherwise disabled, the compiler will then try to parallelize both the creation of multiple memories and the reduction
of each of these memories into a single accumulator.

--------------

**Static methods**

@table-start
object MemReduce

  /** 
    * On-chip memory reduction over a one dimensional space.
    * Returns the accumulator `accum`. 
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr: Counter)(map: Int => C[T])(reduce: (T,T) => T): C[T]

  /** 
    * On-chip memory reduction over a two dimensional space.
    * Returns the accumulator `accum`.
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr1: Counter, ctr2: Counter)(map: (Int,Int) => C[T])(reduce: (T,T) => T): C[T]

  /** 
    * On-chip memory reduction over a three dimensional space.
    * Returns the accumulator `accum`.
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr1: Counter, ctr2: Counter, ctr3: Counter)(map: (Int,Int,Int) => C[T])(reduce: (T,T) => T): C[T]

  /** 
    * On-chip memory reduction over a 4+ dimensional space.
    * Returns the accumulator `accum`.
    * Note that the `map` function is on a `List` of iterators.
    * The number of iterators will be the same as the number of counters supplied.
    **/
  @api def apply[T,C[T]](accum: C[T])(ctr1: Counter, ctr2: Counter, ctr3: Counter, ctr4: Counter, ctr5: Counter*)(map: List[Int] => C[T])(reduce: (T,T) => T): C[T]

@table-end