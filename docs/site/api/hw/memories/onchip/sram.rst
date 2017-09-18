
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

.. _SRAM:

SRAM
====

@alias SRAM
@alias SRAM1
@alias SRAM2
@alias SRAM3
@alias SRAM4
@alias SRAM5

**SRAMs** are on-chip scratchpads with fixed size. SRAMs can be specified as multi-dimensional, but the underlying addressing
in hardware is always flat. The contents of SRAMs are persistent across loop iterations, even when they are declared in an inner scope.
Up to 5-dimensional SRAMs are currently supported.


---------------

**Static methods**

@table-start
object SRAM

/** Allocates a 1-dimensional SRAM with the specified `length`. **/
@api def apply[T:Type:Bits](length: Index): SRAM1[T] = SRAM1(SRAM.alloc[T,SRAM1](length.s))
/** Allocates a 2-dimensional SRAM with the specified number of `rows` and `cols`. **/
@api def apply[T:Type:Bits](rows: Index, cols: Index): SRAM2[T] = SRAM2(SRAM.alloc[T,SRAM2](rows.s,cols.s))
/** Allocates a 3-dimensional SRAM with the specified dimensions. **/
@api def apply[T:Type:Bits](p: Index, r: Index, c: Index): SRAM3[T] = SRAM3(SRAM.alloc[T,SRAM3](p.s,r.s,c.s))
/** Allocates a 4-dimensional SRAM with the specified dimensions. **/
@api def apply[T:Type:Bits](q: Index, p: Index, r: Index, c: Index): SRAM4[T] = SRAM4(SRAM.alloc[T,SRAM4](q.s,p.s,r.s,c.s))
/** Allocates a 5-dimensional SRAM with the specified dimensions. **/
@api def apply[T:Type:Bits](m: Index, q: Index, p: Index, r: Index, c: Index): SRAM5[T] = SRAM5(SRAM.alloc[T,SRAM5](m.s,q.s,p.s,r.s,c.s))

@table-end


--------------

**Infix methods**

@table-start
abstract class SRAM[T]

/** Returns a Scala List of the dimensions of this DRAM **/
@api def dims: List[Index] = wrap(stagedDimsOf(s)).toList 

@table-end


@table-start
class SRAM1[T] extends SRAM[T]

/** Returns the total size of this SRAM1. **/
@api def length: Index = wrap(stagedDimsOf(s).head)
/** Returns the total size of this SRAM1. **/
@api def size: Index = wrap(stagedDimsOf(s).head)

/**
  * Annotates that addresses in this SRAM1 can be read in parallel by factor `p`.
  *
  * Used when creating references to sparse regions of DRAM.
  */
@api def par(p: Index): SRAM1[T] = { val x = SRAM1(s); x.p = Some(p); x }

/** Returns the value in this SRAM1 at the given address `a`. **/
@api def apply(a: Index): T = wrap(SRAM.load(this.s, stagedDimsOf(s), Seq(a.s), ofs, Bit.const(true)))
/** Updates the value in this SRAM1 at the given address `a` to `data`. **/
@api def update(a: Index, data: T): MUnit = MUnit(SRAM.store(this.s, stagedDimsOf(s), Seq(a.s), ofs, data.s, Bit.const(true)))

/**
  * Create a sparse load from the given sparse region of DRAM to this on-chip memory.
  *
  * Elements will be gathered and stored contiguously in this memory.
  */
@api def gather(dram: DRAMSparseTile[T]): MUnit = DRAMTransfers.sparse_transfer(dram, this, isLoad = true)

/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM1[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile1[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end



@table-start
class SRAM2[T] extends SRAM[T]

/** Returns the number of rows in this SRAM2. **/
@api def rows: Index = wrap(stagedDimsOf(s).head)
/** Returns the number of columns in this SRAM2. **/
@api def cols: Index = wrap(stagedDimsOf(s).apply(1))
/** Returns the total size of this SRAM2. **/
@api def size: Index = rows * cols

/** Returns the value in this SRAM2 at the given `row` and `col`. **/
@api def apply(row: Index, col: Index): T = wrap(SRAM.load(this.s, stagedDimsOf(s), Seq(row.s,col.s), ofs, Bit.const(true)))
/** Updates the value in this SRAM2 at the given `row` and `col` to `data`. **/
@api def update(row: Index, col: Index, data: T): MUnit = MUnit(SRAM.store(this.s, stagedDimsOf(s), Seq(row.s,col.s), ofs, data.s, Bit.const(true)))
/**
  * Annotates that addresses in this SRAM2 can be read in parallel by factor `p`.
  *
  * Used when creating references to sparse regions of DRAM.
  */
@api def par(p: Index): SRAM2[T] = { val x = SRAM2(s); x.p = Some(p); x }

/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM2[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile2[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end


@table-start
class SRAM3[T] extends SRAM[T]

/** Returns the first dimension of this SRAM3. **/
@api def dim0: Index = wrap(stagedDimsOf(s).apply(0))
/** Returns the second dimension of this SRAM3. **/
@api def dim1: Index = wrap(stagedDimsOf(s).apply(1))
/** Returns the third dimension of this SRAM3. **/
@api def dim2: Index = wrap(stagedDimsOf(s).apply(2))
/** Returns the total size of this SRAM3. **/
@api def size: Index = dim0 * dim1 * dim2

/** Returns the value in this SRAM3 at the given 3-dimensional address `a`, `b`, `c`. **/
@api def apply(a: Index, b: Index, c: Index): T = wrap(SRAM.load(this.s, stagedDimsOf(s), Seq(a.s,b.s,c.s), ofs, Bit.const(true)))
/** Updates the value in this SRAM3 at the given 3-dimensional address to `data`. **/
@api def update(a: Index, b: Index, c: Index, data: T): MUnit = MUnit(SRAM.store(this.s, stagedDimsOf(s), Seq(a.s,b.s,c.s), ofs, data.s, Bit.const(true)))
/**
  * Annotates that addresses in this SRAM2 can be read in parallel by factor `p`.
  *
  * Used when creating references to sparse regions of DRAM.
  */
@api def par(p: Index): SRAM3[T] = { val x = SRAM3(s); x.p = Some(p); x }

/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM3[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile3[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end



@table-start
class SRAM4[T] extends SRAM[T]

/** Returns the first dimension of this SRAM4. **/
@api def dim0: Index = wrap(stagedDimsOf(s).apply(0))
/** Returns the second dimension of this SRAM4. **/
@api def dim1: Index = wrap(stagedDimsOf(s).apply(1))
/** Returns the third dimension of this SRAM4. **/
@api def dim2: Index = wrap(stagedDimsOf(s).apply(2))
/** Returns the fourth dimension of this SRAM4. **/
@api def dim3: Index = wrap(stagedDimsOf(s).apply(3))
/** Returns the total size of this SRAM4. **/
@api def size: Index = dim0 * dim1 * dim2 * dim3

/** Returns the value in this SRAM4 at the 4-dimensional address `a`, `b`, `c`, `d`. **/
@api def apply(a: Index, b: Index, c: Index, d: Index): T = wrap(SRAM.load(this.s, stagedDimsOf(s), Seq(a.s,b.s,c.s,d.s), ofs, Bit.const(true)))
/** Updates the value in this SRAM4 at the 4-dimensional address to `data`. **/
@api def update(a: Index, b: Index, c: Index, d: Index, data: T): MUnit = MUnit(SRAM.store(this.s, stagedDimsOf(s), Seq(a.s,b.s,c.s,d.s), ofs, data.s, Bit.const(true)))

/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM4[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile4[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end



@table-start
class SRAM5[T] extends SRAM[T]

/** Returns the first dimension of this SRAM5. **/
@api def dim0: Index = wrap(stagedDimsOf(s).apply(0))
/** Returns the second dimension of this SRAM5. **/
@api def dim1: Index = wrap(stagedDimsOf(s).apply(1))
/** Returns the third dimension of this SRAM5. **/
@api def dim2: Index = wrap(stagedDimsOf(s).apply(2))
/** Returns the fourth dimension of this SRAM5. **/
@api def dim3: Index = wrap(stagedDimsOf(s).apply(3))
/** Returns the fifth dimension of this SRAM5. **/
@api def dim4: Index = wrap(stagedDimsOf(s).apply(4))
/** Returns the total size of this SRAM5. **/
@api def size: Index = dim0 * dim1 * dim2 * dim3 * dim4

/** Returns the value in this SRAM5 at the 5-dimensional address `a`, `b`, `c`, `d`, `e`. **/
@api def apply(a: Index, b: Index, c: Index, d: Index, e: Index): T = wrap(SRAM.load(this.s, stagedDimsOf(s), Seq(a.s,b.s,c.s,d.s,e.s), ofs, Bit.const(true)))
/** Updates the value in this SRAM5 at the 5-dimensional address to `data`. **/
@api def update(a: Index, b: Index, c: Index, d: Index, e: Index, data: T): MUnit = MUnit(SRAM.store(this.s, stagedDimsOf(s), Seq(a.s,b.s,c.s,d.s,e.s), ofs, data.s, Bit.const(true)))

/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM5[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Create a dense, burst load from the given region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile5[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end