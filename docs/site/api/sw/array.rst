
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

.. _Array:

Array
=====

@alias Array

Class and companion object for managing one dimensional arrays on the CPU.

Note that this type shadows the unstaged Scala Array.
In the case where an unstaged Array type is required, use the full `scala.Array` name.


---------------------

**Constructor**

The following syntax is available for constructing Arrays from indexed functions:: 

  (0::32){i => func(i) }

This returns an Array of size 32 with elements defined by `func(i)`.
More general @Range forms can also be used, including strided (e.g. 0::2::8) and offset (e.g. 32::64). 
The iterator `i` will iterate over all values in the supplied range.

---------------------

**Static methods**

@table-start
object Array

  /** Returns an immutable Array with the given `size` and elements defined by `func`. **/
  @api def tabulate[T:Type](size: Index)(func: Index => T): MArray[T]
    = Array(mapindices(size.s, {i => func(wrap(i)).s}, fresh[Index]))
  /**
    * Returns an immutable Array with the given `size` and elements defined by `func`.
    * Note that while `func` does not depend on the index, it is still executed `size` times.
    */
  @api def fill[T:Type](size: Index)(func: => T): MArray[T] = this.tabulate(size){ _ => func}

  /** Returns an empty, mutable Array with the given `size`. **/
  @api def empty[T:Type](size: Index): MArray[T] = Array(mutable[T](size.s))

  /** Returns an immutable Array with the given elements. **/
  @api def apply[T:Type](elements: T*): MArray[T] = Array(from_seq(unwrap(elements)))

@table-end


------------------


**Infix methods**

@table-start
class Array[T]

    /** Returns the size of this Array. **/
  @api def length: Index = wrap{ Array.length(this.s) }

  /** Returns the element at index `i`. **/
  @api def apply(i: Index): T = wrap{ Array.apply(this.s, i.s) }
  /** Updates the element at index `i` to data. **/
  @api def update[A](i: Index, data: A)(implicit lift: Lift[A,T]): MUnit = Unit(Array.update(this.s,i.s,lift(data).s))

  /** Applies the function **func** on each element in the Array. **/
  @api def foreach(func: T => MUnit): MUnit

  /** Returns a new Array created using the mapping `func` over each element in this Array. **/
  @api def map[R:Type](func: T => R): Array[R]

  /** Returns a new Array created using the pairwise mapping `func` over each element in this Array
    * and the corresponding element in `that`.
    */
  @api def zip[S:Type,R:Type](that: Array[S])(func: (T,S) => R): Array[R]

  /** Reduces the elements in this Array into a single element using associative function `rfunc`. **/
  @api def reduce(rfunc: (T,T) => T): T

  /**
    * Reduces the elements in this Array and the given initial value into a single element
    * using associative function `rfunc`.
    */
  @api def fold(init: T)(rfunc: (T,T) => T): T

  /** Returns a new Array with all elements in this Array which satisfy the given predicate `cond`. **/
  @api def filter(cond: T => MBoolean): Array[T]

  /** Returns a new Array created by concatenating the results of `func` applied to all elements in this Array. **/
  @api def flatMap[R:Type](func: T => Array[R]): Array[R]

  /** Partitions this Array using the `key` function, then maps each element using `value`, and
    * finally combines values in each bin using the associative `reduce` function. 
    **/
  @api def groupByReduce[K:Type,V:Type](key: A => K)(value: A => V)(reduce: (V,V) => V): MHashMap[K,V] = {

  /** Creates a string representation of this Array using the given `delimeter`. **/
  @api def mkString(delimeter: MString) = this.mkString("", delimeter, "")

  /** Creates a string representation of this Array using the given `delimeter`, bracketed by `start` and `stop`. **/
  @api def mkString(start: MString, delimeter: MString, stop: MString): MString

  /** Returns an immutable view of the data in this Array as a @Matrix with given `rows` and `cols`. **/
  @api def reshape(rows: Index, cols: Index): Matrix[T] = {
  
  /** Returns an immutable view of the data in this Array as a @Tensor3 with given dimensions. **/
  @api def reshape(dim0: Index, dim1: Index, dim2: Index): Tensor3[T] = {
  
  /** Returns an immutable view of the data in this Array as a @Tensor4 with given dimensions. **/
  @api def reshape(dim0: Index, dim1: Index, dim2: Index, dim3: Index): Tensor4[T] = {
  
  /** Returns an immutable view of the data in this Array as a @Tensor5 with given dimensions. **/
  @api def reshape(dim0: Index, dim1: Index, dim2: Index, dim3: Index, dim4: Index): Tensor5[T] = {
  

  /** Returns true if this Array and `that` differ by at least one element, false otherwise. **/
  @api def !=(that: Array[T]): MBoolean = this.zip(that){(x,y) => x =!= y }.reduce{_ || _}

  /** Returns true if this Array and `that` contain the same elements, false otherwise. **/
  @api def ==(that: Array[T]): MBoolean = this.zip(that){(x,y) => x === y }.reduce{_ && _}
  
@table-end
