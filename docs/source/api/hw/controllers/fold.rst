
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

.. _Fold:

Fold
====


**Fold** is a control node which takes an initial value and many scalar values and combines them into one value using some associative operator.
Unless otherwise disabled, the compiler will attempt to automatically pipeline and parallelize *Fold* nodes.
A Fold consists of a *map* function, which is responsible for producing the scalar values, and
a *reduction* function to describe how the values should be combined.

Calling any form of Fold returns the accumulator which, at the end of the loop, will contain the final result of the reduction.



--------------

**Static methods**

+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Fold**                                                                                                                                                                                                                                                                                                                                                  |
+==========+===========================================================================================================================================================================================================================================================================================================================================================+
| |    def   **apply**\[T\]\(initial\: T\)\(ctr\: :doc:`Counter <../onchip/counter>`\)\(map\: :doc:`Int <../../common/fixpt>` => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]                                                                                                                                                                                               |
| |            Fold over a one dimensional space with implicit accumulator but explicit initial value.                                                                                                                                                                                                                                                                 |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(initial\: T\)\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`\)\(map\: \(:doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`\) => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]                                                                                                              |
| |            Fold over a two dimensional space with implicit accumulator but explicit initial value.                                                                                                                                                                                                                                                                 |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(initial\: T\)\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`, ctr3\: :doc:`Counter <../onchip/counter>`\)\(map\: \(:doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`\) => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]                                  |
| |            Fold over a three dimensional space with implicit accumulator but explicit initial value.                                                                                                                                                                                                                                                               |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(initial\: T\)\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`, ctr3\: :doc:`Counter <../onchip/counter>`, ctr4\: :doc:`Counter <../onchip/counter>`, ctr5\: :doc:`Counter <../onchip/counter>`\*\)\(map\: List\[:doc:`Int <../../common/fixpt>`\] => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]        |
| |            Fold over an 4+ dimensional space with implicit accumulator but explicit initial value.                                                                                                                                                                                                                                                                 |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
| |            Note that the **map** function is on a **List** of iterators.                                                                                                                                                                                                                                                                                           |
| |            The number of iterators will be the same as the number of counters supplied.                                                                                                                                                                                                                                                                            |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(accum\: Reg\[T\]\)\(ctr\: :doc:`Counter <../onchip/counter>`\)\(map\: :doc:`Int <../../common/fixpt>` => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]                                                                                                                                                                                          |
| |            Fold over a one dimensional space with explicit accumulator.                                                                                                                                                                                                                                                                                            |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(accum\: Reg\[T\]\)\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`\)\(map\: \(:doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`\) => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]                                                                                                         |
| |            Fold over a two dimensional space with explicit accumulator.                                                                                                                                                                                                                                                                                            |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(accum\: Reg\[T\]\)\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`, ctr3\: :doc:`Counter <../onchip/counter>`\)\(map\: \(:doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`\) => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]                             |
| |            Fold over a three dimensional space with explicit accumulator.                                                                                                                                                                                                                                                                                          |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\]\(accum\: Reg\[T\]\)\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`, ctr3\: :doc:`Counter <../onchip/counter>`, ctr4\: :doc:`Counter <../onchip/counter>`, ctr5\: :doc:`Counter <../onchip/counter>`\*\)\(map\: List\[:doc:`Int <../../common/fixpt>`\] => T\)\(reduce\: \(T,T\) => T\)\: Reg\[T\]   |
| |            Fold over an 4+ dimensional space with explicit accumulator.                                                                                                                                                                                                                                                                                            |
| |            Returns the accumulator used to implement this reduction.                                                                                                                                                                                                                                                                                               |
| |            Note that the **map** function is on a **List** of iterators.                                                                                                                                                                                                                                                                                           |
| |            The number of iterators will be the same as the number of counters supplied.                                                                                                                                                                                                                                                                            |
+----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

