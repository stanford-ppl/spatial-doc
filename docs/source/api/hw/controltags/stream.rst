
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

.. _Stream:

Stream
======

**Stream** is a scheduling directive which tells the compiler to overlap inner computation in a fine-grained, streaming fashion.
In controllers which contain multiple control stages, this implies that communication is being done through :doc:`FIFO <../memories/onchip/fifo>` memories.
at an element-wise level.

Communication across stages within Stream controllers through any memory except FIFOs is currently disallowed.
Note that this may change as the language evolves.


-----------------

**Static methods**

+----------+---------------------------------------------------------------------------------------------------------------------------+
| object     **Stream**                                                                                                                |
+==========+===========================================================================================================================+
| |    def   **apply**\(func\: => :doc:`Unit <../../common/unit>`\)\: :doc:`Unit <../../common/unit>`                                  |
| |            Creates a streaming Unit Pipe, akin to a Foreach with one iteration.                                                    |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Fold <../controllers/fold>`\: :doc:`Fold <../controllers/fold>`                                                     |
| |            References the :doc:`Fold <../controllers/fold>` object with streaming specified as the scheduling directive.           |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Foreach <../controllers/foreach>`\: :doc:`Foreach <../controllers/foreach>`                                         |
| |            References the :doc:`Foreach <../controllers/foreach>` object with streaming specified as the scheduling directive.     |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`MemFold <../controllers/memfold>`\: :doc:`MemFold <../controllers/memfold>`                                         |
| |            References the :doc:`MemFold <../controllers/memfold>` object with streaming specified as the scheduling directive.     |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`MemReduce <../controllers/memreduce>`\: :doc:`MemReduce <../controllers/memreduce>`                                 |
| |            References the :doc:`MemReduce <../controllers/memreduce>` object with streaming specified as the scheduling directive. |
+----------+---------------------------------------------------------------------------------------------------------------------------+
| |    def   :doc:`Reduce <../controllers/reduce>`\: :doc:`Reduce <../controllers/reduce>`                                             |
| |            References the :doc:`Reduce <../controllers/reduce>` object with streaming specified as the scheduling directive.       |
+----------+---------------------------------------------------------------------------------------------------------------------------+

