
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

.. _FILO:

FILO
====

@alias FILO

**FILOs** (first-in, last-out) are on-chip scratchpads with additional control logic for address-less enqueue/dequeue operations.
FILOs acts as a Stack, reversing the order of elements it receives. A FILO's **pop** operation always returns the most
recently **pushed** element.

---------------

**Static methods**

@table-start
object FILO

  /**
    * Creates a FILO with given `depth`.
    *
    * Depth must be a statically determinable signed integer.
    **/
  @api def apply[T:Type:Bits](size: Index): FILO[T] = FILO(FILO.alloc[T](size.s))

@table-end


--------------

**Infix methods**

@table-start
class FILO[T]

  /**
    * Annotates that addresses in this FIFO can be read in parallel by factor `p`.
    *
    * Used when creating references to sparse regions of DRAM.
    */
  @api def par(p: Index): FILO[T] = { val x = FILO(s); x.p = Some(p); x }

  /** Returns true when this FILO contains no elements, false otherwise. **/
  @api def empty(): Bit = wrap(FILO.is_empty(this.s))
  /** Returns true when this FILO cannot fit any more elements, false otherwise. **/
  @api def full(): Bit = wrap(FILO.is_full(this.s))
  /** Returns true when this FILO contains exactly one element, false otherwise. **/
  @api def almostEmpty(): Bit = wrap(FILO.is_almost_empty(this.s))
  /** Returns true when this FILO can fit exactly one more element, false otherwise. **/
  @api def almostFull(): Bit = wrap(FILO.is_almost_full(this.s))
  /** Returns the number of elements currently in this FILO. **/
  @api def numel(): Index = wrap(FILO.numel(this.s))

  /** Creates a push (write) port to this FILO of `data`. **/
  @api def push(data: T): MUnit = this.push(data, true)
  /** Creates a conditional push (write) port to this FILO of `data` enabled by `en`. **/
  @api def push(data: T, en: Bit): MUnit = MUnit(FILO.push(this.s, data.s, en.s))

  /** Creates a pop (destructive read) port to this FILO. **/
  @api def pop(): T = this.pop(true)
  /** Creates a conditional pop (destructive read) port to this FILO enabled by `en`. **/
  @api def pop(en: Bit): T = wrap(FILO.pop(this.s, en.s))

  /** Creates a non-destructive read port to this FILO. **/
  @api def peek(): T = wrap(FILO.peek(this.s))

  /** Creates a dense, burst load from the specified region of DRAM to this on-chip memory. **/
  @api def load(dram: DRAMDenseTile1[T]): MUnit = DRAMTransfers.dense_transfer(dram, this, isLoad = true)
  /** Creates a sparse load from the specified sparse region of DRAM to this on-chip memory. **/
  @api def gather(dram: DRAMSparseTile[T]): MUnit = DRAMTransfers.sparse_transfer_mem(dram.toSparseTileMem, this, isLoad = true)

@table-end