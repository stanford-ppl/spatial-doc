
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

.. _Order:

Order
=====

@alias Order

Type class used to supply evidence that type T has basic ordering operations defined on it.

---------------------

**Abstract Methods**

@table-start
trait Order[T]

  /** Returns true if `a` is less than `b`, false otherwise. **/
  @api def lessThan(a: T, b: T): MBoolean

  /** Returns true if `a` is less than or equal to `b`, false otherwise. **/
  @api def lessThanOrEqual(a: T, b: T): MBoolean

  /** Returns true if `a` is equal to `b`, false otherwise. **/
  @api def equal(a: T, b: T): MBoolean

@table-end

