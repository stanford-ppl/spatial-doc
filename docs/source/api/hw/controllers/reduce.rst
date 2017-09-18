
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

.. _Reduce:

Reduce
======

@alias Reduce

**Reduce** is a control node which takes many scalar values and combines them into one value using some associative operator.
Unless otherwise disabled, the compiler will attempt to automatically pipeline and parallelize *Reduce* nodes.
A Reduce consists of a *map* function, which is responsible for producing scalar values, and
a *reduction* function to describe how the values should be combined.

Calling any form of Reduce returns the accumulator which, at the end of the loop, will contain the final result of the reduction.



--------------

**Static methods**

@table-start
object Reduce

  /**
    * Reduction over a one dimensional space with implicit accumulator but explicit identity value. 
    * Returns the accumulator used to implement this reduction.
    **/
  @api def apply[T](identity: T)(ctr: Counter)(map: Int => T)(reduce: (T,T) => T): Reg[T]

  /** 
    * Reduction over a two dimensional space with implicit accumulator but explicit identity value. 
    * Returns the accumulator used to implement this reduction.
    **/
  @api def apply[T](identity: T)(ctr1: Counter, ctr2: Counter)(map: (Int,Int) => T)(reduce: (T,T) => T): Reg[T]

  /** 
    * Reduction over a three dimensional space with implicit accumulator but explicit identity value. 
    * Returns the accumulator used to implement this reduction.
    **/
  @api def apply[T](identity: T)(ctr1: Counter, ctr2: Counter, ctr3: Counter)(map: (Int,Int,Int) => T)(reduce: (T,T) => T): Reg[T]

   /** 
    * Reduction over an 4+ dimensional space with implicit accumulator but explicit identity value. 
    * Returns the accumulator used to implement this reduction.
    * Note that the `map` function is on a `List` of iterators.
    * The number of iterators will be the same as the number of counters supplied.
    **/
  @api def apply[T](identity: T)(ctr1: Counter, ctr2: Counter, ctr3: Counter, ctr4: Counter, ctr5: Counter*)(map: List[Int] => T)(reduce: (T,T) => T): Reg[T]


  /**
    * Reduction over a one dimensional space with explicit accumulator. 
    * Returns the accumulator used to implement this reduction.
    **/
  @api def apply[T](accum: Reg[T])(ctr: Counter)(map: Int => T)(reduce: (T,T) => T): Reg[T]

  /** 
    * Reduction over a two dimensional space with explicit accumulator. 
    * Returns the accumulator used to implement this reduction.
    **/
  @api def apply[T](accum: Reg[T])(ctr1: Counter, ctr2: Counter)(map: (Int,Int) => T)(reduce: (T,T) => T): Reg[T]

  /** 
    * Reduction over a three dimensional space with explicit accumulator. 
    * Returns the accumulator used to implement this reduction.
    **/
  @api def apply[T](accum: Reg[T])(ctr1: Counter, ctr2: Counter, ctr3: Counter)(map: (Int,Int,Int) => T)(reduce: (T,T) => T): Reg[T]

   /** 
    * Reduction over an 4+ dimensional space with explicit accumulator. 
    * Returns the accumulator used to implement this reduction.
    * Note that the `map` function is on a `List` of iterators.
    * The number of iterators will be the same as the number of counters supplied.
    **/
  @api def apply[T](accum: Reg[T])(ctr1: Counter, ctr2: Counter, ctr3: Counter, ctr4: Counter, ctr5: Counter*)(map: List[Int] => T)(reduce: (T,T) => T): Reg[T]

@table-end
