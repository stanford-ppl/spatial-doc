
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

.. _Debug:

Debugging Operations
====================

These operations are available for use on the CPU and during simulation to aid runtime debugging.


------------------

**Methods**

+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **assert**\(cond\: MBoolean, msg\: :doc:`String <string>`\)\: :doc:`Unit <../common/unit>`                                                                                  |
| |             Checks that the given condition **cond** is true at runtime.                                                                                                              |
| |             If not, exits the program with the given message.                                                                                                                         |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **assert**\(cond\: MBoolean\)\: :doc:`Unit <../common/unit>`                                                                                                                |
| |             Checks that the given condition **cond** is true at runtime.                                                                                                              |
| |             If not, exits the program with a generic exception.                                                                                                                       |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **println**\(\)\: :doc:`Unit <../common/unit>`                                                                                                                              |
| |             Prints an empty line to the console.                                                                                                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **print**\[T\::doc:`Type <../typeclasses/type>`\]\(x\: T\)\: :doc:`Unit <../common/unit>`                                                                                   |
| |             Prints a String representation of the given value to the console.                                                                                                         |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **println**\[T\::doc:`Type <../typeclasses/type>`\]\(x\: T\)\: :doc:`Unit <../common/unit>`                                                                                 |
| |             Prints a String representation of the given value to the console followed by a linebreak.                                                                                 |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **print**\(x\: :doc:`String <string>`\)\: :doc:`Unit <../common/unit>`                                                                                                      |
| |             Prints the given String to the console.                                                                                                                                   |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **println**\(x\: :doc:`String <string>`\)\: :doc:`Unit <../common/unit>`                                                                                                    |
| |             Prints the given String to the console, followed by a linebreak.                                                                                                          |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **printArray**\[T\::doc:`Type <../typeclasses/type>`\]\(array\: :doc:`Array <array>`\[T\], heading\: :doc:`String <string>` = \"\"\)\: :doc:`Unit <../common/unit>`         |
| |             Prints the given Array to the console, preceded by an optional heading.                                                                                                   |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **printMatrix**\[T\::doc:`Type <../typeclasses/type>`\]\(matrix\: :doc:`Matrix <matrix>`\[T\], heading\: :doc:`String <string>` = \"\"\)\: :doc:`Unit <../common/unit>`     |
| |             Prints the given Matrix to the console, preceded by an optional heading.                                                                                                  |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **printTensor3**\[T\::doc:`Type <../typeclasses/type>`\]\(tensor\: :doc:`Tensor3 <tensor>`\[T\], heading\: :doc:`String <string>` = \"\"\)\: :doc:`Unit <../common/unit>`   |
| |             Prints the given Tensor3 to the console, preceded by an optional heading.                                                                                                 |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **printTensor4**\[T\::doc:`Type <../typeclasses/type>`\]\(tensor\: :doc:`Tensor4 <tensor>`\[T\], heading\: :doc:`String <string>` = \"\"\)\: :doc:`Unit <../common/unit>`   |
| |             Prints the given Tensor4 to the console, preceded by the an optional heading.                                                                                             |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **printTensor5**\[T\::doc:`Type <../typeclasses/type>`\]\(tensor\: :doc:`Tensor5 <tensor>`\[T\], heading\: :doc:`String <string>` = \"\"\)\: :doc:`Unit <../common/unit>`   |
| |             Prints the given Tensor5 to the console, preceded by the an optional heading.                                                                                             |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

