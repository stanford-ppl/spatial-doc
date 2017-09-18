
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

.. _BufferedOut:

BufferedOut
===========

@alias BufferedOut

**BufferedOut** defines an addressable output stream from the accelerator, implemented in hardware as an SRAM buffer
which is asynchronously (and implicitly) used to drive a set of output pins.

BufferedOut is similar in functionality to :doc:`streamout`, but allows address and frame-based writing.
This is useful, for example, in video processing, when video needs to be processed on a frame-by-frame basis rather
than a pixel-by-pixel basis.

In Spatial, BufferedOuts are specified outside the Accel block, in host code.


-----------------

**Static methods**

@table-start
object BufferedOut

  /**
    * Creates a BufferedOut of type T connected to the specified bus.
    * The size of the buffer is currently fixed at 240 x 320 elements. 
    */
  @api def apply[T:Type:Bits](bus: Bus): BufferedOut[T]

@table-end


-------------

**Infix methods**

@table-start
class BufferedOut[T]

  /** Write `data` to the given two dimensional address. **/
  @api def update(row: Index, col: Index, data: T): MUnit = MUnit(BufferedOut.write(s, data.s, Seq(row.s,col.s), Bit.const(true)))

@table-end
