
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

.. _DRAM:

DRAM
====

@alias DRAM
@alias DRAM1
@alias DRAM2
@alias DRAM3
@alias DRAM4
@alias DRAM5

DRAMs are pointers to locations in the accelerator's main memory comprising dense multi-dimensional arrays. They are the primary form of communication
of data between the host and the accelerator. Data may be loaded to and from the accelerator in contiguous chunks (Tiles),
or by bulk scatter and gather operations (SparseTiles).

Up to to 5-dimensional DRAMs are currently supported. Dimensionality of a DRAM instance is encoded by the subclass of DRAM. DRAM1, for instance represents a 1-dimensional DRAM.  

A dense :doc:`tile` can be created from a DRAM either using address range selection or by implicit conversion.
When a Tile is created implicitly, it has the same address space as the entire original DRAM.

In Spatial, DRAMs are specified outside the Accel scope in the host code.

----------------

**Static Methods**

@table-start
object DRAM

 /**
    * Creates a reference to a 1-dimensional array in main memory with the given length.
    * Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.
    */
  @api def apply[T:Type:Bits](length: Index): DRAM1[T] = DRAM1(alloc[T,DRAM1](length.s))
  /**
    * Creates a reference to a 2-dimensional array in main memory with given rows and cols.
    * Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.
    */
  @api def apply[T:Type:Bits](d1: Index, d2: Index): DRAM2[T] = DRAM2(alloc[T,DRAM2](d1.s,d2.s))
  /**
    * Creates a reference to a 3-dimensional array in main memory with given dimensions.
    *
    * Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.
    */
  @api def apply[T:Type:Bits](d1: Index, d2: Index, d3: Index): DRAM3[T] = DRAM3(alloc[T,DRAM3](d1.s,d2.s,d3.s))
  /**
    * Creates a reference to a 4-dimensional array in main memory with given dimensions.
    * Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.
    */
  @api def apply[T:Type:Bits](d1: Index, d2: Index, d3: Index, d4: Index): DRAM4[T] = DRAM4(alloc[T,DRAM4](d1.s,d2.s,d3.s,d4.s))
  /**
    * Creates a reference to a 5-dimensional array in main memory with given dimensions.
    * Dimensions of a DRAM should be statically calculable functions of constants, parameters, and ArgIns.
    */
  @api def apply[T:Type:Bits](d1: Index, d2: Index, d3: Index, d4: Index, d5: Index): DRAM5[T] = DRAM5(alloc[T,DRAM5](d1.s,d2.s,d3.s,d4.s,d5.s))

@table-end


--------------

**Infix methods**

@table-start
abstract class DRAM[T]

/** Returns the 64-bit physical address in main memory of the start of this DRAM **/
@api def address: Int64
/** Returns a Scala List of the dimensions of this DRAM **/
@api def dims: List[Index] = wrap(stagedDimsOf(s)).toList

@table-end


@table-start
class DRAM1[T] extends DRAM[T]

/** Returns the total number of elements in this DRAM1. **/
@api def size: Index = wrap(stagedDimsOf(s).head)
/** Returns the total number of elements in this DRAM1. **/
@api def length: Index = wrap(stagedDimsOf(s).head)

/** Creates a reference to a dense region of this DRAM1 for creating burst loads and stores. **/
@api def apply(range: Range): DRAMDenseTile1[T] = DRAMDenseTile1(this.s, Seq(range))

/**
  * Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers
  * using all addresses in `addrs`.
  **/
@api def apply(addrs: SRAM1[Index]): DRAMSparseTile[T] = this.apply(addrs, wrap(stagedDimsOf(addrs.s).head))
/**
  * Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers
  * using the first `size` addresses in `addrs`.
  */
@api def apply(addrs: SRAM1[Index], size: Index): DRAMSparseTile[T] = DRAMSparseTile(this.s, addrs, size)

// TODO: Should this be sizeOf(addrs) or addrs.numel?
/**
  * Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers
  * using all addresses in `addrs`.
  **/
@api def apply(addrs: FIFO[Index]): DRAMSparseTile[T] = this.apply(addrs, addrs.numel())
/**
  * Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers
  * using the first `size` addresses in `addrs`.
  */
@api def apply(addrs: FIFO[Index], size: Index): DRAMSparseTile[T] = DRAMSparseTileMem(this.s, addrs, size)
/**
  * Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers
  * using all addresses in `addrs`.
  **/
@api def apply(addrs: FILO[Index]): DRAMSparseTile[T] = this.apply(addrs, addrs.numel())
/**
  * Creates a reference to a sparse region of this DRAM1 for use in scatter and gather transfers
  * using the first `size` addresses in `addrs`.
  */
@api def apply(addrs: FILO[Index], size: Index): DRAMSparseTile[T] = DRAMSparseTileMem(this.s, addrs, size)

/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(data: SRAM1[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(data.ranges), data, isLoad = false)
/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(data: FIFO[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(data.ranges), data, isLoad = false)
/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(data: FILO[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(data.ranges), data, isLoad = false)
/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(data: RegFile1[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(data.ranges), data, isLoad = false)

@table-end


@table-start
class DRAM2[T] extends DRAM[T]

/** Returns the number of rows in this DRAM2 **/
@api def rows: Index = wrap(stagedDimsOf(s).apply(0))
/** Returns the number of columns in this DRAM2 **/
@api def cols: Index = wrap(stagedDimsOf(s).apply(1))
/** Returns the total number of elements in this DRAM2 **/
@api def size: Index = rows * cols

/** Creates a reference to a dense slice of a row of this DRAM2 for creating burst loads and stores. **/
@api def apply(row: Index, cols: Range) = DRAMDenseTile1(this.s, Seq(row.toRange, cols))
/** Creates a reference to a dense slice of a column of this DRAM2 for creating burst loads and stores. **/
@api def apply(rows: Range, col: Index) = DRAMDenseTile1(this.s, Seq(rows, col.toRange))
/** Creates a reference to a 2-dimensional, dense region of this DRAM2 for creating burst loads and stores. **/
@api def apply(rows: Range, cols: Range) = DRAMDenseTile2(this.s, Seq(rows, cols))

/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(sram: SRAM2[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(sram.ranges), sram, isLoad = false)
/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(regs: RegFile2[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(regs.ranges), regs, isLoad = false)

@table-end



@table-start
class DRAM3[T] extends DRAM[T]

/** Returns the first dimension for this DRAM3. **/
  @api def dim0: Index = wrap(stagedDimsOf(s).apply(0))
  /** Returns the second dimension for this DRAM3. **/
  @api def dim1: Index = wrap(stagedDimsOf(s).apply(1))
  /** Returns the third dimension for this DRAM3. **/
  @api def dim2: Index = wrap(stagedDimsOf(s).apply(2))
  /** Returns the total number of elements in this DRAM3. **/
  @api def size: Index = dim0 * dim1 * dim2

  /** Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Index, r: Index, c: Range) = DRAMDenseTile1(this.s, Seq(p.toRange, r.toRange, c))
  /** Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Index, r: Range, c: Index) = DRAMDenseTile1(this.s, Seq(p.toRange, r, c.toRange))
  /** Creates a reference to a 1-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Range, r: Index, c: Index) = DRAMDenseTile1(this.s, Seq(p, r.toRange, c.toRange))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Index, r: Range, c: Range) = DRAMDenseTile2(this.s, Seq(p.toRange, r, c))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Range, r: Index, c: Range) = DRAMDenseTile2(this.s, Seq(p, r.toRange, c))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Range, r: Range, c: Index) = DRAMDenseTile2(this.s, Seq(p, r, c.toRange))
  /** Creates a reference to a 3-dimensional, dense region of this DRAM3 for creating burst loads and stores. **/
  @api def apply(p: Range, r: Range, c: Range) = DRAMDenseTile3(this.s, Seq(p, r, c))

  /** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
  @api def store(sram: SRAM3[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(sram.ranges), sram, isLoad = false)

@table-end



@table-start
class DRAM4[T] extends DRAM[T]


  /** Returns the first dimension of this DRAM4. **/
  @api def dim0: Index = wrap(stagedDimsOf(s).apply(0))
  /** Returns the second dimension of this DRAM4. **/
  @api def dim1: Index = wrap(stagedDimsOf(s).apply(1))
  /** Returns the third dimension of this DRAM4. **/
  @api def dim2: Index = wrap(stagedDimsOf(s).apply(2))
  /** Returns the fourth dimension of this DRAM4. **/
  @api def dim3: Index = wrap(stagedDimsOf(s).apply(3))
  /** Returns the total number of elements in this DRAM4. **/
  @api def size: Index = dim0 * dim1 * dim2 * dim3

  /** Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Index, r: Index, c: Range) = DRAMDenseTile1(this.s, Seq(q.toRange, p.toRange, r.toRange, c))
  /** Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Index, r: Range, c: Index) = DRAMDenseTile1(this.s, Seq(q.toRange, p.toRange, r, c.toRange))
  /** Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Range, r: Index, c: Index) = DRAMDenseTile1(this.s, Seq(q.toRange, p, r.toRange, c.toRange))
  /** Creates a reference to a 1-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Index, r: Index, c: Index) = DRAMDenseTile1(this.s, Seq(q, p.toRange, r.toRange, c.toRange))

  /** Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Index, r: Range, c: Range) = DRAMDenseTile2(this.s, Seq(q.toRange, p.toRange, r, c))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Index, r: Index, c: Range) = DRAMDenseTile2(this.s, Seq(q, p.toRange, r.toRange, c))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Range, r: Index, c: Index) = DRAMDenseTile2(this.s, Seq(q, p, r.toRange, c.toRange))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Range, r: Index, c: Range) = DRAMDenseTile2(this.s, Seq(q.toRange, p, r.toRange, c))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Index, r: Range, c: Index) = DRAMDenseTile2(this.s, Seq(q, p.toRange, r, c.toRange))
  /** Creates a reference to a 2-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Range, r: Range, c: Index) = DRAMDenseTile2(this.s, Seq(q.toRange, p, r, c.toRange))

  /** Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Index, p: Range, r: Range, c: Range) = DRAMDenseTile3(this.s, Seq(q.toRange, p, r, c))
  /** Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Index, r: Range, c: Range) = DRAMDenseTile3(this.s, Seq(q, p.toRange, r, c))
  /** Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Range, r: Index, c: Range) = DRAMDenseTile3(this.s, Seq(q, p, r.toRange, c))
  /** Creates a reference to a 3-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Range, r: Range, c: Index) = DRAMDenseTile3(this.s, Seq(q, p, r, c.toRange))

  /** Creates a reference to a 4-dimensional, dense region of this DRAM4 for creating burst loads and stores. **/
  @api def apply(q: Range, p: Range, r: Range, c: Range) = DRAMDenseTile4(this.s, Seq(q, p, r, c))

  /** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
  @api def store(data: SRAM4[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(data.ranges), data, isLoad = false)

@table-end



@table-start
class DRAM5[T] extends DRAM[T]

/** Returns the first dimension of this DRAM5. **/
@api def dim0: Index = wrap(stagedDimsOf(s).apply(0))
/** Returns the second dimension of this DRAM5. **/
@api def dim1: Index = wrap(stagedDimsOf(s).apply(1))
/** Returns the third dimension of this DRAM5. **/
@api def dim2: Index = wrap(stagedDimsOf(s).apply(2))
/** Returns the fourth dimension of this DRAM5. **/
@api def dim3: Index = wrap(stagedDimsOf(s).apply(3))
/** Returns the fifth dimension of this DRAM5. **/
@api def dim4: Index = wrap(stagedDimsOf(s).apply(4))
/** Returns the total number of elements in this DRAM5. **/
@api def size: Index = dim0 * dim1 * dim2 * dim3 * dim4

// I'm not getting carried away, you're getting carried away! By the amazingness of this code!
/** Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Index, r: Index, c: Range): DRAMDenseTile1[T] = DRAMDenseTile1(this.s, Seq(x.toRange, q.toRange, p.toRange, r.toRange, c))
/** Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Index, r: Range, c: Index): DRAMDenseTile1[T] = DRAMDenseTile1(this.s, Seq(x.toRange, q.toRange, p.toRange, r, c.toRange))
/** Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Range, r: Index, c: Index): DRAMDenseTile1[T] = DRAMDenseTile1(this.s, Seq(x.toRange, q.toRange, p, r.toRange, c.toRange))
/** Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Index, r: Index, c: Index): DRAMDenseTile1[T] = DRAMDenseTile1(this.s, Seq(x.toRange, q, p.toRange, r.toRange, c.toRange))
/** Creates a reference to a 1-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Index, r: Index, c: Index): DRAMDenseTile1[T] = DRAMDenseTile1(this.s, Seq(x, q.toRange, p.toRange, r.toRange, c.toRange))

/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Index, r: Range, c: Range): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x.toRange, q.toRange, p.toRange, r, c))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Range, r: Index, c: Range): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x.toRange, q.toRange, p, r.toRange, c))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Range, r: Range, c: Index): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x.toRange, q.toRange, p, r, c.toRange))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Index, r: Index, c: Range): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x.toRange, q, p.toRange, r.toRange, c))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Index, r: Range, c: Index): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x.toRange, q, p.toRange, r, c.toRange))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Range, r: Index, c: Index): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x.toRange, q, p, r.toRange, c.toRange))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Index, r: Index, c: Range): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x, q.toRange, p.toRange, r.toRange, c))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Index, r: Range, c: Index): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x, q.toRange, p.toRange, r, c.toRange))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Range, r: Index, c: Index): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x, q.toRange, p, r.toRange, c.toRange))
/** Creates a reference to a 2-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Index, r: Index, c: Index): DRAMDenseTile2[T] = DRAMDenseTile2(this.s, Seq(x, q, p.toRange, r.toRange, c.toRange))

/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Index, p: Range, r: Range, c: Range): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x.toRange, q.toRange, p, r, c))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Index, r: Range, c: Range): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x.toRange, q, p.toRange, r, c))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Range, r: Index, c: Range): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x.toRange, q, p, r.toRange, c))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Range, r: Range, c: Index): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x.toRange, q, p, r, c.toRange))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Index, r: Range, c: Range): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x, q.toRange, p.toRange, r, c))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Range, r: Index, c: Range): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x, q.toRange, p, r.toRange, c))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Range, r: Range, c: Index): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x, q.toRange, p, r, c.toRange))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Index, r: Index, c: Range): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x, q, p.toRange, r.toRange, c))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Index, r: Range, c: Index): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x, q, p.toRange, r, c.toRange))
/** Creates a reference to a 3-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Range, r: Index, c: Index): DRAMDenseTile3[T] = DRAMDenseTile3(this.s, Seq(x, q, p, r.toRange, c.toRange))

/** Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Index, q: Range, p: Range, r: Range, c: Range): DRAMDenseTile4[T] = DRAMDenseTile4(this.s, Seq(x.toRange, q, p, r, c))
/** Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Index, p: Range, r: Range, c: Range): DRAMDenseTile4[T] = DRAMDenseTile4(this.s, Seq(x, q.toRange, p, r, c))
/** Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Index, r: Range, c: Range): DRAMDenseTile4[T] = DRAMDenseTile4(this.s, Seq(x, q, p.toRange, r, c))
/** Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Range, r: Index, c: Range): DRAMDenseTile4[T] = DRAMDenseTile4(this.s, Seq(x, q, p, r.toRange, c))
/** Creates a reference to a 4-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Range, r: Range, c: Index): DRAMDenseTile4[T] = DRAMDenseTile4(this.s, Seq(x, q, p, r, c.toRange))

/** Creates a reference to a 5-dimensional, dense region of this DRAM5 for creating burst loads and stores. **/
@api def apply(x: Range, q: Range, p: Range, r: Range, c: Range): DRAMDenseTile5[T] = DRAMDenseTile5(this.s, Seq(x, q, p, r, c))

/** Creates a dense, burst transfer from the given on-chip `data` to this DRAM's region of main memory. **/
@api def store(data: SRAM5[T]): MUnit = DRAMTransfers.dense_transfer(this.toTile(data.ranges), data, isLoad = false)


@table-end

--------------

**Implicit methods**

@table-start
NoHeading

  /** Implicitly converts a DRAM1 to a DRAMDenseTile1 with the same address space. **/
  @api def createTile1[T](dram: DRAM1[T]): DRAMDenseTile1[T]

  /** Implicitly converts a DRAM2 to a DRAMDenseTile2 with the same address space. **/
  @api def createTile2[T](dram: DRAM2[T]): DRAMDenseTile2[T]

  /** Implicitly converts a DRAM3 to a DRAMDenseTile3 with the same address space. **/
  @api def createTile3[T](dram: DRAM3[T]): DRAMDenseTile3[T]

  /** Implicitly converts a DRAM4 to a DRAMDenseTile4 with the same address space. **/
  @api def createTile4[T](dram: DRAM4[T]): DRAMDenseTile4[T]

  /** Implicitly converts a DRAM5 to a DRAMDenseTile5 with the same address space. **/
  @api def createTile2[T](dram: DRAM5[T]): DRAMDenseTile5[T]

@table-end
  