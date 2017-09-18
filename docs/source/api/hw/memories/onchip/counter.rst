
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

.. _Counter:

Counter
=======


Counter is a single hardware counter with an associated start (inclusive), end (exclusive), step size, and parallelization factor.
By default, the parallelization factor is assumed to be a design parameter. Counters can be chained together using
CounterChain, but this is typically done implicitly when creating controllers.

It is generally recommended to create a :doc:`Range <../../../common/range>` and allow the compiler to implicitly convert this to a Counter,
as Range provides slightly better syntax sugar.

----------------

**Static Methods**

+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **Counter**                                                                                                                                                                                                                  |
+==========+==============================================================================================================================================================================================================================+
| |    def   **apply**\(end\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`Counter <counter>`                                                                                                                                           |
| |            Creates a Counter with start of 0, given **end**, and step size of 1.                                                                                                                                                      |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(start\: :doc:`Index <../../../common/fixpt>`, end\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`Counter <counter>`                                                                                             |
| |            Creates a Counter with given **start** and **end**, and step size of 1.                                                                                                                                                    |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(start\: :doc:`Index <../../../common/fixpt>`, end\: :doc:`Index <../../../common/fixpt>`, step\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`Counter <counter>`                                                |
| |            Creates a Counter with given **start**, **end**, and **step** size.                                                                                                                                                        |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\(start\: :doc:`Index <../../../common/fixpt>`, end\: :doc:`Index <../../../common/fixpt>`, step\: :doc:`Index <../../../common/fixpt>`, par\: :doc:`Index <../../../common/fixpt>`\)\: :doc:`Counter <counter>`    |
| |            Creates a Counter with given **start**, **end**, **step**, and **par** parallelization factor.                                                                                                                             |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

