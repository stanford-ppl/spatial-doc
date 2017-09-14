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

.. _Matrix:

Matrix
======

Class and companion object for dense matrices on the CPU. 


**Infix methods**

+---------------------+----------------------------------------------------------------------------------------------------------------------+
|      `class`          **Matrix**\[T\]                                                                                                      |
+=====================+======================================================================================================================+
| |               def   **rows**: :doc:`Int <../common/fixpt>`                                                                               |
| |                       Returns the number of rows in this Matrix                                                                          |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **cols**: :doc:`Int <../common/fixpt>`                                                                               |
| |                       Returns the number of columns in this Matrix                                                                       |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(row: :doc:`Int <../common/fixpt>`, col: :doc:`Int <../common/fixpt>`): T                                  |
| |                       Returns the element at the two dimensional address (**row**, **col**) 																						 |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **update**\[T\](row: :doc:`Int <../common/fixpt>`, col: :doc:`Int <../common/fixpt>`, value: T): Unit                |
| |                       Updates the element at (**row**, **col**) to **value**                                                             |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **flatten**																																																					 |
| |                       Returns a flattened, immutable view of this Matrix's underlying data  																			       |
+---------------------+----------------------------------------------------------------------------------------------------------------------+

