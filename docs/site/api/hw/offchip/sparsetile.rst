
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

.. _DRAMSparseTile:

DRAMSparseTile
==============

@alias DRAMSparseTile

A **DRAMSparseTile** describes a sparse section of a DRAM memory which can be loaded onto the accelerator using a gather operation, or which can
be updated using a scatter operation.

--------------

**Infix methods**

@table-start
class DRAMSparseTile
  
  /** Creates a sparse transfer from the given on-chip `data` to this sparse region of main memory. **/
  @api def scatter(data: SRAM1[T]): MUnit = DRAMTransfers.sparse_transfer(this, data, isLoad = false)
  /** Creates a sparse transfer from the given on-chip `data` to this sparse region of main memory. **/
  @api def scatter(data: FIFO[T]): MUnit = DRAMTransfers.sparse_transfer_mem(this.toSparseTileMem, data, isLoad = false)
  /** Creates a sparse transfer from the given on-chip `data` to this sparse region of main memory. **/
  @api def scatter(data: FILO[T]): MUnit = DRAMTransfers.sparse_transfer_mem(this.toSparseTileMem, data, isLoad = false)

@table-end
