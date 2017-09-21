
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

.. _FIFO:

FIFO
====

@alias FIFO

**FIFOs** (first-in, first-out) are on-chip scratchpads with additional control logic for address-less enqueue/dequeue operations.
FIFOs preserve the ordering between elements as they are enqueued. A FIFO's **deq** operation always returns the oldest
**enqueued** element which has not yet been dequeued.

---------------

**Static methods**

@table-start
object FIFO

  /** Creates a FIFO with given `depth`. 
    *
    * Depth must be a statically determinable signed integer.
    **/
  @api def apply[T:Type:Bits](depth: Int): FIFO[T]

@table-end

--------------

**Infix methods**

@table-start
class FIFO[T]

  /**
    * Annotates that addresses in this FIFO can be read in parallel by factor `p`.
    *
    * Used when creating references to sparse regions of DRAM.
    */
  @api def par(p: Index): FIFO[T] = { val x = FIFO(s); x.p = Some(p); x }

  /** Returns true when this FIFO contains no elements, false otherwise. **/
  @api def empty(): Bit = wrap(FIFO.is_empty(this.s))
  /** Returns true when this FIFO cannot fit any more elements, false otherwise. **/
  @api def full(): Bit = wrap(FIFO.is_full(this.s))
  /** Returns true when this FIFO contains exactly one element, false otherwise. **/
  @api def almostEmpty(): Bit = wrap(FIFO.is_almost_empty(this.s))
  /** Returns true when this FIFO can fit exactly one more element, false otherwise. **/
  @api def almostFull(): Bit = wrap(FIFO.is_almost_full(this.s))
  /** Returns the number of elements currently in this FIFO. **/
  @api def numel(): Index = wrap(FIFO.numel(this.s))

  /** Creates an enqueue (write) port of `data` to this FIFO. **/
  @api def enq(data: T): MUnit = this.enq(data, true)
  /** Creates an enqueue (write) port of `data` to this FIFO, enabled by `en`. **/
  @api def enq(data: T, en: Bit): MUnit = MUnit(FIFO.enq(this.s, data.s, en.s))

  /** Creates a dequeue (destructive read) port from this FIFO. **/
  @api def deq(): T = this.deq(true)
  /** Creates a dequeue (destructive read) port from this FIFO, enabled by `en`. **/
  @api def deq(en: Bit): T = wrap(FIFO.deq(this.s, en.s))

  /** Creates a non-destructive read port from this FIFO. **/
  @api def peek(): T = wrap(FIFO.peek(this.s))

  /** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
  @api def load(dram: DRAMDenseTile1[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)
  /** Creates a sparse load from the specified sparse region of DRAM to this on-chip memory. **/
  @api def gather(dram: DRAMSparseTile[T]): MUnit = DRAMTransfers.sparse_transfer_mem(dram.toSparseTileMem, this, isLoad = true)


@table-end
