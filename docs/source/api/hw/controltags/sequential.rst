
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

.. _Sequential:

Sequential
==========

**Sequential** is a scheduling directive which tells the compiler not to attempt to parallelize or to pipeline inner computation.
In this scheduling mode, the controller's counter will only increment when it's last stage is complete.
This directive is needed primarily when the algorithm contains long loop-carry dependencies that cannot be optimized away.

-----------------

**Static methods**

+---------------------+------------------------------------------------------------------------------------------------------------------+
|      `object`         **Sequential**                                                                                                   |
+=====================+==================================================================================================================+
| |               def   **Fold**\: :doc:`../controllers/fold`                                                                            |
| |                       References the :doc:`../controllers/fold` object with sequential execution as the scheduling directive.        |
+---------------------+------------------------------------------------------------------------------------------------------------------+
| |               def   **Foreach**\: :doc:`../controllers/foreach`                                                                      |
| |                       References the :doc:`../controllers/foreach` object with sequential execution as the scheduling directive.     |
+---------------------+------------------------------------------------------------------------------------------------------------------+
| |               def   **MemFold**\: :doc:`../controllers/memfold`                                                                      |
| |                       References the :doc:`../controllers/memfold` object with sequential execution as the scheduling directive.     |
+---------------------+------------------------------------------------------------------------------------------------------------------+
| |               def   **MemReduce**\: :doc:`../controllers/memreduce`                                                                  |
| |                       References the :doc:`../controllers/memreduce` object with sequential execution as the scheduling directive.   |
+---------------------+------------------------------------------------------------------------------------------------------------------+
| |               def   **Reduce**\: :doc:`../controllers/reduce`                                                                        |
| |                       References the :doc:`../controllers/reduce` object with sequential execution as the scheduling directive.      |
+---------------------+------------------------------------------------------------------------------------------------------------------+
