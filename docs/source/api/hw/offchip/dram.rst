
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

+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **DRAM**                                                                                                                                                                                                                                                                                                                           |
+==========+====================================================================================================================================================================================================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(length\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAM1 <dram>`\[T\]                                                                                                                                                                |
| |            Creates a reference to a 1-dimensional array in main memory with the given length.                                                                                                                                                                                                                                               |
| |            Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.                                                                                                                                                                                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(d1\: :doc:`Index <../../common/fixpt>`, d2\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAM2 <dram>`\[T\]                                                                                                                            |
| |            Creates a reference to a 2-dimensional array in main memory with given rows and cols.                                                                                                                                                                                                                                            |
| |            Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.                                                                                                                                                                                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(d1\: :doc:`Index <../../common/fixpt>`, d2\: :doc:`Index <../../common/fixpt>`, d3\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAM3 <dram>`\[T\]                                                                                    |
| |            Creates a reference to a 3-dimensional array in main memory with given dimensions.                                                                                                                                                                                                                                               |
| |                                                                                                                                                                                                                                                                                                                                             |
| |            Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.                                                                                                                                                                                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(d1\: :doc:`Index <../../common/fixpt>`, d2\: :doc:`Index <../../common/fixpt>`, d3\: :doc:`Index <../../common/fixpt>`, d4\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAM4 <dram>`\[T\]                                            |
| |            Creates a reference to a 4-dimensional array in main memory with given dimensions.                                                                                                                                                                                                                                               |
| |            Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.                                                                                                                                                                                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(d1\: :doc:`Index <../../common/fixpt>`, d2\: :doc:`Index <../../common/fixpt>`, d3\: :doc:`Index <../../common/fixpt>`, d4\: :doc:`Index <../../common/fixpt>`, d5\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAM5 <dram>`\[T\]    |
| |            Creates a reference to a 5-dimensional array in main memory with given dimensions.                                                                                                                                                                                                                                               |
| |            Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.                                                                                                                                                                                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+



--------------

**Infix methods**

+----------------+--------------------------------------------------------------------------------+
| abstract class   **DRAM**\[T\]                                                                  |
+================+================================================================================+
| |          def   **address**\: :doc:`Int64 <../../common/fixpt>`                                |
| |                  Returns the 64-bit physical address in main memory of the start of this DRAM |
+----------------+--------------------------------------------------------------------------------+
| |          def   **dims**\: List\[:doc:`Index <../../common/fixpt>`\]                           |
| |                  Returns a Scala List of the dimensions of this DRAM                          |
+----------------+--------------------------------------------------------------------------------+



+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **DRAM1**\[T\] extends DRAM\[T\]                                                                                                                                               |
+==========+================================================================================================================================================================================+
| |    def   **size**\: :doc:`Index <../../common/fixpt>`                                                                                                                                   |
| |            Returns the total number of elements in this DRAM1.                                                                                                                          |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **length**\: :doc:`Index <../../common/fixpt>`                                                                                                                                 |
| |            Returns the total number of elements in this DRAM1.                                                                                                                          |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(range\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]                                                                                     |
| |            Creates a reference to a dense region of this DRAM1 for creating burst loads and stores.                                                                                     |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(addrs\: :doc:`SRAM1 <../onchip/sram>`\[:doc:`Index <../../common/fixpt>`\]\)\: :doc:`DRAMSparseTile <sparsetile>`\[T\]                                              |
| |            Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                                                 |
| |            using all addresses in **addrs**.                                                                                                                                            |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(addrs\: :doc:`SRAM1 <../onchip/sram>`\[:doc:`Index <../../common/fixpt>`\], size\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMSparseTile <sparsetile>`\[T\]    |
| |            Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                                                 |
| |            using the first **size** addresses in **addrs**.                                                                                                                             |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(addrs\: :doc:`FIFO <../onchip/fifo>`\[:doc:`Index <../../common/fixpt>`\]\)\: :doc:`DRAMSparseTile <sparsetile>`\[T\]                                               |
| |            Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                                                 |
| |            using all addresses in **addrs**.                                                                                                                                            |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(addrs\: :doc:`FIFO <../onchip/fifo>`\[:doc:`Index <../../common/fixpt>`\], size\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMSparseTile <sparsetile>`\[T\]     |
| |            Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                                                 |
| |            using the first **size** addresses in **addrs**.                                                                                                                             |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(addrs\: :doc:`FILO <../onchip/filo>`\[:doc:`Index <../../common/fixpt>`\]\)\: :doc:`DRAMSparseTile <sparsetile>`\[T\]                                               |
| |            Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                                                 |
| |            using all addresses in **addrs**.                                                                                                                                            |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(addrs\: :doc:`FILO <../onchip/filo>`\[:doc:`Index <../../common/fixpt>`\], size\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMSparseTile <sparsetile>`\[T\]     |
| |            Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers                                                                                 |
| |            using the first **size** addresses in **addrs**.                                                                                                                             |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`SRAM1 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                       |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`FIFO <../onchip/fifo>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                        |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`FILO <../onchip/filo>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                        |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`RegFile1 <../onchip/regfile>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                 |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                        |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+



+----------+-----------------------------------------------------------------------------------------------------------+
| class      **DRAM2**\[T\] extends DRAM\[T\]                                                                          |
+==========+===========================================================================================================+
| |    def   **rows**\: :doc:`Index <../../common/fixpt>`                                                              |
| |            Returns the number of rows in this DRAM2                                                                |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **cols**\: :doc:`Index <../../common/fixpt>`                                                              |
| |            Returns the number of columns in this DRAM2                                                             |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **size**\: :doc:`Index <../../common/fixpt>`                                                              |
| |            Returns the total number of elements in this DRAM2                                                      |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(row\: :doc:`Index <../../common/fixpt>`, cols\: :doc:`Range <../../common/range>`\)            |
| |            Creates a reference to a dense slice of a row of this DRAM2 for creating burst loads and stores.        |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(rows\: :doc:`Range <../../common/range>`, col\: :doc:`Index <../../common/fixpt>`\)            |
| |            Creates a reference to a dense slice of a column of this DRAM2 for creating burst loads and stores.     |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(rows\: :doc:`Range <../../common/range>`, cols\: :doc:`Range <../../common/range>`\)           |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM2 for creating burst loads and stores. |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **store**\(sram\: :doc:`SRAM2 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                  |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.   |
+----------+-----------------------------------------------------------------------------------------------------------+
| |    def   **store**\(regs\: :doc:`RegFile2 <../onchip/regfile>`\[T\]\)\: :doc:`Unit <../../common/unit>`            |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.   |
+----------+-----------------------------------------------------------------------------------------------------------+




+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| class      **DRAM3**\[T\] extends DRAM\[T\]                                                                                                    |
+==========+=====================================================================================================================================+
| |    def   **dim0**\: :doc:`Index <../../common/fixpt>`                                                                                        |
| |            Returns the first dimension for this DRAM3.                                                                                       |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim1**\: :doc:`Index <../../common/fixpt>`                                                                                        |
| |            Returns the second dimension for this DRAM3.                                                                                      |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim2**\: :doc:`Index <../../common/fixpt>`                                                                                        |
| |            Returns the third dimension for this DRAM3.                                                                                       |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **size**\: :doc:`Index <../../common/fixpt>`                                                                                        |
| |            Returns the total number of elements in this DRAM3.                                                                               |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM3 for creating burst loads and stores.                           |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(sram\: :doc:`SRAM3 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                            |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------+




+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **DRAM4**\[T\] extends DRAM\[T\]                                                                                                                                           |
+==========+============================================================================================================================================================================+
| |    def   **dim0**\: :doc:`Index <../../common/fixpt>`                                                                                                                               |
| |            Returns the first dimension of this DRAM4.                                                                                                                               |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim1**\: :doc:`Index <../../common/fixpt>`                                                                                                                               |
| |            Returns the second dimension of this DRAM4.                                                                                                                              |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim2**\: :doc:`Index <../../common/fixpt>`                                                                                                                               |
| |            Returns the third dimension of this DRAM4.                                                                                                                               |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim3**\: :doc:`Index <../../common/fixpt>`                                                                                                                               |
| |            Returns the fourth dimension of this DRAM4.                                                                                                                              |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **size**\: :doc:`Index <../../common/fixpt>`                                                                                                                               |
| |            Returns the total number of elements in this DRAM4.                                                                                                                      |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)    |
| |            Creates a reference to a 4-dimensional, dense region of this DRAM4 for creating burst loads and stores.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`SRAM4 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                   |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                    |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+




+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **DRAM5**\[T\] extends DRAM\[T\]                                                                                                                                                                                                                      |
+==========+=======================================================================================================================================================================================================================================================+
| |    def   **dim0**\: :doc:`Index <../../common/fixpt>`                                                                                                                                                                                                          |
| |            Returns the first dimension of this DRAM5.                                                                                                                                                                                                          |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim1**\: :doc:`Index <../../common/fixpt>`                                                                                                                                                                                                          |
| |            Returns the second dimension of this DRAM5.                                                                                                                                                                                                         |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim2**\: :doc:`Index <../../common/fixpt>`                                                                                                                                                                                                          |
| |            Returns the third dimension of this DRAM5.                                                                                                                                                                                                          |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim3**\: :doc:`Index <../../common/fixpt>`                                                                                                                                                                                                          |
| |            Returns the fourth dimension of this DRAM5.                                                                                                                                                                                                         |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **dim4**\: :doc:`Index <../../common/fixpt>`                                                                                                                                                                                                          |
| |            Returns the fifth dimension of this DRAM5.                                                                                                                                                                                                          |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **size**\: :doc:`Index <../../common/fixpt>`                                                                                                                                                                                                          |
| |            Returns the total number of elements in this DRAM5.                                                                                                                                                                                                 |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]    |
| |            Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]    |
| |            Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]    |
| |            Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Index <../../common/fixpt>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile4 <tile>`\[T\]    |
| |            Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Index <../../common/fixpt>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile4 <tile>`\[T\]    |
| |            Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Index <../../common/fixpt>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile4 <tile>`\[T\]    |
| |            Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Index <../../common/fixpt>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile4 <tile>`\[T\]    |
| |            Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Index <../../common/fixpt>`\)\: :doc:`DRAMDenseTile4 <tile>`\[T\]    |
| |            Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(x\: :doc:`Range <../../common/range>`, q\: :doc:`Range <../../common/range>`, p\: :doc:`Range <../../common/range>`, r\: :doc:`Range <../../common/range>`, c\: :doc:`Range <../../common/range>`\)\: :doc:`DRAMDenseTile5 <tile>`\[T\]    |
| |            Creates a reference to a 5-dimensional, dense region of this DRAM5 for creating burst loads and stores.                                                                                                                                             |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`SRAM5 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                                                                                                                                                              |
| |            Creates a dense, burst transfer from the given on-chip **data** to this DRAM's region of main memory.                                                                                                                                               |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


--------------

**Implicit methods**

+-----------+-----------------------------------------------------------------------------------------------+
| |     def   **createTile1**\[T\]\(dram\: :doc:`DRAM1 <dram>`\[T\]\)\: :doc:`DRAMDenseTile1 <tile>`\[T\]   |
| |             Implicitly converts a DRAM1 to a DRAMDenseTile1 with the same address space.                |
+-----------+-----------------------------------------------------------------------------------------------+
| |     def   **createTile2**\[T\]\(dram\: :doc:`DRAM2 <dram>`\[T\]\)\: :doc:`DRAMDenseTile2 <tile>`\[T\]   |
| |             Implicitly converts a DRAM2 to a DRAMDenseTile2 with the same address space.                |
+-----------+-----------------------------------------------------------------------------------------------+
| |     def   **createTile3**\[T\]\(dram\: :doc:`DRAM3 <dram>`\[T\]\)\: :doc:`DRAMDenseTile3 <tile>`\[T\]   |
| |             Implicitly converts a DRAM3 to a DRAMDenseTile3 with the same address space.                |
+-----------+-----------------------------------------------------------------------------------------------+
| |     def   **createTile4**\[T\]\(dram\: :doc:`DRAM4 <dram>`\[T\]\)\: :doc:`DRAMDenseTile4 <tile>`\[T\]   |
| |             Implicitly converts a DRAM4 to a DRAMDenseTile4 with the same address space.                |
+-----------+-----------------------------------------------------------------------------------------------+
| |     def   **createTile2**\[T\]\(dram\: :doc:`DRAM5 <dram>`\[T\]\)\: :doc:`DRAMDenseTile5 <tile>`\[T\]   |
| |             Implicitly converts a DRAM5 to a DRAMDenseTile5 with the same address space.                |
+-----------+-----------------------------------------------------------------------------------------------+

  
