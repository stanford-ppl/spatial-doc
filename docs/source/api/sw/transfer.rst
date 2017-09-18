
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

@table-start
NoHeading

 /**
    * Transfer a scalar value from the host to the accelerator through the register `reg`.
    * `reg` should be allocated as the HostIO or ArgIn methods.
    */
  @api def setArg[T](reg: Reg[T], value: T): MUnit = {


  /**
    * Transfer a scalar value from the accelerator to the host through the register `reg`.
    * `reg` should be allocated using the HostIO or ArgIn methods.
    */
  @api def getArg[T:Type:Bits](reg: Reg[T]): T = wrap(get_arg(reg.s))

  /** Transfers the given @Array of `data` from the host's memory to `dram`'s region of accelerator DRAM. **/
  @api def setMem[T:Type:Bits](dram: DRAM[T], data: MArray[T]): MUnit = wrap(set_mem(dram.s, data.s))
  /** Transfers `dram`'s region of accelerator DRAM to the host's memory and returns the result as an @Array. **/
  @api def getMem[T:Type:Bits](dram: DRAM[T]): MArray[T] = {


  /** Transfers the given @Matrix of `data` from the host's memory to `dram`'s region of accelerator DRAM. **/
  @api def setMem[T:Type:Bits](dram: DRAM[T], data: Matrix[T]): MUnit = setMem(dram, data.data)
  /** Transfers `dram`'s region of accelerator DRAM to the host's memory and returns the result as a @Matrix. **/
  @api def getMatrix[T:Type:Bits](dram: DRAM2[T])(implicit ctx: SrcCtx): Matrix[T] = {

  /** Transfers the given Tensor3 of `data` from the host's memory to `dram`'s region of accelerator DRAM. **/
  @api def setMem[T:Type:Bits](dram: DRAM[T], tensor3: Tensor3[T]): MUnit = setMem(dram, tensor3.data)
  /** Transfers `dram`'s region of accelerator DRAM to the host's memory and returns the result as a Tensor3. **/
  @api def getTensor3[T:Type:Bits](dram: DRAM3[T])(implicit ctx: SrcCtx): Tensor3[T] = {

  /** Transfers the given Tensor4 of `data` from the host's memory to `dram`'s region of accelerator DRAM. **/
  @api def setMem[T:Type:Bits](dram: DRAM[T], tensor4: Tensor4[T]): MUnit = setMem(dram, tensor4.data)
  /** Transfers `dram`'s region of accelerator DRAM to the host's memory and returns the result as a Tensor4. **/
  @api def getTensor4[T:Type:Bits](dram: DRAM4[T])(implicit ctx: SrcCtx): Tensor4[T] = {

  /** Transfers the given Tensor5 of `data` from the host's memory to `dram`'s region of accelerator DRAM. **/
  @api def setMem[T:Type:Bits](dram: DRAM[T], tensor5: Tensor5[T]): MUnit = setMem(dram, tensor5.data)
  /** Transfers `dram`'s region of accelerator DRAM to the host's memory and returns the result as a Tensor5. **/
  @api def getTensor5[T:Type:Bits](dram: DRAM5[T])(implicit ctx: SrcCtx): Tensor5[T] = {

@table-end

