
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

.. _FileIO:

File I/O
========


File I/O operations are available for use on the host PU and during simulation.

------------------

**Methods**

+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **loadCSV1D**\[T\::doc:`Type <../typeclasses/type>`\]\(filename\: :doc:`String <string>`, delimiter\: :doc:`String <string>`\)\(implicit cast\: Cast\[:doc:`String <string>`,T\]\)\: :doc:`Array <array>`\[T\]      |
| |             Loads the CSV at **filename** as an :doc:`Array <array>` using the supplied **delimiter** for parsing.                                                                                                            |
| |             The delimiter defaults to a comma if none is supplied.                                                                                                                                                            |
+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **loadCSV2D**\[T\::doc:`Type <../typeclasses/type>`\]\(filename\: :doc:`String <string>`, delimiter\: :doc:`String <string>`\)\(implicit cast\: Cast\[:doc:`String <string>`,T\]\)\: :doc:`Matrix <matrix>`\[T\]    |
| |             Loads the CSV at **filename** as a :doc:`Matrix <matrix>`, using the supplied element delimiter and linebreaks across rows.                                                                                       |
+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **writeCSV1D**\[T\::doc:`Type <../typeclasses/type>`\]\(array\: :doc:`Array <array>`\[T\], filename\: :doc:`String <string>`, delimiter\: :doc:`String <string>`\)\: :doc:`Unit <../common/unit>`                   |
| |             Writes the given Array to the file at **filename** using the given **delimiter**.                                                                                                                                 |
| |             If no delimiter is given, defaults to comma.                                                                                                                                                                      |
+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **writeCSV2D**\[T\::doc:`Type <../typeclasses/type>`\]\(matrix\: :doc:`Matrix <matrix>`\[T\], filename\: :doc:`String <string>`, delimiter\: :doc:`String <string>`\)\: :doc:`Unit <../common/unit>`                |
| |             Writes the given Matrix to the file at **filename** using the given element **delimiter**.                                                                                                                        |
| |             If no element delimiter is given, defaults to comma.                                                                                                                                                              |
+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


------------------

**Under development**

The following methods are not yet fully supported. If you need support for these methods,
please contact the Spatial group. 

+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **loadBinary**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Num <../typeclasses/num>`\]\(filename\: :doc:`String <string>`\)\: :doc:`Array <array>`\[T\]                                          |
| |             Loads the given binary file at **filename** as an :doc:`Array <array>`.                                                                                                                         |
+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **writeBinary**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Num <../typeclasses/num>`\]\(array\: :doc:`Array <array>`\[T\], filename\: :doc:`String <string>`\)\: :doc:`Unit <../common/unit>`   |
| |             Saves the given Array to disk as a binary file at **filename**.                                                                                                                                 |
+-----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

