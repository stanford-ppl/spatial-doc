
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

.. _MemReduce:

MemReduce
=========


**MemReduce** describes the reduction *across* multiple local memories.
Like @Reduce, MemReduce requires both a *map* and a *reduction* function. However, in MemReduce, the *map*
describes the creation and population of a local memory (typically an @SRAM).
The *reduction* function still operates on scalars, and is used to combine local memories together element-wise.
Unlike Reduce, MemReduce always requires an explicit accumulator.
Unless otherwise disabled, the compiler will then try to parallelize both the creation of multiple memories and the reduction
of each of these memories into a single accumulator.

--------------

**Static methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **MemReduce**                                                                                                                                                                                                                                                                                                                                                                                                  |
+==========+================================================================================================================================================================================================================================================================================================================================================================================================================+
| |    def   **apply**\[T,C\[T\]\]\(accum\: C\[T\]\)\(ctr\: :doc:`Counter <../memories/onchip/counter>`\)\(map\: :doc:`Int <../../common/fixpt>` => C\[T\]\)\(reduce\: \(T,T\) => T\)\: C\[T\]                                                                                                                                                                                                                              |
| |            On-chip memory reduction over a one dimensional space.                                                                                                                                                                                                                                                                                                                                                       |
| |            Returns the accumulator **accum**.                                                                                                                                                                                                                                                                                                                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T,C\[T\]\]\(accum\: C\[T\]\)\(ctr1\: :doc:`Counter <../memories/onchip/counter>`, ctr2\: :doc:`Counter <../memories/onchip/counter>`\)\(map\: \(:doc:`Int <../../common/fixpt>`,:doc:`Int <../../common/fixpt>`\) => C\[T\]\)\(reduce\: \(T,T\) => T\)\: C\[T\]                                                                                                                                     |
| |            On-chip memory reduction over a two dimensional space.                                                                                                                                                                                                                                                                                                                                                       |
| |            Returns the accumulator **accum**.                                                                                                                                                                                                                                                                                                                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T,C\[T\]\]\(accum\: C\[T\]\)\(ctr1\: :doc:`Counter <../memories/onchip/counter>`, ctr2\: :doc:`Counter <../memories/onchip/counter>`, ctr3\: :doc:`Counter <../memories/onchip/counter>`\)\(map\: \(:doc:`Int <../../common/fixpt>`,:doc:`Int <../../common/fixpt>`,:doc:`Int <../../common/fixpt>`\) => C\[T\]\)\(reduce\: \(T,T\) => T\)\: C\[T\]                                                 |
| |            On-chip memory reduction over a three dimensional space.                                                                                                                                                                                                                                                                                                                                                     |
| |            Returns the accumulator **accum**.                                                                                                                                                                                                                                                                                                                                                                           |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T,C\[T\]\]\(accum\: C\[T\]\)\(ctr1\: :doc:`Counter <../memories/onchip/counter>`, ctr2\: :doc:`Counter <../memories/onchip/counter>`, ctr3\: :doc:`Counter <../memories/onchip/counter>`, ctr4\: :doc:`Counter <../memories/onchip/counter>`, ctr5\: :doc:`Counter <../memories/onchip/counter>`\*\)\(map\: List\[:doc:`Int <../../common/fixpt>`\] => C\[T\]\)\(reduce\: \(T,T\) => T\)\: C\[T\]   |
| |            On-chip memory reduction over a 4+ dimensional space.                                                                                                                                                                                                                                                                                                                                                        |
| |            Returns the accumulator **accum**.                                                                                                                                                                                                                                                                                                                                                                           |
| |            Note that the **map** function is on a **List** of iterators.                                                                                                                                                                                                                                                                                                                                                |
| |            The number of iterators will be the same as the number of counters supplied.                                                                                                                                                                                                                                                                                                                                 |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

