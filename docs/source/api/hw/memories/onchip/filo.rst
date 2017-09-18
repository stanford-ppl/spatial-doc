
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

.. _FILO:

FILO
====


**FILOs** (first-in, last-out) are on-chip scratchpads with additional control logic for address-less enqueue/dequeue operations.
FILOs acts as a Stack, reversing the order of elements it receives. A FILO's **pop** operation always returns the most
recently **pushed** element.

---------------

**Static methods**

+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **FILO**                                                                                                                                                                     |
+==========+==============================================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\(size\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`FILO <filo>`\[T\]    |
| |            Creates a FILO with given **depth**.                                                                                                                                       |
| |                                                                                                                                                                                       |
| |            Depth must be a statically determinable signed integer.                                                                                                                    |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+



--------------

**Infix methods**

+----------+---------------------------------------------------------------------------------------------------------------------------+
| class      **FILO**\[T\]                                                                                                             |
+==========+===========================================================================================================================+
| |    def   **par**\(p\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`FILO <filo>`\[T\] = { val x = :doc:`FILO <filo>`\(s\); x.p    |
| |            Annotates that addresses in this FIFO can be read in parallel by factor **p**.                                          |
| |                                                                                                                                    |
| |            Used when creating references to sparse regions of DRAM.                                                                |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **empty**\(\)\: :doc:`Bit <../../../common/bit>`                                                                          |
| |            Returns true when this FILO contains no elements, false otherwise.                                                      |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **full**\(\)\: :doc:`Bit <../../../common/bit>`                                                                           |
| |            Returns true when this FILO cannot fit any more elements, false otherwise.                                              |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **almostEmpty**\(\)\: :doc:`Bit <../../../common/bit>`                                                                    |
| |            Returns true when this FILO contains exactly one element, false otherwise.                                              |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **almostFull**\(\)\: :doc:`Bit <../../../common/bit>`                                                                     |
| |            Returns true when this FILO can fit exactly one more element, false otherwise.                                          |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **numel**\(\)\: :doc:`Index <../../../common/fixpt>`                                                                      |
| |            Returns the number of elements currently in this FILO.                                                                  |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **push**\(data\: T\)\: :doc:`Unit <../../../common/unit>`                                                                 |
| |            Creates a push (write) port to this FILO of **data**.                                                                   |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **push**\(data\: T, en\: :doc:`Bit <../../../common/bit>`\)\: :doc:`Unit <../../../common/unit>`                          |
| |            Creates a conditional push (write) port to this FILO of **data** enabled by **en**.                                     |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **pop**\(\)\: T                                                                                                           |
| |            Creates a pop (destructive read) port to this FILO.                                                                     |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **pop**\(en\: :doc:`Bit <../../../common/bit>`\)\: T                                                                      |
| |            Creates a conditional pop (destructive read) port to this FILO enabled by **en**.                                       |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **peek**\(\)\: T                                                                                                          |
| |            Creates a non-destructive read port to this FILO.                                                                       |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **load**\(dram\: :doc:`DRAMDenseTile1 <../offchip/tile>`\[T\]\)\: :doc:`Unit <../../../common/unit>`                      |
| |            Creates a dense, burst load from the specified region of DRAM to this on-chip memory.                                   |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   **gather**\(dram\: :doc:`DRAMSparseTile <../offchip/sparsetile>`\[T\]\)\: :doc:`Unit <../../../common/unit>`              |
| |            Creates a sparse load from the specified sparse region of DRAM to this on-chip memory.                                  |
+----------+---------------------------------------------------------------------------------------------------------------------------+

