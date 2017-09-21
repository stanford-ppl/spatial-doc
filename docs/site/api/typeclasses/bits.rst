
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

.. _Bits:

Bits
====

@alias Bits

The Bits type class is used to supply evidence that a type T is representable by a statically known number of bits.


----------------

**Abstract Methods**

@table-start
trait Bits[T]

  /** Returns the minimum number of bits required to represent type T. **/
  def length: scala.Int

  /** Returns the zero value for type T. **/
  def zero: T

  /** Returns the one value for type T. **/
  def one: T

  /** 
    * Generates a pseudorandom value uniformly distributed between 0 and max. 
    * If max is unspecified, type T's default maximum is used instead. 
    * For @FixPt types, the default maximum is the maximum representable number. 
    * For @FltPt types, the default maximum is 1. 
    * For composite @Tuple2 and @Struct types, the maximum is determined per component. 
    **/
  def random(max: Option[T]): T

@table-end

----------------

**Related methods**

@table-start
NoHeading

  /** Returns the zero value for type T. **/
  def zero[T:Bits]: T

  /** Returns the one value for type T. **/
  def one[T:Bits]: T

  /** Generates a pseudorandom value uniformly distributed between 0 and the default maximum for type T. **/
  def random[T:Bits]: T

  /** Generates a pseudorandom value uniformly distributed between 0 and `max`. **/
  def random[T:Bits](max: T): T

@table-end


----------------

**Infix methods**

Instances of types which have evidence of Bits also have infix methods defined on them: 

@table-start
NoHeading

  /**
    * Returns the given bit in this value.
    * 0 corresponds to the least significant bit (LSB).
    **/
  @api def apply(i: Int): Bit = dataAsBitVector(x).apply(i)

  /**
    * Returns a slice of the bits in this word as a VectorN.
    * The range must be statically determinable with a stride of 1.
    * The range is inclusive for both the start and end.
    * The range can be big endian (e.g. ``3::0``) or little endian (e.g. ``0::3``).
    * In both cases, element 0 is always the least significant element.
    *
    * For example, ``x(3::0)`` returns a Vector of the 4 least significant bits of ``x``.
    */
  @api def apply(range: MRange): BitVector = dataAsBitVector(x).apply(range)

  /**
    * Re-interprets this value's bits as the given type, without conversion.
    * If B has fewer bits than this value's type, the MSBs will be dropped.
    * If B has more bits than this value's type, the resulting MSBs will be zeros.
    */
  @api def as[B:Type:Bits]: B

  /** Returns a value of the same type with this value's bits in reverse order. **/
  @api def reverse: A

  /**
    * Returns a slice of X bits of this value starting at the given `offset` from the LSB.
    * To satisfy Scala's static type analysis, each bit-width has a separate method.
    *
    * For example, ``x.take3(1)`` returns the 3 least significant bits of x after the LSB
    * as a Vector3[Bit].
    */
  @api def takeX(offset: scala.Int): VectorX[Bit] = dataAsBitVector(x).takeJJ(offset)

  /**
    * Returns a slice of X bits of this value, starting at the given `offset` from the MSB.
    * To satisfy Scala's static type analysis, each bit-width has a separate method.
    * Slices between 1 and 128 bits are currently supported.
    *
    * For example, ``x.take3MSB(1)`` returns the 3 most significant bits of x after the MSB
    * as a Vector3[Bit].
    */
  @api def takeXMSB(scala.Int): VectorX[Bit]

  /**
    * Returns a view of this value's bits as a X-bit Vector.
    * To satisfy Scala's static analysis, each bit-width has a separate method.
    * Conversions between 1 and 128 bits are currently supported.
    * 
    * If X is smaller than this value's total bits, the MSBs will be dropped.
    * If X is larger than this value's total bits, the resulting MSBs will be zeros.
    */
  @api def asXb: VectorX[Bit]

@table-end




