
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

.. _Tuple2:

Tuple2
========


Tuple2[A,B] is a simple data structure used to hold a pair of staged values.

Note that this name shadows the unstaged Scala type. For the unstaged type, use the full name scala.Tuple2[A,B]

-----------

**Infix methods**

+----------+----------------------------------------------------------------------------------------------------------------------------+
| class      **Tuple2**\[A,B\]                                                                                                          |
+==========+============================================================================================================================+
| |    def   **_1**\: A                                                                                                                 |
| |            Returns the first field in this Tuple2.                                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **_2**\: B                                                                                                                 |
| |            Returns the second field in this Tuple2.                                                                                 |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **toString**\: :doc:`String <../sw/string>`                                                                                |
| |            Returns a printable String from this value.                                                                              |
| |                                                                                                                                     |
| |            **NOTE**: This method is unsynthesizable and can only be used on the CPU or in simulation.                               |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **----------**                                                                                                             |
| |                                                                                                                                     |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **\*\*Related** methods\*\*                                                                                                |
| |                                                                                                                                     |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **@table-start**                                                                                                           |
| |                                                                                                                                     |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **NoHeading**                                                                                                              |
| |                                                                                                                                     |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **pack**\[A\::doc:`Type <../typeclasses/type>`,B\::doc:`Type <../typeclasses/type>`\]\(t\: \(A, B\)\)\: MTuple2\[A,B\]     |
| |            Returns a staged Tuple2 from the given unstaged Tuple2.                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **pack**\[A\::doc:`Type <../typeclasses/type>`,B\::doc:`Type <../typeclasses/type>`\]\(a\: A, b\: B\)\: MTuple2\[A,B\]     |
| |            Returns a staged Tuple2 from the given pair of values. Shorthand for ****pack((a,b))****.                                |
+----------+----------------------------------------------------------------------------------------------------------------------------+
| |    def   **unpack**\[A\::doc:`Type <../typeclasses/type>`,B\::doc:`Type <../typeclasses/type>`\]\(t\: MTuple2\[A,B\]\)\: \(A,B\)    |
| |            Returns an unstaged scala.Tuple2 from this staged Tuple2. Shorthand for ****(x._1, x._2)****.                            |
+----------+----------------------------------------------------------------------------------------------------------------------------+


