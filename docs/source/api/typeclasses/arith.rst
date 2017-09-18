
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

.. _Arith:

Arith
=====

@alias Arith

Type class used to supply evidence that type T has basic arithmetic operations defined on it.

-------------

**Abstract methods**

+---------------------+----------------------------------------------------------------------------------------------------------------------+
|      `trait`         **Arith**\[T\]                                                                                                        |
+=====================+======================================================================================================================+
| |               def   **negate**\(x: T): T                                                                                                 |
| |                       Returns a negated version of the given value                                                                       |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **plus**\(x: T, y: T): T                                                                                             |
| |                       Returns the result of adding x and y                                                                               |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **minus**\(x: T, y: T): T                                                                                            |
| |                       Returns the result of subtracting y from x                                                                         |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **times**\(x: T, y: T): T                                                                                            |
| |                       Returns the result of multiplying x and y                                                                          |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **divide**\(x: T, y: T): T                                                                                           |
| |                       Returns the result of dividing x by y                                                                              |
+---------------------+----------------------------------------------------------------------------------------------------------------------+

