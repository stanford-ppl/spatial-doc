
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

+----------+----------------------------------------------------------------------------------------------------------------------------+
| object     **Pipe**                                                                                                                   |
+==========+============================================================================================================================+
| |    def   **apply**\(func\: => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`                                   |
| |            Creates a Unit Pipe, akin to a Foreach with one iteration.                                                               |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Fold <../controllers/fold>`\: :doc:`Fold <../controllers/fold>`                                                      |
| |            References the :doc:`Fold <../controllers/fold>` object with pipelining specified as the scheduling directive.           |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Foreach <../controllers/foreach>`\: :doc:`Foreach <../controllers/foreach>`                                          |
| |            References the :doc:`Foreach <../controllers/foreach>` object with pipelining specified as the scheduling directive.     |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`MemFold <../controllers/memfold>`\: :doc:`MemFold <../controllers/memfold>`                                          |
| |            References the :doc:`MemFold <../controllers/memfold>` object with pipelining specified as the scheduling directive.     |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`MemReduce <../controllers/memreduce>`\: :doc:`MemReduce <../controllers/memreduce>`                                  |
| |            References the :doc:`MemReduce <../controllers/memreduce>` object with pipelining specified as the scheduling directive. |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Reduce <../controllers/reduce>`\: :doc:`Reduce <../controllers/reduce>`                                              |
| |            References the :doc:`Reduce <../controllers/reduce>` object with pipelining specified as the scheduling directive.       |
+----------+----------------------------------------------------------------------------------------------------------------------------+

