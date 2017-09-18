
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

.. _LUT:

LUT
====

@alias LUT
@alias LUT1
@alias LUT2
@alias LUT3
@alias LUT4
@alias LUT5


**LUTs** are on-chip, read-only memories of fixed size. LUTs can be specified as 1 to 5 dimensional.

LUTs can be created from files using the ``fromFile`` methods in the LUT object. This file reading is currently done at compilation time.

---------------

**Static methods**

@table-start
object LUT

 /**
    * Creates a 1-dimensional read-only lookup table with given elements.
    * The number of supplied elements must match the given dimensions.
    */
  @api def apply[T:Type:Bits](dim: Int)(elems: T*): LUT1[T] = {
  /**
    * Creates a 2-dimensional read-only lookup table with given elements.
    * The number of supplied elements must match the given dimensions.
    */
  @api def apply[T:Type:Bits](dim1: Int, dim2: Int)(elems: T*): LUT2[T] = {
  /**
    * Creates a 3-dimensional read-only lookup table with given elements.
    * The number of supplied elements must match the given dimensions.
    */
  @api def apply[T:Type:Bits](dim1: Int, dim2: Int, dim3: Int)(elems: T*): LUT3[T] = {
  /**
    * Creates a 4-dimensional read-only lookup table with given elements.
    * The number of supplied elements must match the given dimensions.
    */
  @api def apply[T:Type:Bits](dim1: Int, dim2: Int, dim3: Int, dim4: Int)(elems: T*): LUT4[T] = {
  /**
    * Creates a 5-dimensional read-only lookup table with given elements.
    * The number of supplied elements must match the given dimensions.
    */
  @api def apply[T:Type:Bits](dim1: Int, dim2: Int, dim3: Int, dim4: Int, dim5: Int)(elems: T*): LUT5[T] = {


/**
    * Creates a 1-dimensional read-only lookup table from the given data file.
    * Note that this file is read during `compilation`, not runtime.
    * The number of supplied elements must match the given dimensions.
    */
  @api def fromFile[T:Type:Bits](dim1: Int)(filename: String): LUT1[T] = {

  /**
    * Creates a 2-dimensional read-only lookup table from the given data file.
    * Note that this file is read during `compilation`, not runtime.
    * The number of supplied elements must match the given dimensions.
    */
  @api def fromFile[T:Type:Bits](dim1: Int, dim2: Int)(filename: String): LUT2[T] = {
  /**
    * Creates a 3-dimensional read-only lookup table from the given data file.
    * Note that this file is read during `compilation`, not runtime.
    * The number of supplied elements must match the given dimensions.
    */
  @api def fromFile[T:Type:Bits](dim1: Int, dim2: Int, dim3: Int)(filename: String): LUT3[T] = {
  /**
    * Creates a 4-dimensional read-only lookup table from the given data file.
    * Note that this file is read during `compilation`, not runtime.
    * The number of supplied elements must match the given dimensions.
    */
  @api def fromFile[T:Type:Bits](dim1: Int, dim2: Int, dim3: Int, dim4: Int)(filename: String): LUT4[T] = {
  /**
    * Creates a 5-dimensional read-only lookup table from the given data file.
    * Note that this file is read during `compilation`, not runtime.
    * The number of supplied elements must match the given dimensions.
    */
  @api def fromFile[T:Type:Bits](dim1: Int, dim2: Int, dim3: Int, dim4: Int, dim5: Int)(filename: String): LUT5[T] = {

@table-end

--------------

**Infix methods**

@table-start
abstract class LUT[T]

@table-end


@table-start
class LUT1[T] extends LUT[T]

  /** Returns the element at the given address `i`. **/
  @api def apply(i: Index): T = wrap(LUT.load(s, Seq(i.s), Bit.const(true)))

@table-end


@table-start
class LUT2[T] extends LUT[T]

  /** Returns the element at the given address `r`, `c`. **/
  @api def apply(r: Index, c: Index): T = wrap(LUT.load(s, Seq(r.s, c.s), Bit.const(true)))

@table-end


@table-start
class LUT3[T] extends LUT[T]

  /** Returns the element at the given 3-dimensional address. **/
  @api def apply(r: Index, c: Index, p: Index): T = wrap(LUT.load(s, Seq(r.s, c.s, p.s), Bit.const(true)))

@table-end



@table-start
class LUT4[T] extends LUT[T]

  /** Returns the element at the given 4-dimensional address. **/
  @api def apply(r: Index, c: Index, p: Index, q: Index): T = wrap(LUT.load(s, Seq(r.s, c.s, p.s, q.s), Bit.const(true)))

@table-end


@table-start
class LUT5[T] extends LUT[T]

  /** Returns the element at the given 5-dimensional address. **/
  @api def apply(r: Index, c: Index, p: Index, q: Index, m: Index): T = wrap(LUT.load(s, Seq(r.s, c.s, p.s, q.s, m.s), Bit.const(true)))

@table-end 

