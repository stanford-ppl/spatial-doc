
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

.. _Mem:

Mem
====


Type class used to supply evidence that type T is a local memory, potentially with multiple dimensions.


**Abstract Methods**

+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
| trait      **Mem**\[T,C\]                                                                                                                                         |
+==========+========================================================================================================================================================+
| |    def   **load**\(mem\: C\[T\], indices\: Seq\[:doc:`Index <../common/fixpt>`\], en\: :doc:`Bit <../common/bit>`\)\: T                                         |
| |            Loads an element from **mem** at the given multi-dimensional address **indices** with enable **en**.                                                 |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **store**\(mem\: C\[T\], indices\: Seq\[:doc:`Index <../common/fixpt>`\], data\: T, en\: :doc:`Bit <../common/bit>`\)\: :doc:`Unit <../common/unit>`   |
| |            Stores **data** into **mem** at the given multi-dimensional address **indices** with enable **en**.                                                  |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **iterators**\(mem\: C\[T\]\)\: Seq\[:doc:`Counter <../hw/onchip/counter>`\]                                                                           |
| |            Returns a **Seq** of counters which define the iteration space of the given memory **mem**.                                                          |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **par**\(mem\: C\[T\]\)\: Option\[:doc:`Index <../common/fixpt>`\]                                                                                     |
| |            Returns the parallelization annotation for this memory.                                                                                              |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+


