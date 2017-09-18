
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


**BufferedOut** defines an addressable output stream from the accelerator, implemented in hardware as an SRAM buffer
which is asynchronously (and implicitly) used to drive a set of output pins.

BufferedOut is similar in functionality to :doc:`streamout`, but allows address and frame-based writing.
This is useful, for example, in video processing, when video needs to be processed on a frame-by-frame basis rather
than a pixel-by-pixel basis.

In Spatial, BufferedOuts are specified outside the Accel block, in host code.


-----------------

**Static methods**

+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| object     **BufferedOut**                                                                                                                                                      |
+==========+======================================================================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\(bus\: :doc:`Bus <bus>`\)\: :doc:`BufferedOut <bufferedout>`\[T\]   |
| |            Creates a BufferedOut of type T connected to the specified bus.                                                                                                    |
| |            The size of the buffer is currently fixed at 240 x 320 elements.                                                                                                   |
+----------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------+



-------------

**Infix methods**

+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
| class      **BufferedOut**\[T\]                                                                                                                                   |
+==========+========================================================================================================================================================+
| |    def   **update**\(row\: :doc:`Index <../../../common/fixpt>`, col\: :doc:`Index <../../../common/fixpt>`, data\: T\)\: :doc:`Unit <../../../common/unit>`    |
| |            Write **data** to the given two dimensional address.                                                                                                 |
+----------+--------------------------------------------------------------------------------------------------------------------------------------------------------+

