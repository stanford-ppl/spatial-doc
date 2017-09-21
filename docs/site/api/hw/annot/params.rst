
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

.. _Params:

DSE Parameters
================

To help drive automated design space exploration, Spatial includes syntax for users to explicitly annotate a value as a design parameter.
These values are typically used to set the size of on-chip scratchpads like @SRAM memories or to tune the parallelization/unrolling factors of @Controllers.

Parameters have two parts: a default value that is used when compiler DSE is not run, and an associated range within which the compiler should explore.

There are two ways of creating explicit parameters in Spatial::

  param(3)

This creates an integer parameter with a default value of 3. The range is left to be determined by the compiler. 
Alternatively, the user can set the range explicitly using::

  1 (1 -> 10)
  4 (1 -> 2 -> 10)

The first example shows a parameter with a default of 1, and a range of [1, 10]. 
The second example shows a parameter with a default of 4, and a range of {1, 2, 4, 8, 10} (stride of 2, including 1). 

