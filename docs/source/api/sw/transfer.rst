
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

.. _Transfer:

Transfer Operations
===================

These operations are used to transfer scalar values and arrays between the CPU host and the hardware accelerator.
They must be specified explicitly in the host code (not in Accel scopes).


**Methods**

+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **setArg**\[T\]\(reg\: Reg\[T\], value\: T\)\: :doc:`Unit <../common/unit>`                                                                                                                                     |
| |             Transfer a scalar value from the host to the accelerator through the register **reg**.                                                                                                                        |
| |             **reg** should be allocated as the HostIO or ArgIn methods.                                                                                                                                                   |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **getArg**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(reg\: Reg\[T\]\)\: T                                                                                                     |
| |             Transfer a scalar value from the accelerator to the host through the register **reg**.                                                                                                                        |
| |             **reg** should be allocated using the HostIO or ArgIn methods.                                                                                                                                                |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **setMem**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM <../hw/offchip/dram>`\[T\], data\: :doc:`Array <array>`\[T\]\)\: :doc:`Unit <../common/unit>`          |
| |             Transfers the given :doc:`Array <array>` of **data** from the host's memory to **dram**'s region of accelerator DRAM.                                                                                         |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **getMem**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM <../hw/offchip/dram>`\[T\]\)\: :doc:`Array <array>`\[T\]                                               |
| |             Transfers **dram**'s region of accelerator DRAM to the host's memory and returns the result as an :doc:`Array <array>`.                                                                                       |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **setMem**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM <../hw/offchip/dram>`\[T\], data\: :doc:`Matrix <matrix>`\[T\]\)\: :doc:`Unit <../common/unit>`        |
| |             Transfers the given :doc:`Matrix <matrix>` of **data** from the host's memory to **dram**'s region of accelerator DRAM.                                                                                       |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **getMatrix**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM2 <../hw/offchip/dram>`\[T\]\)\(implicit ctx\: SrcCtx\)\: :doc:`Matrix <matrix>`\[T\]                |
| |             Transfers **dram**'s region of accelerator DRAM to the host's memory and returns the result as a :doc:`Matrix <matrix>`.                                                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **setMem**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM <../hw/offchip/dram>`\[T\], tensor3\: :doc:`Tensor3 <tensor>`\[T\]\)\: :doc:`Unit <../common/unit>`    |
| |             Transfers the given Tensor3 of **data** from the host's memory to **dram**'s region of accelerator DRAM.                                                                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **getTensor3**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM3 <../hw/offchip/dram>`\[T\]\)\(implicit ctx\: SrcCtx\)\: :doc:`Tensor3 <tensor>`\[T\]              |
| |             Transfers **dram**'s region of accelerator DRAM to the host's memory and returns the result as a Tensor3.                                                                                                     |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **setMem**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM <../hw/offchip/dram>`\[T\], tensor4\: :doc:`Tensor4 <tensor>`\[T\]\)\: :doc:`Unit <../common/unit>`    |
| |             Transfers the given Tensor4 of **data** from the host's memory to **dram**'s region of accelerator DRAM.                                                                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **getTensor4**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM4 <../hw/offchip/dram>`\[T\]\)\(implicit ctx\: SrcCtx\)\: :doc:`Tensor4 <tensor>`\[T\]              |
| |             Transfers **dram**'s region of accelerator DRAM to the host's memory and returns the result as a Tensor4.                                                                                                     |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **setMem**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM <../hw/offchip/dram>`\[T\], tensor5\: :doc:`Tensor5 <tensor>`\[T\]\)\: :doc:`Unit <../common/unit>`    |
| |             Transfers the given Tensor5 of **data** from the host's memory to **dram**'s region of accelerator DRAM.                                                                                                      |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| |     def   **getTensor5**\[T\::doc:`Type <../typeclasses/type>`\::doc:`Bits <../typeclasses/bits>`\]\(dram\: :doc:`DRAM5 <../hw/offchip/dram>`\[T\]\)\(implicit ctx\: SrcCtx\)\: :doc:`Tensor5 <tensor>`\[T\]              |
| |             Transfers **dram**'s region of accelerator DRAM to the host's memory and returns the result as a Tensor5.                                                                                                     |
+-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


