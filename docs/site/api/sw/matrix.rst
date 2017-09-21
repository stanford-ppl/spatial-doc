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

.. _Matrix:

Matrix
======

@alias Matrix

Class and companion object for dense matrices on the CPU. 

------------------

**Constructor**

The following syntax is available for constructing Matrix instances from indexed functions:: 

  (0::16, 0::32){(i,j) => func(i,j) }

This returns a Matrix with 16 rows and 32 columns, with elements defined by `func(i,j)`.
More general @Range forms can also be used, including strided (e.g. 0::2::8) and offset (e.g. 32::64). 
The iterators `i` and `j` will iterate over all values in their respective ranges.

------------------

**Static methods**

@table-start
object Matrix

  /** Returns an immutable Matrix with the given `rows` and `cols` and elements defined by `func`. **/
  @api def tabulate[T:Type](rows: Index, cols: Index)(func: (Index, Index) => T): Matrix[T]

  /**
    * Returns an immutable Matrix with the given `rows` and `cols` and elements defined by `func`.
    * Note that while `func` does not depend on the index, it is still executed `rows`*`cols` times.
    */
  @api def fill[T:Type](rows: Index, cols: Index)(func: => T): Matrix[T] = this.tabulate(rows, cols){(_,_) => func}


@table-end


------------------

**Infix methods**

@table-start
class Matrix

  /** Returns the number of rows in this Matrix. **/
  @api def rows: Index = field[Index]("rows")
  /** Returns the number of columns in this Matrix. **/
  @api def cols: Index = field[Index]("cols")
  /** Returns the element at the given two-dimensional address (`i`, `j`). **/
  @api def apply(i: Index, j: Index): T = data.apply(i*cols + j)
  /** Updates the element at the given two dimensional address to `elem`. **/
  @api def update(i: Index, j: Index, elem: T): MUnit = data.update(i*cols + j, elem)

  /** Returns a flattened, immutable @Array view of this Matrix's data. **/
  @api def flatten: MArray[T] = data

  /** Applies the function `func` on each element in this Matrix. **/
  @api def foreach(func: T => MUnit): MUnit = data.foreach(func)
  /** Returns a new Matrix created using the mapping `func` over each element in this Matrix. **/
  @api def map[R:Type](func: T => R): Matrix[R] = matrix(data.map(func), rows, cols)
  /** Returns a new Matrix created using the pairwise mapping `func` over each element in this Matrix
    * and the corresponding element in `that`.
    */
  @api def zip[S:Type,R:Type](that: Matrix[S])(func: (T,S) => R): Matrix[R] = matrix(data.zip(that.data)(func), rows, cols)
  /** Reduces the elements in this Matrix into a single element using associative function `rfunc`. **/
  @api def reduce(rfunc: (T,T) => T): T = data.reduce(rfunc)
  /** Returns the transpose of this Matrix. **/
  @api def transpose(): Matrix[T] = (0::cols, 0::rows){(j, i) => apply(i,j) }

@table-end

