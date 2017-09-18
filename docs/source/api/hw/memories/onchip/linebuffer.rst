
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

.. _LineBuffer:

LineBuffer
==========


**LineBuffers** are two dimensional, on-chip scratchpads with a fixed size.
LineBuffers act as a FIFO on input, supporting only queued writes, but support addressed reading like SRAMs.
For writes, the current row buffer and column is maintained using an internal counter.
This counter resets every time the controller containing the enqueue completes execution.

The contents of LineBuffers are persistent across loop iterations, even when they are declared in an inner scope.
Up to 5-dimensional LineBuffers are currently supported.


---------------

**Static methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **LineBuffer**                                                                                                                                                                                                                                                                         |
+==========+========================================================================================================================================================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\(rows\: :doc:`Index <../../../common/fixpt>`, cols\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`LineBuffer <linebuffer>`\[T\]                                                     |
| |            Allocates a LineBuffer with given **rows** and **cols**.                                                                                                                                                                                                                             |
| |            The contents of this LineBuffer are initially undefined.                                                                                                                                                                                                                             |
| |            **rows** and **cols** must be statically determinable integers.                                                                                                                                                                                                                      |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **strided**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\(rows\: :doc:`Index <../../../common/fixpt>`, cols\: :doc:`Index <../../../common/fixpt>`, stride\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`LineBuffer <linebuffer>`\[T\]    |
| |            Allocates a LineBuffer with given number of **rows** and **cols**, and with given **stride**.                                                                                                                                                                                        |
| |            The contents of this LineBuffer are initially undefined.                                                                                                                                                                                                                             |
| |            **rows**, **cols**, and **stride** must be statically determinable integers.                                                                                                                                                                                                         |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+



--------------

**Infix methods**

+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **LineBuffer**\[T\]                                                                                                                                           |
+==========+===============================================================================================================================================================+
| |    def   **apply**\(row\: :doc:`Index <../../../common/fixpt>`, col\: :doc:`Index <../../../common/fixpt>`\)\: T                                                       |
| |            Creates a load port to this LineBuffer at the given **row** and **col**.                                                                                    |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(row\: :doc:`Index <../../../common/fixpt>`, cols\: :doc:`Range <../../../common/range>`\)\(implicit ctx\: SrcCtx\)\: :doc:`Vector <vector>`\[T\]   |
| |            Creates a vectorized load port to this LineBuffer at the given **row** and **cols**.                                                                        |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(rows\: :doc:`Range <../../../common/range>`, col\: :doc:`Index <../../../common/fixpt>`\)\(implicit ctx\: SrcCtx\)\: :doc:`Vector <vector>`\[T\]   |
| |            Creates a vectorized load port to this LineBuffer at the given **rows** and **col**.                                                                        |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **enq**\(data\: T\)\: :doc:`Unit <../../../common/unit>`                                                                                                      |
| |            Creates an enqueue (write) port of **data** to this LineBuffer.                                                                                             |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **enq**\(data\: T, en\: :doc:`Bit <../../../common/bit>`\)\: :doc:`Unit <../../../common/unit>`                                                               |
| |            Creates an enqueue (write) port of **data** to this LineBuffer, enabled by **en**.                                                                          |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAMDenseTile1 <../offchip/tile>`\[T\]\)\(implicit ctx\: SrcCtx\)\: :doc:`Unit <../../../common/unit>`                                 |
| |            Creates a dense transfer from the given region of DRAM to this on-chip memory.                                                                              |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------+

