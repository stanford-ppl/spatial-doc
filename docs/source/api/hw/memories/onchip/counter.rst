
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

@alias Counter

Counter is a single hardware counter with an associated start (inclusive), end (exclusive), step size, and parallelization factor.
By default, the parallelization factor is assumed to be a design parameter. Counters can be chained together using
CounterChain, but this is typically done implicitly when creating controllers.

It is generally recommended to create a @Range and allow the compiler to implicitly convert this to a Counter,
as Range provides slightly better syntax sugar.

----------------

**Static Methods**

@table-start
object Counter

  /** Creates a Counter with start of 0, given `end`, and step size of 1. **/
  @api def apply(end: Index): Counter = counter(0, end, 1, Some( wrap(intParam(1)) ))
  /** Creates a Counter with given `start` and `end`, and step size of 1. **/
  @api def apply(start: Index, end: Index): Counter = counter(start, end, 1, Some(wrap(intParam(1))))
  /** Creates a Counter with given `start`, `end`, and `step` size. **/
  @api def apply(start: Index, end: Index, step: Index): Counter = counter(start, end, step, Some(wrap(intParam(1))))
  /** Creates a Counter with given `start`, `end`, `step`, and `par` parallelization factor. **/
  @api def apply(start: Index, end: Index, step: Index, par: Index): Counter = counter(start, end, step, Some(par))

@table-end
