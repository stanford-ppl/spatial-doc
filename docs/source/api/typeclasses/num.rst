
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


Combination of :doc:`arith`, :doc:`bits`, and :doc:`order` type classes

------------------------

**Abstract Methods**

+----------+-------------------------------------------------------------------------------------------+
| trait      **Num**\[T\] extends Arith\[T\] with Bits\[T\] with Order\[T\]                            |
+==========+===========================================================================================+
| |    def   **toFixPt**\[S\:BOOL,I\:INT,F\:INT\]\(x\: T\)\: :doc:`FixPt <../common/fixpt>`\[S,I,F\]   |
| |            Converts **x** to a :doc:`FixPt <../common/fixpt>` value.                               |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **toFltPt**\[G\:INT,E\:INT\]\(x\: T\)\: :doc:`FltPt <../common/fltpt>`\[G,E\]             |
| |            Converts **x** to a :doc:`FltPt <../common/fltpt>` value.                               |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **fromInt**\(x\: scala.Int, force\: :doc:`Boolean <../common/bit>` = true\)\: T           |
| |            Returns the closest representable value of type T to **x**.                             |
| |            If **force** is true, gives a warning when the conversion is not exact.                 |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **fromLong**\(x\: scala.Long, force\: :doc:`Boolean <../common/bit>` = true\)\: T         |
| |            Returns the closest representable value of type T to **x**.                             |
| |            If **force** is true, gives a warning when the conversion is not exact.                 |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **fromFloat**\(x\: scala.Float, force\: :doc:`Boolean <../common/bit>` = true\)\: T       |
| |            Returns the closest representable value of type T to **x**.                             |
| |            If **force** is true, gives a warning when the conversion is not exact.                 |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **fromDouble**\(x\: scala.Double, force\: :doc:`Boolean <../common/bit>` = true\)\: T     |
| |            Returns the closest representable value of type T to **x**.                             |
| |            If **force** is true, gives a warning when the conversion is not exact.                 |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **maxValue**\: T                                                                          |
| |            Returns the largest, finite, positive value representable by type T.                    |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **minValue**\: T                                                                          |
| |            Returns the most negative, finite value representable by type T.                        |
+----------+-------------------------------------------------------------------------------------------+
| |    def   **minPositiveValue**\: T                                                                  |
| |            Returns the smallest positive (nonzero) value representable by type T.                  |
+----------+-------------------------------------------------------------------------------------------+

