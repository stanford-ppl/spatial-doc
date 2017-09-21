
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


**StreamOut** defines a hardware bus used to output streaming data from the FPGA.
StreamOuts may not be read from. For streaming inputs, use :doc:`StreamIn <streamin>`.
StreamOuts are specified using a :doc:`Bits <../../typeclasses/bits>` - based type and a target :doc:`Bus <bus>`.

In Spatial, StreamOuts are specified outside the Accel block, in host code.


-----------------

**Static methods**

+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **StreamOut**                                                                                                                                              |
+==========+============================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../typeclasses/type>`\::doc:`Bits <../../typeclasses/bits>`\]\(bus\: :doc:`Bus <bus>`\)\: :doc:`StreamOut <streamout>`\[T\]   |
| |            Creates a StreamOut of type T connected to the specified target bus pins.                                                                                |
+----------+------------------------------------------------------------------------------------------------------------------------------------------------------------+



-------------

**Infix methods**

+----------+----------------------------------------------------------------------------------------------+
| class      **StreamOut**\[T\]                                                                           |
+==========+==============================================================================================+
| |    def   **\:=**\(data\: T\)\: :doc:`Unit <../../common/unit>`                                        |
| |            Connect the given **data** to this StreamOut.                                              |
+----------+----------------------------------------------------------------------------------------------+
| |    def   **\:=**\(data\: T, en\: :doc:`Bit <../../common/bit>`\)\: :doc:`Unit <../../common/unit>`    |
| |            Connect the given **data** to this StreamOut with enable **en**.                           |
+----------+----------------------------------------------------------------------------------------------+


