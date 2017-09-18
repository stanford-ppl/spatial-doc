
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

.. _StreamIn:

StreamIn
========

@alias StreamIn

**StreamIn** defines a hardware bus used to receive streaming data from outside of the FPGA.
StreamIns may not be written to. For streaming outputs, use @StreamOut.
StreamIns are specified using a @Bits - based type and a target @Bus.

In Spatial, StreamIns are specified outside the Accel block, in host code.


-----------------

**Static methods**

@table-start
object StreamIn

  /** Creates a StreamIn of type T connected to the specified `bus` pins. **/
  @api def apply[T:Type:Bits](bus: Bus): StreamIn[T]

@table-end

-------------

**Infix methods**

@table-start
class StreamIn[T]

  /** 
    * Returns the current value of this StreamIn. 
    * Equivalent in hardware to connecting a wire to the StreamIn's bus.
    **/
  @api def value: T

@table-end


--------------

**Implicit methods**

@table-start
NoHeading

/** Implicitly creates a wire connecting to the StreamIn's bus. **/
@api def readStream[T](stream: StreamIn[T]): T

@table-end
