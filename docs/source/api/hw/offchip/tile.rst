
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

.. _DRAMDenseTile:

DRAMDenseTile
=============


A **DRAMDenseTile** describes a continguous slice of a :doc:`dram` memory's address space which can be loaded onto the
accelerator for processing or which can be updated with results once FPGA computation is complete.

----------------

**Infix methods**

+----------+---------------------------------+
| class      **DRAMDenseTile**               |
+----------+---------------------------------+




+----------+----------------------------------------------------------------------------------------------------------+
| class      **DRAMDenseTile1**\[T\] extends DRAMDenseTile\[T\]                                                       |
+==========+==========================================================================================================+
| |    def   **store**\(data\: :doc:`SRAM1 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                 |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`FIFO <../onchip/fifo>`\[T\]\)\: :doc:`Unit <../../common/unit>`                  |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`FILO <../onchip/filo>`\[T\]\)\: :doc:`Unit <../../common/unit>`                  |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`RegFile1 <../onchip/regfile>`\[T\]\)\: :doc:`Unit <../../common/unit>`           |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+



+----------+----------------------------------------------------------------------------------------------------------+
| class      **DRAMDenseTile2**\[T\] extends DRAMDenseTile\[T\]                                                       |
+==========+==========================================================================================================+
| |    def   **store**\(data\: :doc:`SRAM2 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                 |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+
| |    def   **store**\(data\: :doc:`RegFile2 <../onchip/regfile>`\[T\]\)\: :doc:`Unit <../../common/unit>`           |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+



+----------+----------------------------------------------------------------------------------------------------------+
| class      **DRAMDenseTile3**\[T\] extends DRAMDenseTile\[T\]                                                       |
+==========+==========================================================================================================+
| |    def   **store**\(data\: :doc:`SRAM3 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                 |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+




+----------+----------------------------------------------------------------------------------------------------------+
| class      **DRAMDenseTile4**\[T\] extends DRAMDenseTile\[T\]                                                       |
+==========+==========================================================================================================+
| |    def   **store**\(data\: :doc:`SRAM4 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                 |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+



+----------+----------------------------------------------------------------------------------------------------------+
| class      **DRAMDenseTile5**\[T\] extends DRAMDenseTile\[T\]                                                       |
+==========+==========================================================================================================+
| |    def   **store**\(data\: :doc:`SRAM5 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`                 |
| |            Creates a dense, burst transfer from the given on-chip **data** to this tiles's region of main memory. |
+----------+----------------------------------------------------------------------------------------------------------+

