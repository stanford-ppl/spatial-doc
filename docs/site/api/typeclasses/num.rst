
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

.. _Num:

Num
====

@alias Num

Combination of :doc:`arith`, :doc:`bits`, and :doc:`order` type classes

------------------------

**Abstract Methods**

@table-start
trait Num[T] extends Arith[T] with Bits[T] with Order[T]

  /** Converts `x` to a @FixPt value. **/
  @api def toFixPt[S:BOOL,I:INT,F:INT](x: T): FixPt[S,I,F]
  /** Converts `x` to a @FltPt value. **/
  @api def toFltPt[G:INT,E:INT](x: T): FltPt[G,E]

  /** 
    * Returns the closest representable value of type T to `x`. 
    * If `force` is true, gives a warning when the conversion is not exact.
    **/
  @api def fromInt(x: scala.Int, force: Boolean = true): T
  /** 
  * Returns the closest representable value of type T to `x`. 
  * If `force` is true, gives a warning when the conversion is not exact.
  **/
  @api def fromLong(x: scala.Long, force: Boolean = true): T
  /** 
  * Returns the closest representable value of type T to `x`. 
  * If `force` is true, gives a warning when the conversion is not exact.
  **/
  @api def fromFloat(x: scala.Float, force: Boolean = true): T
  /** 
  * Returns the closest representable value of type T to `x`. 
  * If `force` is true, gives a warning when the conversion is not exact.
  **/
  @api def fromDouble(x: scala.Double, force: Boolean = true): T

  /** Returns the largest, finite, positive value representable by type T. **/
  @api def maxValue: T
  
  /** Returns the most negative, finite value representable by type T. **/
  @api def minValue: T

  /** Returns the smallest positive (nonzero) value representable by type T. **/
  @api def minPositiveValue: T

@table-end
