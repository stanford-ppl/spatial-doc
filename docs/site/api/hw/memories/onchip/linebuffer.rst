
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

.. _LineBuffer:

LineBuffer
==========

@alias LineBuffer

**LineBuffers** are two dimensional, on-chip scratchpads with a fixed size.
LineBuffers act as a FIFO on input, supporting only queued writes, but support addressed reading like SRAMs.
For writes, the current row buffer and column is maintained using an internal counter.
This counter resets every time the controller containing the enqueue completes execution.

The contents of LineBuffers are persistent across loop iterations, even when they are declared in an inner scope.
Up to 5-dimensional LineBuffers are currently supported.


---------------

**Static methods**

@table-start
object LineBuffer

  /** Allocates a LineBuffer with given `rows` and `cols`.
    * The contents of this LineBuffer are initially undefined.
    * `rows` and `cols` must be statically determinable integers.
    */
  @api def apply[T:Type:Bits](rows: Index, cols: Index): LineBuffer[T] = wrap(alloc[T](rows.s, cols.s, int32s(1)))

  /**
    * Allocates a LineBuffer with given number of `rows` and `cols`, and with given `stride`.
    * The contents of this LineBuffer are initially undefined.
    * `rows`, `cols`, and `stride` must be statically determinable integers.
    */
  @api def strided[T:Type:Bits](rows: Index, cols: Index, stride: Index): LineBuffer[T] = wrap(alloc[T](rows.s, cols.s, stride.s))

@table-end


--------------

**Infix methods**

@table-start
class LineBuffer[T]

 /** Creates a load port to this LineBuffer at the given `row` and `col`. **/
  @api def apply(row: Index, col: Index): T = wrap(LineBuffer.load(s, row.s, col.s, Bit.const(true)))

  /** Creates a vectorized load port to this LineBuffer at the given `row` and `cols`. **/
  @api def apply(row: Index, cols: Range)(implicit ctx: SrcCtx): Vector[T]
 
  /** Creates a vectorized load port to this LineBuffer at the given `rows` and `col`. **/
  @api def apply(rows: Range, col: Index)(implicit ctx: SrcCtx): Vector[T] 
  
  /** Creates an enqueue (write) port of `data` to this LineBuffer. **/
  @api def enq(data: T): MUnit = MUnit(LineBuffer.enq(this.s, data.s, Bit.const(true)))
  /** Creates an enqueue (write) port of `data` to this LineBuffer, enabled by `en`. **/
  @api def enq(data: T, en: Bit): MUnit = MUnit(LineBuffer.enq(this.s, data.s, en.s))


  /** Creates a dense transfer from the given region of DRAM to this on-chip memory. **/
  @api def load(dram: DRAMDenseTile1[T])(implicit ctx: SrcCtx): MUnit

@table-end