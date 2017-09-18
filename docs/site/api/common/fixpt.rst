
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

.. _FixPt:

FixPt
=====

@alias FixPt
@alias Int
@alias Index
@alias Int32
@alias Int64

FixPt[S,I,F] represents an arbitrary precision fixed point representation.
FixPt values may be signed or unsigned. Negative values, if applicable, are represented
in twos complement.

The type parameters for FixPt are:

+---+------+-----------------------------------+-----------------+
| S | BOOL | Signed representation             | TRUE \| FALSE   |
+---+------+-----------------------------------+-----------------+
| I | INT  | Number of integer bits            | (_1 - _64)      |
+---+------+-----------------------------------+-----------------+
| F | INT  | Number of fractional bits         | (_0 - _64)      |
+---+------------------------------------------+-----------------+

Note that numbers of bits use the underscore prefix as integers cannot be used as type parameters in Scala.


**Type Aliases**

Specific types of FixPt values can be managed using type aliases.
New type aliases can be created using syntax like the following::

  type Q16_16 = FixPt[TRUE,_16,_16]



Spatial defines the following type aliases by default:


+----------+-------+-----------------------------+-------------------------------------+
| **type** | IntN  | :doc:`fixpt`\[TRUE,_N,_0\]  | Signed, N bit integer (_2 - _128)   |
+----------+-------+-----------------------------+-------------------------------------+
| **type** | UIntN | :doc:`fixpt`\[TRUE,_N,_0\]  | Unsigned, N bit integer (_2 - _128) |
+----------+-------+-----------------------------+-------------------------------------+
| **type** | Char  | :doc:`fixpt`\[TRUE,_8,_0\]  | Signed, 8 bit integer               |
+----------+-------+-----------------------------+-------------------------------------+
| **type** | Short | :doc:`fixpt`\[TRUE,_16,_0\] | Signed, 16 bit integer              |
+----------+-------+-----------------------------+-------------------------------------+
| **type** | Int   | :doc:`fixpt`\[TRUE,_32,_0\] | Signed, 32 bit integer              |
+----------+-------+-----------------------------+-------------------------------------+
| **type** | Index | :doc:`fixpt`\[TRUE,_32,_0\] | Signed, 32 bit integer (indexing)   |
+----------+-------+-----------------------------+-------------------------------------+
| **type** | Long  | :doc:`fixpt`\[TRUE,_64,_0\] | Signed, 64 bit integer              |
+----------+-------+-----------------------------+-------------------------------------+

Note that the Char, Short, Int, and Long types shadow their respective unstaged Scala types.
In the case where an unstaged type is required, use the full `scala.*` name.

-------------

**Infix methods**

The following infix methods are defined on all FixPt classes. When the method takes a right hand argument,
only values of the same FixPt class can be used for this argument.

@table-start
class FixPt[S,I,F]

/** Returns negation of this fixed point value. **/
  @api def unary_-(): FixPt[S,I,F] = FixPt(fix.neg(this.s))
  /** Returns bitwise inversion of this fixed point value. **/
  @api def unary_~(): FixPt[S,I,F] = FixPt(fix.inv(this.s))
  /** Fixed point addition. **/
  @api def + (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.add(this.s,that.s))
  /** Fixed point subtraction. **/
  @api def - (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.sub(this.s,that.s))
  /** Fixed point multiplication. **/
  @api def * (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.mul(this.s,that.s))
  /** Fixed point division. **/
  @api def / (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.div(this.s,that.s))
  /** Fixed point modulus. **/
  @api def % (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.mod(this.s,that.s))

  /**
    * Fixed point multiplication with unbiased rounding.
    *
    * After multiplication, probabilistically rounds up or down to the closest representable number.
    */
  @api def *& (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.mul_unbias(this.s,that.s))

  /**
    * Fixed point division with unbiased rounding.
    *
    * After division, probabilistically rounds up or down to the closest representable number.
    */
  @api def /& (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.div_unbias(this.s,that.s))

  /**
    * Saturating fixed point addition.
    *
    * Addition which saturates at the largest or smallest representable number upon over/underflow.
    */
  @api def <+> (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.add_sat(this.s,that.s))
  /**
    * Saturating fixed point subtraction.
    *
    * Subtraction which saturates at the largest or smallest representable number upon over/underflow.
    */
  @api def <-> (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.sub_sat(this.s,that.s))
  /**
    * Saturating fixed point multiplication.
    *
    * Multiplication which saturates at the largest or smallest representable number upon over/underflow.
    */
  @api def <*> (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.mul_sat(this.s,that.s))
  /**
    * Saturating fixed point division.
    *
    * Division which saturates at the largest or smallest representable number upon over/underflow.
    */
  @api def </> (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.div_sat(this.s,that.s))

  // Saturating and unbiased rounding operators
  /**
    * Saturating fixed point multiplication with unbiased rounding.
    *
    * After multiplication, probabilistically rounds up or down to the closest representable number.
    * After rounding, also saturates at the largest or smallest representable number upon over/underflow.
    */
  @api def <*&> (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.mul_unb_sat(this.s,that.s))
  /**
    * Saturating fixed point division with unbiased rounding.
    *
    * After division, probabilistically rounds up or down to the closest representable number.
    * After rounding, also saturates at the largest or smallest representable number upon over/underflow.
    */
  @api def </&> (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.div_unb_sat(this.s,that.s))

  /**
    * Less than comparison.
    *
    * Returns `true` if this value is less than `that` value. Otherwise returns `false`.
    */
  @api def < (that: FixPt[S,I,F]): MBoolean     = Boolean( fix.lt(this.s,that.s))
  /**
    * Less than or equal comparison.
    *
    * Returns `true` if this value is less than or equal to `that` value. Otherwise returns `false`.
    */
  @api def <=(that: FixPt[S,I,F]): MBoolean     = Boolean(fix.leq(this.s,that.s))
  /**
    * Greater than comparison
    *
    * Returns `true` if this value is greater than `that` value. Otherwise returns `false`.
    */
  @api def > (that: FixPt[S,I,F]): MBoolean     = Boolean( fix.lt(that.s,this.s))
  /**
    * Greater than or equal comparison.
    *
    * Returns `true` if this value is greater than or equal to `that` value. Otherwise returns `false`.
    */
  @api def >=(that: FixPt[S,I,F]): MBoolean     = Boolean(fix.leq(that.s,this.s))


  /**
    * Value inequality comparison.
    * Returns `true` if this value is not equal to the right hand side. Otherwise returns `false`.   
    **/
  @api def !=(that: FixPt[S,I,F]): Boolean 

  /**
    * Value equality comparison.
    * Returns `true` if this value is equal to the right hand side. Otherwise returns `false`.  
    **/
  @api def !=(that: FixPt[S,I,F]): Boolean 

  /** Bit-wise AND. **/
  @api def & (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.and(this.s,that.s))
  /** Bit-wise OR. **/
  @api def | (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.or(this.s,that.s))
  /** Bit-wise XOR. **/
  @api def ^ (that: FixPt[S,I,F]): FixPt[S,I,F] = FixPt(fix.xor(this.s,that.s))
  /** Logical shift left. **/
  @api def <<(that: FixPt[S,I,_0]): FixPt[S,I,F] = FixPt(fix.lsh(this.s, that.s))
  /** Arithmetic (sign-preserving) shift right. **/
  @api def >>(that: FixPt[S,I,_0]): FixPt[S,I,F] = FixPt(fix.rsh(this.s, that.s))
  /** Logical (zero-padded) shift right. **/
  @api def >>>(that: FixPt[S,I,_0]): FixPt[S,I,F] = FixPt(fix.ursh(this.s, that.s))

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

  /** Returns a FixPt value with this value's bits in reverse order. **/
  @api def reverse: FixPt[S,I,F]


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


--------------

**Specialized infix methods**

These methods are defined on only specific classes of FixPt values.

+---------------------+----------------------------------------------------------------------------------------------------------------------+
|      `subclass`       **Int** (aliases: **Index**, **FixPt**\[TRUE, _32, _0\])                                                             |
+=====================+======================================================================================================================+
| |               def   **::**\(end: :doc:`Int <fixpt>`): :doc:`range`                                                                       |
| |                       Creates a Range with this as the start (inclusive), the given end (noninclusive), and step of 1.                   |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **by**\(step: :doc:`Int <fixpt>`): :doc:`range`                                                                      |
| |                       Creates a Range with start of 0 (inclusive), this value as the end (noninclusive), and the given step.             |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **until**\(end: :doc:`Int <fixpt>`): :doc:`range`                                                                    |
| |                       Creates a Range with this as the start (inclusive), the given end (noninclusive), and step of 1.                   |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
