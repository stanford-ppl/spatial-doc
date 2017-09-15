
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

.. _DRAM:

DRAM
====


DRAMs are pointers to locations in the accelerator's main memory comprising dense multi-dimensional arrays. They are the primary form of communication
of data between the host and the accelerator. Data may be loaded to and from the accelerator in contiguous chunks (Tiles),
or by bulk scatter and gather operations (SparseTiles).

Up to to 5-dimensional DRAMs are currently supported. Dimensionality of a DRAM instance is encoded by the subclass of DRAM. DRAM1, for instance represents a 1-dimensional DRAM.  

A dense :doc:`tile` can be created from a DRAM either using address range selection or by implicit conversion.
When a Tile is created implicitly, it has the same address space as the entire original DRAM.

In Spatial, DRAMs are specified outside the Accel scope in the host code.

----------------

**Static Methods**

+---------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
|      `object`         **DRAM**                                                                                                                                                                                                                                                                 |
+=====================+==========================================================================================================================================================================================================================================================================+
| |               def   **apply**\[T::doc:`../../typeclasses/bits`\](length: :doc:`Int <../../common/fixpt>`\): :doc:`DRAM1 <dram>`\[T\]                                                                                                                                                         |
| |                       Creates a reference to a 1-dimensional array in main memory with given **length**.                                                                                                                                                                                     |
| |                       Dimensions of a DRAM should be statically calculable functions of constants, parameters, and :doc:`ArgIns <reg>`.                                                                                                                                                      |
+---------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\[T::doc:`../../typeclasses/bits`\](rows: :doc:`Int <../../common/fixpt>`\, cols: :doc:`Int <../../common/fixpt>`\): :doc:`DRAM2 <dram>`\[T\]                                                                                                                   |
| |                       Creates a reference to a 2-dimensional array in main memory with given **rows** and **cols**.                                                                                                                                                                          |
| |                       Dimensions of a DRAM should be statically calculable functions of constants, parameters, and :doc:`ArgIns <reg>`.                                                                                                                                                      |
+---------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\[T::doc:`../../typeclasses/bits`\](d0: :doc:`Int <../../common/fixpt>`\, d1: :doc:`Int <../../common/fixpt>`\, d2: :doc:`Int <../../common/fixpt>`\): :doc:`DRAM3 <dram>`\[T\]                                                                                 |
| |                       Creates a reference to a 3-dimensional array in main memory with given dimensions **d0**, **d1**, and **d2**.                                                                                                                                                          |
| |                       Dimensions of a DRAM should be statically calculable functions of constants, parameters, and :doc:`ArgIns <reg>`.                                                                                                                                                      |
+---------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\[T::doc:`../../typeclasses/bits`\](d0: :doc:`Int <../../common/fixpt>`\, d1: :doc:`Int <../../common/fixpt>`\, d2: :doc:`Int <../../common/fixpt>`\, d3: :doc:`Int <../../common/fixpt>`\): :doc:`DRAM4 <dram>`\[T\]                                           |
| |                       Creates a reference to a 4-dimensional array in main memory with given dimensions **d0**, **d1**, **d2**, and **d3**.                                                                                                                                                  |
| |                       Dimensions of a DRAM should be statically calculable functions of constants, parameters, and :doc:`ArgIns <reg>`.                                                                                                                                                      |
+---------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\[T::doc:`../../typeclasses/bits`\](d0: :doc:`Int <../../common/fixpt>`\, d1: :doc:`Int <../../common/fixpt>`\, d2: :doc:`Int <../../common/fixpt>`\, d3: :doc:`Int <../../common/fixpt>`\, d4: :doc:`Int <../../common/fixpt>`\): :doc:`DRAM5 <dram>`\[T\]     |
| |                       Creates a reference to a 5-dimensional array in main memory with given dimensions **d0**, **d1**, **d2**, **d3**, and **d4**.                                                                                                                                          |
| |                       Dimensions of a DRAM should be statically calculable functions of constants, parameters, and :doc:`ArgIns <reg>`.                                                                                                                                                      |
+---------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

--------------

**Infix methods**

+---------------------+---------------------------------------------------------------------------------------------------------------------------------------+
| `abstract class`     **DRAM**\[T\]                                                                                                                          |
+=====================+=======================================================================================================================================+
| |               def   **address**: :doc:`Int64 <../../common/fixpt>`\                                                                                       |
| |                     Returns the 64-bit physical address in main memory of the start of this DRAM                                                          |
+---------------------+---------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dims**: List\[:doc:`Int <../../common/fixpt>`\]                                                                                     |
| |                     Returns a Scala List of the dimensions of this DRAM                                                                                   |
+---------------------+---------------------------------------------------------------------------------------------------------------------------------------+



+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
|      `class`         **DRAM1**\[T\] extends DRAM\[T\]                                                                                                               |
+=====================+===============================================================================================================================================+
| |               def   **size**: :doc:`Int <../../common/fixpt>`                                                                                                     |
| |                       Returns the total number of elements in this DRAM1.                                                                                         |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **length**: :doc:`Int <../../common/fixpt>`                                                                                                   |
| |                       Returns the total number of elements in this DRAM1.                                                                                         |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(range\: :doc:`../../common/range`\): :doc:`DRAMDenseTile1 <tile>`\[T\]                                                             |
| |                       Creates a reference to a dense region of this DRAM1 for creating burst loads and stores.                                                    |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(addrs: :doc:`SRAM1 <sram>`\[:doc:`Int <../../common/fixpt>`\]): :doc:`sparsetile`\[T\]                                             |
| |                       Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                |
| |                       using all addresses in **addrs**.                                                                                                           |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(addrs\: :doc:`SRAM1 <sram>`\[:doc:`Int <../../common/fixpt>`\], size\: :doc:`Int <../../common/fixpt>`): :doc:`sparsetile`\[T\]    |
| |                       Creates a reference to a sparse region of this DRAM for use in scatter and gather transfers                                                 |
| |                       using the first **size** addresses in **addrs**.                                                                                            |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(addrs: :doc:`fifo`\[:doc:`Int <../../common/fixpt>`\]): :doc:`sparsetile`\[T\]                                                     |
| |                       Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                |
| |                       using all addresses in **addrs**.                                                                                                           |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(addrs\: :doc:`fifo`\[:doc:`Int <../../common/fixpt>`\], size\: :doc:`Int <../../common/fixpt>`): :doc:`sparsetile`\[T\]            |
| |                       Creates a reference to a sparse region of this DRAM for use in scatter and gather transfers                                                 |
| |                       using the first **size** addresses in **addrs**.                                                                                            |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(addrs: :doc:`filo`\[:doc:`Int <../../common/fixpt>`\]): :doc:`sparsetile`\[T\]                                                     |
| |                       Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                |
| |                       using all addresses in **addrs**.                                                                                                           |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(addrs\: :doc:`filo`\[:doc:`Int <../../common/fixpt>`\], size\: :doc:`Int <../../common/fixpt>`): :doc:`sparsetile`\[T\]            |
| |                       Creates a reference to a sparse region of this DRAM for use in scatter and gather transfers                                                 |
| |                       using the first **size** addresses in **addrs**.                                                                                            |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`SRAM1 <sram>`\[T\]`: Unit                                                                                             |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                       |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`RegFile1 <regfile>`\[T\]`: Unit                                                                                       |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                       |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`fifo`\[T\]`: Unit                                                                                                     |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                       |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`filo`\[T\]`: Unit                                                                                                     |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                       |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+


+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
|      `class`         **DRAM2**\[T\] extends DRAM\[T\]                                                                                                               |
+=====================+===============================================================================================================================================+
| |               def   **size**: :doc:`Int <../../common/fixpt>`                                                                                                     |
| |                       Returns the total number of elements in this DRAM2.                                                                                         |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **rows**: :doc:`Int <../../common/fixpt>`                                                                                                     |
| |                       Returns the number of rows in this DRAM2.                                                                                                   |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **cols**: :doc:`Int <../../common/fixpt>`                                                                                                     |
| |                       Returns the number of columns in this DRAM2.                                                                                                |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(rows\: :doc:`../../common/range`, cols: :doc:`../../common/range`): :doc:`DRAMDenseTile2 <tile>`\[T\]                              |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM2 for creating burst loads and stores.                                     |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(row\: :doc:`Int <../../common/fixpt>`, cols: :doc:`../../common/range`): :doc:`DRAMDenseTile1 <tile>`\[T\]                         |
| |                       Creates a reference to a dense slice of a row of this DRAM2 for creating burst loads and stores.                                            |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(rows\: :doc:`../../common/range`, col: :doc:`../../common/fixpt`): :doc:`DRAMDenseTile1 <tile>`\[T\]                               |
| |                       Creates a reference to a dense slice of a column of this DRAM2 for creating burst loads and stores.                                         |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`SRAM2 <sram>`\[T\]`: Unit                                                                                             |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                       |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`RegFile2 <regfile>`\[T\]`: Unit                                                                                       |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                       |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+


+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
|      `class`         **DRAM3**\[T\] extends DRAM\[T\]                                                                                                                           |
+=====================+===========================================================================================================================================================+
| |               def   **size**: :doc:`Int <../../common/fixpt>`                                                                                                                 |
| |                       Returns the total number of elements in this DRAM3.                                                                                                     |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim0**: :doc:`Int <../../common/fixpt>`                                                                                                                 |
| |                       Returns the first dimension of this DRAM3.                                                                                                              |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim1**: :doc:`Int <../../common/fixpt>`                                                                                                                 |
| |                       Returns the second dimension of this DRAM3.                                                                                                             |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim2**: :doc:`Int <../../common/fixpt>`                                                                                                                 |
| |                       Returns the third dimension of this DRAM3.                                                                                                              |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`../../common/range`, r2: :doc:`../../common/range`): :doc:`DRAMDenseTile3 <tile>`\[T\]               |
| |                       Creates a reference to a 3-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`../../common/range`, r2: :doc:`../../common/range`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`../../common/range`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`../../common/range`, r2: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`../../common/range`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`../../common/range`, r2: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores.                                                 |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`SRAM3 <sram>`\[T\]`: Unit                                                                                                         |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                   |
+---------------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------+


+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
|      `class`         **DRAM4**\[T\] extends DRAM\[T\]                                                                                                                                                                |
+=====================+================================================================================================================================================================================================+
| |               def   **size**: :doc:`Int <../../common/fixpt>`                                                                                                                                                      |
| |                       Returns the total number of elements in this DRAM4.                                                                                                                                          |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim0**: :doc:`Int <../../common/fixpt>`                                                                                                                                                      |
| |                       Returns the first dimension of this DRAM4.                                                                                                                                                   |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim1**: :doc:`Int <../../common/fixpt>`                                                                                                                                                      |
| |                       Returns the second dimension of this DRAM4.                                                                                                                                                  |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim2**: :doc:`Int <../../common/fixpt>`                                                                                                                                                      |
| |                       Returns the third dimension of this DRAM4.                                                                                                                                                   |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **dim3**: :doc:`Int <../../common/fixpt>`                                                                                                                                                      |
| |                       Returns the fourth dimension of this DRAM4.                                                                                                                                                  |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`../../common/range`, r2: :doc:`../../common/range`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile4 <tile>`\[T\]                     |
| |                       Creates a reference to a 4-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`../../common/range`, r2: :doc:`../../common/range`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile3 <tile>`\[T\]               |
| |                       Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`../../common/range`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile3 <tile>`\[T\]               |
| |                       Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`../../common/range`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile3 <tile>`\[T\]               |
| |                       Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`../../common/range`, r2: :doc:`../../common/range`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile3 <tile>`\[T\]               |
| |                       Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`../../common/range`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`../../common/range`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`../../common/range`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`../../common/range`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`../../common/range`, r2: :doc:`../../common/range`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile2 <tile>`\[T\]         |
| |                       Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`../../common/range`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`../../common/range`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`../../common/range`, r3: :doc:`Int <../../common/fixpt>`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **apply**\(r0\: :doc:`Int <../../common/fixpt>`, r1: :doc:`Int <../../common/fixpt>`, r2: :doc:`Int <../../common/fixpt>`, r3: :doc:`../../common/range`): :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |                       Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                                      |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |               def   **store**\(data\: :doc:`SRAM4 <sram>`\[T\]`: Unit                                                                                                                                              |
| |                       Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                                        |
+---------------------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


@table
class DRAM5[T] extends DRAM[T]



--------------

**Implicit methods**

+---------------------+----------------------------------------------------------------------------------------------------------------------+
| |               def   **createTile**\[T::doc:`../../typeclasses/bits`\](dram: :doc:`dram`\[T\]): :doc:`tile`\[T\]                          |
| |                       Implicitly converts a DRAM to a Tile with the same address space                                                   |
+---------------------+----------------------------------------------------------------------------------------------------------------------+
