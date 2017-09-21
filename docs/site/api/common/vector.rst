
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

.. _Vector:

Vector
======

@alias Vector
@alias VectorX
@alias Vector2
@alias Vector3
@alias Vector4
@alias Vector8
@alias Vector16
@alias Vector32
@alias VectorN

**Vector** defines a fixed size collection of scalar values. It is distinct from @Array in that the size must always be statically determinable.
This allows Vector to always be allocated as a bus of wires when implemented in hardware.

Vector is most commonly used for managing parallel accesses to local memories in hardware and for bit-twiddling operations.

To allow static type checking and creation of the @Bits type class, the Vector type is split into subtypes based on the number of elements it contains.
For example, a Vector3[@Int] is composed of 3, 32-bit @Int values, while a Vector32[@Bit] is a single bus of 32 @Bit values.
Spatial currently defines Vector types from **Vector1** up to **Vector128**.

To work around :doc:`limitations with Scala's type system <../../faq>` Spatial also includes a VectorN type for when the vector width cannot be type-encoded.
This type generally needs to be annotated with the number of bits the user intended (e.g. ``vector.as32b``) before it can be written to memories.


----------------------

**Static methods**

@table-start
object Vector

  /** 
    * Creates a VectorX from the given X elements, where X is between 1 and 128.
    * The first element supplied is the most significant (Vector index of X - 1).
    * The last element supplied is the least significant (Vector index of 0).
    * 
    * Note that this method is actually overloaded 128 times based on the number of supplied arguments.
    **/
  @api def LittleEndian[T:Type:Bits](elem: T*): VectorX[T]

  /** 
  * Creates a VectorX from the given X elements, where X is between 1 and 128.
  * The first element supplied is the least significant (Vector index of 0).
  * The last element supplied is the most significant (Vector index of X - 1).
  * 
  * Note that this method is actually overloaded 128 times based on the number of supplied arguments.
  **/
  @api def BigEndian[T:Type:Bits](elem: T*): VectorX[T]
  
  /** A mnemonic for LittleEndian (with reference to the zeroth element being specified last in order). **/
  @api def ZeroLast[T:Type:Bits](elem: T*): VectorX[T]

  /** A mnemonic for BigEndian (with reference to the zeroth element being specified first in order). **/
  @api def ZeroFirst[T:Type:Bits](elem: T*): VectorX[T]

@table-end



Spatial also includes an alternate **Vectorize** object which takes a true arbitrary number of 
elements in all of its functions. As a result, these methods return VectorNs.

@table-start
object Vectorize

  /** 
    * Creates a VectorN from the given elements.
    * The first element supplied is the most significant (Vector index of N - 1).
    * The last element supplied is the least significant (Vector index of 0).
    **/
  @api def LittleEndian[T:Type:Bits](elem: T*): VectorN[T]

  /** 
  * Creates a VectorN from the given elements.
  * The first element supplied is the least significant (Vector index of 0).
  * The last element supplied is the most significant (Vector index of N - 1).
  **/
  @api def BigEndian[T:Type:Bits](elem: T*): VectorN[T]
  
  /** A mnemonic for LittleEndian (with reference to the zeroth element being specified last in order). **/
  @api def ZeroLast[T:Type:Bits](elem: T*): VectorN[T]

  /** A mnemonic for BigEndian (with reference to the zeroth element being specified first in order). **/
  @api def ZeroFirst[T:Type:Bits](elem: T*): VectorN[T]

@table-end



----------------------

**Infix methods**

@table-start
class Vector[T]

  /**
    * Returns the `i`'th element of this Vector.
    * Element 0 is always the LSB.
    */
  @api def apply(i: Int): T = wrap(Vector.select(s,i))

  /**
    * Returns a slice of the elements in this Vector as a VectorN.
    * The range must be statically determinable with a stride of 1.
    * The range is inclusive for both the start and end.
    * The `range` can be big endian (e.g. `3::0`) or little endian (e.g. `0::3`).
    * In both cases, element 0 is always the least significant element.
    *
    * For example, `x(3::0)` returns a Vector of the 4 least significant elements of `x`.
    */
  @api def apply(range: Range)(implicit mT: Type[T], bT: Bits[T]): VectorN[T] = {
  
  /**
    * Returns a slice of N elements of this Vector starting at the given `offset` from the
    * least significant element.
    * To satisfy Scala's static type analysis, each width has a separate method.
    *
    * For example, `x.take3(1)` returns the 3 least significant elements of x after the
    * least significant as a Vector3[T].
    */
  @api def takeX(offset: scala.Int): VectorX[T]


  /** Returns true if this Vector and `that` differ by at least one element, false otherwise. **/
  @api def !=(that: Vector[T]): MBoolean

  /** Returns true if this Vector and `that` contain the same elements, false otherwise. **/
  @api def ==(that: Vector[T]): MBoolean

@table-end



@table-start
class VectorX[T] extends Vector[T]

@table-end



@table-start
class VectorN[T]

  /** 
    * Casts this VectorN as a VectorX.
    * Values of X from 1 to 128 are currently supported. 
    * 
    * If the VectorX type has fewer elements than this value's type, the most significant elements will be dropped.
    * If the VectorX type has more elements than this value's type, the resulting elements will be zeros.
    **/
  @api def asVectorX: VectorX[T]


  /**
    * Returns a view of this VectorN's bits as a X-bit Vector.
    * To satisfy Scala's static analysis, each bit-width has a separate method.
    * Conversions between 1 and 128 bits are currently supported.
    * 
    * If X is smaller than this VectorN's total bits, the MSBs will be dropped.
    * If X is larger than this VectorN's total bits, the resulting MSBs will be zeros.
    */
  @api def asXb: VectorX[Bit]

@table-end

