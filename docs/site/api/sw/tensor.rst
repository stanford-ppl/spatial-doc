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

.. _Tensor:

Tensor
======

@alias Tensor
@alias Tensor3
@alias Tensor4
@alias Tensor5

**Tensors** are multi-dimension, dense arrays with more than 2 dimensions. Currently 3 - 5 dimension Tensors are supported.

Like @Array and @Matrix, Tensors can only be used in host code. In accelerator code, use @DRAM (for off-chip) or @SRAM (on-chip) memories
for multi-dimensional array support.


-------------------------

**Constructors**

Spatial includes syntax for constructing Tensor instances from indexed functions. 


The following returns a 16 x 32 x 8 Tensor3, with elements defined by `func(i,j,k)`::

  (0::16, 0::32, 0::8){(i,j,k) => func(i,j,k) }


A Tensor4 can be constructed in a similar way:: 

  (0::4, 0::16, 0::8, 0::32){(a,b,c,d) => func(a,b,c,d) }


As can a Tensor5::
  
  (0::2, 0::4, 0::5, 0::3, 0::32){(a,b,c,d,e) => func(a,b,c,d,e) }


More general @Range forms can also be used, including strided (e.g. 0::2::8) and offset (e.g. 32::64). 
Iterators (e.g. `i`, `j`, `k` in the above examples) will iterate over all values in their respective ranges.


-------------------------

**Static methods**

@table-start
object Tensor3


  /** Returns an immutable Tensor3 with the given dimensions and elements defined by `func`. **/
  @api def tabulate[T:Type](dim0: Index, dim1: Index, dim2: Index)(func: (Index, Index, Index) => T): Tensor3[T]

  /**
    * Returns an immutable Tensor3 with the given dimensions and elements defined by `func`.
    * Note that while `func` does not depend on the index, it is still executed multiple times.
    */
  @api def fill[T:Type](dim0: Index, dim1: Index, dim2: Index)(func: => T): Tensor3[T]
  

@table-end



@table-start
object Tensor4

  /** Returns an immutable Tensor4 with the given dimensions and elements defined by `func`. **/
  @api def tabulate[T:Type](dim0: Index, dim1: Index, dim2: Index, dim3: Index)(func: (Index, Index, Index, Index) => T): Tensor4[T]
  
  /**
    * Returns an immutable Tensor4 with the given dimensions and elements defined by `func`.
    * Note that while `func` does not depend on the index, it is still executed multiple times.
    */
  @api def fill[T:Type](dim0: Index, dim1: Index, dim2: Index, dim3: Index)(func: => T): Tensor4[T]

@table-end




@table-start
object Tensor5

  /** Returns an immutable Tensor5 with the given dimensions and elements defined by `func`. **/
  @api def tabulate[T:Type](dim0: Index, dim1: Index, dim2: Index, dim3: Index, dim4: Index)(func: (Index, Index, Index, Index, Index) => T): Tensor5[T]

  /**
    * Returns an immutable Tensor5 with the given dimensions and elements defined by `func`.
    * Note that while `func` does not depend on the index, it is still executed multiple times.
    */
  @api def fill[T:Type](dim0: Index, dim1: Index, dim2: Index, dim3: Index, dim4: Index)(func: => T): Tensor5[T]

@table-end

-------------------------


**Infix methods**

@table-start
class Tensor3[T]

 /** Returns the first dimension of this Tensor3. **/
  @api def dim0: Index = field[Index]("dim0")
  /** Returns the second dimension of this Tensor3. **/
  @api def dim1: Index = field[Index]("dim1")
  /** Returns the third dimension of this Tensor3. **/
  @api def dim2: Index = field[Index]("dim2")
  /** Returns the element in this Tensor3 at the given 3-dimensional address. **/
  @api def apply(i: Index, j: Index, k: Index): T = data.apply(i*dim1*dim2 + j*dim2 + k)
  /** Updates the element at the given 3-dimensional address to `elem`. **/
  @api def update(i: Index, j: Index, k: Index, elem: T): MUnit = data.update(i*dim1*dim2 + j*dim1 + k, elem)
  /** Returns a flattened, immutable @Array view of this Tensor3's data. **/
  @api def flatten: MArray[T] = data

  /** Applies the function `func` on each element in this Tensor3. **/
  @api def foreach(func: T => MUnit): MUnit = data.foreach(func)
  /** Returns a new Tensor3 created using the mapping `func` over each element in this Tensor3. **/
  @api def map[R:Type](func: T => R): Tensor3[R] = tensor3(data.map(func), dim0, dim1, dim2)
  /** Returns a new Tensor3 created using the pairwise mapping `func` over each element in this Tensor3
    * and the corresponding element in `that`.
    */
  @api def zip[S,R:Type](that: Tensor3[S])(func: (T,S) => R): Tensor3[R] = tensor3(data.zip(that.data)(func), dim0, dim1, dim2)
  /** Reduces the elements in this Tensor3 into a single element using associative function `rfunc`. **/
  @api def reduce(rfunc: (T,T) => T): T = data.reduce(rfunc)

@table-end


@table-start
class Tensor4[T]

  /** Returns the first dimension of this Tensor4. **/
  @api def dim0: Index = field[Index]("dim0")
  /** Returns the second dimension of this Tensor4. **/
  @api def dim1: Index = field[Index]("dim1")
  /** Returns the third dimension of this Tensor4. **/
  @api def dim2: Index = field[Index]("dim2")
  /** Returns the fourth dimension of this Tensor4. **/
  @api def dim3: Index = field[Index]("dim3")
  /** Returns the element in this Tensor4 at the given 4-dimensional address. **/
  @api def apply(i: Index, j: Index, k: Index, l: Index): T = data.apply(i*dim1*dim2*dim3 + j*dim2*dim3 + k*dim3 + l)
  /** Updates the element at the given 4-dimensional address to `elem`. **/
  @api def update(i: Index, j: Index, k: Index, l: Index, elem: T): MUnit = data.update(i*dim1*dim2*dim3 + j*dim2*dim3 + k*dim3 + l, elem)
  /** Returns a flattened, immutable @Array view of this Tensor4's data. **/
  @api def flatten: MArray[T] = data

  /** Applies the function `func` on each element in this Tensor4. **/
  @api def foreach(func: T => MUnit): MUnit = data.foreach(func)
  /** Returns a new Tensor4 created using the mapping `func` over each element in this Tensor4. **/
  @api def map[R:Type](func: T => R): Tensor4[R] = tensor4(data.map(func), dim0, dim1, dim2, dim3)
  /** Returns a new Tensor4 created using the pairwise mapping `func` over each element in this Tensor4
    * and the corresponding element in `that`.
    */
  @api def zip[S,R:Type](b: Tensor4[S])(func: (T,S) => R): Tensor4[R] = tensor4(data.zip(b.data)(func), dim0, dim1, dim2, dim3)
  /** Reduces the elements in this Tensor4 into a single element using associative function `rfunc`. **/
  @api def reduce(rfunc: (T,T) => T): T = data.reduce(rfunc)

@table-end


@table-start
class Tensor5[T]

  /** Returns the first dimension of this Tensor5. **/
  @api def dim0: Index = field[Index]("dim0")
  /** Returns the second dimension of this Tensor5. **/
  @api def dim1: Index = field[Index]("dim1")
  /** Returns the third dimension of this Tensor5. **/
  @api def dim2: Index = field[Index]("dim2")
  /** Returns the fourth dimension of this Tensor5. **/
  @api def dim3: Index = field[Index]("dim3")
  /** Returns the fifth dimension of this Tensor5. **/
  @api def dim4: Index = field[Index]("dim4")
  /** Returns the element in this Tensor5 at the given 5-dimensional addreess. **/
  @api def apply(i: Index, j: Index, k: Index, l: Index, m: Index): T = data.apply(i*dim1*dim2*dim3*dim4 + j*dim2*dim3*dim4 + k*dim3*dim4 + l*dim4 + m)
  /** Updates the element at the given 5-dimensional address to `elem`. **/
  @api def update(i: Index, j: Index, k: Index, l: Index, m: Index, elem: T): MUnit = data.update(i*dim1*dim2*dim3*dim4 + j*dim2*dim3*dim4 + k*dim3*dim4 + l*dim4 + m, elem)
  /** Returns a flattened, immutable @Array view of this Tensor5's data. **/
  @api def flatten: MArray[T] = data

  /** Applies the function `func` on each element in this Tensor5. **/
  @api def foreach(func: T => MUnit): MUnit = data.foreach(func)
  /** Returns a new Tensor5 created using the mapping `func` over each element in this Tensor5. **/
  @api def map[R:Type](func: T => R): Tensor5[R] = tensor5(data.map(func), dim0, dim1, dim2, dim3, dim4)
  /** Returns a new Tensor5 created using the pairwise mapping `func` over each element in this Tensor5
    * and the corresponding element in `that`.
    */
  @api def zip[S,R:Type](b: Tensor5[S])(func: (T,S) => R): Tensor5[R] = tensor5(data.zip(b.data)(func), dim0, dim1, dim2, dim3, dim4)
  /** Reduces the elements in this Tensor5 into a single element using associative function `rfunc`. **/
  @api def reduce(rfunc: (T,T) => T): T = data.reduce(rfunc)

@table-end
