
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

.. _Foreach:

Foreach
=======


The *Foreach* controller is similar to a *for* loop. Significantly, however, unless explicitly told otherwise, the compiler
will assume each iteration of *Foreach* is independent, and will attempt to parallelize and pipeline the body.
*Foreach* has no usable return value.


--------------

**Static methods**

+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Foreach**                                                                                                                                                                                                                                                                                                                                                   |
+==========+===============================================================================================================================================================================================================================================================================================================================================================+
| |    def   **apply**\(ctr\: :doc:`Counter <../onchip/counter>`\)\(func\: :doc:`Int <../../common/fixpt>` => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`                                                                                                                                                                                          |
| |            Foreach over a one dimensional space.                                                                                                                                                                                                                                                                                                                       |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`\)\(func\: \(:doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`\) => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`                                                                                                         |
| |            Foreach over a two dimensional space.                                                                                                                                                                                                                                                                                                                       |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`, ctr3\: :doc:`Counter <../onchip/counter>`\)\(func\: \(:doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`, :doc:`Int <../../common/fixpt>`\) => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`                             |
| |            Foreach over a three dimensional space.                                                                                                                                                                                                                                                                                                                     |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(ctr1\: :doc:`Counter <../onchip/counter>`, ctr2\: :doc:`Counter <../onchip/counter>`, ctr3\: :doc:`Counter <../onchip/counter>`, ctr4\: :doc:`Counter <../onchip/counter>`, ctr5\: :doc:`Counter <../onchip/counter>`\*\)\(func\: List\[:doc:`Int <../../common/fixpt>`\] => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`   |
| |            Foreach over a 4+ dimensional space.                                                                                                                                                                                                                                                                                                                        |
| |            Note that **func** is on a **List** of iterators.                                                                                                                                                                                                                                                                                                           |
| |            The number of iterators will be the same as the number of counters supplied.                                                                                                                                                                                                                                                                                |
+----------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

