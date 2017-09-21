
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

.. _DRAMSparseTile:

DRAMSparseTile
==============


A **DRAMSparseTile** describes a sparse section of a DRAM memory which can be loaded onto the accelerator using a gather operation, or which can
be updated using a scatter operation.

--------------

**Infix methods**

+----------+---------------------------------------------------------------------------------------------------+
| class      **DRAMSparseTile**                                                                                |
+==========+===================================================================================================+
| |    def   **scatter**\(data\: :doc:`SRAM1 <../onchip/sram>`\[T\]\)\: :doc:`Unit <../../common/unit>`        |
| |            Creates a sparse transfer from the given on-chip **data** to this sparse region of main memory. |
+----------+---------------------------------------------------------------------------------------------------+
| |    def   **scatter**\(data\: :doc:`FIFO <../onchip/fifo>`\[T\]\)\: :doc:`Unit <../../common/unit>`         |
| |            Creates a sparse transfer from the given on-chip **data** to this sparse region of main memory. |
+----------+---------------------------------------------------------------------------------------------------+
| |    def   **scatter**\(data\: :doc:`FILO <../onchip/filo>`\[T\]\)\: :doc:`Unit <../../common/unit>`         |
| |            Creates a sparse transfer from the given on-chip **data** to this sparse region of main memory. |
+----------+---------------------------------------------------------------------------------------------------+

