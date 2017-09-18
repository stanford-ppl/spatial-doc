
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

.. _Mem:

Mem
====

@alias Mem

Type class used to supply evidence that type T is a local memory, potentially with multiple dimensions.


**Abstract Methods**

@table-start
trait Mem[T,C]

  /** Loads an element from `mem` at the given multi-dimensional address `indices` with enable `en`. **/
  @api def load(mem: C[T], indices: Seq[Index], en: Bit): T
  /** Stores `data` into `mem` at the given multi-dimensional address `indices` with enable `en`. **/
  @api def store(mem: C[T], indices: Seq[Index], data: T, en: Bit): MUnit
  /** Returns a `Seq` of counters which define the iteration space of the given memory `mem`. **/
  @api def iterators(mem: C[T]): Seq[Counter]

  /** Returns the parallelization annotation for this memory. **/
  def par(mem: C[T]): Option[Index]

@table-end

