
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

.. _StreamOut:

StreamOut
=========

@alias StreamOut

**StreamOut** defines a hardware bus used to output streaming data from the FPGA.
StreamOuts may not be read from. For streaming inputs, use :doc:`streamin`.
StreamOuts are specified using a :doc:`../../typeclasses/bits`-based type and a target :doc:`bus`.

In Spatial, StreamOuts are specified outside the Accel block, in host code.


-----------------

**Static methods**

@table-start
object StreamOut

  /** Creates a StreamOut of type T connected to the specified target bus pins. **/
  @api def apply[T:Type:Bits](bus: Bus): StreamOut[T]

@table-end


-------------

**Infix methods**

@table-start
class StreamOut[T]

  /** Connect the given `data` to this StreamOut. **/
  @api def :=(data: T): MUnit = this := (data, true)
  /** Connect the given `data` to this StreamOut with enable `en`. **/
  @api def :=(data: T, en: Bit): MUnit = MUnit(StreamOut.write(s, data.s, en.s))

@table-end
