
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


+----------+--------------------------------------------------------------------------------------------------------------------------------------+
| object     **Sequential**                                                                                                                       |
+==========+======================================================================================================================================+
| |    def   **apply**\(func\: => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`                                             |
| |            Creates a Unit Pipe, akin to a Foreach with one iteration.                                                                         |
+----------+--------------------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Fold <../controllers/fold>`\: :doc:`Fold <../controllers/fold>`                                                                |
| |            References the :doc:`Fold <../controllers/fold>` object with sequential execution specified as the scheduling directive.           |
+----------+--------------------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Foreach <../controllers/foreach>`\: :doc:`Foreach <../controllers/foreach>`                                                    |
| |            References the :doc:`Foreach <../controllers/foreach>` object with sequential execution specified as the scheduling directive.     |
+----------+--------------------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`MemFold <../controllers/memfold>`\: :doc:`MemFold <../controllers/memfold>`                                                    |
| |            References the :doc:`MemFold <../controllers/memfold>` object with sequential execution specified as the scheduling directive.     |
+----------+--------------------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`MemReduce <../controllers/memreduce>`\: :doc:`MemReduce <../controllers/memreduce>`                                            |
| |            References the :doc:`MemReduce <../controllers/memreduce>` object with sequential execution specified as the scheduling directive. |
+----------+--------------------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Reduce <../controllers/reduce>`\: :doc:`Reduce <../controllers/reduce>`                                                        |
| |            References the :doc:`Reduce <../controllers/reduce>` object with sequential execution specified as the scheduling directive.       |
+----------+--------------------------------------------------------------------------------------------------------------------------------------+

