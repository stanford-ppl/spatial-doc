
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

.. _FltPt:

FltPt
=====

@alias FltPt
@alias Half
@alias Float
@alias Double

FltPt[G,E] represents an arbitrary precision, IEEE-754-like representation.
FltPt values are always assumed to be signed.

The type parameters for FltPt are:

+---+-----+------------------------------------------------+---------------+
| G | INT | Number of significand bits, including sign bit | (_2 - _64)    |
+---+-----+------------------------------------------------+---------------+
| E | INT | Number of exponent bits                        | (_1 - _64)    |
+---+-----+------------------------------------------------+---------------+

Note that numbers of bits use the underscore prefix as integers cannot be used as type parameters in Scala.


**Type Aliases**

Specific types of FltPt values can be managed using type aliases.
New type aliases can be created using:::

    type MyType = FltPt[_##,_##]


Spatial defines the following type aliases by default:

+----------+---------+-------------------------+---------------------------+
| **type** | Half    | :doc:`fltpt`\[_11,_5\]  | IEEE-754 half precision   |
+----------+---------+-------------------------+---------------------------+
| **type** | Float   | :doc:`fltpt`\[_24,_8\]  | IEEE-754 single precision |
+----------+---------+-------------------------+---------------------------+
| **type** | Double  | :doc:`fltpt`\[_53,_11\] | IEEE-754 double precision |
+----------+---------+-------------------------+---------------------------+

Note that the Float and Double types shadow their respective unstaged Scala types.
In the case where an unstaged type is required, use the full `scala.*` name.


**Infix methods**

@table-start
class FltPt[G,E]

  /** Returns the negation of this floating point value. **/
  @api def unary_-(): FltPt[G,E] = FltPt(flt.neg(this.s))
  /** Floating point addition. **/
  @api def + (that: FltPt[G,E]): FltPt[G,E] = FltPt(flt.add(this.s,that.s))
  /** Floating point subtraction. **/
  @api def - (that: FltPt[G,E]): FltPt[G,E] = FltPt(flt.sub(this.s,that.s))
  /** Floating point multiplication. **/
  @api def * (that: FltPt[G,E]): FltPt[G,E] = FltPt(flt.mul(this.s,that.s))
  /** Floating point division. **/
  @api def / (that: FltPt[G,E]): FltPt[G,E] = FltPt(flt.div(this.s,that.s))

  /** Integer exponentiation, implemented in hardware as a reduction tree with **exp** inputs. **/
  @api def \*\*(exp: scala.Int): FltPt[G,E]

  /**
    * Less than comparison.
    *
    * Returns `true` if this value is less than `that` value. Otherwise returns `false`.
    */
  @api def < (that: FltPt[G,E]): MBoolean   = Boolean( flt.lt(this.s,that.s))
  /**
    * Less than or equal comparison.
    *
    * Returns `true` if this value is less than or equal to `that` value. Otherwise returns `false`.
    */
  @api def <=(that: FltPt[G,E]): MBoolean   = Boolean(flt.leq(this.s,that.s))
  /**
    * Greater than comparison.
    *
    * Returns `true` if this value is greater than `that` value. Otherwise returns `false`.
    */
  @api def > (that: FltPt[G,E]): MBoolean   = Boolean( flt.lt(that.s,this.s))
  /**
    * Greater than or equal comparison.
    *
    * Returns `true` if this value is less than `that` value. Otherwise returns `false`.
    */
  @api def >=(that: FltPt[G,E]): MBoolean   = Boolean(flt.leq(that.s,this.s))

  /**
    * Value inequality comparison.
    * Returns `true` if this value is not equal to the right hand side. Otherwise returns `false`.   
    **/
  @api def !=(that: FltPt[G,E]): Boolean 

  /**
    * Value equality comparison.
    * Returns `true` if this value is equal to the right hand side. Otherwise returns `false`.  
    **/
  @api def !=(that: FltPt[G,E]): Boolean 

  /** Re-interprets this value's bits as the given type, without conversion. **/
  @api def as[T:Type:Bits]: T

  /** 
    * Returns the given bit in this value. 
    * 0 corresponds to the least significant bit (LSB).
    **/
  @api def apply(i: scala.Int): Bit

  /**
    * Returns a vector of bits based on the given range.
    * The range must be statically determinable values.
    */
  @api def apply(range: Range): Vector[Bit]

  /** Returns a floating point value with this value's bits in reverse order. **/
  @api def reverse: FltPt[G,E]


  /**
    * Converts this value to the given type.
    * 
    * Currently supported types are @FixPt, @FltPt, and @String.
    **/
  @api def to[T:Type:Bits]: T

  /** Creates a printable String representation of this value.
    * 
    * `NOTE`: This method is unsynthesizable, and can be used only on the CPU or in simulation. 
    */
  @api def toString: String

@table-end
