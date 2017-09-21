
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


**StreamIn** defines a hardware bus used to receive streaming data from outside of the FPGA.
StreamIns may not be written to. For streaming outputs, use :doc:`StreamOut <streamout>`.
StreamIns are specified using a :doc:`Bits <../../typeclasses/bits>` - based type and a target :doc:`Bus <bus>`.

In Spatial, StreamIns are specified outside the Accel block, in host code.


-----------------

**Static methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **StreamIn**                                                                                                                                             |
+==========+==========================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(bus\: :doc:`Bus <bus>`\)\: :doc:`StreamIn <streamin>`\[T\]   |
| |            Creates a StreamIn of type T connected to the specified **bus** pins.                                                                                  |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------+


-------------

**Infix methods**

+----------+----------------------------------------------------------------------+
| class      **StreamIn**\[T\]                                                    |
+==========+======================================================================+
| |    def   **value**\: T                                                        |
| |            Returns the current value of this StreamIn.                        |
| |            Equivalent in hardware to connecting a wire to the StreamIn's bus. |
+----------+----------------------------------------------------------------------+



--------------

**Implicit methods**

+-----------+-----------------------------------------------------------------------+
| |     def   **readStream**\[T\]\(stream\: :doc:`StreamIn <streamin>`\[T\]\)\: T   |
| |             Implicitly creates a wire connecting to the StreamIn's bus.         |
+-----------+-----------------------------------------------------------------------+

