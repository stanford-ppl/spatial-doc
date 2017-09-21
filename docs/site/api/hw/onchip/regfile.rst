
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

.. _RegFile:

RegFile
=======

@alias RegFile
@alias RegFile1
@alias RegFile2
@alias RegFile3

**RegFiles** are on-chip arrays of registers with fixed size. RegFiles currently can be specified as one or two dimensional.
Like other memories in Spatial, the contents of RegFiles are persistent across loop iterations, even when they are declared
in an inner scope.

Using the **<<=** operator, RegFiles can be used as shift registers. 2-dimensional RegFiles must select a specific
row or column before shifting using `regfile(row, \*)` or `regfile(\*, col)`, respectively.

---------------

**Static methods**

@table-start
object RegFile

/** Allocates a 1-dimensional Regfile with specified `length`. **/
@api def apply[T:Type:Bits](length: Index): RegFile1[T] = wrap(alloc[T,RegFile1](None, length.s))

/**
  * Allocates a 1-dimensional RegFile with specified `length` and initial values `inits`.
  *
  * The number of initial values must be the same as the total size of the RegFile.
  */
@api def apply[T:Type:Bits](length: Int, inits: List[T]): RegFile1[T]

/** Allocates a 2-dimensional RegFile with specified `rows` and `cols`. **/
@api def apply[T:Type:Bits](rows: Index, cols: Index): RegFile2[T] = wrap(alloc[T,RegFile2](None,rows.s,cols.s))

/**
  * Allocates a 2-dimensional RegFile with specified `rows` and `cols` and initial values `inits`.
  *
  * The number of initial values must be the same as the total size of the RegFile
  */
@api def apply[T:Type:Bits](rows: Int, cols: Int, inits: List[T]): RegFile2[T]

/** Allocates a 3-dimensional RegFile with specified dimensions. **/
@api def apply[T:Type:Bits](dim0: Index, dim1: Index, dim2: Index): RegFile3[T] = wrap(alloc[T,RegFile3](None,dim0.s, dim1.s, dim2.s))
/**
  * Allocates a 3-dimensional RegFile with specified dimensions and initial values `inits`.
  *
  * The number of initial values must be the same as the total size of the RegFile
  */
@api def apply[T:Type:Bits](dim0: Int, dim1: Int, dim2: Int, inits: List[T]): RegFile3[T]

@table-end

--------------

**Infix methods**

@table-start
abstract class RegFile[T]

/** Resets this RegFile to its initial values (or zeros, if unspecified). **/
@api def reset: MUnit = wrap(RegFile.reset(this.s, Bit.const(true)))
/** Conditionally resets this RegFile based on `cond` to its inital values (or zeros if unspecified). **/
@api def reset(cond: Bit): MUnit = wrap(RegFile.reset(this.s, cond.s))

@table-end



@table-start
class RegFile1[T] extends RegFile[T]

/** Returns the value held by the register at address `i`. **/
@api def apply(i: Index): T = wrap(RegFile.load(s, Seq(i.s), Bit.const(true)))
/** Updates the register at address `i` to hold `data`. **/
@api def update(i: Index, data: T): MUnit = MUnit(RegFile.store(s, Seq(i.s), data.s, Bit.const(true)))

/** Shifts in `data` into the first register, shifting all other values over by one position. **/
@api def <<=(data: T): MUnit = wrap(RegFile.shift_in(s, Seq(int32s(0)), 0, data.s, Bit.const(true)))

/**
  * Shifts in `data` into the first N registers, where N is the size of the given Vector.
  * All other elements are shifted by N positions.
  */
@api def <<=(data: Vector[T]): MUnit = wrap(RegFile.par_shift_in(s, Seq(int32s(0)), 0, data.s, Bit.const(true)))

/** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM1[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile1[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end


@table-start
class RegFile2[T] extends RegFile[T]

/** Returns the value held by the register at row `r`, column `c`. **/
@api def apply(r: Index, c: Index): T = wrap(RegFile.load(s, Seq(r.s, c.s), Bit.const(true)))
/** Updates the register at row `r`, column `c` to hold the given `data`. **/
@api def update(r: Index, c: Index, data: T): MUnit = MUnit(RegFile.store(s, Seq(r.s, c.s), data.s, Bit.const(true)))

/** Returns a view of row `i` of this RegFile. **/
@api def apply(i: Index, y: Wildcard) = RegFileView(s, Seq(i,lift[Int,Index](0)), 1)
/** Returns a view of column `i` of this RegFile. **/
@api def apply(y: Wildcard, i: Index) = RegFileView(s, Seq(lift[Int,Index](0),i), 0)

/** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM2[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile2[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end



@table-start
class RegFile3[T] extends RegFile[T]

/** Returns the value held by the register at the given 3-dimensional address. **/
@api def apply(dim0: Index, dim1: Index, dim2: Index): T = wrap(RegFile.load(s, Seq(dim0.s, dim1.s, dim2.s), Bit.const(true)))
/** Updates the register at the given 3-dimensional address to hold the given `data`. **/
@api def update(dim0: Index, dim1: Index, dim2: Index, data: T): MUnit = MUnit(RegFile.store(s, Seq(dim0.s, dim1.s, dim2.s), data.s, Bit.const(true)))

/** Returns a 1-dimensional view of part of this RegFile3. **/
@api def apply(i: Index, j: Index, y: Wildcard) = RegFileView(s, Seq(i,j,lift[Int,Index](0)), 2)
/** Returns a 1-dimensional view of part of this RegFile3. **/
@api def apply(i: Index, y: Wildcard, j: Index) = RegFileView(s, Seq(i,lift[Int,Index](0),j), 1)
/** Returns a 1-dimensional view of part of this RegFile3. **/
@api def apply(y: Wildcard, i: Index, j: Index) = RegFileView(s, Seq(lift[Int,Index](0),i,j), 0)

/** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
@api def load(dram: DRAM3[T]): MUnit = DRAMTransfers.dense_transfer(dram.toTile(ranges), this, isLoad = true)
/** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
@api def load(dram: DRAMDenseTile3[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)

@table-end
