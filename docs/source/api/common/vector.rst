
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


**Vector** defines a fixed size collection of scalar values. It is distinct from :doc:`Array <../sw/array>` in that the size must always be statically determinable.
This allows Vector to always be allocated as a bus of wires when implemented in hardware.

Vector is most commonly used for managing parallel accesses to local memories in hardware and for bit-twiddling operations.

To allow static type checking and creation of the :doc:`Bits <../typeclasses/bits>` type class, the Vector type is split into subtypes based on the number of elements it contains.
For example, a Vector3[:doc:`Int <fixpt>`] is composed of 3, 32-bit :doc:`Int <fixpt>` values, while a Vector32[:doc:`Bit <bit>`] is a single bus of 32 :doc:`Bit <bit>` values.
Spatial currently defines Vector types from **Vector1** up to **Vector128**.

To work around :doc:`limitations with Scala's type system <../../faq>` Spatial also includes a VectorN type for when the vector width cannot be type-encoded.
This type generally needs to be annotated with the number of bits the user intended (e.g. ``vector.as32b``) before it can be written to memories.


----------------------

**Static methods**

+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Vector**                                                                                                                                 |
+==========+============================================================================================================================================+
| |    def   **LittleEndian**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorX <vector>`\[T\]   |
| |            Creates a VectorX from the given X elements, where X is between 1 and 128.                                                               |
| |            The first element supplied is the most significant (Vector index of X - 1).                                                              |
| |            The last element supplied is the least significant (Vector index of 0).                                                                  |
| |                                                                                                                                                     |
| |            Note that this method is actually overloaded 128 times based on the number of supplied arguments.                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **BigEndian**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorX <vector>`\[T\]      |
| |            Creates a VectorX from the given X elements, where X is between 1 and 128.                                                               |
| |            The first element supplied is the least significant (Vector index of 0).                                                                 |
| |            The last element supplied is the most significant (Vector index of X - 1).                                                               |
| |                                                                                                                                                     |
| |            Note that this method is actually overloaded 128 times based on the number of supplied arguments.                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **ZeroLast**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorX <vector>`\[T\]       |
| |            A mnemonic for LittleEndian (with reference to the zeroth element being specified last in order).                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **ZeroFirst**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorX <vector>`\[T\]      |
| |            A mnemonic for BigEndian (with reference to the zeroth element being specified first in order).                                          |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+




Spatial also includes an alternate **Vectorize** object which takes a true arbitrary number of 
elements in all of its functions. As a result, these methods return VectorNs.

+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Vectorize**                                                                                                                              |
+==========+============================================================================================================================================+
| |    def   **LittleEndian**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorN <vector>`\[T\]   |
| |            Creates a VectorN from the given elements.                                                                                               |
| |            The first element supplied is the most significant (Vector index of N - 1).                                                              |
| |            The last element supplied is the least significant (Vector index of 0).                                                                  |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **BigEndian**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorN <vector>`\[T\]      |
| |            Creates a VectorN from the given elements.                                                                                               |
| |            The first element supplied is the least significant (Vector index of 0).                                                                 |
| |            The last element supplied is the most significant (Vector index of N - 1).                                                               |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **ZeroLast**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorN <vector>`\[T\]       |
| |            A mnemonic for LittleEndian (with reference to the zeroth element being specified last in order).                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **ZeroFirst**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(elem\: T\*\)\: :doc:`VectorN <vector>`\[T\]      |
| |            A mnemonic for BigEndian (with reference to the zeroth element being specified first in order).                                          |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------+




----------------------

**Infix methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **Vector**\[T\]                                                                                                                                                                  |
+==========+==================================================================================================================================================================================+
| |    def   **apply**\(i\: :doc:`Int <fixpt>`\)\: T                                                                                                                                          |
| |            Returns the **i**'th element of this Vector.                                                                                                                                   |
| |            Element 0 is always the LSB.                                                                                                                                                   |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(range\: :doc:`Range <range>`\)\(implicit mT\: :doc:`Type <../typeclasses/type>`\[T\], bT\: :doc:`Bits <../typeclasses/bits>`\[T\]\)\: :doc:`VectorN <vector>`\[T\]    |
| |            Returns a slice of the elements in this Vector as a VectorN.                                                                                                                   |
| |            The range must be statically determinable with a stride of 1.                                                                                                                  |
| |            The range is inclusive for both the start and end.                                                                                                                             |
| |            The **range** can be big endian (e.g. **3::0**) or little endian (e.g. **0::3**).                                                                                              |
| |            In both cases, element 0 is always the least significant element.                                                                                                              |
| |                                                                                                                                                                                           |
| |            For example, **x(3::0)** returns a Vector of the 4 least significant elements of **x**.                                                                                        |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **takeX**\(offset\: scala.Int\)\: :doc:`VectorX <vector>`\[T\]                                                                                                                   |
| |            Returns a slice of N elements of this Vector starting at the given **offset** from the                                                                                         |
| |            least significant element.                                                                                                                                                     |
| |            To satisfy Scala's static type analysis, each width has a separate method.                                                                                                     |
| |                                                                                                                                                                                           |
| |            For example, **x.take3(1)** returns the 3 least significant elements of x after the                                                                                            |
| |            least significant as a Vector3[T].                                                                                                                                             |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **!=**\(that\: :doc:`Vector <vector>`\[T\]\)\: MBoolean                                                                                                                          |
| |            Returns true if this Vector and **that** differ by at least one element, false otherwise.                                                                                      |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **==**\(that\: :doc:`Vector <vector>`\[T\]\)\: MBoolean                                                                                                                          |
| |            Returns true if this Vector and **that** contain the same elements, false otherwise.                                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+




+----------+------------------------------------------+
| class      **VectorX**\[T\] extends Vector\[T\]     |
+----------+------------------------------------------+




+----------+-----------------------------------------------------------------------------------------------------------------+
| class      **VectorN**\[T\]                                                                                                |
+==========+=================================================================================================================+
| |    def   **asVectorX**\: :doc:`VectorX <vector>`\[T\]                                                                    |
| |            Casts this VectorN as a VectorX.                                                                              |
| |            Values of X from 1 to 128 are currently supported.                                                            |
| |                                                                                                                          |
| |            If the VectorX type has fewer elements than this value's type, the most significant elements will be dropped. |
| |            If the VectorX type has more elements than this value's type, the resulting elements will be zeros.           |
+----------+-----------------------------------------------------------------------------------------------------------------+
| |    def   **asXb**\: :doc:`VectorX <vector>`\[:doc:`Bit <bit>`\]                                                          |
| |            Returns a view of this VectorN's bits as a X-bit Vector.                                                      |
| |            To satisfy Scala's static analysis, each bit-width has a separate method.                                     |
| |            Conversions between 1 and 128 bits are currently supported.                                                   |
| |                                                                                                                          |
| |            If X is smaller than this VectorN's total bits, the MSBs will be dropped.                                     |
| |            If X is larger than this VectorN's total bits, the resulting MSBs will be zeros.                              |
+----------+-----------------------------------------------------------------------------------------------------------------+


