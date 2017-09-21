
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

.. _Debug:

Debugging Operations
====================

These operations are available for use on the CPU and during simulation to aid runtime debugging.


------------------

**Methods**

@table-start
NoHeading

  /** Checks that the given condition `cond` is true at runtime.
    * If not, exits the program with the given message.
    */
  @api def assert(cond: MBoolean, msg: MString): MUnit = MUnit(AssertOps.assert(cond.s, Some(msg.s)))

  /** Checks that the given condition `cond` is true at runtime.
    * If not, exits the program with a generic exception.
    */
  @api def assert(cond: MBoolean): MUnit = MUnit(AssertOps.assert(cond.s, None))

 /** Prints an empty line to the console. **/
  @api def println(): MUnit = println("")

  /** Prints a String representation of the given value to the console. **/
  @api def print[T:Type](x: T): MUnit = MUnit(PrintOps.print(x.toText.s))
  /** Prints a String representation of the given value to the console followed by a linebreak. **/
  @api def println[T:Type](x: T): MUnit = MUnit(PrintOps.println(x.toText.s))

  /** Prints the given String to the console. **/
  @api def print(x: String): MUnit = print(MString(x))
  /** Prints the given String to the console, followed by a linebreak. **/
  @api def println(x: String): MUnit = println(MString(x))


 /** Prints the given Array to the console, preceded by an optional heading. **/
  @api def printArray[T:Type](array: MArray[T], heading: MString = ""): MUnit

  /** Prints the given Matrix to the console, preceded by an optional heading. **/
  @api def printMatrix[T:Type](matrix: Matrix[T], heading: MString = ""): MUnit

  /** Prints the given Tensor3 to the console, preceded by an optional heading. **/
  @api def printTensor3[T:Type](tensor: Tensor3[T], heading: MString = ""): MUnit

  /** Prints the given Tensor4 to the console, preceded by the an optional heading. **/
  @api def printTensor4[T:Type](tensor: Tensor4[T], heading: MString = ""): MUnit

  /** Prints the given Tensor5 to the console, preceded by the an optional heading. **/
  @api def printTensor5[T:Type](tensor: Tensor5[T], heading: MString = ""): MUnit

@table-end
