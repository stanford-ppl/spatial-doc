
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

.. _DRAMDenseTile:

DRAMDenseTile
=============

@alias DRAMDenseTile
@alias DRAMDenseTile1
@alias DRAMDenseTile2
@alias DRAMDenseTile3
@alias DRAMDenseTile4
@alias DRAMDenseTile5

A **DRAMDenseTile** describes a continguous slice of a :doc:`dram` memory's address space which can be loaded onto the
accelerator for processing or which can be updated with results once FPGA computation is complete.

----------------

**Infix methods**

@table-start
class DRAMDenseTile

@table-end



@table-start
class DRAMDenseTile1[T] extends DRAMDenseTile[T]

  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: SRAM1[T]): MUnit = DRAMTransfers.dense_transfer(this, data, isLoad = false)
  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: FIFO[T]): MUnit = DRAMTransfers.dense_transfer(this, data, isLoad = false)
  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: FILO[T]): MUnit = DRAMTransfers.dense_transfer(this, data, isLoad = false)
  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: RegFile1[T]): MUnit = DRAMTransfers.dense_transfer(this, data, isLoad = false)

@table-end


@table-start
class DRAMDenseTile2[T] extends DRAMDenseTile[T]

  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: SRAM2[T]): MUnit    = DRAMTransfers.dense_transfer(this, data, isLoad = false)
  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: RegFile2[T]): MUnit = DRAMTransfers.dense_transfer(this, data, isLoad = false)

@table-end


@table-start
class DRAMDenseTile3[T] extends DRAMDenseTile[T]

  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: SRAM3[T]): MUnit   = DRAMTransfers.dense_transfer(this, data, isLoad = false)

@table-end



@table-start
class DRAMDenseTile4[T] extends DRAMDenseTile[T]

  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: SRAM4[T]): MUnit   = DRAMTransfers.dense_transfer(this, data, isLoad = false)

@table-end


@table-start
class DRAMDenseTile5[T] extends DRAMDenseTile[T]

  /** Creates a dense, burst transfer from the given on-chip `data` to this tiles's region of main memory. **/
  @api def store(data: SRAM5[T]): MUnit   = DRAMTransfers.dense_transfer(this, data, isLoad = false)

@table-end
