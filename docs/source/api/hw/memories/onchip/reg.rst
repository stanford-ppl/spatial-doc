
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

.. _Reg:

Reg
===


**Reg** defines a hardware register used to hold a scalar value.
The default reset value for a Reg is the numeric zero value for it's specified type.

**ArgIn**, **ArgOut**, and **HostIO** are specialized forms of Reg which are used to transfer scalar values
to and from the accelerator. ArgIns and ArgOuts are used for setup values at the initialization of the FPGA.
ArgIns may not be written to, while ArgOuts generally should not be read from.
HostIOs are for values which may be continuously changed or read by the host during FPGA execution.

In Spatial, ArgIns, ArgOuts, and HostIO registers are specified outside the Accel block, in host code.


-----------------

**Static methods**

+----------+-----------------------------------------------------------------------------------------------------------------------------+
| object     **Reg**                                                                                                                     |
+==========+=============================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\: Reg\[T\]                 |
| |            Creates a register of type T with a reset value of zero.                                                                  |
+----------+-----------------------------------------------------------------------------------------------------------------------------+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\(reset\: T\)\: Reg\[T\]    |
| |            Creates a register of type T with the given **reset** value.                                                              |
+----------+-----------------------------------------------------------------------------------------------------------------------------+



+----------+----------------------------------------------------------------------------------------------------------------+
| object     **ArgIn**                                                                                                      |
+==========+================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\: Reg\[T\]    |
| |            Creates an input argument register of type T with a reset value of zero.                                     |
+----------+----------------------------------------------------------------------------------------------------------------+



+----------+----------------------------------------------------------------------------------------------------------------+
| object     **ArgOut**                                                                                                     |
+==========+================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\: Reg\[T\]    |
| |            Creates an output argument register of type T with a reset value of zero.                                    |
+----------+----------------------------------------------------------------------------------------------------------------+



+----------+----------------------------------------------------------------------------------------------------------------+
| object     **HostIO**                                                                                                     |
+==========+================================================================================================================+
| |    def   **apply**\[T\::doc:`Type <../../../typeclasses/type>`\::doc:`Bits <../../../typeclasses/bits>`\]\: Reg\[T\]    |
| |            Creates a host I/O register of type T with a reset value of zero.                                            |
+----------+----------------------------------------------------------------------------------------------------------------+




-------------

**Infix methods**

+----------+------------------------------------------------------------------------------------------------+
| class      **Reg**\[T\]                                                                                   |
+==========+================================================================================================+
| |    def   **value**\: T                                                                                  |
| |            Returns the value currently held by this register.                                           |
+----------+------------------------------------------------------------------------------------------------+
| |    def   **\:=**\(data\: T\)\: :doc:`Unit <../../../common/unit>`                                       |
| |            Writes the given **data** to this register.                                                  |
+----------+------------------------------------------------------------------------------------------------+
| |    def   **reset**\: :doc:`Unit <../../../common/unit>`                                                 |
| |            Resets the value of this register back to its reset value.                                   |
+----------+------------------------------------------------------------------------------------------------+
| |    def   **reset**\(cond\: :doc:`Bit <../../../common/bit>`\)\: :doc:`Unit <../../../common/unit>`      |
| |            Conditionally resets the value of this register back to its reset value if **cond** is true. |
+----------+------------------------------------------------------------------------------------------------+


--------------

**Implicit methods**

+-----------+------------------------------------------------+
| |     def   **readReg**\[T\]\(reg\: Reg\[T\]\)\: T         |
| |             Implicitly reads the value of this register. |
+-----------+------------------------------------------------+

