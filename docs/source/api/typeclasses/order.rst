
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

.. _Order:

Order
=====

@alias Order

Type class used to supply evidence that type T has basic ordering operations defined on it.



**Abstract Methods**

+---------------------+----------------------------------------------------------------------------------------------------------------------+
|      `trait`         **Order**\[T\]                                                                                                        |
+=====================+======================================================================================================================+
| |               def   **lessThan**\(x: T, y: T): :doc:`../common/bit`                                                                      |
| |                       Returns `true` if x is less than y, `false` otherwise                                                              |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **lessThanOrEqual**\(x: T, y: T): :doc:`../common/bit`                                                               |
| |                       Returns `true` if x is less than or equal to y, `false` otherwise                                                  |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **equal**\(x: T, y: T): :doc:`../common/bit`                                                                         |
| |                       Returns `true` if x and y are equal, `false` otherwise                                                             |
+---------------------+----------------------------------------------------------------------------------------------------------------------+


