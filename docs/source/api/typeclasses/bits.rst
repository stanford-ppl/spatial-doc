
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


The Bits type class is used to supply evidence that a type T is representable by a statically known number of bits.


----------------

**Abstract Methods**

+----------+---------------------------------------------------------------------------------------------------------------------------------------+
| trait      **Bits**\[T\]                                                                                                                         |
+==========+=======================================================================================================================================+
| |    def   **length**\: scala.Int                                                                                                                |
| |            Returns the minimum number of bits required to represent type T.                                                                    |
+----------+---------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **zero**\: T                                                                                                                          |
| |            Returns the zero value for type T.                                                                                                  |
+----------+---------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **one**\: T                                                                                                                           |
| |            Returns the one value for type T.                                                                                                   |
+----------+---------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **random**\(max\: Option\[T\]\)\: T                                                                                                   |
| |            Generates a pseudorandom value uniformly distributed between 0 and max.                                                             |
| |            If max is unspecified, type T's default maximum is used instead.                                                                    |
| |            For :doc:`FixPt <../common/fixpt>` types, the default maximum is the maximum representable number.                                  |
| |            For :doc:`FltPt <../common/fltpt>` types, the default maximum is 1.                                                                 |
| |            For composite :doc:`Tuple2 <../common/tuple2>` and :doc:`Struct <../common/struct>` types, the maximum is determined per component. |
+----------+---------------------------------------------------------------------------------------------------------------------------------------+


----------------

**Related methods**

+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **zero**\[T\::doc:`Bits <bits>`\]\: T                                                                |
| |             Returns the zero value for type T.                                                                 |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **one**\[T\::doc:`Bits <bits>`\]\: T                                                                 |
| |             Returns the one value for type T.                                                                  |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **random**\[T\::doc:`Bits <bits>`\]\: T                                                              |
| |             Generates a pseudorandom value uniformly distributed between 0 and the default maximum for type T. |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **random**\[T\::doc:`Bits <bits>`\]\(max\: T\)\: T                                                   |
| |             Generates a pseudorandom value uniformly distributed between 0 and **max**.                        |
+-----------+------------------------------------------------------------------------------------------------------+



----------------

**Infix methods**

Instances of types which have evidence of Bits also have infix methods defined on them: 

+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **apply**\(i\: :doc:`Int <../common/fixpt>`\)\: :doc:`Bit <../common/bit>`                           |
| |             Returns the given bit in this value.                                                               |
| |             0 corresponds to the least significant bit (LSB).                                                  |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **apply**\(range\: :doc:`Range <../common/range>`\)\: BitVector                                      |
| |             Returns a slice of the bits in this word as a VectorN.                                             |
| |             The range must be statically determinable with a stride of 1.                                      |
| |             The range is inclusive for both the start and end.                                                 |
| |             The range can be big endian (e.g. ****3::0****) or little endian (e.g. ****0::3****).              |
| |             In both cases, element 0 is always the least significant element.                                  |
| |                                                                                                                |
| |             For example, ****x(3::0)**** returns a Vector of the 4 least significant bits of ****x****.        |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **as**\[B\::doc:`Type <type>`\::doc:`Bits <bits>`\]\: B                                              |
| |             Re-interprets this value's bits as the given type, without conversion.                             |
| |             If B has fewer bits than this value's type, the MSBs will be dropped.                              |
| |             If B has more bits than this value's type, the resulting MSBs will be zeros.                       |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **reverse**\: A                                                                                      |
| |             Returns a value of the same type with this value's bits in reverse order.                          |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **takeX**\(offset\: scala.Int\)\: :doc:`VectorX <../common/vector>`\[:doc:`Bit <../common/bit>`\]    |
| |             Returns a slice of X bits of this value starting at the given **offset** from the LSB.             |
| |             To satisfy Scala's static type analysis, each bit-width has a separate method.                     |
| |                                                                                                                |
| |             For example, ****x.take3(1)**** returns the 3 least significant bits of x after the LSB            |
| |             as a Vector3[Bit].                                                                                 |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **takeXMSB**\(scala.Int\)\: :doc:`VectorX <../common/vector>`\[:doc:`Bit <../common/bit>`\]          |
| |             Returns a slice of X bits of this value, starting at the given **offset** from the MSB.            |
| |             To satisfy Scala's static type analysis, each bit-width has a separate method.                     |
| |             Slices between 1 and 128 bits are currently supported.                                             |
| |                                                                                                                |
| |             For example, ****x.take3MSB(1)**** returns the 3 most significant bits of x after the MSB          |
| |             as a Vector3[Bit].                                                                                 |
+-----------+------------------------------------------------------------------------------------------------------+
| |     def   **asXb**\: :doc:`VectorX <../common/vector>`\[:doc:`Bit <../common/bit>`\]                           |
| |             Returns a view of this value's bits as a X-bit Vector.                                             |
| |             To satisfy Scala's static analysis, each bit-width has a separate method.                          |
| |             Conversions between 1 and 128 bits are currently supported.                                        |
| |                                                                                                                |
| |             If X is smaller than this value's total bits, the MSBs will be dropped.                            |
| |             If X is larger than this value's total bits, the resulting MSBs will be zeros.                     |
+-----------+------------------------------------------------------------------------------------------------------+





