
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

+----------+----------------------------------------------------------------------------------------------------------------+
| class      **FixPt**\[S,I,F\]                                                                                             |
+==========+================================================================================================================+
| |    def   **unary_-**\(\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                                                |
| |            Returns negation of this fixed point value.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **unary_~**\(\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                                                |
| |            Returns bitwise inversion of this fixed point value.                                                         |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **+** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Fixed point addition.                                                                                        |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **-** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Fixed point subtraction.                                                                                     |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **\*** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                |
| |            Fixed point multiplication.                                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **/** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Fixed point division.                                                                                        |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **%** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Fixed point modulus.                                                                                         |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **\*&** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                               |
| |            Fixed point multiplication with unbiased rounding.                                                           |
| |                                                                                                                         |
| |            After multiplication, probabilistically rounds up or down to the closest representable number.               |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **/&** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                |
| |            Fixed point division with unbiased rounding.                                                                 |
| |                                                                                                                         |
| |            After division, probabilistically rounds up or down to the closest representable number.                     |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<+>** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                               |
| |            Saturating fixed point addition.                                                                             |
| |                                                                                                                         |
| |            Addition which saturates at the largest or smallest representable number upon over/underflow.                |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<->** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                               |
| |            Saturating fixed point subtraction.                                                                          |
| |                                                                                                                         |
| |            Subtraction which saturates at the largest or smallest representable number upon over/underflow.             |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<\*>** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                              |
| |            Saturating fixed point multiplication.                                                                       |
| |                                                                                                                         |
| |            Multiplication which saturates at the largest or smallest representable number upon over/underflow.          |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **</>** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                               |
| |            Saturating fixed point division.                                                                             |
| |                                                                                                                         |
| |            Division which saturates at the largest or smallest representable number upon over/underflow.                |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<\*&>** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                             |
| |            Saturating fixed point multiplication with unbiased rounding.                                                |
| |                                                                                                                         |
| |            After multiplication, probabilistically rounds up or down to the closest representable number.               |
| |            After rounding, also saturates at the largest or smallest representable number upon over/underflow.          |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **</&>** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                              |
| |            Saturating fixed point division with unbiased rounding.                                                      |
| |                                                                                                                         |
| |            After division, probabilistically rounds up or down to the closest representable number.                     |
| |            After rounding, also saturates at the largest or smallest representable number upon over/underflow.          |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: MBoolean                                                      |
| |            Less than comparison.                                                                                        |
| |                                                                                                                         |
| |            Returns **true** if this value is less than **that** value. Otherwise returns **false**.                     |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<=**\(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: MBoolean                                                      |
| |            Less than or equal comparison.                                                                               |
| |                                                                                                                         |
| |            Returns **true** if this value is less than or equal to **that** value. Otherwise returns **false**.         |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **>** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: MBoolean                                                      |
| |            Greater than comparison                                                                                      |
| |                                                                                                                         |
| |            Returns **true** if this value is greater than **that** value. Otherwise returns **false**.                  |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **>=**\(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: MBoolean                                                      |
| |            Greater than or equal comparison.                                                                            |
| |                                                                                                                         |
| |            Returns **true** if this value is greater than or equal to **that** value. Otherwise returns **false**.      |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **!=**\(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`Boolean <bit>`                                          |
| |            Value inequality comparison.                                                                                 |
| |            Returns **true** if this value is not equal to the right hand side. Otherwise returns **false**.             |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **!=**\(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`Boolean <bit>`                                          |
| |            Value equality comparison.                                                                                   |
| |            Returns **true** if this value is equal to the right hand side. Otherwise returns **false**.                 |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **&** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Bit-wise AND.                                                                                                |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **|** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Bit-wise OR.                                                                                                 |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **^** \(that\: :doc:`FixPt <fixpt>`\[S,I,F\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                 |
| |            Bit-wise XOR.                                                                                                |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **<<**\(that\: :doc:`FixPt <fixpt>`\[S,I,_0\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                |
| |            Logical shift left.                                                                                          |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **>>**\(that\: :doc:`FixPt <fixpt>`\[S,I,_0\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                                |
| |            Arithmetic (sign-preserving) shift right.                                                                    |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **>>>**\(that\: :doc:`FixPt <fixpt>`\[S,I,_0\]\)\: :doc:`FixPt <fixpt>`\[S,I,F\]                               |
| |            Logical (zero-padded) shift right.                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **as**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\: T                          |
| |            Re-interprets this value's bits as the given type, without conversion.                                       |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: scala.Int\)\: :doc:`Bit <bit>`                                                                  |
| |            Returns the given bit in this value.                                                                         |
| |            0 corresponds to the least significant bit (LSB).                                                            |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(range\: :doc:`Range <range>`\)\: :doc:`Vector <../hw/memories/onchip/vector>`\[:doc:`Bit <bit>`\]   |
| |            Returns a vector of bits based on the given range.                                                           |
| |            The range must be statically determinable values.                                                            |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **reverse**\: :doc:`FixPt <fixpt>`\[S,I,F\]                                                                    |
| |            Returns a FixPt value with this value's bits in reverse order.                                               |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **to**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\: T                          |
| |            Converts this value to the given type.                                                                       |
| |                                                                                                                         |
| |            Currently supported types are @FixPt, @FltPt, and @String.                                                   |
+----------+----------------------------------------------------------------------------------------------------------------+
| |    def   **toString**\: :doc:`String <../sw/string>`                                                                    |
| |            Creates a printable String representation of this value.                                                     |
| |                                                                                                                         |
| |            **NOTE**: This method is unsynthesizable, and can be used only on the CPU or in simulation.                  |
+----------+----------------------------------------------------------------------------------------------------------------+



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
