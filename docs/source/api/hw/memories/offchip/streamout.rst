
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
StreamOuts may not be read from. For streaming inputs, use @StreamIn.
StreamOuts are specified using a @Bits`- based type and a target :doc:`bus`.

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


+----------+----------------------------------------------------------------------------------------------------+
| class      **StreamOut**\[T\]                                                                                 |
+==========+====================================================================================================+
| |    def   **\:\=**\(data\: T\)\: :doc:`Unit <../../../common/unit>` = this \:                                |
| |            Connect the given **data** to this StreamOut.                                                    |
+----------+----------------------------------------------------------------------------------------------------+
| |    def   **\:\=**\(data\: T, en\: :doc:`Bit <../../../common/bit>`\)\: :doc:`Unit <../../../common/unit>`   |
| |            Connect the given **data** to this StreamOut with enable **en**.                                 |
+----------+----------------------------------------------------------------------------------------------------+


//@table-start
//class StreamOut[T]

//  /** Connect the given `data` to this StreamOut. **/
//  @api def :=(data: T): MUnit = this := (data, true)
//  /** Connect the given `data` to this StreamOut with enable `en`. **/
//  @api def :=(data: T, en: Bit): MUnit = MUnit(StreamOut.write(s, data.s, en.s))

// @table-end
