
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

.. _Pipe:

Pipe
====

**Pipe** is the default scheduling directive in Spatial, and doesn't usually need to be specified.
This directive tells the compiler that the stages of the controller can be overlapped in a pipelined fashion.
If the controller contains other controllers within it, this means that these inner controllers will be executed using
coarse-grained pipeline scheduling.


-----------------

**Static methods**

+---------------------+----------------------------------------------------------------------------------------------------------+
|      `object`         **Pipe**                                                                                                 |
+=====================+==========================================================================================================+
| |               def   **Fold**\: :doc:`../controllers/fold`                                                                    |
| |                       References the :doc:`../controllers/fold` object with pipelining as the scheduling directive.          |
+---------------------+----------------------------------------------------------------------------------------------------------+
| |               def   **Foreach**\: :doc:`../controllers/foreach`                                                              |
| |                       References the :doc:`../controllers/foreach` object with pipelining as the scheduling directive.       |
+---------------------+----------------------------------------------------------------------------------------------------------+
| |               def   **MemFold**\: :doc:`../controllers/memfold`                                                              |
| |                       References the :doc:`../controllers/memfold` object with pipelining as the scheduling directive.       |
+---------------------+----------------------------------------------------------------------------------------------------------+
| |               def   **MemReduce**\: :doc:`../controllers/memreduce`                                                          |
| |                       References the :doc:`../controllers/memreduce` object with pipelining as the scheduling directive.     |
+---------------------+----------------------------------------------------------------------------------------------------------+
| |               def   **Reduce**\: :doc:`../controllers/reduce`                                                                |
| |                       References the :doc:`../controllers/reduce` object with pipelining as the scheduling directive.        |
+---------------------+----------------------------------------------------------------------------------------------------------+


