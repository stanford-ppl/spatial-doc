
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

@table-start
object Reg

  /** Creates a register of type T with a reset value of zero. **/
  @api def apply[T:Type:Bits]: Reg[T] = Reg(Reg.alloc[T](unwrap(implicitly[Bits[T]].zero)))
  /** Creates a register of type T with the given `reset` value. **/
  @api def apply[T:Type:Bits](reset: T): Reg[T] = Reg(Reg.alloc[T](unwrap(reset)))

@table-end


@table-start
object ArgIn

  /** Creates an input argument register of type T with a reset value of zero. **/
  @api def apply[T:Type:Bits]: Reg[T] = Reg(alloc[T](unwrap(implicitly[Bits[T]].zero)))

@table-end


@table-start
object ArgOut

  /** Creates an output argument register of type T with a reset value of zero. **/
  @api def apply[T:Type:Bits]: Reg[T] = Reg(alloc[T](unwrap(implicitly[Bits[T]].zero)))

@table-end


@table-start
object HostIO

  /** Creates a host I/O register of type T with a reset value of zero. **/
  @api def apply[T:Type:Bits]: Reg[T] = Reg(alloc[T](unwrap(implicitly[Bits[T]].zero)))

@table-end



-------------

**Infix methods**

@table-start
class Reg[T]

  /** Returns the value currently held by this register. **/
  @api def value: T

  /** Writes the given `data` to this register. **/
  @api def :=(data: T): MUnit

  /** Resets the value of this register back to its reset value. **/
  @api def reset: MUnit

  /** Conditionally resets the value of this register back to its reset value if **cond** is true. **/
  @api def reset(cond: Bit): MUnit

@table-end

--------------

**Implicit methods**

@table-start
NoHeading

  /** Implicitly reads the value of this register. **/
  @api def readReg[T](reg: Reg[T]): T

@table-end
