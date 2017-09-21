
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

.. _FIFO:

FIFO
====


**FIFOs** (first-in, first-out) are on-chip scratchpads with additional control logic for address-less enqueue/dequeue operations.
FIFOs preserve the ordering between elements as they are enqueued. A FIFO's **deq** operation always returns the oldest
**enqueued** element which has not yet been dequeued.

---------------

**Static methods**

+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **FIFO**                                                                                                                                                          |
+==========+===================================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(depth\: :doc:`Int <../../common/fixpt>`\)\: :doc:`FIFO <fifo>`\[T\]   |
| |            Creates a FIFO with given **depth**.                                                                                                                            |
| |                                                                                                                                                                            |
| |            Depth must be a statically determinable signed integer.                                                                                                         |
+----------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+


--------------

**Infix methods**

+----------+------------------------------------------------------------------------------------------------------------------------+
| class      **FIFO**\[T\]                                                                                                          |
+==========+========================================================================================================================+
| |    def   **par**\(p\: :doc:`Index <../../common/fixpt>`\)\: :doc:`FIFO <fifo>`\[T\] = { val x = :doc:`FIFO <fifo>`\(s\); x.p    |
| |            Annotates that addresses in this FIFO can be read in parallel by factor **p**.                                       |
| |                                                                                                                                 |
| |            Used when creating references to sparse regions of DRAM.                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **empty**\(\)\: :doc:`Bit <../../common/bit>`                                                                          |
| |            Returns true when this FIFO contains no elements, false otherwise.                                                   |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **full**\(\)\: :doc:`Bit <../../common/bit>`                                                                           |
| |            Returns true when this FIFO cannot fit any more elements, false otherwise.                                           |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **almostEmpty**\(\)\: :doc:`Bit <../../common/bit>`                                                                    |
| |            Returns true when this FIFO contains exactly one element, false otherwise.                                           |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **almostFull**\(\)\: :doc:`Bit <../../common/bit>`                                                                     |
| |            Returns true when this FIFO can fit exactly one more element, false otherwise.                                       |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **numel**\(\)\: :doc:`Index <../../common/fixpt>`                                                                      |
| |            Returns the number of elements currently in this FIFO.                                                               |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **enq**\(data\: T\)\: :doc:`Unit <../../common/unit>`                                                                  |
| |            Creates an enqueue (write) port of **data** to this FIFO.                                                            |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **enq**\(data\: T, en\: :doc:`Bit <../../common/bit>`\)\: :doc:`Unit <../../common/unit>`                              |
| |            Creates an enqueue (write) port of **data** to this FIFO, enabled by **en**.                                         |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **deq**\(\)\: T                                                                                                        |
| |            Creates a dequeue (destructive read) port from this FIFO.                                                            |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **deq**\(en\: :doc:`Bit <../../common/bit>`\)\: T                                                                      |
| |            Creates a dequeue (destructive read) port from this FIFO, enabled by **en**.                                         |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **peek**\(\)\: T                                                                                                       |
| |            Creates a non-destructive read port from this FIFO.                                                                  |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAMDenseTile1 <../offchip/tile>`\[T\]\)\: :doc:`Unit <../../common/unit>`                      |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.                                |
+----------+------------------------------------------------------------------------------------------------------------------------+
| |    def   **gather**\(dram\: :doc:`DRAMSparseTile <../offchip/sparsetile>`\[T\]\)\: :doc:`Unit <../../common/unit>`              |
| |            Creates a sparse load from the specified sparse region of DRAM to this on-chip memory.                               |
+----------+------------------------------------------------------------------------------------------------------------------------+

