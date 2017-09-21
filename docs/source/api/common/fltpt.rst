
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


FltPt[G,E] represents an arbitrary precision, IEEE-754-like representation.
FltPt values are always assumed to be signed.

The type parameters for FltPt are:

+---+-----+------------------------------------------------+---------------+
| G | INT | Number of significand bits, including sign bit | (_2 - _64)    |
+---+-----+------------------------------------------------+---------------+
| E | INT | Number of exponent bits                        | (_1 - _64)    |
+---+-----+------------------------------------------------+---------------+

Note that numbers of bits use the underscore prefix as integers cannot be used as type parameters in Scala.


--------------


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


--------------

**Infix methods**

+----------+---------------------------------------------------------------------------------------------------------------+
| class      **FltPt**\[G,E\]                                                                                              |
+==========+===============================================================================================================+
| |    def   **unary_-**\(\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                                 |
| |            Returns the negation of this floating point value.                                                          |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **+** \(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                    |
| |            Floating point addition.                                                                                    |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **-** \(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                    |
| |            Floating point subtraction.                                                                                 |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **\*** \(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                   |
| |            Floating point multiplication.                                                                              |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **/** \(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`FltPt <fltpt>`\[G,E\]                                    |
| |            Floating point division.                                                                                    |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **\\*\\***\(exp\: scala.Int\)\: :doc:`FltPt <fltpt>`\[G,E\]                                                   |
| |            Integer exponentiation, implemented in hardware as a reduction tree with **exp** inputs.                    |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **<** \(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: MBoolean                                                       |
| |            Less than comparison.                                                                                       |
| |                                                                                                                        |
| |            Returns **true** if this value is less than **that** value. Otherwise returns **false**.                    |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **<=**\(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: MBoolean                                                       |
| |            Less than or equal comparison.                                                                              |
| |                                                                                                                        |
| |            Returns **true** if this value is less than or equal to **that** value. Otherwise returns **false**.        |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **>** \(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: MBoolean                                                       |
| |            Greater than comparison.                                                                                    |
| |                                                                                                                        |
| |            Returns **true** if this value is greater than **that** value. Otherwise returns **false**.                 |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **>=**\(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: MBoolean                                                       |
| |            Greater than or equal comparison.                                                                           |
| |                                                                                                                        |
| |            Returns **true** if this value is less than **that** value. Otherwise returns **false**.                    |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **!=**\(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`Boolean <bit>`                                           |
| |            Value inequality comparison.                                                                                |
| |            Returns **true** if this value is not equal to the right hand side. Otherwise returns **false**.            |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **!=**\(that\: :doc:`FltPt <fltpt>`\[G,E\]\)\: :doc:`Boolean <bit>`                                           |
| |            Value equality comparison.                                                                                  |
| |            Returns **true** if this value is equal to the right hand side. Otherwise returns **false**.                |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **as**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\: T                         |
| |            Re-interprets this value's bits as the given type, without conversion.                                      |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: scala.Int\)\: :doc:`Bit <bit>`                                                                 |
| |            Returns the given bit in this value.                                                                        |
| |            0 corresponds to the least significant bit (LSB).                                                           |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(range\: :doc:`Range <range>`\)\: :doc:`Vector <vector>`\[:doc:`Bit <bit>`\]                        |
| |            Returns a vector of bits based on the given range.                                                          |
| |            The range must be statically determinable values.                                                           |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **reverse**\: :doc:`FltPt <fltpt>`\[G,E\]                                                                     |
| |            Returns a floating point value with this value's bits in reverse order.                                     |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **to**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\: T                         |
| |            Converts this value to the given type.                                                                      |
| |                                                                                                                        |
| |            Currently supported types are :doc:`FixPt <fixpt>`, :doc:`FltPt <fltpt>`, and :doc:`String <../sw/string>`. |
+----------+---------------------------------------------------------------------------------------------------------------+
| |    def   **toString**\: :doc:`String <../sw/string>`                                                                   |
| |            Creates a printable String representation of this value.                                                    |
| |                                                                                                                        |
| |            **NOTE**: This method is unsynthesizable, and can be used only on the CPU or in simulation.                 |
+----------+---------------------------------------------------------------------------------------------------------------+

