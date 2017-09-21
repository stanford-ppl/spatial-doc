
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

.. _bound:

Bound
=======

The **bound** annotation provides a quick, simple way of annotating the approximate sizes of data consumed by the application.
This is primarily useful in automated design space exploration, where the ideal parallelization factor or memory size may depend on the
size of the data the accelerator will be processing.

The **bound** annotation is not a guarantee, so no operation optimizations are done as a result of this annotation. It is purely used
to help drive runtime estimation.

While **bound** can be used outside of the `Accel` scopes, it currently has no use except in hardware tuning.


To set the bound of a given value, use the syntax::

  bound(x) = 64

This tells the compiler to expect the value **x** to be approximately 64 at runtime.
