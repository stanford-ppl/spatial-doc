
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

.. _RegFile:

RegFile
=======


**RegFiles** are on-chip arrays of registers with fixed size. RegFiles currently can be specified as one or two dimensional.
Like other memories in Spatial, the contents of RegFiles are persistent across loop iterations, even when they are declared
in an inner scope.

Using the **<<=** operator, RegFiles can be used as shift registers. 2-dimensional RegFiles must select a specific
row or column before shifting using `regfile(row, \*)` or `regfile(\*, col)`, respectively.

---------------

**Static methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **RegFile**                                                                                                                                                                                                                                                                |
+==========+============================================================================================================================================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(length\: :doc:`Index <../../common/fixpt>`\)\: :doc:`RegFile1 <regfile>`\[T\]                                                                                                  |
| |            Allocates a 1-dimensional Regfile with specified **length**.                                                                                                                                                                                                             |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(length\: :doc:`Int <../../common/fixpt>`, inits\: List\[T\]\)\: :doc:`RegFile1 <regfile>`\[T\]                                                                                 |
| |            Allocates a 1-dimensional RegFile with specified **length** and initial values **inits**.                                                                                                                                                                                |
| |                                                                                                                                                                                                                                                                                     |
| |            The number of initial values must be the same as the total size of the RegFile.                                                                                                                                                                                          |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(rows\: :doc:`Index <../../common/fixpt>`, cols\: :doc:`Index <../../common/fixpt>`\)\: :doc:`RegFile2 <regfile>`\[T\]                                                          |
| |            Allocates a 2-dimensional RegFile with specified **rows** and **cols**.                                                                                                                                                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(rows\: :doc:`Int <../../common/fixpt>`, cols\: :doc:`Int <../../common/fixpt>`, inits\: List\[T\]\)\: :doc:`RegFile2 <regfile>`\[T\]                                           |
| |            Allocates a 2-dimensional RegFile with specified **rows** and **cols** and initial values **inits**.                                                                                                                                                                     |
| |                                                                                                                                                                                                                                                                                     |
| |            The number of initial values must be the same as the total size of the RegFile                                                                                                                                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(dim0\: :doc:`Index <../../common/fixpt>`, dim1\: :doc:`Index <../../common/fixpt>`, dim2\: :doc:`Index <../../common/fixpt>`\)\: :doc:`RegFile3 <regfile>`\[T\]                |
| |            Allocates a 3-dimensional RegFile with specified dimensions.                                                                                                                                                                                                             |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(dim0\: :doc:`Int <../../common/fixpt>`, dim1\: :doc:`Int <../../common/fixpt>`, dim2\: :doc:`Int <../../common/fixpt>`, inits\: List\[T\]\)\: :doc:`RegFile3 <regfile>`\[T\]   |
| |            Allocates a 3-dimensional RegFile with specified dimensions and initial values **inits**.                                                                                                                                                                                |
| |                                                                                                                                                                                                                                                                                     |
| |            The number of initial values must be the same as the total size of the RegFile                                                                                                                                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


--------------

**Infix methods**

+----------------+-------------------------------------------------------------------------------------------------------+
| abstract class   **RegFile**\[T\]                                                                                      |
+================+=======================================================================================================+
| |          def   **reset**\: :doc:`Unit <../../common/unit>`                                                           |
| |                  Resets this RegFile to its initial values (or zeros, if unspecified).                               |
+----------------+-------------------------------------------------------------------------------------------------------+
| |          def   **reset**\(cond\: :doc:`Bit <../../common/bit>`\)\: :doc:`Unit <../../common/unit>`                   |
| |                  Conditionally resets this RegFile based on **cond** to its inital values (or zeros if unspecified). |
+----------------+-------------------------------------------------------------------------------------------------------+




+----------+------------------------------------------------------------------------------------------------------+
| class      **RegFile1**\[T\] extends RegFile\[T\]                                                               |
+==========+======================================================================================================+
| |    def   **apply**\(i\: :doc:`Index <../../common/fixpt>`\)\: T                                               |
| |            Returns the value held by the register at address **i**.                                           |
+----------+------------------------------------------------------------------------------------------------------+
| |    def   **update**\(i\: :doc:`Index <../../common/fixpt>`, data\: T\)\: :doc:`Unit <../../common/unit>`      |
| |            Updates the register at address **i** to hold **data**.                                            |
+----------+------------------------------------------------------------------------------------------------------+
| |    def   **<<=**\(data\: T\)\: :doc:`Unit <../../common/unit>`                                                |
| |            Shifts in **data** into the first register, shifting all other values over by one position.        |
+----------+------------------------------------------------------------------------------------------------------+
| |    def   **<<=**\(data\: :doc:`Vector <../../common/vector>`\[T\]\)\: :doc:`Unit <../../common/unit>`         |
| |            Shifts in **data** into the first N registers, where N is the size of the given Vector.            |
| |            All other elements are shifted by N positions.                                                     |
+----------+------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAM1 <../offchip/dram>`\[T\]\)\: :doc:`Unit <../../common/unit>`             |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.              |
+----------+------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAMDenseTile1 <../offchip/tile>`\[T\]\)\: :doc:`Unit <../../common/unit>`    |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.              |
+----------+------------------------------------------------------------------------------------------------------+



+----------+-------------------------------------------------------------------------------------------------------------------------------------------+
| class      **RegFile2**\[T\] extends RegFile\[T\]                                                                                                    |
+==========+===========================================================================================================================================+
| |    def   **apply**\(r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: T                                             |
| |            Returns the value held by the register at row **r**, column **c**.                                                                      |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **update**\(r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`, data\: T\)\: :doc:`Unit <../../common/unit>`    |
| |            Updates the register at row **r**, column **c** to hold the given **data**.                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: :doc:`Index <../../common/fixpt>`, y\: Wildcard\)                                                                          |
| |            Returns a view of row **i** of this RegFile.                                                                                            |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(y\: Wildcard, i\: :doc:`Index <../../common/fixpt>`\)                                                                          |
| |            Returns a view of column **i** of this RegFile.                                                                                         |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAM2 <../offchip/dram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                  |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.                                                   |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAMDenseTile2 <../offchip/tile>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                         |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.                                                   |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------+




+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **RegFile3**\[T\] extends RegFile\[T\]                                                                                                                                                    |
+==========+===========================================================================================================================================================================================+
| |    def   **apply**\(dim0\: :doc:`Index <../../common/fixpt>`, dim1\: :doc:`Index <../../common/fixpt>`, dim2\: :doc:`Index <../../common/fixpt>`\)\: T                                             |
| |            Returns the value held by the register at the given 3-dimensional address.                                                                                                              |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **update**\(dim0\: :doc:`Index <../../common/fixpt>`, dim1\: :doc:`Index <../../common/fixpt>`, dim2\: :doc:`Index <../../common/fixpt>`, data\: T\)\: :doc:`Unit <../../common/unit>`    |
| |            Updates the register at the given 3-dimensional address to hold the given **data**.                                                                                                     |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: :doc:`Index <../../common/fixpt>`, j\: :doc:`Index <../../common/fixpt>`, y\: Wildcard\)                                                                                   |
| |            Returns a 1-dimensional view of part of this RegFile3.                                                                                                                                  |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(i\: :doc:`Index <../../common/fixpt>`, y\: Wildcard, j\: :doc:`Index <../../common/fixpt>`\)                                                                                   |
| |            Returns a 1-dimensional view of part of this RegFile3.                                                                                                                                  |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(y\: Wildcard, i\: :doc:`Index <../../common/fixpt>`, j\: :doc:`Index <../../common/fixpt>`\)                                                                                   |
| |            Returns a 1-dimensional view of part of this RegFile3.                                                                                                                                  |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAM3 <../offchip/dram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                                  |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.                                                                                                   |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAMDenseTile3 <../offchip/tile>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                         |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.                                                                                                   |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

